package pl.hopelew.jrpg.world;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Orientation;
import org.mapeditor.core.RenderOrder;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.utils.MapGenException;

@Log4j2
public class MapBuilder {

	private MapBuilder() {
	}

	/**
	 * Builds a map from specified path
	 * 
	 * @param path TMX file with map data
	 * @return MapBase object of loaded map
	 * @throws MapGenException when cannot read file
	 */
	public static GameMap build(Path path) throws MapGenException {
		log.info("Building map " + path);
		var tmxMap = getTmxData(path);
		var id = Integer.parseInt(tmxMap.getProperties().getProperty("id"));
		var name = tmxMap.getProperties().getProperty("name");
		var map = new GameMap(id, name);
		if (tmxMap.getTileHeight() != 32 || tmxMap.getTileWidth() != 32) {
			throw new MapGenException(
					"Tile Size should be 32x32. Was " + tmxMap.getTileHeight() + "x" + tmxMap.getTileWidth(), path);
		}
		if (tmxMap.getLayerCount() == 0) {
			throw new MapGenException("No layers in file", path);
		}
		if (tmxMap.getRenderorder() != RenderOrder.RIGHT_DOWN) {
			throw new MapGenException("Wrong map render order!", path);
		}
		if (tmxMap.getOrientation() != Orientation.ORTHOGONAL) {
			throw new MapGenException("Wrong map orientation!", path);
		}
		map.setBackgroundColor(tmxMap.getBackgroundcolor());
		map.setVSize(tmxMap.getHeight());
		map.setHSize(tmxMap.getWidth());
		map.setMaxTileHeight(tmxMap.getTileHeightMax());
		var layers = tmxMap.getLayers();
		List<TileLayer> layerst = layers.stream().filter(l -> l instanceof TileLayer).map(m -> (TileLayer) m)
				.collect(Collectors.toList());
		List<ObjectGroup> layerso = layers.stream().filter(l -> l instanceof ObjectGroup).map(m -> (ObjectGroup) m)
				.collect(Collectors.toList());
		map.setTLayers(layerst);
		map.setOLayers(layerso);
		return map;
	}

	/**
	 * Reads TMX data
	 * 
	 * @param path
	 * @return TMX File
	 * @throws MapGenException
	 */
	private static Map getTmxData(Path path) throws MapGenException {
		var tmx = new TMXMapReader();
		tmx.settings.reuseCachedTilesets = true;
		log.warn("Path: {}", path);
		if (!tmx.accept(path.toFile())) {
			throw new MapGenException(new IOException("Wrong file: " + path.toString()));
		}
		try {
			var tmxMap = tmx.readMap(Files.newInputStream(path), path.getParent().toString() + File.separatorChar);
			return tmxMap;
		} catch (Exception e) {
			throw new MapGenException(e);
		}
	}
}
