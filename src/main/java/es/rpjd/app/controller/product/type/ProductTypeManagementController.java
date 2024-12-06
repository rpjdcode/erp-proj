package es.rpjd.app.controller.product.type;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.type.ProductTypeManagementModel;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.AlertUtils;
import es.rpjd.app.utils.ModalUtils;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
	private ApplicationContext context;
	private Environment env;
	private RootController root;

	@FXML
	private VBox view;

	@Autowired
	public ProductTypeManagementController(RootController root, ProductTypeService productTypeService,
			CustomPropertyService customPropertyService, ApplicationContext context, Environment env) {
		this.root = root;
		this.productTypeService = productTypeService;
		this.customPropertyService = customPropertyService;
		this.context = context;
		this.env = env;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new ProductTypeManagementModel();
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

		modifyButton.disableProperty().bind(typesTable.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		deleteButton.disableProperty().bind(typesTable.getSelectionModel().selectedIndexProperty().isEqualTo(-1));

		typesTable.itemsProperty().bind(model.itemsProperty());

	}

	@FXML
	void onAddAction(ActionEvent event) {
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		try {
			String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER,
					env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "products/type/typeForm.fxml");

			Parent load = loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_FORM);
			Stage primary = (Stage) root.getView().getScene().getWindow();

			Stage productFormStg = ModalUtils.createApplicationModal(load, primary, primary.getIcons().get(0),
					I18N.getString("app.stg.form.ptype.add.title"));

			productFormStg.setWidth(550);
			productFormStg.setHeight(150);
			productFormStg.setResizable(false);
			productFormStg.showAndWait();

		} catch (IOException e) {
			LOG.error(
					"Se ha producido un error inesperado al intnetar abrir el formulario de creación de tipos de producto",
					e);
		}
	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		Stage primary = (Stage) root.getView().getScene().getWindow();
		ProductType type = typesTable.getSelectionModel().getSelectedItem();

		Alert alert = AlertUtils.generateAppModalAlert(AlertType.CONFIRMATION,
				I18N.getString("app.stg.form.ptype.del.title"),
				String.format(I18N.getString("app.stg.form.ptype.del.head"), type.getCode()),
				I18N.getString("app.stg.form.ptype.del.body"), primary);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()) {
			LOG.info("El resultado está presente");
			ButtonType buttonType = result.get();

			if (buttonType == ButtonType.OK) {
				// Se procede con la eliminación del registro
				DBResponseModel<Boolean> response = productTypeService.delete(type);

				if (response.getData().booleanValue()) {
					// Se ha eliminado de BBDD, procedemos a eliminar su propiedad personalizada en
					// caso de tenerla
					boolean deleted = customPropertyService.deleteProperty(type.getPropertyName());

					if (deleted) {
						LOG.info("Se ha eliminado la propiedad {} del archivo custom.properties",
								type.getPropertyName());
					} else {
						LOG.info("No se pudo eliminar la propiedad {} del archivo custom.properties",
								type.getPropertyName());
					}

					model.itemsProperty().clear();
					model.itemsProperty().addAll(productTypeService.getTypes().getData());
				}
			} else if (buttonType == ButtonType.CANCEL) {
				// Se cancela la eliminación del registro
				LOG.info("Se ha pulsado CANCEL");
			}

		}
	}

	@FXML
	void onModifyAction(ActionEvent event) {
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER,
				env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "products/type/typeForm.fxml");
		try {
			Parent load = loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_FORM);
			ProductTypeFormController controller = context.getBean(ProductTypeFormController.class);
			controller.loadModifyForm(typesTable.getSelectionModel().getSelectedItem());
			Stage primary = (Stage) root.getView().getScene().getWindow();

			Stage productFormStg = ModalUtils.createApplicationModal(load, primary, primary.getIcons().get(0),
					I18N.getString("app.stg.form.product.mod.title"));

			productFormStg.setWidth(550);
			productFormStg.setHeight(230);
			productFormStg.setResizable(false);
			productFormStg.showAndWait();

			// Después de cierre de formulario de producto
			model.itemsProperty().clear();
			model.itemsProperty().addAll(productTypeService.getTypes().getData());

		} catch (IOException e) {
			LOG.error("Se ha producido un error inesperado al intnetar abrir el formulario de modificación de tipos de producto", e);
		}
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
