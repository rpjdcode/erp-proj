package es.rpjd.app;

import java.io.File;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.utils.AppUtils;
import es.rpjd.app.utils.StringFormatUtils;

/**
 * Clase empleada para la ejecución de la aplicación.
 * Actualmente, sólo esperar recibir un argumento como parámetro de ejecución.
 * Si se indica el parámetro dev, se prepará la ejecución de la aplicación para un entorno de desarrollo,
 * que afecta a las siguientes características:
 * - Ubicación de logs
 */
public class Main {
	
	/**
	 * Método encargado de preparar la ejecución de la app para un entorno de desarrollo.
	 * 
	 * Actualmente, esto solamente afecta a la ubicación de guardado de los logs
	 */
	private static final void initDevelopmentEnvironment() {
		String currentDirectory = System.getProperty("user.dir");
		
		if (!AppUtils.isAppUserHomeDirectoryCreated()) {
			AppUtils.createAppUserHomeDirectory();
			AppUtils.createPDFDirectory();
		}
		System.setProperty(Constants.LOG_PROPERTY, String.format(StringFormatUtils.TRIPLE_PARAMETER, currentDirectory, File.separator, "log"));
		System.setProperty(Constants.CONFIG_PROPERTY, String.format(StringFormatUtils.PENTA_PARAMETER, currentDirectory, File.separator, "dev", File.separator, "application.conf"));
		System.setProperty(Constants.CUSTOM_PROPS_PROPERTY, String.format(StringFormatUtils.PENTA_PARAMETER, currentDirectory, File.separator, "dev", File.separator, "custom.properties"));
		System.setProperty(Constants.ENVIRONMENT_PROPERTY, Constants.Environment.DEVELOPMENT.getValue());
	}
	
	/**
	 * Método encargado de preparar la ejecución de la app para un entorno productivo (release)
	 */
	private static final void initProductionEnvironment() {
		if (!AppUtils.isAppDataDirectoryCreated()) {
			AppUtils.createAppDataDirectory();
		}
		if (!AppUtils.isAppUserHomeDirectoryCreated()) {
			AppUtils.createAppUserHomeDirectory();
			AppUtils.createPDFDirectory();
		}
		System.setProperty(Constants.LOG_PROPERTY, String.format(StringFormatUtils.TRIPLE_PARAMETER, AppUtils.APP_DATA_DIRECTORY, File.separator, "log"));
		System.setProperty(Constants.CONFIG_PROPERTY, String.format(StringFormatUtils.TRIPLE_PARAMETER, AppUtils.APP_DATA_DIRECTORY, File.separator, "application.conf"));
		System.setProperty(Constants.CUSTOM_PROPS_PROPERTY, String.format(StringFormatUtils.TRIPLE_PARAMETER, AppUtils.APP_DATA_DIRECTORY, File.separator, "custom.properties"));
		System.setProperty(Constants.ENVIRONMENT_PROPERTY, Constants.Environment.PRODUCTION.getValue());
	}
	
	public static void main(String[] args) {
		
		/*
		 * Inicialización de la aplicación en base de los argumentos indicados en el arranque.
		 * 
		 * Si se indica un parámetro y ese parámetro es 'dev', se inicializará una configuración de aplicación para el entorno de desarrollo.
		 * 
		 * En caso de no especificar ningún argumento o si este no es 'dev', se establece una configuración de producción
		 */
		
		if (args.length > 0 && args[0].toUpperCase().equals(Constants.Environment.DEVELOPMENT.getValue())) {
			initDevelopmentEnvironment();
		} else {
			initProductionEnvironment();
		}
		
		JavaFXApp.main(args);
	}
}
