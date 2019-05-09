package pl.hopelew.jrpg.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

import org.mapeditor.core.MapObject;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import lombok.Data;

@Data
public class GameMap {
	private int id;
	private String name;
	private int vSize;
	private int hSize;
	private String backgroundColor;
	private List<MapTileLayer> tileLayers;
	private List<MapObjectLayer> objectLayers;
	private List<TileLayer> tLayers;
	private List<ObjectGroup> oLayers;
	private int maxTileHeight;

	public GameMap(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Node getGrid() {
		var sn = new SwingNode();
		var c = new JComponent() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				if (tLayers != null) {
					tLayers.stream().forEach(l -> MapRenderer.paintTileLayer(g2d, l, vSize, hSize, maxTileHeight));
				}
				if (oLayers != null) {
					oLayers.stream().forEach(l -> MapRenderer.paintObjectGroup(g2d, l, hSize, vSize));
				}
			}
		};
		sn.setContent(c);
		return sn;
	}

	private static class MapRenderer {

		public static void paintTileLayer(Graphics2D g, TileLayer layer, int height, int width, int maxTileHeight) {
			final Rectangle clip = g.getClipBounds();
			final int tileWidth = 32, tileHeight = 32;
			final Rectangle bounds = layer.getBounds();

			g.translate(bounds.x * tileWidth, bounds.y * tileHeight);
			clip.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);

			clip.height += maxTileHeight;

			final int startX = Math.max(0, clip.x / tileWidth);
			final int startY = Math.max(0, clip.y / tileHeight);
			final int endX = Math.min(width, (int) Math.ceil(clip.getMaxX() / tileWidth));
			final int endY = Math.min(height, (int) Math.ceil(clip.getMaxY() / tileHeight));

			for (int x = startX; x < endX; ++x) {
				for (int y = startY; y < endY; ++y) {
					final Tile tile = layer.getTileAt(x, y);
					if (tile == null) {
						continue;
					}
					final Image image = tile.getImage();
					if (image == null) {
						continue;
					}

					Point drawLoc = new Point(x * tileWidth, (y + 1) * tileHeight - image.getHeight(null));

					// Add offset from tile layer property
					drawLoc.x += layer.getOffsetX() != null ? layer.getOffsetX() : 0;
					drawLoc.y += layer.getOffsetY() != null ? layer.getOffsetY() : 0;

					// Add offset from tileset property
					drawLoc.x += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getX()
							: 0;
					drawLoc.y += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getY()
							: 0;

					g.drawImage(image, drawLoc.x, drawLoc.y, null);
				}
			}

			g.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);
		}

		public static void paintObjectGroup(Graphics2D g, ObjectGroup group, int width, int height) {
			final Dimension tsize = new Dimension(32, 32);
			assert tsize.width != 0 && tsize.height != 0;
			final Rectangle bounds = new Rectangle(width, height);

			g.translate(bounds.x * tsize.width, bounds.y * tsize.height);

			for (MapObject mo : group) {
				final double ox = mo.getX();
				final double oy = mo.getY();
				final Double objectWidth = mo.getWidth();
				final Double objectHeight = mo.getHeight();
				final double rotation = mo.getRotation();
				final Tile tile = mo.getTile();

				if (tile != null) {
					Image objectImage = tile.getImage();
					AffineTransform old = g.getTransform();
					g.rotate(Math.toRadians(rotation));
					g.drawImage(objectImage, (int) ox, (int) oy, null);
					g.setTransform(old);
				} else if (objectWidth == null || objectWidth == 0 || objectHeight == null || objectHeight == 0) {
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setColor(Color.black);
					g.fillOval((int) ox + 1, (int) oy + 1, 10, 10);
					g.setColor(Color.orange);
					g.fillOval((int) ox, (int) oy, 10, 10);
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				} else {
					g.setColor(Color.black);
					g.drawRect((int) ox + 1, (int) oy + 1, mo.getWidth().intValue(), mo.getHeight().intValue());
					g.setColor(Color.orange);
					g.drawRect((int) ox, (int) oy, mo.getWidth().intValue(), mo.getHeight().intValue());
				}
				final String s = mo.getName() != null ? mo.getName() : "(null)";
				g.setColor(Color.black);
				g.drawString(s, (int) (ox - 5) + 1, (int) (oy - 5) + 1);
				g.setColor(Color.white);
				g.drawString(s, (int) (ox - 5), (int) (oy - 5));
			}

			g.translate(-bounds.x * tsize.width, -bounds.y * tsize.height);
		}
	}
}
