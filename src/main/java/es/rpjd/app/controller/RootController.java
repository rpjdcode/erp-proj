package es.rpjd.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import es.rpjd.app.i18n.I18N;
import es.rpjd.app.service.UserService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_ROOT)
public class RootController implements Initializable {

	private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

	@FXML
	private ToolBar footToolBar;

	@FXML
	private Button aboutAppButton;

	@FXML
	private VBox contentBox;

	@FXML
	private GridPane view;

	private ApplicationContext context;
	private UserService userService;

	private ApplicationController contentController;

	@Autowired
	public RootController(UserService userService, ApplicationContext context) {
		this.userService = userService;
		this.context = context;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando controlador raíz");

		try {
			SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
			loader.load("/fxml/menu/menu.fxml", SpringConstants.BEAN_CONTROLLER_MENU);
			MenuController menu = context.getBean(MenuController.class);

			// Se añade en la primera posición fila del gridpane el menu de la aplicación y
			// se le indica el columnspan y rowspan que abarca
			this.getView().add(menu.getView(), 0, 0, 2, 1);

			// Centrado
			GridPane.setHalignment(menu.getView(), HPos.CENTER);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
		String content = String.format("BUILD VERSION: %s%nBUILD DATE: %s", buildVersion, buildDate);

		this.getView().getScene().getWindow();

		Alert alert = AlertUtils.generateAppModalAlert(AlertType.INFORMATION, appInfoTitle, appInfoHeader, content,
				getView().getScene().getWindow());

		alert.showAndWait();
		LOG.info("USUARIOS: {}", userService.getUsers().getData());
	}

	void onTestingAction() {
		//
		LOG.info("Testing");
		try {
			SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
			loader.load("/fxml/testing/testing.fxml", SpringConstants.BEAN_CONTROLLER_TESTING);
			TestingController testingController = context.getBean(TestingController.class);

//			contentBox.getChildren().add(testingController.getView());
//			VBox.setVgrow(testingController.getView(), Priority.ALWAYS);
			addRootContent(testingController.getView());

		} catch (IOException e) {
			LOG.error("Error lanzado", e);
		}

	}

	void onHomeMenuAction() {
		LOG.info("Click en menú inicio");
	}

	/**
	 * Método encargado de cargar en el nodo de contenido principal de aplicación y
	 * hacerlo responsivo. Elimina el nodo cargado, en caso de existir uno
	 * 
	 * @param view
	 */
	private void addRootContent(Node view) {
		removeRootContent();

		contentBox.getChildren().add(view);
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
		removeRootContent();
		
	}

	private void removeRootContent() {
		if (!contentBox.getChildren().isEmpty()) {
			LOG.info("Eliminando contenido");
			contentBox.getChildren().clear();
		}
	}
}
