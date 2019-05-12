package pl.hopelew.jrpg;

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
import pl.hopelew.jrpg.world.GameMap;

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
	private Stack<GameMap> currentMap = new Stack<>();

	public Game(Player player) throws Exception {
		instance = this;
		this.player = player;
		fileHandler = new FileHandler();
	}

	/**
	 * Changes map to specified map id
	 * @param id
	 * @throws Exception
	 */
	public void goIn(String id) throws Exception {
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

	/**
	 * Adds event handler to specified event type of Game. If game does not have the
	 * type it will be never fired.
	 * 
	 * @param event Type of event for handler to be fired on.
	 * @param l     event handler
	 */
	public void addListener(EventType event, GameEventHandler l) {
		if (!listeners.containsKey(event)) {
			listeners.put(event, new ArrayList<>());
		}
		listeners.get(event).add(l);
	}

	/**
	 * Fires events of specified event
	 * @param ge
	 */
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
			goIn("world_map");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
