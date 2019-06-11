package pl.hopelew.jrpg.entities.data;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.hopelew.jrpg.utils.FileHandler;
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
public class Sprite extends ImageView {
	private int gridX;
	private int gridY;
	private Image image;
	private Res res;
	private Rectangle2D[] clips;
	private double width, height;

	public Sprite(Res res) throws Exception {
		this.res = res;
		int columns = 12, rows = 8;
		if (res.getType() != ResType.SPRITE) {
			throw new Exception("Res Type should be SPRITE, was " + res.getType());
		}
		image = FileHandler.getSprite(res, SpriteImageGroup.WALK);

		width = image.getWidth() / columns;
		height = image.getHeight() / rows;

		clips = new Rectangle2D[rows * columns];
		int count = 0;
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++, count++) {
				clips[count] = new Rectangle2D( width * column, height * row, width, height);
			}
		}
		setImage(image);
		setViewport(clips[2]);

	}

	public void update() {
		
	}

}
