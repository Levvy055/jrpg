package pl.hopelew.jrpg;

import lombok.Getter;
import lombok.Setter;
import pl.hopelew.jrpg.controllers.game.GameWindowController;
import pl.hopelew.jrpg.entities.Player;

/**
 * Main Game thread loop container Started by {@link Thread} method
 * {@link #run()} initializes the game
 * 
 * @author lluka
 *
 */
public class Game implements Runnable {
	private static @Getter Game instance;
	private @Getter Player player;
	private boolean running;
	private static long TPS = 10L;
	private @Getter @Setter GameWindowController window;

	public Game(Player player) {
		this.player = player;
		instance = this;
	}

	/**
	 * Games main loop body
	 * 
	 * @throws InterruptedException
	 */
	private void loopBody() throws InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(1000 / TPS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		System.out.println("Game Loop started.");
		running = true;
		try {
			while (running) {
				loopBody();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			stop();
		}
		System.out.println("Game Loop finished.");
	}

	/**
	 * Stops the main loop
	 */
	public void stop() {
		if (!running) {
			return;
		}
		running = false;
	}
}
