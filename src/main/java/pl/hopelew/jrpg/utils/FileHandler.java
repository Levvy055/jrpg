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
import pl.hopelew.jrpg.world.MapBase;

public class FileHandler {
	private static @Getter FileHandler instance;
	private static final String FILENAME = "app.config";
	private static @Getter Configuration config;
	private static Map<Res, Image> images = new HashMap<>();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public FileHandler() throws IOException {
		instance = this;
		loadConfig();
	}

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

	public void loadConfig() throws JsonSyntaxException, IOException {
		var file = new File(FILENAME);
		if (file.createNewFile()) {
			config = new Configuration();
			saveConfig();
		} else {
			var br = new BufferedReader(new FileReader(FILENAME));
			config = gson.fromJson(br, Configuration.class);
			if (config == null || config.isValid()) {
				System.out.println("Wrong config file! Rewriting.");
				config = new Configuration();
				saveConfig();
			}
		}
	}

	public MapBase getMap(String id) throws IOException {
		var pathname = "maps/" + id + ".map";
		Path path;
		try {
			path = getPath(pathname);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new IOException("Map " + id + " read error! >" + pathname, e);
		}
		if (Files.notExists(path)) {
			throw new IOException("Map " + id + " does not exists! >" + path.toString());
		}
		return null;
	}

	/**
	 * Checks if all resources are available and load them to map
	 */
	public static void validateResourcesAndLoad() {
		for (Res res : Res.values()) {
			try {
				var path = getPath(res.getPath());
				if (Files.notExists(path)) {
					System.out.println("Can't load resource: '" + path + "'");
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

	public static Image getFxImage(Res key) {
		return images.get(key);
	}

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
