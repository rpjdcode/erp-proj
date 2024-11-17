package es.rpjd.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.controller.product.ProductFilesController;
import es.rpjd.app.controller.product.ProductManagementController;
import es.rpjd.app.controller.product.ProductStadisticsController;
import es.rpjd.app.enums.ProductOptions;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.ProductModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT)
public class ProductController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	private ApplicationContext context;
	private Environment env;

	private ProductService productService;
	private ProductTypeService productTypeService;
	
	private ProductManagementController pmc;
	private ProductStadisticsController psc;
	private ProductFilesController pfc;

	@FXML
	private Accordion accordion;

	@FXML
	private TitledPane productsPane;

	@FXML
	private VBox productsBox;

	@FXML
	private TitledPane typesPane;

	@FXML
	private BorderPane productsContent;

	@FXML
	private VBox optionsBox;

	@FXML
	private Label productsManagementLabel;

	@FXML
	private Label productsStadisticsLabel;

	@FXML
	private Label productsFilesLabel;
	
	@FXML
	private Label noOptionLabel;
	
	@FXML
	private VBox productsView;

	@FXML
	private VBox typesBox;
	

	@FXML
	private VBox view;

	private ProductModel model;

	@Autowired
	public ProductController(ApplicationContext context, Environment env, ProductService productService,
			ProductTypeService typesService) {
		this.context = context;
		this.env = env;
		this.productService = productService;
		this.productTypeService = typesService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.model = new ProductModel();
		DBResponseModel<List<ProductType>> response = productTypeService.getTypes();
		LOG.info("Respuesta obtenida: {}", response.getMessage());
		
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		
		try {
			String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "products/management/productManagement.fxml");
			loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_PRODUCT_MANAGEMENT);
			pmc = context.getBean(ProductManagementController.class);
		} catch (IOException e) {
			LOG.error("Se ha producido una IOException al cargar controlador ProductManagement : {}", e.getMessage());
		}
		
		model.productsOptionProperty().addListener((o, ov, nv) -> {
			LOG.info("Cambió el valor de la opción seleccionada: {}", nv);
			switch (nv) {
			case ProductOptions.MANAGEMENT:
				loadProductsView(pmc);
				break;
			
			case ProductOptions.STADISTICS:
				loadProductsView(psc);
				break;

			case ProductOptions.FILES:
				loadProductsView(pfc);
				break;
				
			case ProductOptions.NONE:
				noOptionDisplayed();
				break;
			default:
				break;
			}
		});
		
		model.selectedLabelProperty().addListener((o, ov, nv) -> {
			LOG.info("Cambió la label seleccionada");
			markLabelAsSelected(nv);
		});

	}

	@FXML
	void onProductOptionClick(MouseEvent event) {
		Label src = (Label) event.getSource();
		
		model.setSelectedLabel(src == model.getSelectedLabel() ? null : src);
	}
	
	/**
	 * Método encargado de marcar la label seleccionada con un estilo identificativo de selección de opción
	 * @param label
	 */
	private void markLabelAsSelected(Label label) {
		if (label != null) {
			
			if (label == productsManagementLabel) {
				model.setProductsOption(ProductOptions.MANAGEMENT);
				productsManagementLabel.setTextFill(Constants.OPTION_ACTIVE);
				productsStadisticsLabel.setTextFill(Constants.OPTION_DEFAULT);
				productsFilesLabel.setTextFill(Constants.OPTION_DEFAULT);
			} else if (label == productsStadisticsLabel) {
				model.setProductsOption(ProductOptions.STADISTICS);
				productsManagementLabel.setTextFill(Constants.OPTION_DEFAULT);
				productsStadisticsLabel.setTextFill(Constants.OPTION_ACTIVE);
				productsFilesLabel.setTextFill(Constants.OPTION_DEFAULT);
			} else if (label == productsFilesLabel) {
				model.setProductsOption(ProductOptions.FILES);
				productsManagementLabel.setTextFill(Constants.OPTION_DEFAULT);
				productsStadisticsLabel.setTextFill(Constants.OPTION_DEFAULT);
				productsFilesLabel.setTextFill(Constants.OPTION_ACTIVE);
			}
		} else {
			unmarkProductLabels();
			model.setProductsOption(ProductOptions.NONE);
		}
	}
	
	/**
	 * Método encargado de establecer el estilo de las labels como desmarcado (default)
	 */
	private void unmarkProductLabels() {
		productsManagementLabel.setTextFill(Constants.OPTION_DEFAULT);
		productsStadisticsLabel.setTextFill(Constants.OPTION_DEFAULT);
		productsFilesLabel.setTextFill(Constants.OPTION_DEFAULT);
	}
	
	private void loadProductsView(ApplicationController controller) {
		productsView.getChildren().clear();
		if (controller == null) {
			productsView.setAlignment(Pos.CENTER);
			productsView.getChildren().add(new Label("No se pudo cargar el controlador"));
		} else {
			productsView.setAlignment(Pos.TOP_LEFT);
			productsView.getChildren().add(controller.getView());
		}
		
		
		
	}
	
	private void noOptionDisplayed() {
		productsView.getChildren().clear();
		productsView.setAlignment(Pos.CENTER);
		productsView.getChildren().add(noOptionLabel);
	}

	@Override
	public VBox getView() {
		return view;
	}
	
	@Override
	public void clearResources() {
		// TODO Pendiente de liberar recursos

	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		// TODO Pendiente de actualizar los textos durante cambio de idioma en vivo

	}

}
