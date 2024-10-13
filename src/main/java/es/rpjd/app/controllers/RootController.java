package es.rpjd.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;

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
    
    public RootController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
    	loader.setController(this);
    	loader.load();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public GridPane getView() {
		return view;
	}

}
