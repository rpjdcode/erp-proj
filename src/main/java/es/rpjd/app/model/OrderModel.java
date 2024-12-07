package es.rpjd.app.model;

import es.rpjd.app.hibernate.entity.Order;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderModel extends ApplicationModel {

	private ListProperty<Order> items;
	private ObjectProperty<Order> selectedOrder;

	public OrderModel() {
		super();
		items = new SimpleListProperty<>(FXCollections.observableArrayList());
		selectedOrder = new SimpleObjectProperty<>();
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

}
