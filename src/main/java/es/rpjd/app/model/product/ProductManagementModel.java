package es.rpjd.app.model.product;

import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.model.ApplicationModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductManagementModel extends ApplicationModel {

	private ListProperty<Product> products;

	public ProductManagementModel() {
		super();
		products = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public final ListProperty<Product> productsProperty() {
		return this.products;
	}

	public final ObservableList<Product> getProducts() {
		return this.productsProperty().get();
	}

	public final void setProducts(final ObservableList<Product> products) {
		this.productsProperty().set(products);
	}

}
