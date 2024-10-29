package es.rpjd.app.spring;

/**
 * Clase que contiene las constantes relacionadas con Spring, como por ejemplo
 * los nombres de las beans de controladores, servicios, etc.
 */
public class SpringConstants {

	private SpringConstants() {
	}

	/*
	 * Nombres de Beans relacionados a controladores de JavaFX
	 */
	public static final String BEAN_CONTROLLER_ROOT = "rootController";
	public static final String BEAN_CONTROLLER_TESTING = "testingController";
	public static final String BEAN_CONTROLLER_MENU = "menuController";
	public static final String BEAN_CONTROLLER_HOME = "homeController";
	public static final String BEAN_CONTROLLER_CONFIG = "configController";
	

	/*
	 * Nombres de Beans relacionados con servicios
	 */
	public static final String BEAN_SERVICE_USER = "userService";
}
