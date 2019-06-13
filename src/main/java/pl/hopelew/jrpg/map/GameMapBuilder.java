package pl.hopelew.jrpg.map;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapObject;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Orientation;
import org.mapeditor.core.RenderOrder;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.geometry.Rectangle2D;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.map.MapTileLayer.MapTileLayerBuilder;
import pl.hopelew.jrpg.utils.FileHandler;
import pl.hopelew.jrpg.utils.MapGenException;

/**
 * Static class to get GameMap from provided file
 * 
 * @author lluka
 *
 */
@Log4j2
public class GameMapBuilder {

	private GameMapBuilder() {
	}

	/**
	 * Builds a map from specified path
	 * 
	 * @param path TMX file with map data
	 * @return MapBase object of loaded map
	 * @throws MapGenException when cannot read file
	 */
	public static GameMap build(String mapId) throws MapGenException {
		var tileFile = "/maps/" + mapId + ".tmx";
		var dataFile = "/maps/" + mapId + ".map";
		log.info("Building map [{}]", mapId);

		var tmxMap = getTmxData(tileFile);
		if (tmxMap.getTileHeight() != 32 || tmxMap.getTileWidth() != 32) {
			throw new MapGenException("Tile Size should be 32x32. Was " + tmxMap.getTileHeight() + "x"
					+ tmxMap.getTileWidth() + " " + tileFile);
		}
		if (tmxMap.getLayerCount() == 0) {
			throw new MapGenException("No layers in file " + tileFile);
		}
		if (tmxMap.getRenderorder() != RenderOrder.RIGHT_DOWN) {
			throw new MapGenException("Wrong map render order! " + tileFile);
		}
		if (tmxMap.getOrientation() != Orientation.ORTHOGONAL) {
			throw new MapGenException("Wrong map orientation! " + tileFile);
		}
		var map = getGameMap(dataFile);
		var id = Integer.parseInt(tmxMap.getProperties().getProperty("id"));
		if (id != map.getId()) {
			throw new MapGenException("<map> and <tmx> map IDs are different for map " + mapId);
		}
		map.setBackgroundColor(tmxMap.getBackgroundcolor());
		map.setVSize(tmxMap.getHeight());
		map.setHSize(tmxMap.getWidth());
		map.setMaxTileHeight(tmxMap.getTileHeightMax());
		var layers = tmxMap.getLayers();
		List<MapTileLayer> layerTiles = layers.stream().filter(lm -> lm instanceof TileLayer).map(lm -> {
			var l = (TileLayer) lm;
			var r = l.getBounds();
			String order = l.getProperties().getProperty("Order", "0");
			Rectangle2D bounds = new Rectangle2D(r.x, r.y, r.width, r.height);
			var ml = new MapTileLayerBuilder().name(l.getName()).bounds(bounds).tileMap(l.getTileMap())
					.offsetX(l.getOffsetX()).offsetY(l.getOffsetY()).order(Integer.parseInt(order))
					.visible(l.isVisible()).build();
			return ml;
		}).sorted((l1, l2) -> l1.getOrder().compareTo(l2.getOrder())).collect(Collectors.toList());
		map.setTileLayers(layerTiles);

		List<MapObject> objects = layers.stream().filter(l -> l instanceof ObjectGroup).map(lm -> {
			var l = (ObjectGroup) lm;
			if (!lm.isVisible()) {
				l.getObjects().forEach(o -> o.setVisible(false));
			}
			return l.getObjects();
		}).flatMap(List::stream).sorted((o1, o2) -> {
			return o1.getId().compareTo(o2.getId());
		}).collect(Collectors.toList());
		map.setObjects(objects);
		return map;
	}

	/**
	 * Reads TMX data from the file
	 * 
	 * @param file
	 * @return TMX File
	 * @throws MapGenException
	 */
	private static Map getTmxData(String file) throws MapGenException {
		var tmx = new TMXMapReader();
		tmx.settings.reuseCachedTilesets = true;
		try {
			InputStream inputStream = FileHandler.getStream(file);
			var tmxMap = tmx.readMap(inputStream, File.separatorChar + "maps" + File.separatorChar);
			return tmxMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapGenException(e);
		}
	}

	private static GameMap getGameMap(String dataFile) throws MapGenException {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		try {
			Path path = FileHandler.getPath(dataFile);
			if (!Files.isReadable(path)) {
				throw new MapGenException("Error while reading <map=" + dataFile + "> file");
			}
			var in = Files.readAllLines(path).stream().collect(Collectors.joining());
			GameMap map = gson.fromJson(in, GameMap.class);
			return map;
		} catch (Exception e) {
			throw new MapGenException("Error while reading <map=" + dataFile + "> file", e);
		}
	}
}
