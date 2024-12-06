package es.rpjd.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_ORDER)
public class OrderController implements Initializable, ApplicationController {

    @FXML
    private ListView<?> ordersList;

    @FXML
    private SplitPane ordersSplitPane;

    @FXML
    private VBox ordersSplitViewDown;

    @FXML
    private VBox ordersSplitViewUp;

    @FXML
    private VBox ordersUpLeftColumnBox;

    @FXML
    private VBox ordersUpRightColumnBox;

    @FXML
    private VBox view;
    
    @Autowired
    public OrderController() {}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VBox getView() {
		return view;
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
