package es.rpjd.app.controller.product.type;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.enums.FormOperationType;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.product.type.ProductTypeFormModel;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_FORM)
public class ProductTypeFormController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductTypeFormController.class);

	@FXML
	private Button actionButton;

	@FXML
	private HBox buttonsBox;

	@FXML
	private Button cancelButton;

	@FXML
	private Label typeCodeLabel;

	@FXML
	private TextField typeCodeText;

	@FXML
	private Label typeNameLabel;

	@FXML
	private TextField typeNameText;

	@FXML
	private GridPane view;

	private ProductTypeFormModel model;
	private CustomPropertyService customPropertyService;
	private ProductTypeService productTypeService;

	@Autowired
	public ProductTypeFormController(CustomPropertyService customPropertyService, ProductTypeService productTypeService) {
		this.customPropertyService = customPropertyService;
		this.productTypeService = productTypeService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new ProductTypeFormModel();
		
		typeCodeText.textProperty().bindBidirectional(model.codeProperty());
		typeNameText.textProperty().bindBidirectional(model.nameProperty());

	}

	@FXML
	void onCancelAction(ActionEvent event) {
		clearResources();
		((Stage) view.getScene().getWindow()).close();
	}

	@FXML
	void onFormAction(ActionEvent event) {
		boolean isCreate = model.getOperationMode() == FormOperationType.CREATE;
		ProductType type = new ProductType();

		type.setId(isCreate ? null : model.getId());
		type.setCode(model.getCode());
		type.setCreatedAt(isCreate ? LocalDateTime.now() : null);

		String propertyName = customPropertyService.generatePropertyName(type);
		customPropertyService.addProperty(propertyName, model.getName());
		type.setPropertyName(propertyName);
		LOG.info("Nombre de propiedad custom: {} - Valor: {}", propertyName, model.getName());

		DBResponseModel<ProductType> response = isCreate ? productTypeService.save(type)
				: productTypeService.modify(type);

		LOG.info("Respuesta: {}", response.getData());

		onCancelAction(null);
	}
	
	public void loadModifyForm(ProductType data) {
		model.operationModeProperty().set(FormOperationType.UPDATE);
		model.idProperty().set(data.getId());
		model.codeProperty().set(data.getCode());
		model.nameProperty().set(customPropertyService.getProperty(data.getPropertyName()));
		typeCodeText.setDisable(true);

	}

	@Override
	public GridPane getView() {
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
