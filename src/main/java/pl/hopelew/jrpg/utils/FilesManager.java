package pl.hopelew.jrpg.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import lombok.Getter;

public class FilesManager {
	private static @Getter FilesManager instance = new FilesManager();
	private final String FILENAME = "app.config";
	private JsonParser parser = new JsonParser();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static @Getter Configuration config;

	private FilesManager() {
	}

	public void saveConfig() throws IOException {
		if (config == null) {
			return;
		}
		var file = new File(FILENAME);
		file.createNewFile();
		String json = gson.toJson(parser.parse(gson.toJson(config)));
		try (PrintWriter out = new PrintWriter(file)) {
			out.println(json);
		}
	}

	public void loadConfig() throws JsonSyntaxException, IOException {
		var file = new File(FILENAME);
		if (file.createNewFile()) { // File does not exist, save to file
			String json = gson.toJson(parser.parse(gson.toJson(Configuration.class)));
			try (PrintWriter out = new PrintWriter(file)) {
				out.println(json);
			}
		} else { // File exists, load from file
			config = gson.fromJson(new String(Files.readAllBytes(file.toPath())), Configuration.class);
		}
	}
}
