package es.rpjd.app.utils;

/**
 * Clase que será empleada a la hora de utilizar String.format para indicar ubicaciones de ficheros.
 * 
 * Permitirá no sobrecargar la aplicación con creación de strings que son utilizadas para indicar ubicaciones
 * de ficheros
 */
public class StringFormatUtils {
	
	private StringFormatUtils() {}

	public static final String SINGLE_PARAMETER = "%s";
	public static final String DOUBLE_PARAMETER = "%s%s";
	public static final String TRIPLE_PARAMETER = "%s%s%s";
	public static final String QUAD_PARAMETER = "%s%s%s%s";
	public static final String PENTA_PARAMETER = "%s%s%s%s%s";
	
	/**
	 * Devuelve un formato de parámetros agrupados sin espacios en base a la cantidad indicada por parámetro
	 * @param paramsQuantity
	 * @return
	 */
	public static String getCustomizableParameterFormat(int paramsQuantity) {
		
		String ret = "";
		
		for (int i = 0; i < paramsQuantity; i++) {
			ret = ret.concat(SINGLE_PARAMETER);
		}
		
		return ret;
	}
}
