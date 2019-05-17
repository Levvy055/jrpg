package pl.hopelew.jrpg.world;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.imageio.ImageIO;

import org.mapeditor.core.MapObject;
import org.mapeditor.core.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class GameMap {
	private int id;
	private String name;
	private int vSize;
	private int hSize;
	private String backgroundColor;
	private List<MapTileLayer> tileLayers;
	private List<MapObject> objectLayer;
	private int maxTileHeight;

	public GameMap(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void draw(GraphicsContext g) throws InvocationTargetException, InterruptedException {
		if (tileLayers != null) {
			tileLayers.stream().forEach(l -> MapRenderer.paintTileLayer(g, l, vSize, hSize, maxTileHeight));
		}
		if (objectLayer != null) {
			objectLayer.stream().forEach(l -> MapRenderer.paintObjectLayer(g, l, hSize, vSize));
		}
	}

	private static class MapRenderer {

		/**
		 * Draws all map tiles on provided canvas graphic.
		 * 
		 * @param g
		 * @param l
		 * @param height
		 * @param width
		 * @param maxTileHeight
		 */
		public static void paintTileLayer(GraphicsContext g, MapTileLayer l, int height, int width, int maxTileHeight) {
			final Rectangle clip = new Rectangle((int) g.getCanvas().getWidth(), (int) g.getCanvas().getHeight());
			final int tileWidth = 32, tileHeight = 32;
			var bounds = l.getBounds();

			g.translate(bounds.getMinX() * tileWidth, bounds.getMinY() * tileHeight);
			clip.translate((int) -bounds.getMinX() * tileWidth, (int) (-bounds.getMinY() * tileHeight));

			clip.height += maxTileHeight;

			final int startX = Math.max(0, clip.x / tileWidth);
			final int startY = Math.max(0, clip.y / tileHeight);
			final int endX = Math.min(width, (int) Math.ceil(clip.getMaxX() / tileWidth));
			final int endY = Math.min(height, (int) Math.ceil(clip.getMaxY() / tileHeight));

			for (int x = startX; x < endX; ++x) {
				for (int y = startY; y < endY; ++y) {
					final Tile tile = l.getTileAt(x, y);
					if (tile == null) {
						continue;
					}
					Image image = convertToFxImage(tile.getImage());
					if (image == null) {
						continue;
					}

					Point drawLoc = new Point(x * tileWidth, (y + 1) * tileHeight - 32);

					drawLoc.x += l.getOffsetX() != null ? l.getOffsetX() : 0;
					drawLoc.x += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getX()
							: 0;

					drawLoc.y += l.getOffsetY() != null ? l.getOffsetY() : 0;
					drawLoc.y += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getY()
							: 0;

					g.drawImage(image, drawLoc.x, drawLoc.y);
				}
			}

			g.translate(-bounds.getMinX() * tileWidth, -bounds.getMinY() * tileHeight);
		}

		/**
		 * Draws objects on the map. Now only Tile and ellipse partially supported.
		 * 
		 * @param g
		 * @param l
		 * @param width
		 * @param height
		 */
		public static void paintObjectLayer(GraphicsContext g, MapObject mo, int width, int height) {
			final Dimension tsize = new Dimension(32, 32);
			final Rectangle bounds = new Rectangle(width, height);

			g.translate(bounds.x * tsize.width, bounds.y * tsize.height);
			if (mo.isVisible() != null && !mo.isVisible()) {
				return;
			}
			final double ox = mo.getX();
			final double oy = mo.getY();
			final Double objectWidth = mo.getWidth();
			final Double objectHeight = mo.getHeight();
			final double rotation = mo.getRotation();
			final Tile tile = mo.getTile();
			Image objectImage;
			if (tile != null && (objectImage = convertToFxImage(tile.getImage())) != null) {
				g.rotate(Math.toRadians(rotation));
				g.drawImage(objectImage, (int) ox, (int) oy);
				Affine old = g.getTransform();
				g.setTransform(old);
			} else if (objectWidth == null || objectWidth == 0 || objectHeight == null || objectHeight == 0) {
				// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				// RenderingHints.VALUE_ANTIALIAS_ON); TODO: hints<-
				// g.applyEffect();
				g.setFill(Color.BLACK);
				g.fillOval((int) ox + 1, (int) oy + 1, 10, 10);
				g.setFill(Color.ORANGE);
				g.fillOval((int) ox, (int) oy, 10, 10);
				// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				// RenderingHints.VALUE_ANTIALIAS_OFF);
			} else {
				g.setFill(Color.BLACK);
				g.fillRect((int) ox + 1, (int) oy + 1, mo.getWidth().intValue(), mo.getHeight().intValue());
				g.setFill(Color.ORANGE);
				g.fillRect((int) ox, (int) oy, mo.getWidth().intValue(), mo.getHeight().intValue());
			}
			final String s = mo.getName() != null ? mo.getName() : "(null)";
			g.setFill(Color.BLACK);
			g.fillText(s, (int) (ox - 5) + 1, (int) (oy - 5) + 1);
			g.setFill(Color.WHITE);
			g.fillText(s, (int) (ox - 5), (int) (oy - 5));

			g.translate(-bounds.x * tsize.width, -bounds.y * tsize.height);
		}

		private static Image convertToFxImage(final BufferedImage imageAwt) {
			Image image = null;
			try {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(imageAwt, "png", outputStream);
				outputStream.flush();
				ByteArrayInputStream in = new ByteArrayInputStream(outputStream.toByteArray());
				image = new Image(in);
			} catch (IOException e) {
				e.printStackTrace();
				log.warn("Image conversion problem of tile. Exc: {}", e);
			}
			return image;
		}
	}

}
