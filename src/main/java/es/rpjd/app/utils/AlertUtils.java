package es.rpjd.app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Clase que proporciona métodos estáticos para creación de alertas
 */
public class AlertUtils {

	private AlertUtils() {
	}

	/**
	 * Genera un tipo de alerta de aplicación en modo modal
	 * 
	 * @param type Tipo de alerta (confirmación, información...)
	 * @param title Título del Stage de la alerta
	 * @param header Mensaje de encabezado de alerta
	 * @param content Contenido de la alerta
	 * @param owner Ventana padre de la alerta
	 * @return
	 */
	public static Alert generateAppModalAlert(AlertType type, String title, String header, String content,
			Window owner) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(owner);
		return alert;
	}
}
