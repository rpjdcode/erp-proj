package es.rpjd.app.model;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;

/**
 * Clase que representa un modelo FX genérico de la aplicación
 */
public class ApplicationModel {
	
	/**
	 * Listener que permanece a la escucha de cambios de internacionalización
	 */
	private ChangeListener<? super ResourceBundle> i18nListener;
	
	public ChangeListener<? super ResourceBundle> getI18nListener() {
		return i18nListener;
	}
	
	public void setI18nListener(ChangeListener<? super ResourceBundle> i18nListener) {
		this.i18nListener = i18nListener;
	}
}
