package es.rpjd.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import es.rpjd.app.i18n.I18N;
import es.rpjd.app.model.MenuModel;
import es.rpjd.app.spring.SpringConstants;
import es.rpjd.app.spring.SpringFXMLLoader;
import es.rpjd.app.utils.ModalUtils;
import es.rpjd.app.utils.StringFormatUtils;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Controller(value = SpringConstants.BEAN_CONTROLLER_MENU)
public class MenuController implements Initializable, ApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

	@FXML
	private Button configButton;

	@FXML
	private Button homeButton;

	@FXML
	private Button productButton;
	
	@FXML
	private Button orderButton;

	@FXML
	private Button testingButton;
	
	@FXML
	private Button checkButton;

	@FXML
	private HBox view;
	
	private MenuModel model;

	private ApplicationContext context;
	private Environment env;
	private RootController root;

	@Autowired
	public MenuController(ApplicationContext context, Environment env) {
		this.context = context;
		this.root = this.context.getBean(RootController.class);
		this.env = env;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.model = new MenuModel();
		LOG.info("Inicializando MenuController");
		
		ChangeListener<ResourceBundle> changeListener = (o, ov, nv) -> {
			updateTexts(nv);
		};
		model.setI18nListener(changeListener);
		I18N.bundleProperty().addListener(changeListener);

	}

	@FXML
	void onTestingAction(ActionEvent event) {
		loadApplicationContent(SpringConstants.BEAN_CONTROLLER_TESTING);

	}

	@FXML
	void onConfigAction(ActionEvent event) {
		LOG.info("Se hizo click en config");
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		Parent configLoad;
		try {
			String fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "config/config.fxml");
			configLoad = loader.load(fxmlPath, SpringConstants.BEAN_CONTROLLER_CONFIG);

			Stage primary = (Stage) root.getView().getScene().getWindow();

			Stage configModal = ModalUtils.createApplicationModal(configLoad, primary, primary.getIcons().get(0),
					I18N.getString("app.config"));
			configModal.showAndWait();

			LOG.info("Se salió del modal");
		} catch (IOException e) {
			LOG.error("Se ha producido un error IOException al abrir modal de configuración {0}", e);
		}

	}

	@FXML
	void onHomeAction(ActionEvent event) {
		LOG.info("Click en home");
		loadApplicationContent(SpringConstants.BEAN_CONTROLLER_HOME);
	}
	
	@FXML
	void onOrdersAction(ActionEvent event) {
		LOG.info("Click en comandas");
		loadApplicationContent(SpringConstants.BEAN_CONTROLLER_ORDER);
	}
	
	@FXML
	void onProductsAction(ActionEvent event) {
		LOG.info("Click en productos");
		loadApplicationContent(SpringConstants.BEAN_CONTROLLER_PRODUCT);
	}
	
	@FXML
	void onCheckAction(ActionEvent event) {
		LOG.info("Click en caja");
	}

	public HBox getView() {
		return view;
	}

	/**
	 * Método encargado de cargar el contenido principal de la aplicación en el
	 * RootController
	 * 
	 * @param beanController Nombre del Bean del Controlador a cargar
	 * @see {@link ApplicationController}
	 * @see {@link SpringConstants}
	 */
	private void loadApplicationContent(String beanController) {
		SpringFXMLLoader loader = context.getBean(SpringFXMLLoader.class);
		String fxmlPath = null;
		ApplicationController controller = null;

		try {
			switch (beanController) {
			case SpringConstants.BEAN_CONTROLLER_TESTING:
				fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "testing/testing.fxml");
				controller = context.getBean(TestingController.class);
				break;

			case SpringConstants.BEAN_CONTROLLER_HOME:
				break;
				
			case SpringConstants.BEAN_CONTROLLER_PRODUCT:
				fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "products/products.fxml");
				controller = context.getBean(ProductController.class);
				break;
				
			case SpringConstants.BEAN_CONTROLLER_ORDER:
				fxmlPath = String.format(StringFormatUtils.DOUBLE_PARAMETER, env.getProperty(SpringConstants.PROPERTY_FXML_PATH), "orders/orders.fxml");
				controller = context.getBean(OrderController.class);
				break;
				
			default:
				break;
			}

			if (controller != null && fxmlPath != null) {
				loader.load(fxmlPath, beanController);
				this.root.loadControllerContent(controller);
			} else {
				this.root.unloadControllerContent();
			}

		} catch (IOException e) {
			LOG.error("Se ha producido la siguiente excepción al cargar el contenido de la aplicación", e);
		}
	}

	@Override
	public void clearResources() {
		LOG.info("Liberando recursos del MenuController");
		I18N.bundleProperty().removeListener(model.getI18nListener());
		
	}

	@Override
	public void updateTexts(ResourceBundle bundle) {
		homeButton.setText(bundle.getString("app.menu.home"));
		configButton.setText(bundle.getString("app.menu.config"));
		productButton.setText(bundle.getString("app.menu.db.products"));
		orderButton.setText(bundle.getString("app.menu.db.orders"));
		checkButton.setText(bundle.getString("app.menu.db.check"));
		
	}

}
