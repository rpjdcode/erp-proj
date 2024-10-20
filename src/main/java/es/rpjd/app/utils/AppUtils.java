package es.rpjd.app.utils;

import java.io.File;

import es.rpjd.app.constants.Constants;

public class AppUtils {
	
	private AppUtils() {}
	
	public static final String APP_USER_HOME_DIRECTORY = String.format("%s%s%s", System.getProperty("user.home"), File.separator, Constants.APP_NAME);
	public static final String APP_DATA_DIRECTORY = String.format("%s%s%s", System.getenv("APPDATA"), File.separator, Constants.APP_NAME);
	
	/**
	 * Método encargado de generar la carpeta de la aplicación correspondiente al
	 * perfil del usuario (User Home)
	 */
	public static void createAppUserHomeDirectory() {
		File directory = new File(APP_USER_HOME_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdir();
		}

	}
	
	public static void createAppDataDirectory() {
		File dir = new File(APP_DATA_DIRECTORY);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}
	
	/**
	 * Método encargado de indicar si el directorio de la aplicación del perfil de usuario
	 * ya se encuentra creado
	 * @return true si está creado, false si no
	 */
	public static boolean isAppUserHomeDirectoryCreated() {
		File dir = new File(APP_USER_HOME_DIRECTORY);
		return dir.exists();
	}
	
	/**
	 * Indica si el directorio de la aplicación en el AppData del usuario ya ha sido creado
	 * @return true si existe, false si no existe
	 */
	public static boolean isAppDataDirectoryCreated() {
		File dir = new File(APP_DATA_DIRECTORY);
		return dir.exists();
	}
	
	
}
