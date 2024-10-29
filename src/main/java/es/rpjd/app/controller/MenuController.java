package es.rpjd.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_MENU)
public class MenuController implements Initializable {

	private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

	@FXML
	private Button configButton;

	@FXML
	private Button homeButton;

	@FXML
	private Button productButton;

	@FXML
	private Button testingButton;

	@FXML
	private HBox view;

	private ApplicationContext context;
	private Environment env;
	private RootController root;

	@Autowired
	public MenuController(ApplicationContext context, Environment env) {
		this.context = context;
		this.root = this.context.getBean(RootController.class);
		this.env = env;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void onTestingAction(ActionEvent event) {
		loadApplicationContent(SpringConstants.BEAN_CONTROLLER_TESTING);

//		try {
//			SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
//			loader.load("/fxml/testing/testing.fxml", SpringConstants.BEAN_CONTROLLER_TESTING);
//			TestingController testingController = context.getBean(TestingController.class);
//			this.root.addRootContent(testingController.getView());
//			
//		} catch (IOException e) {
//			LOG.error("Error lanzado", e);
//		}
	}

	@FXML
	void onHomeAction(ActionEvent event) {
		LOG.info("Click en home");
		loadApplicationContent(SpringConstants.BEAN_CONTROLLER_HOME);
	}

	public HBox getView() {
		return view;
	}

	/**
	 * Método encargado de cargar el contenido principal de la aplicación en el
	 * RootController
	 * 
	 * @param beanController Nombre del Bean del Controlador a cargar
	 * @see {@link ApplicationController}
	 * @see {@link SpringConstants}
	 */
	private void loadApplicationContent(String beanController) {
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		String fxmlPath;
		ApplicationController controller;

		try {
			switch (beanController) {
			case SpringConstants.BEAN_CONTROLLER_TESTING:
				fxmlPath = String.format("%s%s", env.getProperty("path.fxml"), "testing/testing.fxml");
				controller = context.getBean(TestingController.class);
				break;

			case SpringConstants.BEAN_CONTROLLER_HOME:
				fxmlPath = null;
				this.root.unloadControllerContent();
				controller = null;
				break;

			default:
				fxmlPath = null;
				controller = null;
				break;
			}

			if (fxmlPath != null && controller != null) {
				loader.load(fxmlPath, beanController);
				this.root.loadControllerContent(controller);
			}

		} catch (IOException e) {
			LOG.error("Se ha producido la siguiente excepción al cargar el contenido de la aplicación: {0}",
					e.getCause());
		}
	}

}
