package es.rpjd.app.service;

import java.util.List;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.model.DBResponseModel;

public interface OrderService {

	DBResponseModel<List<Order>> getOrders();
	DBResponseModel<List<Order>> getUnprocessedOrders();
	DBResponseModel<List<Order>> getProcessedOrders();
	DBResponseModel<List<Order>> getOrdersAndInformation();
	DBResponseModel<Order> getOrder(String orderCode);
	DBResponseModel<Order> getOrderInformation(String orderCode);
	DBResponseModel<Order> save(Order order);
	DBResponseModel<Order> modify(Order order);
	DBResponseModel<Order> delete(Order order);
	
	String generateOrderCode();
}
