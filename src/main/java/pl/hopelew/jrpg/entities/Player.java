package pl.hopelew.jrpg.entities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import pl.hopelew.jrpg.entities.data.EntityState;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.entities.data.Sprite;
import pl.hopelew.jrpg.utils.Res;

public class Player extends Entity {

	public Player(String name, Sex sex) throws Exception {
		super(name, sex);
		switch (sex) {
		case MALE:
			sprite = new Sprite(Res.HERO_SPRITE_MALE);
			break;
		case FEMALE:
			// TODO: sprite = new Sprite(Res.HERO_SPRITE_FEMALE);
			break;
		}
		setupKeys();
	}

	private void setupKeys() {
		GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

			@Override
			public void nativeKeyTyped(NativeKeyEvent e) {
			}

			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
			}

			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				String key = NativeKeyEvent.getKeyText(e.getKeyCode());
				switch (key) {
				case "A":
					position.moveX(-1);
					state = EntityState.WALKING;
					break;
				case "D":
					position.moveX(1);
					state = EntityState.WALKING;
					break;
				case "W":
					position.moveY(-1);
					state = EntityState.WALKING;
					break;
				case "S":
					position.moveY(1);
					state = EntityState.WALKING;
					break;
				}
				System.out.println(key+' '+position);
			}
		});
	}

	@Override
	protected void update() {
		sprite.update(position, state);

	}
}
