package es.rpjd.app.controller.product;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.converter.BigDecimalStringConverter;
import es.rpjd.app.converter.ProductTypeStringConverter;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.ProductFormModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.CustomPropertiesAssistant;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_FORM)
public class ProductFormController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductFormController.class);

	@FXML
	private Label priceLabel;

	@FXML
	private TextField priceText;

	@FXML
	private Label productCodeLabel;

	@FXML
	private TextField productCodeText;

	@FXML
	private Label productNameLabel;

	@FXML
	private TextField productNameText;

	@FXML
	private ComboBox<ProductType> productTypeCombo;

	@FXML
	private Label productTypeLabel;

	@FXML
	private HBox buttonsBox;

	@FXML
	private Button actionButton;

	@FXML
	private Button cancelButton;

	@FXML
	private GridPane view;

	private ApplicationContext context;
	private Environment env;
	private ProductService productService;
	private ProductTypeService productTypeService;

	private ProductFormModel model;

	@Autowired
	public ProductFormController(ApplicationContext context, Environment env, ProductService productService,
			ProductTypeService productTypeService) {
		this.context = context;
		this.env = env;
		this.productService = productService;
		this.productTypeService = productTypeService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando ProductFormController");

		model = new ProductFormModel();

		DBResponseModel<List<ProductType>> typesRequest = productTypeService.getTypes();

		model.typesProperty().addAll(typesRequest.getData());

		productTypeCombo.setConverter(new ProductTypeStringConverter());
		productTypeCombo.itemsProperty().bind(model.typesProperty());
		model.productTypeProperty().bind(productTypeCombo.getSelectionModel().selectedItemProperty());

		productCodeText.textProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				productCodeText.setText(nv.toUpperCase());
			}
		});

		priceText.textProperty().addListener((o, ov, nv) -> {
			if (!nv.matches(Constants.REGEXP_ONLY_DECIMAL)) {
				priceText.setText(ov);
			} else {
				priceText.setText(nv);
			}
		});

		BooleanBinding emptyFieldsBinding = model.codeProperty().isEmpty()
				.or(model.nameProperty().isEmpty())
				.or(model.priceProperty().isEqualTo(0).or(model.priceProperty().isNull()))
				.or(model.productTypeProperty().isNull());
		
		actionButton.disableProperty().bind(emptyFieldsBinding);

		productNameText.textProperty().bindBidirectional(model.nameProperty());
		productCodeText.textProperty().bindBidirectional(model.codeProperty());
		Bindings.bindBidirectional(priceText.textProperty(), model.priceProperty(), new BigDecimalStringConverter());
	}

	@Override
	public void clearResources() {
		LOG.info("Se llama al clearResources");
		productNameText.clear();
		productCodeText.clear();
		priceText.clear();

		productNameText.textProperty().unbindBidirectional(model.nameProperty());
		productCodeText.textProperty().unbindBidirectional(model.codeProperty());
		priceText.textProperty().unbindBidirectional(model.priceProperty());

	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		// TODO Auto-generated method stub

	}

	@FXML
	void onFormAction(ActionEvent event) {

		Product product = new Product();
		product.setProductCode(model.getCode());
		product.setProductType(model.getProductType());
		product.setPrice(model.getPrice());
		product.setCreatedAt(LocalDateTime.now());

		// Asistente para generación de propiedad personalizada
		CustomPropertiesAssistant assistant = new CustomPropertiesAssistant();

		try {
			String propertyName = assistant.generatePropertyName(product);
			assistant.addProperty(propertyName, model.getName());
			product.setPropertyName(propertyName);
			LOG.info("Nombre de propiedad custom: {} - Valor: {}", propertyName, model.getName());

			DBResponseModel<Product> response = productService.save(product);
			LOG.info("Respuesta: {}", response.getData());

			onCancelAction(null);
		} catch (IOException e) {
			LOG.error("Se ha producido un error al intentar añadir propiedad personalizada: {}", e.getMessage());
		}

	}

	@FXML
	void onCancelAction(ActionEvent event) {
		clearResources();

		((Stage) view.getScene().getWindow()).close();
	}

	@Override
	public GridPane getView() {
		return view;
	}

}
