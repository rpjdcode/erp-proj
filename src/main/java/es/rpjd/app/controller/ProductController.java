package es.rpjd.app.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT)
public class ProductController implements Initializable, ApplicationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
	
	private ApplicationContext context;
	private Environment env;
	
	private ProductService productService;
	private ProductTypeService productTypeService;

	@FXML
	private VBox productsBox;

	@FXML
	private TitledPane productsPane;

	@FXML
	private VBox typesBox;

	@FXML
	private TitledPane typesPane;

	@FXML
	private VBox view;



	@Autowired
	public ProductController(ApplicationContext context, Environment env, ProductService productService,
			ProductTypeService typesService) {
		this.context = context;
		this.env = env;
		this.productService = productService;
		this.productTypeService = typesService;
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
		DBResponseModel<List<ProductType>> response = productTypeService.getTypes();
		
		LOG.info("Respuesta obtenida: {}", response.getMessage() );
		
		if (response.getStatus() == DBResponseStatus.OK) {
			List<ProductType> types = response.getData();
			
			for (ProductType productType : types) {
				
			}
		}

	}

	@Override
	public VBox getView() {
		return view;
	}

}
