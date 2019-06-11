package pl.hopelew.jrpg.utils;

import lombok.Getter;

public enum Res {
	HERO_AVATAR_FEMALE("/img/hero/avatars/f_06.png", ResType.IMAGE),
	HERO_AVATAR_MALE("/img/hero/avatars/m_40.png", ResType.IMAGE),
	HERO_SPRITE_MALE("/img/hero/sprites/m_40", ResType.SPRITE),;
	// HERO_SPRITE_FEMALE("/img/hero/sprites/f_06", ResType.SPRITE);

	private @Getter String path;
	private @Getter ResType type;

	private Res(String path, ResType type) {
		this.path = path;
		this.type = type;
	}

}
