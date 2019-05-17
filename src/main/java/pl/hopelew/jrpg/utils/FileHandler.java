package pl.hopelew.jrpg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.world.GameMap;
import pl.hopelew.jrpg.world.GameMapBuilder;

@Log4j2
public class FileHandler {
	private static @Getter FileHandler instance;
	private static final String FILENAME = "config.json";
	private static @Getter Configuration config;
	private static Map<Res, Image> images = new HashMap<>();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public FileHandler() throws IOException {
		instance = this;
		loadConfig();
	}

	/**
	 * Saves config to file
	 * 
	 * @throws IOException when no access to file
	 */
	public void saveConfig() throws IOException {
		if (config == null) {
			throw new IOException("No Config to save! null");
		}
		var file = new File(FILENAME);
		file.createNewFile();
		String json = gson.toJson(config);
		try (PrintWriter out = new PrintWriter(file)) {
			out.println(json);
		}
	}

	/**
	 * Loads configuration from file
	 * 
	 * @throws JsonSyntaxException
	 * @throws IOException         when no access to file
	 */
	public void loadConfig() throws JsonSyntaxException, IOException {
		var file = new File(FILENAME);
		if (file.createNewFile()) {
			log.info("Config file does not exist. Creating ...");
			config = new Configuration();
			saveConfig();
		} else {
			var br = new BufferedReader(new FileReader(FILENAME));
			config = gson.fromJson(br, Configuration.class);
			if (config == null || !config.isValid()) {
				log.warn("Wrong config file! Rewriting.");
				config = new Configuration();
				saveConfig();
			} else {
				log.info("Configuration file loaded.");
			}
		}
	}

	/**
	 * Gets game map from .map file
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GameMap getMap(String id) throws Exception {
		var pathname = "maps/" + id + ".tmx";
		Path path;
		try {
			path = getPath(pathname);
			if (Files.exists(path)) {
				return GameMapBuilder.build(path);
			}
			throw new IOException("Map " + id + " does not exists! >" + path.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new IOException("Map " + id + " read error! >" + pathname, e);
		}
	}

	/**
	 * Checks if all resources are available and load them to corresponding maps
	 */
	public static void validateResourcesAndLoad() {
		for (Res res : Res.values()) {
			try {
				var path = getPath(res.getPath());
				if (Files.notExists(path)) {
					log.error("Can't load resource {}: '{}'", res, path);
				} else {
					if (res.getType() == ResType.IMAGE) {
						images.put(res,
								new Image(FileHandler.class.getClassLoader().getResourceAsStream(res.getPath())));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns Image object
	 * 
	 * @param key associated with image
	 * @return Image from cached map
	 */
	public static Image getFxImage(Res key) {
		return images.get(key);
	}

	/**
	 * Gets Path object from relative String path. It's different when running from
	 * jar and IDE.
	 * 
	 * @param relPath relative path to resource
	 * @return absolute Path to resource
	 * @throws URISyntaxException when wrong path
	 */
	public static Path getPath(String relPath) throws URISyntaxException {
		URI url = FileHandler.class.getClassLoader().getResource(relPath).toURI();
		Path path;
		if (url.getScheme().contentEquals("file")) {
			path = Paths.get(url);
		} else {
			FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
			path = fs.getPath("modules", "jrpg", relPath);
		}
		return path;
	}
}
