package pl.hopelew.jrpg.entities.data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import pl.hopelew.jrpg.utils.Res;
import pl.hopelew.jrpg.utils.ResType;

/**
 * Graphics class controlling displaying object animations and images.
 * {@link #gridX} is a number of columns in sprite main image. {@link #gridY} is
 * a number of rows in sprite main image.
 * 
 * @author lluka
 *
 */
public class Sprite {
	private int gridX;
	private int gridY;
	private Rectangle clippingBounds;
	private Image image;
	private @Getter ImageView imgView;
	private Res res;

	public Sprite(Res res) throws Exception {
		this.res = res;
		if (res.getType() != ResType.SPRITE) {
			throw new Exception("Res Type should be SPRITE, was " + res.getType());
		}
		
		clippingBounds = new Rectangle(res.getW(), res.getH());
		imgView = new ImageView(image);
		imgView.setClip(clippingBounds);
	}

}
