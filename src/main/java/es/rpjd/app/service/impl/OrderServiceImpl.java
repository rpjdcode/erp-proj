package es.rpjd.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.sql.SQLQueries;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.service.OrderService;
import es.rpjd.app.spring.SpringConstants;

@Service(value = SpringConstants.BEAN_SERVICE_ORDER)
public class OrderServiceImpl implements OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public OrderServiceImpl(SessionFactory factory) {
		this.sessionFactory = factory;
	}
	
	@Transactional
	@Override
	public DBResponseModel<List<Order>> getOrders() {
		Session session = sessionFactory.getCurrentSession();
		List<Order> orders = session.createQuery("FROM Order", Order.class).list();
		LOG.info("Comandas obtenidas de bbdd: {}", orders);
		return new DBResponseModel<>(DBResponseStatus.OK, "Comandas obtenidas", orders);
	}

	@Transactional
	@Override
	public DBResponseModel<Order> save(Order order) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(order);
		return new DBResponseModel<>(DBResponseStatus.OK, "Comanda insertada", order);
	}

	@Transactional
	@Override
	public DBResponseModel<Order> modify(Order order) {
		Session session = sessionFactory.getCurrentSession();
		return null;
	}

	@Transactional
	@Override
	public DBResponseModel<Order> delete(Order order) {
		Session session = sessionFactory.getCurrentSession();
		session.remove(order);
		return new DBResponseModel<>(DBResponseStatus.OK, "Comanda eliminada correctamente", order);
	}

	@Transactional
	@Override
	public String generateOrderCode() {
		StringBuilder sb = new StringBuilder();
		Session session = sessionFactory.getCurrentSession();
		
		LocalDateTime curDate = LocalDateTime.now();
		sb.append(curDate.getYear());
		sb.append(curDate.getMonthValue() < 10 ? "0".concat(""+curDate.getMonthValue()) : curDate.getMonthValue());
		sb.append(curDate.getDayOfMonth() < 10 ? "0".concat(""+curDate.getDayOfMonth()) : curDate.getDayOfMonth());
		
		Integer ordersQuantity = session.createNativeQuery(SQLQueries.SELECT_ORDERS_QUANTITY_TODAY, Integer.class).getSingleResult();
		ordersQuantity = ordersQuantity+1;
		// De la cantidad recogida, incrementamos en uno ya que se va a generar un nuevo código identificativo
		String quantityStr = String.valueOf(ordersQuantity);
		
		while (sb.length()+quantityStr.length() < 20) {
			sb.append(0);
		}
		sb.append(ordersQuantity);
		
		return sb.toString();
	}

	@Transactional
	@Override
	public DBResponseModel<List<Order>> getOrdersAndInformation() {
		Session session = sessionFactory.getCurrentSession();
		List<Order> orders = session.createQuery("FROM Order", Order.class).list();
		
		for (Order order : orders) {
			Hibernate.initialize(order.getProductsOrder());
		}
		
		return new DBResponseModel<>(DBResponseStatus.OK, "Comandas y datos de comanda obtenidos", orders);
	}

	@Transactional
	@Override
	public DBResponseModel<Order> getOrder(String orderCode) {
		Session session = sessionFactory.getCurrentSession();
		NativeQuery<Order> query = session.createNativeQuery(SQLQueries.SELECT_ORDERS_BY_CODE, Order.class);
		query.setParameter("code", orderCode);
		
		Order order = query.getSingleResultOrNull();
		
		return new DBResponseModel<>(DBResponseStatus.OK, "Consulta realizada", order);
	}

	@Transactional
	@Override
	public DBResponseModel<Order> getOrderInformation(String orderCode) {
		Session session = sessionFactory.getCurrentSession();
		NativeQuery<Order> query = session.createNativeQuery(SQLQueries.SELECT_ORDERS_BY_CODE, Order.class);
		query.setParameter("code", orderCode);
		
		Order order = query.getSingleResultOrNull();
		
		Hibernate.initialize(order.getProductsOrder());
		
		return new DBResponseModel<>(DBResponseStatus.OK, "Comanda y datos de comanda obtenidos", order);
	}

	@Transactional
	@Override
	public DBResponseModel<List<Order>> getUnprocessedOrders() {
		Session session = sessionFactory.getCurrentSession();
		NativeQuery<Order> query = session.createNativeQuery(SQLQueries.SELECT_UNPROCESSED_ORDERS, Order.class);
		List<Order> orders = query.getResultList();
		return new DBResponseModel<>(DBResponseStatus.OK, "Comandas no procesadas obtenidas", orders);
	}

	@Transactional
	@Override
	public DBResponseModel<List<Order>> getProcessedOrders() {
		Session session = sessionFactory.getCurrentSession();
		NativeQuery<Order> query = session.createNativeQuery(SQLQueries.SELECT_PROCESSED_ORDERS, Order.class);
		List<Order> orders = query.getResultList();
		return new DBResponseModel<>(DBResponseStatus.OK, "Comandas procesadas obtenidas", orders);
	}
	
	

}
