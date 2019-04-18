package pl.hopelew.jrpg.utils;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class Resources {
	private static Map<Res, Image> images = new HashMap<>();

	public static void validateAndLoad() {
		for (Res res : Res.values()) {
			try {
				URI url = Resources.class.getClassLoader().getResource(res.getPath()).toURI();
				Path path;
				if (url.getScheme().contentEquals("file")) {
					path = Paths.get(url);
				} else {
					FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
					path = fs.getPath("modules", "jrpg", res.getPath());
				}
				if (Files.notExists(path)) {
					System.out.println("Can't load resource: '" + path + "'");
				} else {
					if (res.getType() == ResType.IMAGE) {
						images.put(res, new Image(Resources.class.getClassLoader().getResourceAsStream(res.getPath())));
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
}
