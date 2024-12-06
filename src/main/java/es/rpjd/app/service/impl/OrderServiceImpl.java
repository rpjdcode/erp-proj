package es.rpjd.app.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.Order;
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
	
	@Override
	public DBResponseModel<List<Order>> getOrders() {
		Session session = sessionFactory.getCurrentSession();
		List<Order> orders = session.createQuery("FROM Order", Order.class).list();
		LOG.info("Comandas obtenidas de bbdd: {}", orders);
		return new DBResponseModel<>(DBResponseStatus.OK, "Comandas obtenidas", orders);
	}

	@Override
	public DBResponseModel<Order> save(Order order) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(order);
		return new DBResponseModel<>(DBResponseStatus.OK, "Comanda insertada", order);
	}

	@Override
	public DBResponseModel<Order> modify(Order order) {
		Session session = sessionFactory.getCurrentSession();
		return null;
	}

	@Override
	public DBResponseModel<Order> delete(Order order) {
		Session session = sessionFactory.getCurrentSession();
		return null;
	}

}
