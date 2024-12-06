package es.rpjd.app.model.product.type;

import es.rpjd.app.enums.FormOperationType;
import es.rpjd.app.model.ApplicationModel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductTypeFormModel extends ApplicationModel {

	private LongProperty id;
	private StringProperty code;
	private StringProperty name;

	private ObjectProperty<FormOperationType> operationMode;

	public ProductTypeFormModel() {
		super();
		id = new SimpleLongProperty();
		code = new SimpleStringProperty();
		name = new SimpleStringProperty();
		operationMode = new SimpleObjectProperty<>(FormOperationType.CREATE);
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

}
