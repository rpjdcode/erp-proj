package es.rpjd.app.utils;

import java.io.ByteArrayInputStream;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.ImageMask;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Clase que brinda soporte sencillo para la utilización de Tiles personalizados
 */
public class TilesUtils {

	private TilesUtils() {
	}

	private static final int TILE_WIDTH = 125;
	private static final int TILE_HEIGHT = 125;

	public static final Color TILE_DEFAULT_COLOR = Color.web("#2A2A2A");
	public static final Color TILE_SELECTED_COLOR = Color.web("#054C31");

	/**
	 * Devuelve un tile de imagen
	 * 
	 * @param imagen
	 * @param titulo
	 * @return
	 */
	public static Tile createImageTile(byte[] imagen, String titulo) {
		return TileBuilder.create().skinType(SkinType.IMAGE).prefSize(TILE_WIDTH, TILE_HEIGHT).title(titulo)
				.titleAlignment(TextAlignment.CENTER).textSize(TextSize.BIGGER)
				.image((imagen != null) ? new Image(new ByteArrayInputStream(imagen)) : null).imageMask(ImageMask.ROUND)
				.text("").textAlignment(TextAlignment.CENTER).build();
	}
}
