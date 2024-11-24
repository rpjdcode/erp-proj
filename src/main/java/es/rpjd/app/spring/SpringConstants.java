package es.rpjd.app.spring;

import java.util.Map;

import es.rpjd.app.controller.ApplicationController;
import es.rpjd.app.controller.ConfigController;
import es.rpjd.app.controller.MenuController;
import es.rpjd.app.controller.ProductController;
import es.rpjd.app.controller.product.ProductManagementController;

/**
 * Clase que contiene las constantes relacionadas con Spring, como por ejemplo
 * los nombres de las beans de controladores, servicios, etc.
 */
public class SpringConstants {

	private SpringConstants() {
	}
	
	/**
	 * Controladores que son aptos para la precarga durante la inicialización de la aplicación
	 */
	public static final Map<Class<? extends ApplicationController>, String> PRELOADABLE_CONTROLLERS = Map.of(
			MenuController.class, "menu/menu.fxml",
			ConfigController.class, "config/config.fxml",
			ProductController.class, "products/products.fxml",
			ProductManagementController.class, "products/management/productManagement.fxml"
			);

	/*
	 * Propiedades incluidas en el application.properties de Spring
	 */
	public static final String PROPERTY_FXML_PATH = "path.fxml";

	/*
	 * Nombres de Beans relacionados a controladores de JavaFX
	 */
	public static final String BEAN_CONTROLLER_ROOT = "rootController";
	public static final String BEAN_CONTROLLER_TESTING = "testingController";
	public static final String BEAN_CONTROLLER_MENU = "menuController";
	public static final String BEAN_CONTROLLER_HOME = "homeController";
	public static final String BEAN_CONTROLLER_CONFIG = "configController";
	public static final String BEAN_CONTROLLER_PRODUCT = "productController";
	public static final String BEAN_CONTROLLER_PRODUCT_MANAGEMENT = "productManagementController";
	public static final String BEAN_CONTROLLER_PRODUCT_STADISTICS = "productStadisticsController";
	public static final String BEAN_CONTROLLER_PRODUCT_FILES = "productFilesController";
	public static final String BEAN_CONTROLLER_PRODUCT_FORM = "productFormController";
	public static final String BEAN_CONTROLLER_PRODUCT_TYPE_MANAGEMENT = "productTypeManagementController";

	/*
	 * Nombres de Beans relacionados con servicios
	 */
	public static final String BEAN_SERVICE_PROPERTY = "customPropertyService";
	public static final String BEAN_SERVICE_USER = "userService";
	public static final String BEAN_SERVICE_PRODUCT = "productService";
	public static final String BEAN_SERVICE_PRODUCT_TYPE = "productTypeService";
}
