package es.rpjd.app;

import java.io.File;

import es.rpjd.app.utils.AppUtils;

public class Main {
	
	private static final String LOG_PROPERTY = "LOG_PATH";
	
	/**
	 * Método encargado de preparar la ejecución de la app para un entorno de desarrollo.
	 * 
	 * Actualmente, esto solamente afecta a la ubicación de guardado de los logs
	 */
	private static final void initDevelopmentEnvironment() {
		String currentDirectory = System.getProperty("user.dir");
		System.setProperty(LOG_PROPERTY, String.format("%s%s%s", currentDirectory, File.separator, "log"));
	}
	
	/**
	 * Método encargado de preparar la ejecución de la app para un entorno productivo (release)
	 */
	private static final void initProductionEnvironment() {
		if (!AppUtils.isAppDataDirectoryCreated()) {
			AppUtils.createAppDataDirectory();
		}
		System.setProperty(LOG_PROPERTY, String.format("%s%s%s", AppUtils.APP_DATA_DIRECTORY, File.separator, "log"));
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
