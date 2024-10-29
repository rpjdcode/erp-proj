package es.rpjd.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

@Controller(value = SpringConstants.BEAN_CONTROLLER_CONFIG)
public class ConfigController implements Initializable, ApplicationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @FXML
    private ComboBox<?> langCombo;

    @FXML
    private Label langLabel;

    @FXML
    private ComboBox<?> themeCombo;

    @FXML
    private Label themeLabel;

    @FXML
    private GridPane view;
    
    private ApplicationContext context;
    
    @Autowired
    public ConfigController(ApplicationContext context) {
    	this.context = context;
    }

	@Override
	public Node getView() {
		return view;
	}

	@Override
	public void clearResources() {
		LOG.info("Se llama al cleanResources de ConfigController");
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("initialize");
		
	}

}
