package es.rpjd.app.model.product.type;

import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.ApplicationModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductTypeManagementModel extends ApplicationModel {

	private ListProperty<ProductType> items;

	public ProductTypeManagementModel() {
		super();
		items = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public final ListProperty<ProductType> itemsProperty() {
		return this.items;
	}

	public final ObservableList<ProductType> getItems() {
		return this.itemsProperty().get();
	}

	public final void setItems(final ObservableList<ProductType> items) {
		this.itemsProperty().set(items);
	}

}
