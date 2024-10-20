package es.rpjd.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.i18n.I18N;
import es.rpjd.app.service.UserService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

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
	private MenuBar menuBar;

	@FXML
	private ToolBar footToolBar;

	@FXML
	private Button aboutAppButton;

	@FXML
	private GridPane view;

	private UserService userService;

	@Autowired
	public RootController(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public GridPane getView() {
		return view;
	}

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

}
