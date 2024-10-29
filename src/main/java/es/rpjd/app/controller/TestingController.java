package es.rpjd.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_TESTING)
public class TestingController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(TestingController.class);

	@FXML
	private ListView<String> listView;

	@FXML
	private VBox view;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando controlador de Testing");

	}

	@Override
	public VBox getView() {
		return view;
	}
	
	@Override
	public void clearResources() {
		LOG.info("Limpiando recursos en TestingController");
	}

}
