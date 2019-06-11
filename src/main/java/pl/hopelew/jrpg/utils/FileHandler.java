package pl.hopelew.jrpg.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.entities.data.SpriteImageGroup;

@Log4j2
public class FileHandler {
	private static @Getter FileHandler instance;
	private static final String FILENAME = "config.json";
	static String OS = System.getProperty("os.name").toLowerCase();
	private static @Getter Configuration config;
	private static Map<Res, Image> images = new HashMap<>();
	private static Map<Res, Map<SpriteImageGroup, Image>> sprites = new HashMap<>();
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
	 * Checks if all resources are available and load them to corresponding maps
	 */
	public static void validateResourcesAndLoad() {
		for (Res res : Res.values()) {
			if (res.getType() != ResType.SPRITE) {
				try {
					var path = getPath(res.getPath());
					if (path == null) {
						log.error("Can't load resource {}: '{}'", res, path);
					} else {
						if (res.getType() == ResType.IMAGE) {
							images.put(res, new Image(getStream(res.getPath())));
						}
					}
				} catch (Exception e) {
					log.throwing(e);
				}
			} else {
				String p = res.getPath();
				Arrays.asList(SpriteImageGroup.values()).stream().forEach(s -> {
					try {
						String st = p + '_' + s.name().toLowerCase() + ".png";
						var path = getPath(st);
						if (path != null) {
							if (!sprites.containsKey(res)) {
								sprites.put(res, new HashMap<SpriteImageGroup, Image>(3));
							}
							sprites.get(res).put(s, new Image(getStream(st)));
						} else {
							log.error("Can't load sprite {}: '{}'", res, path);
						}
					} catch (Exception e) {
						log.throwing(e);
					}
				});
			}
		}
	}

	/**
	 * Returns javaFx Image object from cache
	 * 
	 * @param key associated with image
	 * @return Image from cached map
	 */
	public static Image getFxImage(Res key) {
		return images.get(key);
	}

	/**
	 * Returns BufferedImage from ImageIO
	 * 
	 * @param imgFilename
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getBuffImage(String imgFilename) throws IOException {
		InputStream stream = getStream(imgFilename);
		return ImageIO.read(stream);
	}

	/**
	 * Converts AWT to JavaFx Image
	 * 
	 * @param imageAwt
	 * @return
	 */
	public static Image convertToFxImage(final BufferedImage imageAwt) {
		if (imageAwt == null) {
			return null;
		}
		Image image = null;
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(imageAwt, "png", outputStream);
			outputStream.flush();
			ByteArrayInputStream in = new ByteArrayInputStream(outputStream.toByteArray());
			image = new Image(in);
		} catch (IOException e) {
			log.warn("Image conversion problem of tile. Exc: {}", e);
		}
		return image;
	}

	/**
	 * Gets Path object from relative String path. It's different when running from
	 * jar and IDE.
	 * 
	 * @param relPath relative path to resource
	 * @return absolute Path to resource
	 * @throws URISyntaxException when wrong path
	 */
	public static Path getPath(String file) throws IOException {
		file = file.replaceAll("\\\\", "/");
		URL resource = FileHandler.class.getResource(file);
		if (resource == null) {
			throw new IOException("Resource does not exist: " + file);
		} else {
			log.debug("Loaded resource: {}", resource);
		}
		String path = resource.getPath();
		if (isWindows() && path.startsWith("/")) {
			path = path.substring(1);
		}
		Path p = Paths.get(path);
		if (!Files.exists(p)) {
			FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
			p = fs.getPath("modules", resource.getPath());
			if (!Files.exists(p)) {
				throw new IOException("Resource does not exist: " + file);
			}
		}
		return p;
	}

	/**
	 * Returns File InputStream from path string
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static InputStream getStream(String file) throws IOException {
		file = file.replaceAll("\\\\", "/");
		InputStream is = FileHandler.class.getResourceAsStream(file);
		if (is != null) {
			log.debug("Loaded resource as stream: {}", file);
			return is;
		}
		Path path = getPath(file);
		URL url = new URL(path.toString());
		return url.openStream();
	}
	
	public static Map<SpriteImageGroup, Image> getSprites(Res res) {
		return sprites.get(res);
	}

	public static boolean isWindows() {
		return OS.contains("win");
	}

	public static boolean isMac() {
		return OS.contains("mac");
	}

	public static boolean isUnix() {
		return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
	}
}
