package es.rpjd.app.hibernate;

import java.util.ArrayList;
import java.util.List;

import es.rpjd.app.hibernate.entity.ProductOrder;
import es.rpjd.app.model.observables.ProductOrderObservable;
import javafx.collections.ObservableList;

public class HibernateAdapter {
	
	private HibernateAdapter() {}

	public static List<ProductOrder> observableListToProductOrder(ObservableList<ProductOrderObservable> list) {
		List<ProductOrder> ret = new ArrayList<>();
		
		list.stream().forEach(productOrderObservable -> {
			ProductOrder po = new ProductOrder();
			po.setId(productOrderObservable.getId());
			po.setOrder(productOrderObservable.getOrder());
			po.setProduct(productOrderObservable.getProduct());
			po.setQuantity(productOrderObservable.getQuantity());
			ret.add(po);
		});
		
		return ret;
	}
}
