package pl.hopelew.jrpg.utils;

public class MapGenException extends Exception {
	private static final long serialVersionUID = 1L;

	public MapGenException() {
	}

	public MapGenException(String message) {
		super(message);
	}

	public MapGenException(Throwable cause) {
		super(cause);
	}

	public MapGenException(String message, Throwable cause) {
		super(message, cause);
	}

	public MapGenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
