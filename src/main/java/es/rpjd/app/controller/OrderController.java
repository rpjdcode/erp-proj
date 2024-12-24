package es.rpjd.app.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import es.rpjd.app.hibernate.HibernateAdapter;
import es.rpjd.app.hibernate.entity.Order;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.hibernate.entity.ProductOrder;
import es.rpjd.app.hibernate.entity.ProductType;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.listeners.SelectedOrderChangedListener;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.model.OrderModel;
import es.rpjd.app.model.observables.ProductOrderObservable;
import es.rpjd.app.service.CustomPropertyService;
import es.rpjd.app.service.OrderService;
import es.rpjd.app.service.ProductTypeService;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.utils.AlertUtils;
import es.rpjd.app.utils.TilesUtils;
import eu.hansolo.tilesfx.Tile;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
import javafx.scene.input.MouseButton;
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
	private TableView<ProductOrderObservable> orderProductsTable;

	@FXML
	private TableColumn<ProductOrderObservable, String> productCodeColumn;

	@FXML
	private TableColumn<ProductOrderObservable, String> productNameColumn;

	@FXML
	private TableColumn<ProductOrderObservable, Integer> quantityColumn;

	@FXML
	private TableColumn<ProductOrderObservable, Void> operationsColumn;

	@FXML
	private Button applyChangesButton;

	@FXML
	private Button cancelChangesButton;

	@FXML
	private VBox view;

	@SuppressWarnings("unused")
	private ApplicationContext context;
	@SuppressWarnings("unused")
	private Environment env;

	private OrderModel model;
	private CustomPropertyService customPropertyService;
	private ProductTypeService productTypeService;
	private OrderService orderService;

	private VBox noOrderSelectedBoxTop;
	private Label noOrderSelectedLabelTop;
	private VBox noOrderSelectedBox;
	private Label noOrderSelectedLabel;

	private List<ChangeListener<?>> listens;
	private ChangeListener<Order> selectedOrderListener;
	private ChangeListener<Order> orderSelectionMessagesListener;
	private ChangeListener<Boolean> editionModeListener;

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
		listens = new ArrayList<>();

		// Inicialización de contenedor de detalles y bottombox
		initDetailsAndBottomBox();

		// Inicialización de factorías de celdas y listas
		initializeCellFactories();

		// Inicialización de bindings
		initBindingsAndListeners();

		// Carga de datos de comanda en la pantalla
		model.itemsProperty().addAll(orderService.getUnprocessedOrders().getData());

		// Método de prueba para el sistema de selección y adición de productos a una
		// comanda
		initSelectorComponent();
	}

	/**
	 * Inicializa los contenedores que contienen los mensajes a mostrar al usuario
	 * cuando no se selecciona una comanda
	 */
	private void initDetailsAndBottomBox() {
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
	}

	/**
	 * Método encargado de la inicialización de los bindings y listeners a utilizar
	 * en la interfaz
	 */
	private void initBindingsAndListeners() {
		model.selectedOrderProperty().bind(ordersList.getSelectionModel().selectedItemProperty());
		applyChangesButton.disableProperty().bind(model.editionModeProperty().not()
				.or(model.selectedOrderRequestsProperty().isEqualTo(model.unmodifiedDataProperty())));
		modifyOrderButton.disableProperty().bind(ordersList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		deleteOrderButton.disableProperty().bind(ordersList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		ordersList.itemsProperty().bind(model.itemsProperty());
		orderProductsTable.itemsProperty().bind(model.selectedOrderRequestsProperty());

		orderSelectionMessagesListener = new SelectedOrderChangedListener(detailsBox, bottomBox, noOrderSelectedBoxTop,
				noOrderSelectedBox, ordersBox);
		model.selectedOrderProperty().addListener(orderSelectionMessagesListener);
		listens.add(orderSelectionMessagesListener);

		// Cambio de comanda seleccionada
		selectedOrderListener = selectedOrderChangeListener();
		model.selectedOrderProperty().addListener(selectedOrderListener);
		listens.add(selectedOrderListener);

		// Detección de cambio de modo edición
		editionModeListener = (o, ov, nv) -> {
			LOG.info("Listener de editionMode para controlador de comandas");
			if (nv.booleanValue()) {
				LOG.info("Activando modo edición");
				// Copia de los datos de la comanda
				ListProperty<ProductOrderObservable> copied = new SimpleListProperty<>(
						FXCollections.observableArrayList());
				for (ProductOrderObservable productOrder : model.getSelectedOrderRequests()) {
					copied.add(new ProductOrderObservable(productOrder));
				}

				model.setUnmodifiedData(copied);
			} else {
				LOG.info("El nuevo valor de modo edición es false {}", nv);
			}
		};
		model.editionModeProperty().addListener(editionModeListener);
		listens.add(editionModeListener);

	}

	/**
	 * Método que define el listener para controlar el cambio de comanda
	 * seleccionada
	 * 
	 * @return
	 */
	private ChangeListener<Order> selectedOrderChangeListener() {
		return (o, ov, nv) -> {
			LOG.info("Comanda seleccionada cambiada");
			if (ov != nv) {
				LOG.info("El antiguo valor es distinto del nuevo valor");
				// Comprobar si nos encontramos en modo edición
				if (Boolean.TRUE.equals(model.editionModeProperty().getValue())) {
					// Hay cambios pendientes de aplicar
					Alert aviso = AlertUtils.generateAppModalAlert(AlertType.CONFIRMATION, I18N.getString("app.stg.order.edition.changes.title"),
							I18N.getString("app.stg.order.edition.changes.head"),
							I18N.getString("app.stg.order.edition.changes.body"),
							root.getView().getScene().getWindow());
					Optional<ButtonType> response = aviso.showAndWait();

					if (response.isPresent() && ButtonType.OK == response.get()) {
						// Aplicar cambios en BBDD
						List<ProductOrder> poList = HibernateAdapter.observableListToProductOrder(model.getSelectedOrderRequests());
						ov.setProductsOrder(poList);
						DBResponseModel<Order> updateResponse = orderService.modify(ov);
						LOG.info("Respuesta obtenida de modificación: {}", updateResponse.getStatus());
						

					}
					// Salir del modo edición
					model.unmodifiedDataProperty().clear();
					model.setEditionMode(false);
					model.selectedOrderRequestsProperty().clear();
				}
				if (nv != null) {
					model.setSelectedOrderRequests(
							ProductOrderObservable.productOrdersToObservableList(nv.getProductsOrder()));
					initializeProductOrdersListeners();
				}
				// Aseguramos que se refresca correctamente la tabla
				orderProductsTable.refresh();
			}
		};
	}

	@Override
	public VBox getView() {
		return view;
	}

	@Override
	public void clearResources() {
		LOG.info("Llamando a eliminación de recursos en controlador de comandas");
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		// TODO Auto-generated method stub

	}

	/**
	 * Crea una comanda en blanco en base de datos y la añade a la lista de comandas
	 * 
	 * @param event
	 */
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

	/**
	 * TODO: Pendiente de desarrollar
	 * 
	 * @param event
	 */
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
			Alert alert = AlertUtils.generateAppModalAlert(AlertType.CONFIRMATION,
					I18N.getString("app.stg.form.order.del.title"),
					String.format(I18N.getString("app.stg.form.order.del.head"),
							model.getSelectedOrder().getOrderCode()),
					I18N.getString("app.stg.form.order.del.body"), root.getView().getScene().getWindow());
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				LOG.info("Se pulsó en OK");
				/*
				 * TODO: Valorar si se debería permitir eliminar comandas con productos, o si
				 * por el contrario se debe informar al usuario de que debe eliminar los
				 * productos existentes de la comanda
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

	/**
	 * Inicializa las factorias de cada celda para indicar la forma en la que se
	 * representan o el valor a mostrar en ellas
	 */
	private void initializeCellFactories() {
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

		operationsColumn.setCellFactory(col -> new OperationsTableCell<>(model));
		productCodeColumn
				.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getProduct().getProductCode()));
		productNameColumn
				.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getProduct().getPropertyName()));
		quantityColumn.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getQuantity()).asObject());
	}

	/**
	 * Método que inicializa el componente de selección de productos para la comanda
	 */
	private void initSelectorComponent() {

		DBResponseModel<List<ProductType>> response = productTypeService.getTypesAndInformation();
		List<ProductType> types = response.getData();

		Iterator<ProductType> iterator = types.iterator();

		while (iterator.hasNext()) {
			ProductType productType = iterator.next();
			String propertyName = productType.getPropertyName();
			String tabName = (propertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX)) ? I18N.getString(propertyName)
					: customPropertyService.getProperty(propertyName);
			Tab tab = new Tab(tabName);
			tab.setClosable(false);
			List<Product> products = productType.getProducts();
			FlowPane tabFlowPane = new FlowPane(Orientation.HORIZONTAL, 5, 5);
			tabFlowPane.setPadding(new Insets(5, 5, 5, 5));
			ScrollPane tabScrollPane = new ScrollPane();
			
			products.forEach(product -> {
				String productPropertyName = product.getPropertyName();
				String productName = (productPropertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX))
						? I18N.getString(productPropertyName)
						: customPropertyService.getProperty(productPropertyName);
				Tile ti = TilesUtils.createImageTile(null, productName);

				ti.setOnMouseClicked(e -> {
					model.setEditionMode(true);
					Optional<ProductOrderObservable> res = model.findProductInRequests(product.getProductCode());

					if (e.getButton() == MouseButton.PRIMARY) {
						// Añadir producto a comanda/incrementar cantidad
						if (res.isPresent()) {
							ProductOrderObservable obs = res.get();
							LOG.info("Se encontró el producto en la lista. {}", obs.getProduct().getProductCode());
							obs.setQuantity(obs.getQuantity() + 1);
							orderProductsTable.refresh();
						} else {
							LOG.info("No se encontró el producto en la lista, se añade");
							ProductOrder po = new ProductOrder();
							po.setId(0l);
							po.setProduct(product);
							po.setQuantity(1);
							po.setOrder(model.getSelectedOrder());
							ProductOrderObservable obsNew = new ProductOrderObservable(po);
							model.getSelectedOrderRequests().add(obsNew);
						}
						orderProductsTable.refresh();
						
					} else if (e.getButton() == MouseButton.SECONDARY) {
						// Eliminar producto de comanda/decrementar cantidad
						if (res.isPresent()) {
							ProductOrderObservable obs = res.get();
							int quantityOperation = obs.getQuantity() - 1;
							if (quantityOperation == 0) {
								// Se saca el producto de la lista
								model.getSelectedOrderRequests().remove(obs);
							} else {
								obs.setQuantity(quantityOperation);
								
							}
							orderProductsTable.refresh();
						}
					}
				});
				tabFlowPane.getChildren().add(ti);
			});

			tabScrollPane.setContent(tabFlowPane);

			tab.setContent(tabScrollPane);

			ordersTabPane.getTabs().add(tab);
		}
	}

	/**
	 * Inicializa los listeners que permanecen a la escucha de cambios en la
	 * comanda. En el caso de detectar que los cambios en la comanda son idénticos
	 * en la lista original, se desactiva el modo edición y conlleva a que se
	 * deshabilite el botón de aplicar cambios
	 */
	private void initializeProductOrdersListeners() {
		model.selectedOrderRequestsProperty().forEach(po -> {
			po.quantityProperty().addListener((o, ov, nv) -> {
				LOG.info("Cantidad modificada");

				if (Boolean.TRUE.equals(
						model.unmodifiedDataProperty().isEqualTo(model.selectedOrderRequestsProperty()).getValue())) {
					LOG.info("unmodifiedDataProperty es igual a selectedOrderRequestsProperty, desactivando modo edición");
					LOG.info("Valor de unmodifiedDataProperty: {}", model.getUnmodifiedData());
					LOG.info("Valor de selectedOrderRequestsProperty: {}", model.getSelectedOrderRequests());
					model.setEditionMode(false);
				}
			});
		});
	}

	/**
	 * Método llamado para aplicar cambios de modificación en la comanda
	 * 
	 * @param event
	 */
	@FXML
	void onApplyChangesAction(ActionEvent event) {
		LOG.info("Aplicar cambios");
		List<ProductOrder> poList = HibernateAdapter.observableListToProductOrder(model.getSelectedOrderRequests());
		Order order = model.getSelectedOrder();
		order.setProductsOrder(poList);
		orderService.modify(order);
		model.setEditionMode(false);
		model.unmodifiedDataProperty().clear();
	}

	/**
	 * Método llamado para cancelar cambios de modificación en la comanda
	 * 
	 * @param event
	 */
	@FXML
	void onCancelChangesAction(ActionEvent event) {
		if (event != null) {
			LOG.info("Cancelando cambios aplicados en la comanda actual");
		}
		
		model.setEditionMode(false);
		model.selectedOrderRequestsProperty().clear();
		model.selectedOrderRequestsProperty().set(FXCollections.observableArrayList(List.copyOf(model.getUnmodifiedData())));
		model.unmodifiedDataProperty().clear();
	}

}
