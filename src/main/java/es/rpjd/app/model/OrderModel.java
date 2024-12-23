package es.rpjd.app.model;

import java.util.Optional;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.model.observables.ProductOrderObservable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderModel extends ApplicationModel {

	private ListProperty<Order> items;
	private ObjectProperty<Order> selectedOrder;
	private ListProperty<ProductOrderObservable> selectedOrderRequests;

	public OrderModel() {
		super();
		items = new SimpleListProperty<>(FXCollections.observableArrayList());
		selectedOrder = new SimpleObjectProperty<>();
		selectedOrderRequests = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public final ListProperty<Order> itemsProperty() {
		return this.items;
	}

	public final ObservableList<Order> getItems() {
		return this.itemsProperty().get();
	}

	public final void setItems(final ObservableList<Order> items) {
		this.itemsProperty().set(items);
	}

	public final ObjectProperty<Order> selectedOrderProperty() {
		return this.selectedOrder;
	}

	public final Order getSelectedOrder() {
		return this.selectedOrderProperty().get();
	}

	public final void setSelectedOrder(final Order selectedOrder) {
		this.selectedOrderProperty().set(selectedOrder);
	}

	public final ListProperty<ProductOrderObservable> selectedOrderRequestsProperty() {
		return this.selectedOrderRequests;
	}

	public final ObservableList<ProductOrderObservable> getSelectedOrderRequests() {
		return this.selectedOrderRequestsProperty().get();
	}

	public final void setSelectedOrderRequests(final ObservableList<ProductOrderObservable> selectedOrderRequests) {
		this.selectedOrderRequestsProperty().set(selectedOrderRequests);
	}
	
	public final boolean requestsContainsProduct(String productCode) {
		return selectedOrderRequests.stream().anyMatch(po -> productCode.equals(po.getProduct().getProductCode()));
	}
	
	public final Optional<ProductOrderObservable> findProductInRequests(String productCode) {
		return selectedOrderRequests.stream().filter(po -> productCode.equals(po.getProduct().getProductCode())).findFirst();
	}

}
