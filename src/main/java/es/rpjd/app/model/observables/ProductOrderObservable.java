package es.rpjd.app.model.observables;

import java.util.List;
import java.util.Objects;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductOrder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class ProductOrderObservable {
	
	public static ListProperty<ProductOrderObservable> productOrdersToObservableList(List<ProductOrder> list) {
		ListProperty<ProductOrderObservable> out = new SimpleListProperty<>(FXCollections.observableArrayList());
		out.addAll(list.stream().map(ProductOrderObservable::new).toList());
		
		return out;
	}

	private LongProperty id;
	private ObjectProperty<Product> product;
	private ObjectProperty<Order> order;
	private IntegerProperty quantity;

	public ProductOrderObservable(ProductOrder po) {
		id = new SimpleLongProperty(po.getId());
		product = new SimpleObjectProperty<>(po.getProduct());
		order = new SimpleObjectProperty<>(po.getOrder());
		quantity = new SimpleIntegerProperty(po.getQuantity());
	}
	
	public ProductOrderObservable(ProductOrderObservable po) {
		id = new SimpleLongProperty(po.id.get());
		product = new SimpleObjectProperty<>(po.product.get());
		order = new SimpleObjectProperty<>(po.order.get());
		quantity = new SimpleIntegerProperty(po.quantity.get());
	}

	public final LongProperty idProperty() {
		return this.id;
	}

	public final long getId() {
		return this.idProperty().get();
	}

	public final void setId(final long id) {
		this.idProperty().set(id);
	}

	public final ObjectProperty<Product> productProperty() {
		return this.product;
	}

	public final Product getProduct() {
		return this.productProperty().get();
	}

	public final void setProduct(final Product product) {
		this.productProperty().set(product);
	}

	public final ObjectProperty<Order> orderProperty() {
		return this.order;
	}

	public final Order getOrder() {
		return this.orderProperty().get();
	}

	public final void setOrder(final Order order) {
		this.orderProperty().set(order);
	}

	public final IntegerProperty quantityProperty() {
		return this.quantity;
	}

	public final int getQuantity() {
		return this.quantityProperty().get();
	}

	public final void setQuantity(final int quantity) {
		this.quantityProperty().set(quantity);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(id.get(), quantity.get());
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (!(obj instanceof ProductOrderObservable)) {
	        return false;
	    }
	    ProductOrderObservable other = (ProductOrderObservable) obj;
	    return Objects.equals(id.get(), other.id.get()) && 
	           Objects.equals(quantity.get(), other.quantity.get());
	}

	@Override
	public String toString() {
		return "ProductOrderObservable [id=" + id.get() + ", product=" + product.get() + ", order=" + order.get() + ", quantity="
				+ quantity.get() + "]";
	}
	
	

}
