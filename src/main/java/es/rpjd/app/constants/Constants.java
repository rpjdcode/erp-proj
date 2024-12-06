package es.rpjd.app.constants;

import javafx.scene.paint.Color;

public class Constants {
	
	public class CSS {
		
		private CSS() {}
		
		public static final String CSS_THEME_NAME_TEST = "Test";
		public static final String CSS_THEME_TEST = "css/test.css";
		
		public static final String CSS_THEME_NAME_LIGHT = "Light";
		public static final String CSS_THEME_LIGHT = "/css/light.css";
		
		public static final String CSS_THEME_NAME_DARK = "Dark";
		public static final String CSS_THEME_DARK = "/css/dark.css";
	}
	
	public enum Environment {
		DEVELOPMENT("DEV"),
		PRODUCTION("PRO");
		
		private String value;
		
		Environment(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private Constants() {}

	public static final String APP_NAME = "HostManager";
	
	public static final String APP_I18N_DEFAULT_PREFIX = "app.lang";
	
	public static final String FONT_MONTSERRAT_TTF = "/fonts/montserrat/Montserrat-Regular.ttf";
	public static final String FONT_MONTSERRAT_BOLD_TTF = "/fonts/montserrat/Montserrat-Bold.ttf";
	
	public static final String FILE_EXTENSION_CSS = ".css";
	
	/*
	 * Propiedades de JVM
	 */
	public static final String LOG_PROPERTY = "LOG_PATH";
	public static final String CONFIG_PROPERTY = "HM_CONFIG_FILE";
	public static final String CUSTOM_PROPS_PROPERTY = "CUSTOM_PROPS";
	public static final String ENVIRONMENT_PROPERTY = "HMRP_ENV";
	
	/*
	 * Propiedades de colores
	 */
	public static final Color OPTION_ACTIVE = Color.VIOLET;
	public static final Color OPTION_DEFAULT = Color.web("#0400e8");
	
	/*
	 * Propiedades de expresiones regulares
	 */
	public static final String REGEXP_ONLY_DECIMAL = "\\d*\\.?\\d*";
	
	/**
	 * Representa el prefijo que debe contener un código de comanda
	 */
	public static final String ORDER_CODE_PREFIX = "ORD";
	
}
