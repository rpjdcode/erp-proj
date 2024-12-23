package es.rpjd.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.i18n.I18N;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.AlertUtils;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_ROOT)
public class RootController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @FXML
    private Button aboutAppButton;

    @FXML
    private HBox contentBox;

    @FXML
    private ScrollPane contentScroll;

    @FXML
    private ToolBar footToolBar;

    @FXML
    private HBox menuBarBox;

    @FXML
    private VBox scrollContentBox;

    @FXML
    private HBox toolbarBox;
    
    @FXML
    private HBox customMenuBarBox;

    @FXML
    private GridPane view;

	private ApplicationContext context;
	private Environment env;

	private ApplicationController contentController;

	@Autowired
	public RootController(ApplicationContext context, Environment env) {
		this.context = context;
		this.env = env;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando controlador raíz");

		try {
			SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
			String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "menu/menu.fxml");
			loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_MENU);
			MenuController menu = context.getBean(MenuController.class);

			// Se añade en la primera posición fila del gridpane el menu de la aplicación y
			// se le indica el columnspan y rowspan que abarca
			customMenuBarBox.getChildren().add(menu.getView());
			HBox.setHgrow(menu.getView(), Priority.ALWAYS);
//			// Centrado

		} catch (IOException e) {
			LOG.error("Se ha lanzado una IOException al inicializar controlador raíz", e);
		}

	}

	@Override
	public GridPane getView() {
		return view;
	}

	/**
	 * Método que se llama al hacer click en el botón "Acerca de " de la toolbar
	 * 
	 * @param event
	 */
	@FXML
	void onAboutAppAction(ActionEvent event) {
		String appInfoTitle = I18N.getString("app.tool.button.about");
		String appInfoHeader = I18N.getString("app.alert.appinfo.header");
		String buildVersion = I18N.getString("app.build.version");
		String buildDate = I18N.getString("app.build.date");
		String buildStatus = I18N.getString("app.build.status");
		String content = String.format("BUILD VERSION: %s%nBUILD DATE: %s%nBUILD STATUS: %s", buildVersion, buildDate, buildStatus);

		this.getView().getScene().getWindow();

		Alert alert = AlertUtils.generateAppModalAlert(AlertType.INFORMATION, appInfoTitle, appInfoHeader, content,
				getView().getScene().getWindow());

		alert.showAndWait();
	}

	/**
	 * Método encargado de cargar en el nodo de contenido principal de aplicación y
	 * hacerlo responsivo. Elimina el nodo cargado, en caso de existir uno
	 * 
	 * @param view
	 */
	private void addRootContent(Node view) {
		removeRootContent();

		scrollContentBox.getChildren().add(view);
		
		VBox.setVgrow(view, Priority.ALWAYS);

	}

	/**
	 * Método que es llamado de forma externa (otro controlador) para cargar el
	 * contenido en el nodo de contenido del RootController. Recibe por parámetro un
	 * ApplicationController que referencia al controlador de la aplicación
	 * 
	 * @param controller Controlador cuya vista será mostrada en el content
	 */
	public void loadControllerContent(ApplicationController controller) {
		if (contentController != null) {
			LOG.info("Llamando a liberación de recursos");
			contentController.clearResources();
		}
		addRootContent(controller.getView());
		contentController = controller;
	}
	
	public void unloadControllerContent() {
		LOG.info("Método unloadControllerContent");
		removeRootContent();
		
	}

	private void removeRootContent() {
		if (!scrollContentBox.getChildrenUnmodifiable().isEmpty()) {
			LOG.info("Eliminando contenido de controlador raíz");
			scrollContentBox.getChildren().clear();
		}
	}

	@Override
	public void clearResources() {
		// TODO Pendiente de actualizar los textos durante cambio de idioma en vivo
		LOG.info("Limpiando recursos de controlador raíz");
		
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		throw new UnsupportedOperationException("Operación no permitida. No es necesario realizarlo desde este controlador");
		
	}
}
