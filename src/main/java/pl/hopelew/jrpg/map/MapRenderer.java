package pl.hopelew.jrpg.map;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.mapeditor.core.MapObject;
import org.mapeditor.core.Tile;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.utils.FileHandler;

@Log4j2
public class MapRenderer {
	private static final int tileWidth = 32, tileHeight = 32;
	private static final Dimension tsize = new Dimension(32, 32);
	private Canvas upperLayer;
	private Canvas entitiesCanvas;
	private Canvas bottomLayer;

	public MapRenderer(Canvas bottomLayer, Canvas entitiesCanvas, Canvas upperLayer) {
		this.bottomLayer = bottomLayer;
		this.entitiesCanvas = entitiesCanvas;
		this.upperLayer = upperLayer;

	}

	/**
	 * Draws all map tiles on provided canvas graphic.
	 * 
	 * @param g
	 * @param layer
	 * @param height
	 * @param width
	 * @param maxTileHeight
	 */
	private void drawTileLayer(GraphicsContext g, MapTileLayer layer, int height, int width, int maxTileHeight) {
		final Rectangle clip = new Rectangle((int) g.getCanvas().getWidth(), (int) g.getCanvas().getHeight());
		var bounds = layer.getBounds();

		g.translate(bounds.getMinX() * tileWidth, bounds.getMinY() * tileHeight);
		clip.translate((int) -bounds.getMinX() * tileWidth, (int) (-bounds.getMinY() * tileHeight));

		clip.height += maxTileHeight;

		final int startX = Math.max(0, clip.x / tileWidth);
		final int startY = Math.max(0, clip.y / tileHeight);
		final int endX = Math.min(width, (int) Math.ceil(clip.getMaxX() / tileWidth));
		final int endY = Math.min(height, (int) Math.ceil(clip.getMaxY() / tileHeight));

		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {

				drawTile(g, x, y, layer);
			}
		}

		g.translate(-bounds.getMinX() * tileWidth, -bounds.getMinY() * tileHeight);
	}

	/**
	 * Draws provided tile layer
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param layer
	 */
	private void drawTile(GraphicsContext g, int x, int y, MapTileLayer layer) {
		final Tile tile = layer.getTileAt(x, y);
		if (tile == null) {
			return;
		}
		Image image = FileHandler.convertToFxImage(tile.getImage());
		if (image == null) {
			log.warn("No image for tile at {}/{} with ID {} of TileSet {}", x, y, tile.getId(),
					tile.getTileSet().getName());
			return;
		}

		Point drawLoc = new Point(x * tileWidth, (y + 1) * tileHeight - 32);

		drawLoc.x += layer.getOffsetX() != null ? layer.getOffsetX() : 0;
		drawLoc.x += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getX() : 0;

		drawLoc.y += layer.getOffsetY() != null ? layer.getOffsetY() : 0;
		drawLoc.y += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getY() : 0;

		g.drawImage(image, drawLoc.x, drawLoc.y);
	}

	/**
	 * Draws objects on the map. Now only Tile and ellipse partially supported.
	 * 
	 * @param g
	 * @param l
	 * @param width
	 * @param height
	 */
	public void drawObject(GraphicsContext g, MapObject mo, int width, int height) {
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
		if (tile != null) {
			Image objectImage = FileHandler.convertToFxImage(tile.getImage());
			if (objectImage == null) {
				log.warn("Tile of object {} is null!", mo.getName());
			} else {
				Affine old = g.getTransform();
				g.rotate(rotation);
				g.drawImage(objectImage, (int) ox, (int) oy);
				g.setTransform(old);
			}
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

	public void renderBottomTileLayers(GameMap map) {
		map.getTileLayers().stream().filter(tl -> tl.getOrder() < 100).forEachOrdered(tl -> {
			drawTileLayer(bottomLayer.getGraphicsContext2D(), tl, map.getVSize(), map.getHSize(),
					map.getMaxTileHeight());
		});
	}

	public void renderBottomObjects(GameMap map) {
		map.getObjects().stream().filter(mo -> {
			String orderString = mo.getProperties().getProperty("Order");
			int order = orderString != null ? Integer.parseInt(orderString) : 0;
			return order < 100;
		}).forEachOrdered(mo -> {
			drawObject(bottomLayer.getGraphicsContext2D(), mo, map.getVSize(), map.getHSize());
		});
	}

	public void renderUpperTileLayers(GameMap map) {
		map.getTileLayers().stream().filter(tl -> tl.getOrder() > 100).forEachOrdered(tl -> {
			drawTileLayer(upperLayer.getGraphicsContext2D(), tl, map.getVSize(), map.getHSize(),
					map.getMaxTileHeight());
		});
	}

	public void renderUpperObjects(GameMap map) {
		map.getObjects().stream().filter(mo -> {
			String orderString = mo.getProperties().getProperty("Order");
			int order = orderString != null ? Integer.parseInt(orderString) : 0;
			return order < 100;
		}).forEachOrdered(mo -> {
			drawObject(upperLayer.getGraphicsContext2D(), mo, map.getVSize(), map.getHSize());
		});
	}

	public void clearLayers() {
		clearLayer(bottomLayer);
		clearLayer(entitiesCanvas);
		clearLayer(upperLayer);
	}

	private void clearLayer(Canvas canvas) {
		var gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
}