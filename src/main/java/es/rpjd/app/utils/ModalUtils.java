package es.rpjd.app.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Clase utilizada para la generación de modales propios de la aplicación
 */
public class ModalUtils {

	private ModalUtils() {}
	
	public static Stage createApplicationModal(Parent root, Window owner, Image icon ,String title) {
		Stage ret = new Stage();
		Scene scn = new Scene(root);
		scn.getStylesheets().add(ModalUtils.class.getResource("/css/test.css").toExternalForm());
		
		
		ret.initStyle(StageStyle.DECORATED);
		ret.initOwner(owner);
		ret.initModality(Modality.APPLICATION_MODAL);
		ret.getIcons().add(icon);
		ret.setTitle(title);
		ret.setScene(scn);
		
		return ret;
	}
}
