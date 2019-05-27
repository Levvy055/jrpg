package pl.hopelew.jrpg.utils;

import lombok.Getter;

public enum Res {
	HERO_AVATAR_FEMALE("/img/hero_avatars/f_06.png", ResType.IMAGE),
	HERO_AVATAR_MALE("/img/hero_avatars/m_40.png", ResType.IMAGE);

	private @Getter String path;
	private @Getter ResType type;

	private Res(String path, ResType type) {
		this.path = path;
		this.type = type;

	}
}

enum ResType {
	AUDIO, VIDEO, IMAGE
}