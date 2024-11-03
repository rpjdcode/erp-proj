package es.rpjd.app;

import java.io.File;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.utils.AppUtils;

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
		System.setProperty(Constants.LOG_PROPERTY, String.format("%s%s%s", currentDirectory, File.separator, "log"));
		System.setProperty(Constants.CONFIG_PROPERTY, String.format("%s%s%s%s%s", currentDirectory, File.separator, "dev", File.separator, "application.conf"));
	}
	
	/**
	 * Método encargado de preparar la ejecución de la app para un entorno productivo (release)
	 */
	private static final void initProductionEnvironment() {
		if (!AppUtils.isAppDataDirectoryCreated()) {
			AppUtils.createAppDataDirectory();
		}
		System.setProperty(Constants.LOG_PROPERTY, String.format("%s%s%s", AppUtils.APP_DATA_DIRECTORY, File.separator, "log"));
		System.setProperty(Constants.CONFIG_PROPERTY, String.format("%s%s%s", AppUtils.APP_DATA_DIRECTORY, File.separator, "application.conf"));
	}
	
	public static void main(String[] args) {
		
		if (args.length > 0 && args[0].equals("dev")) {
			initDevelopmentEnvironment();
		} else {
			initProductionEnvironment();
		}
		
		JavaFXApp.main(args);
	}
}
