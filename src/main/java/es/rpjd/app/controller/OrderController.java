package es.rpjd.app.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.OrderModel;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.OrderService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.TilesUtils;
import eu.hansolo.tilesfx.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
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
	private FlowPane ordersTilesFlowPane;

	@FXML
	private TabPane ordersTabPane;

	@FXML
	private VBox view;

	private ApplicationContext context;
	private Environment env;

	private OrderModel model;
	private CustomPropertyService customPropertyService;
	private ProductTypeService productTypeService;
	private OrderService orderService;

	@Autowired
	public OrderController(ApplicationContext context, Environment env, OrderService orderService,
			ProductTypeService typeService, CustomPropertyService customPropertyService) {
		this.context = context;
		this.env = env;
		this.orderService = orderService;
		this.productTypeService = typeService;
		this.customPropertyService = customPropertyService;
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

		initSelectorComponent();
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

	private void initSelectorComponent() {

		DBResponseModel<List<ProductType>> response = productTypeService.getTypesAndInformation();
		List<ProductType> types = response.getData();

		if (!types.isEmpty()) {
			Iterator<ProductType> iterator = types.iterator();

			while (iterator.hasNext()) {
				ProductType productType = iterator.next();
				String propertyName = productType.getPropertyName();
				String tabName = (propertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX))
						? I18N.getString(propertyName)
						: customPropertyService.getProperty(propertyName);
				Tab tab = new Tab(tabName);
				tab.setClosable(false);
				List<Product> products = productType.getProducts();
				FlowPane tabFlowPane = new FlowPane(Orientation.HORIZONTAL, 5, 5);
				tabFlowPane.setPadding(new Insets(5, 5, 5, 5));
				ScrollPane tabScrollPane = new ScrollPane();
				for (Product product : products) {
					String productPropertyName = product.getPropertyName();
					String productName = (productPropertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX))
							? I18N.getString(productPropertyName)
							: customPropertyService.getProperty(productPropertyName);
					Tile ti = TilesUtils.createImageTile(null, productName);
					tabFlowPane.getChildren().add(ti);
				}

				tabScrollPane.setContent(tabFlowPane);

				tab.setContent(tabScrollPane);

				ordersTabPane.getTabs().add(tab);
			}
		}

		for (int x = 0; x < 100; x++) {
			Tile ti = TilesUtils.createImageTile(null, String.format("Ejemplo de tile %d", x));
			ordersTilesFlowPane.getChildren().add(ti);
		}
	}

}
