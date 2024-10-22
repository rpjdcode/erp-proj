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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_ROOT)
public class RootController implements Initializable {

	private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

	@FXML
	private Menu editMenu;

	@FXML
	private Menu fileMenu;

	@FXML
	private Menu helpMenu;

	@FXML
	private Menu databaseMenu;
	
	@FXML
	private Menu testingMenu;
	
	@FXML
	private MenuItem testingMenuItem;

	@FXML
	private MenuBar menuBar;

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
	

	@Autowired
	public RootController(UserService userService, ApplicationContext context) {
		this.userService = userService;
		this.context = context;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando controlador raíz");
	}

	public GridPane getView() {
		return view;
	}

	/**
	 * Método que se llama al hacer click en el botón "Acerca de " de la toolbar
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
	
	@FXML
	void onTestingAction(ActionEvent event) {
		//
		LOG.info("Testing");
		try {
			SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
			loader.load("/fxml/testing/testing.fxml", SpringConstants.BEAN_CONTROLLER_TESTING);
			TestingController testingController = context.getBean(TestingController.class);
			
			contentBox.getChildren().add(testingController.getView());
			VBox.setVgrow(testingController.getView(), Priority.ALWAYS);
			
		} catch (IOException e) {
			LOG.error("Error lanzado", e);
		}
		
		
	}

}
