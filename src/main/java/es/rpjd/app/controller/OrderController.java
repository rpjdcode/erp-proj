package es.rpjd.app.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.components.OperationsTableCell;
import es.rpjd.app.constants.Constants;
import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductOrder;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.listeners.SelectedOrderChangedListener;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.OrderModel;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.OrderService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.AlertUtils;
import es.rpjd.app.utils.TilesUtils;
import eu.hansolo.tilesfx.Tile;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
	private VBox detailsBox;

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
	private VBox topBox;
	
	@FXML
	private VBox bottomBox;
	
	@FXML
	private HBox ordersBox;
	
	@FXML
	private TableView<ProductOrder> orderProductsTable;
	
	@FXML
	private TableColumn<ProductOrder, String> productCodeColumn;
	
	@FXML
	private TableColumn<ProductOrder, String> productNameColumn;
	
	@FXML
	private TableColumn<ProductOrder, Integer> quantityColumn;
	
	@FXML
	private TableColumn<ProductOrder, Void> operationsColumn;
	
	@FXML
	private Button applyChangesButton;
	
	@FXML
	private Button cancelChangesButton;

	@FXML
	private VBox view;

	private ApplicationContext context;
	private Environment env;

	private OrderModel model;
	private CustomPropertyService customPropertyService;
	private ProductTypeService productTypeService;
	private OrderService orderService;

	private VBox noOrderSelectedBoxTop;
	private Label noOrderSelectedLabelTop;
	private VBox noOrderSelectedBox;
	private Label noOrderSelectedLabel;

	private RootController root;

	@Autowired
	public OrderController(ApplicationContext context, Environment env, OrderService orderService,
			ProductTypeService typeService, CustomPropertyService customPropertyService,
			RootController rootController) {
		this.context = context;
		this.env = env;
		this.orderService = orderService;
		this.productTypeService = typeService;
		this.customPropertyService = customPropertyService;
		this.root = rootController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new OrderModel();

		// No se mostrará el TabPane hasta que se seleccione una comanda
		bottomBox.getChildren().remove(ordersBox);
		noOrderSelectedLabelTop = new Label(I18N.getString("app.lang.orders.no.selection"));
		noOrderSelectedBoxTop = new VBox(noOrderSelectedLabelTop);
		noOrderSelectedBoxTop.setAlignment(Pos.CENTER);
		noOrderSelectedLabel = new Label(I18N.getString("app.lang.orders.no.selection"));
		noOrderSelectedBox = new VBox(noOrderSelectedLabel);
		noOrderSelectedBox.setAlignment(Pos.CENTER);
		
		detailsBox.getChildren().add(noOrderSelectedBoxTop);
		bottomBox.getChildren().add(noOrderSelectedBox);
		
		VBox.setVgrow(noOrderSelectedBoxTop, Priority.ALWAYS);
		VBox.setVgrow(noOrderSelectedBox, Priority.ALWAYS);

		model.selectedOrderProperty().bind(ordersList.getSelectionModel().selectedItemProperty());
		model.selectedOrderProperty()
				.addListener(new SelectedOrderChangedListener(detailsBox, bottomBox, noOrderSelectedBoxTop , noOrderSelectedBox, ordersBox));
		
		model.selectedOrderProperty().addListener((o, ov, nv) -> {
			model.selectedOrderRequestsProperty().clear();
			if (nv != null) {
				model.selectedOrderRequestsProperty().addAll(nv.getProductsOrder());
			}
		});

		// CellFactory para personalizar qué se visualizará de cada registro Order en la
		// lista
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
		
		initializeTableColumns();

		model.itemsProperty().addAll(orderService.getUnprocessedOrders().getData());
		ordersList.itemsProperty().bind(model.itemsProperty());
		orderProductsTable.itemsProperty().bind(model.selectedOrderRequestsProperty());

		modifyOrderButton.disableProperty().bind(ordersList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		deleteOrderButton.disableProperty().bind(ordersList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));

		// Método de prueba para el sistema de selección y adición de productos a una
		// comanda
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
		DBResponseModel<Order> response = orderService.getOrderInformation(model.getSelectedOrder().getOrderCode());

		if (response.getStatus() != DBResponseStatus.OK) {
			LOG.error("Error al consultar datos de la comanda seleccionada");
			return;
		}

		Order data = response.getData();

		if (!data.getProductsOrder().isEmpty()) {
			// Mostrar alerta de confirmación
			Alert alert = AlertUtils
					.generateAppModalAlert(AlertType.CONFIRMATION, I18N.getString("app.stg.form.order.del.title"),
							String.format(I18N.getString("app.stg.form.order.del.head"),
									model.getSelectedOrder().getOrderCode()),
							I18N.getString("app.stg.form.order.del.body"), root.getView().getScene().getWindow());
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				LOG.info("Se pulsó en OK");
				/* TODO: Valorar si se debería permitir eliminar comandas con productos,
				 * o si por el contrario se debe informar al usuario de que debe eliminar los productos existentes de la comanda
				*/
				ordersList.getItems().remove(model.getSelectedOrder());
				orderService.delete(response.getData());
			}
		} else {
			ordersList.getItems().remove(model.getSelectedOrder());
			orderService.delete(response.getData());
		}

		LOG.info("Respuesta obtenida");
	}
	
	private void initializeTableColumns() {
		operationsColumn.setCellFactory(col -> new OperationsTableCell<>());
		productCodeColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getProduct().getProductCode()));
		productNameColumn.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getProduct().getPropertyName()));
		quantityColumn.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getQuantity()).asObject());
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
	
	@FXML
	void onApplyChangesAction(ActionEvent event) {}
	
	@FXML
	void onCancelChangesAction(ActionEvent event) {}

}
