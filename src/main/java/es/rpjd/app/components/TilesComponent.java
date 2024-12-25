package es.rpjd.app.components;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.rpjd.app.constants.Constants;
import es.rpjd.app.hibernate.entity.Product;
import es.rpjd.app.i18n.I18N;
import es.rpjd.app.service.CustomPropertyService;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.ImageMask;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Clase que brinda soporte sencillo para la utilización de Tiles personalizados
 */
@Component
public class TilesComponent {
	
	private final CustomPropertyService customPropertyService;

	@Autowired
	public TilesComponent(CustomPropertyService customPropertyService) {
		this.customPropertyService = customPropertyService;
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
	public Tile createProductImageTile(byte[] imagen, Product product) {
		String productPropertyName = product.getPropertyName();
		String productName = (productPropertyName.contains(Constants.APP_I18N_DEFAULT_PREFIX))
				? I18N.getString(productPropertyName)
				: customPropertyService.getProperty(productPropertyName);
		Tile t = TileBuilder.create()
				.skinType(SkinType.IMAGE)
				.prefSize(TILE_WIDTH, TILE_HEIGHT)
				.textSize(TextSize.BIGGER)
				.title(product.getProductCode())
				.titleAlignment(TextAlignment.CENTER)
				.image((imagen != null) ? new Image(new ByteArrayInputStream(imagen)) : null)
				.imageMask(ImageMask.ROUND)
				.text(productName)
				.styleClass(Constants.CSS.CSS_STYLE_CLASS_TILE_ORDER)
				.textAlignment(TextAlignment.CENTER)
				.build();
		t.setFocusTraversable(false);
		t.setCustomFont(Font.loadFont(TilesComponent.class.getResource(Constants.FONT_MONTSERRAT_TTF).toExternalForm(), 16));
		t.setCustomFontEnabled(true);
		t.setOnMouseEntered(e -> t.setBackgroundColor(TILE_SELECTED_COLOR));
		t.setOnMouseExited(e -> t.setBackgroundColor(TILE_DEFAULT_COLOR));
		return t;
	}
}
