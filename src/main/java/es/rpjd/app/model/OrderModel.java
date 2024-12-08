package es.rpjd.app.model;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.ProductOrder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderModel extends ApplicationModel {

	private ListProperty<Order> items;
	private ObjectProperty<Order> selectedOrder;
	private ListProperty<ProductOrder> selectedOrderRequests;

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

	public final ListProperty<ProductOrder> selectedOrderRequestsProperty() {
		return this.selectedOrderRequests;
	}

	public final ObservableList<ProductOrder> getSelectedOrderRequests() {
		return this.selectedOrderRequestsProperty().get();
	}

	public final void setSelectedOrderRequests(final ObservableList<ProductOrder> selectedOrderRequests) {
		this.selectedOrderRequestsProperty().set(selectedOrderRequests);
	}

}
