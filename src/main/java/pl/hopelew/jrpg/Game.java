package pl.hopelew.jrpg;

import lombok.Getter;

public class Game implements Runnable {
	@Getter
	private static Game instance;
	private Player player;
	private boolean running;
	private static long TPS = 10L;

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
		running = false;
	}
}
