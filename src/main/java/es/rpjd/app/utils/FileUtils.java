package es.rpjd.app.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Método que proporciona métodos auxiliares de trabajo con ficheros
 */
public class FileUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);
	
	private FileUtils() {}


	/**
	 * Método encargado de crear un nuevo fichero en una ruta indicada.
	 * @param path
	 * @param fileName
	 * @return Objeto File si se crea el fichero o si ya existe, null si no se crea o se produce un error
	 */
	public static File createNewFile(String path, String fileName) {
		File f = new File(path.concat(fileName));
		if (f.exists()) {
			return f;
		}
		
		try {
			if (!f.createNewFile()) {
				f = null;
			}
		} catch (IOException e) {
			LOG.error("Error durante la creación de fichero {}", f.getAbsolutePath());
			f = null;
			
		}
		
		return f;
	}
	
	public static File createNewFile(File fi) {
		if (!fi.exists()) {
			try {
				fi.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return fi;
	}
	
	/**
	 * Método encargado de crear un nuevo directorio, y retorna un objeto file con la referencia
	 * a ese directorio.
	 * @param directoryPath
	 * @return Objeto file si se crea el directorio, null si ya existe o no se pudo crear
	 */
	public static File createNewDirectory(String directoryPath) {
		File dir = new File(directoryPath);
		
		if (dir.exists()) {
			return null;
		}
		
		if (!dir.mkdirs()) {
			return null;
		}
		
		return dir;
	}
	
	public static List<Path> obtainFilesFromResourcesDirectory(String directory) {
		Path dir = Paths.get("src/main/resources/css");
		List<Path> ret = null;
		try (Stream<Path> files = Files.walk(dir)){
			
			ret = files
					.filter(Files::isRegularFile)
					.filter(file -> file.toString().endsWith(".css"))
					.toList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
		
	}
	
	/**
	 * Devuelve un objeto File si ya existe
	 * @return
	 */
	public static File getFile(String path) {
		File fi = new File(path);
		
		if (!fi.exists()) {
			return null;
		}
		
		return fi;
	}
}
