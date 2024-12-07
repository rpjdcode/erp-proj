package es.rpjd.app.components;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductOrder;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.TilesUtils;
import eu.hansolo.tilesfx.Tile;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

@Component(value = SpringConstants.BEAN_COMPONENT_ORDER_SELECTOR)
public class OrderSelectorComponent implements Initializable {

    @FXML
    private FlowPane view;

	private ObjectProperty<Order> order;
	private ObjectProperty<Product> product;
	private ListProperty<ProductOrder> requestedProducts;

	@Autowired
	public OrderSelectorComponent() {
		order = new SimpleObjectProperty<>();
		product = new SimpleObjectProperty<>();
		requestedProducts = new SimpleListProperty<>(FXCollections.observableArrayList());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int x = 0; x < 100; x++) {
			Tile ti = TilesUtils.createImageTile(null, String.format("Ejemplo de tile %d", x));
			view.getChildren().add(ti);
		}

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

	public final ListProperty<ProductOrder> requestedProductsProperty() {
		return this.requestedProducts;
	}

	public final ObservableList<ProductOrder> getRequestedProducts() {
		return this.requestedProductsProperty().get();
	}

	public final void setRequestedProducts(final ObservableList<ProductOrder> requestedProducts) {
		this.requestedProductsProperty().set(requestedProducts);
	}
	
	public FlowPane getView() {
		return view;
	}

}
