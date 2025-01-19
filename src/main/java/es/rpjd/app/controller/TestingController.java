package es.rpjd.app.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.service.JasperReportService;
import es.rpjd.app.service.OrderService;
import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JRException;

@Controller(value = SpringConstants.BEAN_CONTROLLER_TESTING)
public class TestingController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(TestingController.class);

	@FXML
	private ListView<String> listView;

	@FXML
	private VBox view;
	
	private OrderService orderService;
	private JasperReportService jasperService;
	
	@Autowired
	public TestingController(JasperReportService s1, OrderService s2) {
		this.jasperService = s1;
		this.orderService = s2;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando controlador de Testing");
		
		VBox testingBox = new VBox();
		Button reportButton = new Button();
		reportButton.setText("JasperReport");
		reportButton.setOnAction(event -> {
			Map<String, Object> map = new HashMap<>();
			try {
				DBResponseModel<Order> response = orderService.getOrderInformation("20241222000000000001");
				
				if (response.getData() != null) {
					jasperService.generateOrderReport(map, response.getData().getProductsOrder());

				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		testingBox.getChildren().add(reportButton);
		
		view.getChildren().add(testingBox);

	}

	@Override
	public VBox getView() {
		return view;
	}
	
	@Override
	public void clearResources() {
		LOG.info("Limpiando recursos en TestingController");
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		// Método de actualización de textos durante cambio de internacionalización en tiempo real
		// (requiere adición de listener)
		
	}

}
