package pl.hopelew.jrpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.controllers.game.GameWindowController;
import pl.hopelew.jrpg.entities.Player;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.map.MapRenderer;
import pl.hopelew.jrpg.utils.MapGenException;
import pl.hopelew.jrpg.utils.TickTimer;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.GameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.GameEventHandler;
import pl.hopelew.jrpg.utils.eventhandlers.MapChangedGameEvent;

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
	private static final float TPS = 1F;
	private boolean running;
	private @Getter Player player;
	private Map<EventType, List<GameEventHandler>> listeners = new HashMap<>();
	private Stack<GameMap> currentMap = new Stack<>();
	private GameLoop loop;
	private GameWindowController window;

	public Game(Player player) throws Exception {
		instance = this;
		this.player = player;
		loop = new GameLoop();
	}

	public void postInitialization(GameWindowController window) {
		this.window = window;
		addListener(EventType.MAP_CHANGED, ge -> {
			var mcge = (MapChangedGameEvent) ge;
			GameMap map = mcge.getMap();

		});
	}

	/**
	 * Changes map to a specified map id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void goIn(String id) throws MapGenException {
		GameMap map = GameMap.getMap(id);
		log.info("Entering map <{}>", map.getName());
		currentMap.add(map);
		fireEvent(new MapChangedGameEvent(this, currentMap.lastElement()));
	}

	public void goOut() throws MapGenException {
		if (currentMap.size() == 1) {
			log.warn("Can't exit World Map!");
		} else {
			GameMap map = currentMap.pop();
			log.info("Exited from map <{}>", map.getName());
		}
		fireEvent(new MapChangedGameEvent(this, currentMap.lastElement()));
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
	 * 
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
		} catch (Exception e) {
			log.throwing(e);
			e.printStackTrace();
		}
		try {
			loop.run();
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
		log.info("Game Loop finished.");
	}

	/**
	 * Stops the main loop
	 */
	public void stop() {
		running = false;
	}

	/**
	 * Gets map from the top of map stack
	 * 
	 * @return current map
	 */
	private GameMap getCurrentMap() {
		return currentMap.peek();
	}

	/**
	 * Inside class that
	 * 
	 * @author lluka
	 *
	 */
	private class GameLoop {
		private TickTimer timer;

		private void run() throws Exception {
			timer = new TickTimer();
			timer.initTime(TPS);
			while (running) {
				if (timer.isFullCycle()) {
					loop();
				}
				timer.sync();
			}
		}

		/**
		 * Games main loop body
		 * 
		 * @throws InterruptedException
		 */
		private void loop() {
			var map = getCurrentMap();
			MapRenderer mapRend = window.getMapRenderer();
			try {
				mapRend.renderBottomTileLayers(map);
				mapRend.renderBottomObjects(map);
				// TODO: render player here
				mapRend.renderUpperTileLayers(map);
				mapRend.renderUpperObjects(map);
			} catch (Exception e) {
				e.printStackTrace();
				log.throwing(e);
			}
		}
	}
}
