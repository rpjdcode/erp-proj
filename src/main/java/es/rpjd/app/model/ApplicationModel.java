package es.rpjd.app.model;

import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

/**
 * Clase que representa un modelo FX genérico de la aplicación
 */
public class ApplicationModel {

	private BooleanProperty editionMode;

	/**
	 * Listener que permanece a la escucha de cambios de internacionalización
	 */
	private ChangeListener<ResourceBundle> i18nListener;

	public ApplicationModel() {
		editionMode = new SimpleBooleanProperty(false);
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

}
