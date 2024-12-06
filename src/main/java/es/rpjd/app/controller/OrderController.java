package es.rpjd.app.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.OrderModel;
import es.rpjd.app.service.OrderService;
import es.rpjd.app.spring.SpringConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Controller(value = SpringConstants.BEAN_CONTROLLER_ORDER)
public class OrderController implements Initializable, ApplicationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @FXML
    private ListView<Order> ordersList;

    @FXML
    private SplitPane ordersSplitPane;

    @FXML
    private VBox ordersSplitViewDown;

    @FXML
    private VBox ordersSplitViewUp;

    @FXML
    private VBox ordersUpLeftColumnBox;

    @FXML
    private VBox ordersUpRightColumnBox;
    
    @FXML
    private HBox ordersButtonsBox;
    
    @FXML
    private Button addOrderButton;
    
    @FXML
    private Button modifyOrderButton;
    
    @FXML
    private Button deleteOrderButton;

    @FXML
    private VBox view;
    
    private OrderModel model;
    private OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
    	this.orderService = orderService;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new OrderModel();
		
        ordersList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getOrderCode());
                }
            }
        });
		
		model.itemsProperty().addAll(orderService.getOrders().getData());
		ordersList.itemsProperty().bind(model.itemsProperty());
		
		modifyOrderButton.disableProperty().bind(ordersList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		deleteOrderButton.disableProperty().bind(ordersList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		
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
	void onAddOrderAction(ActionEvent event) {
		LOG.info("Añadir comanda");
		String orderCode = orderService.generateOrderCode();
		Order order = new Order();
		order.setCreatedAt(LocalDateTime.now());
		order.setOrderCode(orderCode);
		DBResponseModel<Order> response = orderService.save(order);
		
		if (response.getStatus() == DBResponseStatus.OK) {
			model.itemsProperty().add(response.getData());
		}
		
	}
	
	@FXML
	void onProcessOrderAction(ActionEvent event) {
		LOG.info("Procesar comanda");
		
	}
	
	@FXML
	void onDeleteOrderAction(ActionEvent event) {
		LOG.info("Eliminar comanda");
	}


}
