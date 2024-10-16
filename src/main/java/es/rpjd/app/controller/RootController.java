package es.rpjd.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.service.UserService;
import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

@Controller(value = SpringConstants.BEAN_CONTROLLER_ROOT)
public class RootController implements Initializable {

    @FXML
    private Menu editMenu;

    @FXML
    private Menu fileMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuBar menuBar;

    @FXML
    private GridPane view;
    
    private UserService userService;

    @Autowired
    public RootController(UserService userService) {
    	this.userService = userService;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		editMenu.setOnAction(e -> {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("pruieba");
			alerta.setContentText("contenido");
			alerta.showAndWait();
			System.out.println("USUARIOS: " + userService.getUsers());
		});
		
	}

}
