package es.rpjd.app.constants;

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
	
	private Constants() {}

	public static final String APP_NAME = "HostManager";
	
	public static final String FONT_MONTSERRAT_TTF = "/fonts/montserrat/Montserrat-Regular.ttf";
	public static final String FONT_MONTSERRAT_BOLD_TTF = "/fonts/montserrat/Montserrat-Bold.ttf";
	
	public static final String FILE_EXTENSION_CSS = ".css";
	
	/*
	 * Propiedades de JVM
	 */
	public static final String LOG_PROPERTY = "LOG_PATH";
	public static final String CONFIG_PROPERTY = "HM_CONFIG_FILE";
}
