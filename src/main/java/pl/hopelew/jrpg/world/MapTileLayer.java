package pl.hopelew.jrpg.world;

import org.mapeditor.core.Tile;

import javafx.geometry.Rectangle2D;
import lombok.Builder;
import lombok.Getter;

public class MapTileLayer {
	private @Getter Rectangle2D bounds;
	private Tile[][] tileMap;
	private @Getter Integer x;
	private @Getter Integer y;
	private @Getter Integer offsetX;
	private @Getter Integer offsetY;

	@Builder
	private MapTileLayer(Rectangle2D bounds, Tile[][] tileMap, Integer offsetX, Integer offsetY, Integer x, Integer y) {
		this.bounds = bounds;
		this.tileMap = tileMap;
		this.x = x;
		this.y = y;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	/**
	 * Returns the tile at the specified position.
	 *
	 * @param tx Tile-space x coordinate
	 * @param ty Tile-space y coordinate
	 * @return tile at position (tx, ty) or <code>null</code> when (tx, ty) is
	 *         outside this layer
	 */
	public Tile getTileAt(int tx, int ty) {
		return getBounds().contains(tx, ty) ? tileMap[ty - this.y][tx - this.x] : null;
	}

}
