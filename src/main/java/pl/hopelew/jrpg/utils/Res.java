package pl.hopelew.jrpg.utils;

import lombok.Getter;

public enum Res {
	HERO_AVATAR_FEMALE("/img/hero_avatars/f_06.png"), HERO_AVATAR_MALE("/img/hero_avatars/m_40.png");

	private @Getter String path;

	private Res(String path) {
		this.path = path;

	}
}
