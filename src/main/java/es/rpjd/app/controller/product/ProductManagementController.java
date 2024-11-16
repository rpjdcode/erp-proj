package es.rpjd.app.controller.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.ProductManagementModel;
import es.rpjd.app.service.ProductService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.CustomPropertiesAssistant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
    
    @Autowired
    public ProductManagementController(ProductService productService) {
    	this.productService = productService;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.model = new ProductManagementModel();
		
		initializeTables();
		
		DBResponseModel<List<Product>> response = productService.getProducts();
		
		modifyProductButton.disableProperty().bind(productsTable.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		deleteProductButton.disableProperty().bind(productsTable.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		model.productsProperty().set(FXCollections.observableArrayList(response.getData()));
		
		productsTable.itemsProperty().bind(model.productsProperty());
		
		
	}

	private void initializeTables() {
		// Inicialización de CellValueFactory para cada columna de tabla de productos
		productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productCodeColumn.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	void onAddProductAction(ActionEvent event) {

		// testingCustomProperties();
	}
	
	@FXML
	void onModifyProductAction(ActionEvent event) {}
	
	@FXML
	void onDeleteProductAction(ActionEvent event) {}

	private void testingCustomProperties() {
		CustomPropertiesAssistant generator = new CustomPropertiesAssistant();
		try {
			Map<String,String> mapa = new HashMap<>();
			mapa.put("property.prueba1", "valor1");
			mapa.put("property.prueba2", "valor2");
			mapa.put("property.prueba3", "valor3");
			
			Set<String> keys = mapa.keySet();
			
			for (String prop : keys) {
				generator.addProperty(prop, mapa.get(prop));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

