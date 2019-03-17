package pl.hopelew.jrpg.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Strings {
	private static Map<String, String> supportedLocales = new HashMap<>() {
		private static final long serialVersionUID = 1L;

		{
			put("PL", "pl");
			put("US", "EN");
		}
	};
	private static ResourceBundle resources;
	private static String name="strings.Strings";

	public static void init(Locale locale) {
		// TODO: check if locale is supported
		if (supportedLocales.containsKey(locale.getCountry())) {
			resources = ResourceBundle.getBundle(name, locale);
		} else {
			resources = ResourceBundle.getBundle(name);
		}
	}

	public static String get(String key) {
		return resources.getString(key);
	}

	public static ResourceBundle currentBundle() {
		return resources;
	}
}
