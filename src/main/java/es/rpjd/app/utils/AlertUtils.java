package es.rpjd.app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Clase que proporciona métodos estáticos para creación de alertas
 */
public class AlertUtils {

	private AlertUtils() {}
	
	public static Alert generateAppAlert(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}
}
