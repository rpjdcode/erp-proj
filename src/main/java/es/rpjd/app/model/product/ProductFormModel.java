package es.rpjd.app.model.product;

import java.math.BigDecimal;

import es.rpjd.app.enums.FormOperationType;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.ApplicationModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductFormModel extends ApplicationModel {

	private LongProperty id;
	private StringProperty code;
	private StringProperty name;
	private ObjectProperty<BigDecimal> price;
	private ObjectProperty<ProductType> productType;
	private ListProperty<ProductType> types;
	private ObjectProperty<FormOperationType> operationMode;

	public ProductFormModel() {
		super();
		id = new SimpleLongProperty();
		code = new SimpleStringProperty();
		name = new SimpleStringProperty();
		price = new SimpleObjectProperty<>();
		productType = new SimpleObjectProperty<>();
		types = new SimpleListProperty<>(FXCollections.observableArrayList());
		operationMode = new SimpleObjectProperty<>(FormOperationType.CREATE);
	}

	public final StringProperty codeProperty() {
		return this.code;
	}

	public final String getCode() {
		return this.codeProperty().get();
	}

	public final void setCode(final String code) {
		this.codeProperty().set(code);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final ObjectProperty<BigDecimal> priceProperty() {
		return this.price;
	}

	public final BigDecimal getPrice() {
		return this.priceProperty().get();
	}

	public final void setPrice(final BigDecimal price) {
		this.priceProperty().set(price);
	}

	public final ObjectProperty<ProductType> productTypeProperty() {
		return this.productType;
	}

	public final ProductType getProductType() {
		return this.productTypeProperty().get();
	}

	public final void setProductType(final ProductType productType) {
		this.productTypeProperty().set(productType);
	}

	public final ListProperty<ProductType> typesProperty() {
		return this.types;
	}

	public final ObservableList<ProductType> getTypes() {
		return this.typesProperty().get();
	}

	public final void setTypes(final ObservableList<ProductType> types) {
		this.typesProperty().set(types);
	}

	public final ObjectProperty<FormOperationType> operationModeProperty() {
		return this.operationMode;
	}

	public final FormOperationType getOperationMode() {
		return this.operationModeProperty().get();
	}

	public final void setOperationMode(final FormOperationType operationMode) {
		this.operationModeProperty().set(operationMode);
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

}
