package pl.hopelew.jrpg.entities.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
	private Map<SpriteImageGroup, Pair<Image, List<Rectangle2D>>> images;
	private Res res;

	public Sprite(Res res) throws Exception {
		this.res = res;
		if (!(res.getType() == ResType.SPRITE_FULL || res.getType() == ResType.SPRITE)) {
			throw new Exception("Res Type should be SPRITE_FULL, was " + res.getType());
		}
		images = new HashMap<>();
		FileHandler.getSprites(res).forEach((k, v) -> {
			var width = v.getWidth() / k.getMaxCols();
			var height = v.getHeight() / k.getMaxRows();
			ArrayList<Rectangle2D> clips = new ArrayList<>(k.getCols() * k.getRows());
			int count = 0;
			for (int row = 0; row < k.getRows(); row++) {
				for (int column = 0; column < k.getCols(); column++, count++) {
					clips.add(count, new Rectangle2D(width * column, height * row, width, height));
				}
			}
			var clipedImages = new ImmutablePair<Image, List<Rectangle2D>>(v, clips);
			images.put(k, clipedImages);
		});
		if (!images.containsKey(SpriteImageGroup.WALK)) {
			images.put(SpriteImageGroup.WALK, images.values().stream().findFirst().get());
		}
	}

	public void update(Position position, EntityState state) {
		setX(position.getX() * 32);
		setY(position.getY() * 32);
		if (state == EntityState.DEFAULT) {
			setImage(images.get(SpriteImageGroup.WALK).getLeft());
			setViewport(images.get(SpriteImageGroup.WALK).getRight().get(0));
		} else if (state == EntityState.WALKING) {
			setImage(images.get(SpriteImageGroup.WALK).getLeft());
			switch (position.getDirection()) {
			case EAST:
				setViewport(images.get(SpriteImageGroup.WALK).getRight().get(6));
				break;
			case NORTH:
				setViewport(images.get(SpriteImageGroup.WALK).getRight().get(9));
				break;
			case SOUTH:
				setViewport(images.get(SpriteImageGroup.WALK).getRight().get(0));
				break;
			case WEST:
				setViewport(images.get(SpriteImageGroup.WALK).getRight().get(3));
				break;
			}
		}
	}

}
