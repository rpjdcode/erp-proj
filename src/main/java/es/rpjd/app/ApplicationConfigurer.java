package es.rpjd.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rpjd.app.config.ApplicationConfiguration;
import es.rpjd.app.config.Theme;
import es.rpjd.app.constants.Constants;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.i18n.SupportedLocale;
import javafx.scene.text.Font;

/**
 * Clase encargada de la inicialización de configuraciones básicas de la
 * aplicación, tales como el idioma o fuentes.
 * 
 * Se encapsula la inicialización de cada tipo de parámetro configurable en una
 * nested class
 */
public final class ApplicationConfigurer {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfigurer.class);

	/**
	 * Inicializa la carga de configuraciones de aplicación
	 */
	public static void initialize() {
		// Carga de fuentes
		FontConfigurer.loadApplicationFonts();

		// Inicialización de configuración de aplicación
		ConfigInitializer.initializeApplicationConfig();
	}

	public static void saveConfig(ApplicationConfiguration config) {
		ConfigInitializer.saveConfiguration(config);
	}
	
	public static List<Theme> getApplicationThemes() {
		return ThemeConfigurer.collectThemes();
	}

	public static ApplicationConfiguration getApplicationConfiguration() {
		return ConfigInitializer.appConfiguration;
	}

	/**
	 * Clase encargada de la selección de idioma de la aplicación
	 */
	private final class LanguageConfigurer {

		protected static void setApplicationLanguage(Locale loc) {
			I18N.load(loc);
		}
	}

	/**
	 * Clase encargada de la carga de fuentes personalizadas en la aplicación
	 */
	private final class FontConfigurer {

		/**
		 * Método encargado de cargar las fuentes personalizadas de la aplicación
		 */
		protected static void loadApplicationFonts() {
			// Tamaños de fuente incluidos en los .ttf
			int[] sizes = new int[] { 12, 18, 24, 36, 48, 60, 72 };

			for (int size : sizes) {
				Font.loadFont(JavaFXApp.class.getResource(Constants.FONT_MONTSERRAT_TTF).toExternalForm(), size);
				Font.loadFont(JavaFXApp.class.getResource(Constants.FONT_MONTSERRAT_BOLD_TTF).toExternalForm(), size);
			}
		}
	}

	/**
	 * Clase encargada de la recolección de temas disponibles para la aplicación
	 */
	private final class ThemeConfigurer {

		protected static List<Theme> collectThemes() {
			Path dir = Paths.get("src/main/resources/css");
			try (Stream<Path> files = Files.walk(dir)) {

				return files.filter(Files::isRegularFile)
						.filter(file -> file.toString().endsWith(Constants.FILE_EXTENSION_CSS)).map(file -> {
							String fileName = file.getFileName().toString().replace(Constants.FILE_EXTENSION_CSS, "");
							String capitalized = String.format("%s%s", fileName.substring(0, 1).toUpperCase(),
									fileName.substring(1));
							Path path;
							switch (capitalized) {
							case Constants.CSS.CSS_THEME_NAME_TEST:
								path = Paths.get(Constants.CSS.CSS_THEME_TEST);
								break;
							case Constants.CSS.CSS_THEME_NAME_LIGHT:
								path = Paths.get(Constants.CSS.CSS_THEME_LIGHT);
								break;
							case Constants.CSS.CSS_THEME_NAME_DARK:
								path = Paths.get(Constants.CSS.CSS_THEME_DARK);
								break;
							default:
								path = Paths.get(Constants.CSS.CSS_THEME_TEST);
								break;
							}

							return new Theme(capitalized, path);
						}).toList();

			} catch (IOException e) {
				LOG.error("Se ha producido una excepción durante la recolección de temas: {0}", e);
			}
			
			return Collections.emptyList();
		}
	}

	/**
	 * Clase encargada de la inicialización de configuración
	 */
	private final class ConfigInitializer {

		protected static ApplicationConfiguration appConfiguration = null;

		private static final String CONFIG_FILE = System.getProperty(Constants.CONFIG_PROPERTY);

		/**
		 * Método encargado de guardar la configuración de la aplicación en su fichero
		 * application.conf.
		 */
		protected static void saveConfiguration(ApplicationConfiguration config) {
			try {
				writeConfigFile(config);
				String language = config.getLanguage().toString();

				Locale loc = Locale.of(language, language);
				LanguageConfigurer.setApplicationLanguage(loc);
			} catch (IOException e) {
				LOG.error("Se produjo un error inesperado al guardar la configuración en fichero. Traza: {0}", e);
			}
		}

		/**
		 * Método encargado de inicializar la configuración de la aplicación. El método
		 * comprueba que el fichero de configuración se encuentre creado para leer la
		 * config, en caso de no encontrarlo crea uno por defecto.
		 */
		protected static void initializeApplicationConfig() {

			if (new File(CONFIG_FILE).exists()) {
				// Si existe el fichero de configuración, leemos el objeto
				ConfigInitializer.appConfiguration = readFromFile();
			} else {
				// Si no existe el fichero de configuración, se crea uno por defecto
				ConfigInitializer.appConfiguration = initializeConfigFile();
			}

			String lang = ConfigInitializer.appConfiguration.getLanguage().toString();

			Locale loc = Locale.of(lang, lang);
			LanguageConfigurer.setApplicationLanguage(loc);

		}

		/**
		 * Método encargado de crear el fichero de configuración de la aplicación por
		 * primera vez, aplicando una configuración por defecto
		 */
		private static ApplicationConfiguration initializeConfigFile() {
			ApplicationConfiguration config = new ApplicationConfiguration();
			config.setLanguage(SupportedLocale.EN);
			Path themePath = Paths.get(Constants.CSS.CSS_THEME_TEST);
			Theme auto = new Theme(Constants.CSS.CSS_THEME_NAME_TEST, themePath);
			config.setTheme(auto);

			try {
				writeConfigFile(config);
			} catch (IOException e) {
				LOG.error("Se produjo un error inesperado al escribir fichero de configuración. Traza: {0}", e);
			}

			return config;

		}

		/**
		 * Obtiene la configuración de la aplicación almacenada en el fichero
		 * application.conf
		 * 
		 * @return Objeto ApplicationConfiguration con la configuración actual de la
		 *         aplicación
		 */
		private static ApplicationConfiguration readFromFile() {
			ApplicationConfiguration config = null;

			File fi = new File(CONFIG_FILE);

			if (fi.exists()) {
				try (FileInputStream fis = new FileInputStream(fi);
						ObjectInputStream ois = new ObjectInputStream(fis)) {
					config = (ApplicationConfiguration) ois.readObject();
				} catch (FileNotFoundException e) {
					LOG.error("Excepción FileNotFound al leer configuración de app desde fichero: {0}", e);
				} catch (IOException e) {
					LOG.error("Excepción IOException al leer configuración de app desde fichero: {0}", e);
				} catch (ClassNotFoundException e) {
					LOG.error("Excepción ClassNotFound al leer configuración de app desde fichero: {0}", e);
				}
			}

			return config;

		}

		/**
		 * Escribe en el fichero application.config el objeto ApplicationConfiguration
		 * pasado por parámetro
		 * 
		 * @param config
		 * @throws IOException
		 * @see {@link ApplicationConfiguration}
		 */
		private static void writeConfigFile(ApplicationConfiguration config) throws IOException {
			try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE, false);
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {

				oos.writeObject(config);
			}
		}
	}
}
