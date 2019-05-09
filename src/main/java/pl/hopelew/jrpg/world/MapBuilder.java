package pl.hopelew.jrpg.world;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MapBuilder {

	private MapBuilder() {
	}

	/**
	 * Builds a map from specified path
	 * 
	 * @param path TMX file with map data
	 * @return MapBase object of loaded map
	 * @throws Exception when cannot read file
	 */
	public static MapBase buildMap(Path path) throws Exception {
		var tmxMap = getTmxData(path);
		// MapBase map =new GameMap(id, vSize, hSize, name);
		tmxMap.getBackgroundcolor();

		return null;
	}

	public static MapBase buildWorldMap(Path path) throws Exception {
		var tmxMap = getTmxData(path);
		var bgColor = tmxMap.getBackgroundcolor();
		var mh = tmxMap.getHeight();
		var mw = tmxMap.getWidth();
		var id = Integer.parseInt(tmxMap.getProperties().getProperty("id"));
		var name = tmxMap.getProperties().getProperty("name");
		MapBase map = new WorldMap(id, mh, mw, name, bgColor);
		return null;
	}

	/**
	 * Reads TMX data
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	private static Map getTmxData(Path path) throws Exception {
		var tmx = new TMXMapReader();
		tmx.settings.reuseCachedTilesets = true;
		if (!tmx.accept(path.toFile())) {
			throw new IOException("Wrong file: " + path.toString());
		}
		var tmxMap = tmx.readMap(Files.newInputStream(path), path.getParent().toString() + File.separatorChar);
		return tmxMap;
	}
}
