package es.rpjd.app.model;

import java.util.ResourceBundle;

import es.rpjd.app.model.observables.ProductOrderObservable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Clase que representa un modelo FX genérico de la aplicación
 */
public class ApplicationModel {

	private BooleanProperty editionMode;
	private ListProperty<ProductOrderObservable> unmodifiedData;

	/**
	 * Listener que permanece a la escucha de cambios de internacionalización
	 */
	private ChangeListener<ResourceBundle> i18nListener;

	public ApplicationModel() {
		editionMode = new SimpleBooleanProperty(false);
		unmodifiedData = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public ChangeListener<ResourceBundle> getI18nListener() {
		return i18nListener;
	}

	public void setI18nListener(ChangeListener<ResourceBundle> i18nListener) {
		this.i18nListener = i18nListener;
	}

	public final BooleanProperty editionModeProperty() {
		return this.editionMode;
	}

	public final boolean isEditionMode() {
		return this.editionModeProperty().get();
	}

	public final void setEditionMode(final boolean editionMode) {
		this.editionModeProperty().set(editionMode);
	}

	public final ListProperty<ProductOrderObservable> unmodifiedDataProperty() {
		return this.unmodifiedData;
	}

	public final ObservableList<ProductOrderObservable> getUnmodifiedData() {
		return this.unmodifiedDataProperty().get();
	}

	public final void setUnmodifiedData(final ObservableList<ProductOrderObservable> unmodifiedData) {
		this.unmodifiedDataProperty().set(unmodifiedData);
	}

}
