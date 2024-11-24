package es.rpjd.app.controller.product.type;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.spring.SpringConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_PRODUCT_TYPE_MANAGEMENT)
public class ProductTypeManagementController implements Initializable, ApplicationController {

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

    @FXML
    private VBox view;
    
    public ProductTypeManagementController() {}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

    @FXML
    void onAddAction(ActionEvent event) {

    }

    @FXML
    void onDeleteAction(ActionEvent event) {

    }

    @FXML
    void onModifyAction(ActionEvent event) {

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
