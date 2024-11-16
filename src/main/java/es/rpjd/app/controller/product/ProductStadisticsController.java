package es.rpjd.app.controller.product;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.Initializable;
import javafx.scene.Node;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_STADISTICS)
public class ProductStadisticsController implements Initializable, ApplicationController{

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
