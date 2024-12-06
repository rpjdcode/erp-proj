package es.rpjd.app.controller.product.type;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.spring.SpringConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_FORM)
public class ProductTypeFormController implements Initializable, ApplicationController {

    @FXML
    private Button actionButton;

    @FXML
    private HBox buttonsBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Label typeCodeLabel;

    @FXML
    private TextField typeCodeText;

    @FXML
    private Label typeNameLabel;

    @FXML
    private TextField typeNameText;

    @FXML
    private GridPane view;
    
    @Autowired
    public ProductTypeFormController() {
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

    @FXML
    void onCancelAction(ActionEvent event) {
    	((Stage)view.getScene().getWindow()).close();
    }

    @FXML
    void onFormAction(ActionEvent event) {

    }

	@Override
	public Node getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		// TODO Auto-generated method stub
		
	}



}

