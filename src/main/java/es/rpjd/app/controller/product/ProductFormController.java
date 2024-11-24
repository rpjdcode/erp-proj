package es.rpjd.app.controller.product;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.converter.BigDecimalStringConverter;
import es.rpjd.app.converter.ProductTypeStringConverter;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.ProductFormModel;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
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

	private ProductService productService;
	private ProductTypeService productTypeService;
	private CustomPropertyService customPropertyService;

	private ProductFormModel model;

	@Autowired
	public ProductFormController(ProductService productService,
			ProductTypeService productTypeService, CustomPropertyService customPropertyService) {
		this.productService = productService;
		this.productTypeService = productTypeService;
		this.customPropertyService = customPropertyService;
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
			}
		});



		productNameText.textProperty().bindBidirectional(model.nameProperty());
		productCodeText.textProperty().bindBidirectional(model.codeProperty());
		Bindings.bindBidirectional(priceText.textProperty(), model.priceProperty(), new BigDecimalStringConverter());
		
		BooleanBinding emptyFieldsBinding = model.codeProperty().isEmpty()
				.or(model.nameProperty().isEmpty())
				.or(model.priceProperty().isEqualTo(BigDecimal.valueOf(0)))
				.or(model.priceProperty().isNull())
				.or(model.productTypeProperty().isNull());
		
		actionButton.disableProperty().bind(emptyFieldsBinding);
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

	/**
	 * Método que es llamado cuando se hace click en aplicar  cambios.
	 * @param event
	 */
	@FXML
	void onFormAction(ActionEvent event) {

		Product product = new Product();
		product.setProductCode(model.getCode());
		product.setProductType(model.getProductType());
		product.setPrice(model.getPrice());
		product.setCreatedAt(LocalDateTime.now());
		
		String propertyName = customPropertyService.generatePropertyName(product);
		customPropertyService.addProperty(propertyName, model.getName());
		product.setPropertyName(propertyName);
		LOG.info("Nombre de propiedad custom: {} - Valor: {}", propertyName, model.getName());

		DBResponseModel<Product> response = productService.save(product);
		LOG.info("Respuesta: {}", response.getData());

		onCancelAction(null);

	}

	@FXML
	void onCancelAction(ActionEvent event) {
		clearResources();

		((Stage) view.getScene().getWindow()).close();
	}
	
	/**
	 * Método para cargar datos de un producto en los inputs del formulario para ser modificados
	 * @param data
	 */
	public void loadModifyForm(Product data) {
		model.codeProperty().set(data.getProductCode());
		model.nameProperty().set(customPropertyService.getProperty(data.getPropertyName()));
		model.priceProperty().set(data.getPrice());
		productTypeCombo.getSelectionModel().select(data.getProductType());
		productCodeText.setDisable(true);
		
	}

	@Override
	public GridPane getView() {
		return view;
	}

}
