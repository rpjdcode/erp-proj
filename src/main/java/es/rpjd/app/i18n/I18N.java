package es.rpjd.app.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase utilizada para la internacionalización dentro de la aplicación
 */
public class I18N {

	private I18N() {}
	
    private static final String BASE_NAME = "i18n/messages";
    
    private static ResourceBundle resourceBundle;

    /**
     * Método que se carga las propiedades de internacionalización
     * en base al idioma proporcionado por parámetro
     * @param locale
     */
    public static void load(Locale locale) {
    	boolean supported = isLocaleSupported(locale);
    	
    	if (supported) {
    		resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
    	} else {
    		resourceBundle = ResourceBundle.getBundle(BASE_NAME);
    	}
        
    }
    
    /**
     * Método que verifica que el Locale proporcionado por parámetro es soportado en la aplicación, comprobando
     * que el código de país alfa-2 pueda ser transformado en un SupportedLocale
     * @param locale
     * @return
     * @see SupportedLocale
     */
    private static boolean isLocaleSupported(Locale locale) {

    	boolean valid = false;
    	try {
    		SupportedLocale.valueOf(locale.getCountry());
    		valid = true;
    	} catch (Exception e) {
    		// Idioma no soportado
    		e.printStackTrace();
    	}
    	
    	return valid;
    }

    /**
     * Método que devuelve una cadena de mensaje en base a la clave proporcionada
     * por parámetro. El mensaje obtenido depende del idioma utilizado por la aplicación
     * @param key Clave de mensaje
     * @return Mensaje internacionalizado
     */
    public static String getString(String key) {
        return resourceBundle.getString(key);
    }
    
    public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
