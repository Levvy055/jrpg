package pl.hopelew.jrpg.world;

import java.nio.file.Path;

import org.mapeditor.io.TMXMapReader;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MapBuilder {

	private MapBuilder() {
	}

	/**
	 * Builds a map from specified path
	 * 
	 * @param path
	 * @return
	 */
	public static MapBase build(Path path) {
		var tmx = new TMXMapReader();
		tmx.settings.reuseCachedTilesets = true;
		var accept = tmx.accept(path.toFile());
		log.info("File {} is accpted: {}", path, accept);
		return null;
	}
}
