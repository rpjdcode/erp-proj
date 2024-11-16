package es.rpjd.app.model;

import es.rpjd.app.enums.ProductOptions;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class ProductModel extends ApplicationModel{

	public ProductModel() {
		super();
		selectedLabel = new SimpleObjectProperty<>();
		productsOption = new SimpleObjectProperty<>(ProductOptions.NONE);
	}

	private ObjectProperty<Label> selectedLabel;
	private ObjectProperty<ProductOptions> productsOption;

	public final ObjectProperty<Label> selectedLabelProperty() {
		return this.selectedLabel;
	}

	public final Label getSelectedLabel() {
		return this.selectedLabelProperty().get();
	}

	public final void setSelectedLabel(final Label selectedLabel) {
		this.selectedLabelProperty().set(selectedLabel);
	}

	public final ObjectProperty<ProductOptions> productsOptionProperty() {
		return this.productsOption;
	}

	public final ProductOptions getProductsOption() {
		return this.productsOptionProperty().get();
	}

	public final void setProductsOption(final ProductOptions productsOption) {
		this.productsOptionProperty().set(productsOption);
	}

}
