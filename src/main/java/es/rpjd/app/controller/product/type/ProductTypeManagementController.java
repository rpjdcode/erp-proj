package es.rpjd.app.controller.product.type;

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
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.type.ProductTypeManagementModel;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_MANAGEMENT)
public class ProductTypeManagementController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductTypeManagementController.class);

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button modifyButton;

	@FXML
	private TableColumn<ProductType, String> typeCodeColumn;

	@FXML
	private TableColumn<ProductType, LocalDateTime> typeCreatedColumn;

	@FXML
	private TableColumn<ProductType, Long> typeIdColumn;

	@FXML
	private TableColumn<ProductType, String> typeNameColumn;

	@FXML
	private TableColumn<ProductType, LocalDateTime> typeUpdatedColumn;

	@FXML
	private TableView<ProductType> typesTable;
	
	private ProductTypeManagementModel model;

	private ProductTypeService productTypeService;
	private CustomPropertyService customPropertyService;

	@FXML
	private VBox view;

	@Autowired
	public ProductTypeManagementController(ProductTypeService productTypeService,
			CustomPropertyService customPropertyService) {
		this.productTypeService = productTypeService;
		this.customPropertyService = customPropertyService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new ProductTypeManagementModel();
//		typesTable.setPlaceholder(new Label("texto internacionalizado"));
		DBResponseModel<List<ProductType>> response = productTypeService.getTypes();

		typeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		typeNameColumn.setCellValueFactory(v -> {
			String propertyName = v.getValue().getPropertyName();

			String finalName;
			if (propertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX)) {
				return new SimpleStringProperty(I18N.getString(propertyName));
			}

			finalName = customPropertyService.getProperty(v.getValue().getPropertyName());

			return finalName != null ? new SimpleStringProperty(finalName) : new SimpleStringProperty(propertyName);
		});
		typeCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		typeCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		typeUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedAt"));
		
		model.itemsProperty().addAll(response.getData());
		
		typesTable.itemsProperty().bind(model.itemsProperty());

	}

	@FXML
	void onAddAction(ActionEvent event) {
		LOG.info("Añadir");
	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		LOG.info("Eliminar");
	}

	@FXML
	void onModifyAction(ActionEvent event) {
		LOG.info("Modificar");
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
