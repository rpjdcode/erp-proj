package es.rpjd.app.i18n;

import java.util.Locale;

/**
 * Enumerado que representa los idiomas soportados por la aplicación
 */
public enum SupportedLocale {
	
	ES,
	EN;
	
	public static SupportedLocale localeCasting(Locale loc) {
		SupportedLocale ret = null;
		try {
			ret = SupportedLocale.valueOf(loc.getCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
