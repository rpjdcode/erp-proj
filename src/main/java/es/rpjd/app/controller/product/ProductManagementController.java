package es.rpjd.app.controller.product;

import java.io.IOException;
import java.math.BigDecimal;
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
import es.rpjd.app.controller.RootController;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.ProductManagementModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.CustomPropertiesAssistant;
import es.rpjd.app.utils.ModalUtils;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_MANAGEMENT)
public class ProductManagementController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductManagementController.class);

	private ProductManagementModel model;

	private ProductService productService;

	@FXML
	private TableColumn<Product, String> productCodeColumn;

	@FXML
	private TableColumn<Product, LocalDateTime> productCreatedColumn;

	@FXML
	private TableColumn<Product, Long> productIdColumn;

	@FXML
	private TableColumn<Product, String> productNameColumn;

	@FXML
	private TableColumn<Product, BigDecimal> productPriceColumn;

	@FXML
	private TableColumn<Product, String> productTypeColumn;

	@FXML
	private TableColumn<Product, LocalDateTime> productUpdatedColumn;

	@FXML
	private TableView<Product> productsTable;

	@FXML
	private Button addProductButton;

	@FXML
	private Button modifyProductButton;

	@FXML
	private Button deleteProductButton;

	@FXML
	private VBox view;

	private ApplicationContext context;
	private Environment env;
	private RootController root;

	@Autowired
	public ProductManagementController(ApplicationContext context, ProductService productService, RootController root,
			Environment env) {
		this.productService = productService;
		this.context = context;
		this.root = root;
		this.env = env;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOG.info("Inicializando ProductManagementController");
		this.model = new ProductManagementModel();

		ChangeListener<? super ResourceBundle> changeListener = (o, ov, nv) -> 	updateTexts(nv);

		model.setI18nListener(changeListener);
		I18N.bundleProperty().addListener(changeListener);

		initializeTables();

		DBResponseModel<List<Product>> response = productService.getProducts();

		modifyProductButton.disableProperty()
				.bind(productsTable.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		deleteProductButton.disableProperty()
				.bind(productsTable.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		model.productsProperty().set(FXCollections.observableArrayList(response.getData()));

		productsTable.itemsProperty().bind(model.productsProperty());

	}

	private void initializeTables() {

		// Inicialización de CellValueFactory para cada columna de tabla de productos
		productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productCodeColumn.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		productNameColumn.setCellValueFactory(v -> {
			String propertyName = v.getValue().getPropertyName();
			CustomPropertiesAssistant assistant = new CustomPropertiesAssistant();
			String finalName;
			if (propertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX)) {
				return new SimpleStringProperty(I18N.getString(propertyName));
			}
			try {
				finalName = assistant.getProperty(v.getValue().getPropertyName());
			} catch (IOException e) {
				finalName = null;
			}

			return finalName != null ? new SimpleStringProperty(finalName) : new SimpleStringProperty(propertyName);
		});
		productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		productTypeColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getProductType().getCode()));
		productCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		productUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedAt"));
	}

	@Override
	public VBox getView() {
		return view;
	}

	@Override
	public void clearResources() {
		I18N.bundleProperty().removeListener(model.getI18nListener());

	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		productIdColumn.setText(bundle.getString("app.lang.products.management.table.id"));
		productNameColumn.setText(bundle.getString("app.lang.products.management.table.name"));
		productCodeColumn.setText(bundle.getString("app.lang.products.management.table.code"));
		productTypeColumn.setText(bundle.getString("app.lang.products.management.table.type"));
		productPriceColumn.setText(bundle.getString("app.lang.products.management.table.price"));
		productCreatedColumn.setText(bundle.getString("app.lang.products.management.table.created"));
		productUpdatedColumn.setText(bundle.getString("app.lang.products.management.table.updated"));

		addProductButton.setText(bundle.getString("app.operation.add"));
		modifyProductButton.setText(bundle.getString("app.operation.modify"));
		deleteProductButton.setText(bundle.getString("app.operation.delete"));

	}

	@FXML
	void onAddProductAction(ActionEvent event) {
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER,
				env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "products/productForm.fxml");
		try {
			Parent load = loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_PRODUCT_FORM);
			Stage primary = (Stage) root.getView().getScene().getWindow();

			Stage productFormStg = ModalUtils.createApplicationModal(load, primary, primary.getIcons().get(0),
					"Stage product form");

			productFormStg.setWidth(550);
			productFormStg.setHeight(230);
			productFormStg.setResizable(false);
			productFormStg.showAndWait();

			// Después de cierre de formulario de producto
			model.productsProperty().clear();
			model.productsProperty().addAll(productService.getProducts().getData());
		} catch (IOException e) {
			LOG.error("Se ha producido un error inesperado durante la apertura de formulario de producto: {}",
					e.getLocalizedMessage());
			LOG.error("asasd", e);
		}

	}

	@FXML
	void onModifyProductAction(ActionEvent event) {
		// pendiente de implementar
	}

	@FXML
	void onDeleteProductAction(ActionEvent event) {
		// pendiente de implementar
	}

}
