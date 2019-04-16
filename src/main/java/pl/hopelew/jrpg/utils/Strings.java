package pl.hopelew.jrpg.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class to make it easy to get localised strings from Strings.properties file
 * 
 * @author lluka
 *
 */
public class Strings {
	private static Map<String, String> supportedLocales = new HashMap<>() {
		private static final long serialVersionUID = 1L;

		{
			put("PL", "pl");
			put("US", "EN");
		}
	};
	private static ResourceBundle resources;
	private static String name = "strings.Strings";

	/**
	 * Loads locale properties file with strings
	 * 
	 * @param locale Locale to get strings from. If doesn't exists then default is
	 *               taken which is EN
	 */
	public static void init(Locale locale) {
		if (supportedLocales.containsKey(locale.getCountry())) {
			resources = ResourceBundle.getBundle(name, locale);
		} else {
			resources = ResourceBundle.getBundle(name);
		}
	}

	/**
	 * Returns localized text from properties translation file.
	 * 
	 * @param key associated with text you want to get
	 * @return
	 */
	public static String get(String key) {
		return resources.getString(key);
	}

	public static ResourceBundle currentBundle() {
		return resources;
	}
}
