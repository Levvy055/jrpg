package pl.hopelew.jrpg.map;

import org.mapeditor.core.Tile;

import javafx.geometry.Rectangle2D;
import lombok.Builder;
import lombok.Getter;

public class MapTileLayer implements Comparable<MapTileLayer> {
	private @Getter String name;
	private @Getter Rectangle2D bounds;
	private Tile[][] tileMap;
	private @Getter Integer offsetX;
	private @Getter Integer offsetY;
	/** Player order is 100 */
	private @Getter Integer order;
	private @Getter boolean visible;

	@Builder
	private MapTileLayer(String name, Rectangle2D bounds, Tile[][] tileMap, Integer offsetX, Integer offsetY,
			Integer order, boolean visible) {
		this.bounds = bounds;
		this.tileMap = tileMap;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.order = order;
		this.visible = visible;
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
		return getBounds().contains(tx, ty) ? tileMap[ty][tx] : null;
	}

	@Override
	public int compareTo(MapTileLayer o) {
		return order.compareTo(o.getOrder());
	}

}
