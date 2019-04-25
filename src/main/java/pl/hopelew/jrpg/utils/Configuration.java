package pl.hopelew.jrpg.utils;

import lombok.Data;

@Data
public class Configuration {
	private double initialHp = 100;
	private double initialMp = 20;

	Configuration() {
	}

	public boolean isValid() {
		return !(initialHp <= 0 || initialMp <= 0);
	}

}
