package es.rpjd.app.service;

import java.util.List;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.model.DBResponseModel;

public interface OrderService {

	DBResponseModel<List<Order>> getOrders();
	DBResponseModel<Order> save(Order order);
	DBResponseModel<Order> modify(Order order);
	DBResponseModel<Order> delete(Order order);
	
	String generateOrderCode();
}
