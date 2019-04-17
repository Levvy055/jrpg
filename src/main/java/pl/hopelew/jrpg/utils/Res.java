package pl.hopelew.jrpg.utils;

import lombok.Getter;

public enum Res {
	HERO_AVATAR_FEMALE("img/hero_avatars/f_06.png", true), HERO_AVATAR_MALE("img/hero_avatars/m_40.png", true);

	private @Getter String path;
	private @Getter boolean isImage;

	private Res(String path, boolean isImage) {
		this.path = path;
		this.isImage = isImage;

	}
}
