package pl.hopelew.jrpg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.controllers.game.GameWindowController;
import pl.hopelew.jrpg.entities.Player;
import pl.hopelew.jrpg.utils.FileHandler;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.GameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.GameEventHandler;
import pl.hopelew.jrpg.utils.eventhandlers.MapChangedGameEvent;
import pl.hopelew.jrpg.world.MapBase;

/**
 * Main Game thread loop container Started by {@link Thread} method
 * {@link #run()} initializes the game
 * 
 * @author lluka
 *
 */
@Log4j2
public class Game implements Runnable {
	private static @Getter Game instance;
	private static final long TPS = 10L;
	private @Getter @Setter GameWindowController window;
	private boolean running;
	private @Getter Player player;
	private FileHandler fileHandler;
	private Map<EventType, List<GameEventHandler>> listeners = new HashMap<>();
	private Stack<MapBase> currentMap = new Stack<>();

	public Game(Player player) throws IOException {
		instance = this;
		this.player = player;
		fileHandler = new FileHandler();
		currentMap.add(fileHandler.getMap("world_map"));
	}

	public void goIn(String id) throws IOException {
		currentMap.add(fileHandler.getMap(id));
		fireEvent(new MapChangedGameEvent(this, currentMap.lastElement()));
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

	public void addListener(EventType event, GameEventHandler l) {
		if (!listeners.containsKey(event)) {
			listeners.put(event, new ArrayList<>());
		}
		listeners.get(event).add(l);
	}

	private void fireEvent(GameEvent ge) {
		if (!listeners.containsKey(ge.getType())) {
			return;
		}
		listeners.get(ge.getType()).forEach(eh -> eh.actionPerformed(ge));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		log.info("Game Loop started.");
		running = true;
		try {
			while (running) {
				loopBody();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			stop();
		}
		log.info("Game Loop finished.");
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
