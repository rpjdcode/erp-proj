package es.rpjd.app.model;

import es.rpjd.app.hibernate.entity.Order;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderModel extends ApplicationModel {

	private ListProperty<Order> items;

	public OrderModel() {
		super();
		items = new SimpleListProperty<>(FXCollections.observableArrayList());
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

}
