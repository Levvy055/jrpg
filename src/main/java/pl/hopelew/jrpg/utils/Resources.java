package pl.hopelew.jrpg.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public class Resources {

	public static void validate() {
		for (Res res : Res.values()) {
			if (!Files.exists(Paths.get(res.getPath()))) {
				System.out.println("Can't load resource: '" + res.getPath() + "'");
			}
		}
	}

	public static Image getFxImage(Res key) {
		return new Image(Resources.class.getResourceAsStream(key.getPath()));
	}
}
