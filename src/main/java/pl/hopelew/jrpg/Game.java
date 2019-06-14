package pl.hopelew.jrpg;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pl.hopelew.jrpg.controllers.game.GameWindowController;
import pl.hopelew.jrpg.entities.Entity;
import pl.hopelew.jrpg.entities.Player;
import pl.hopelew.jrpg.entities.Skeleton;
import pl.hopelew.jrpg.entities.data.Sex;
import pl.hopelew.jrpg.map.GameMap;
import pl.hopelew.jrpg.map.GameMapBuilder;
import pl.hopelew.jrpg.map.MapRenderer;
import pl.hopelew.jrpg.utils.MapGenException;
import pl.hopelew.jrpg.utils.TickTimer;
import pl.hopelew.jrpg.utils.eventhandlers.EventType;
import pl.hopelew.jrpg.utils.eventhandlers.GameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.GameEventHandler;
import pl.hopelew.jrpg.utils.eventhandlers.KeyGameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.MapChangedGameEvent;
import pl.hopelew.jrpg.utils.eventhandlers.MapSwitchedGameEvent;

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
	private static final float TPS = 20F;
	private boolean running;
	private @Getter Player player;
	private Map<EventType, Map<Object, Set<GameEventHandler>>> listeners = new HashMap<>();
	private Stack<GameMap> currentMap = new Stack<>();
	private GameLoop loop;
	private GameWindowController window;
	private boolean initialized;
	private ObservableSet<Entity> liveEntities = FXCollections.observableSet();

	public Game(Player player) throws Exception {
		instance = this;
		this.player = player;
		loop = new GameLoop();
	}

	/**
	 * Calledafter{@link GameWindowController} finishes loading
	 * 
	 * @param window
	 */
	public void postInitialization(GameWindowController window) {
		this.window = window;
		liveEntities.addListener((SetChangeListener<Entity>) change -> {
			if (change.wasAdded()) {
				this.window.addEntitySprite(change.getElementAdded());
			} else if (change.wasRemoved()) {
				this.window.removeEntitySprite(change.getElementRemoved());
			}
		});
		setupKeysEventim();

		addListener(EventType.MAP_CHANGED, ge -> {
			var mcge = (MapChangedGameEvent) ge;

		}, null);

		addEntity(player);
		try {
			addEntity(new Skeleton("Skeleton 1", Sex.MALE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialized = true;
	}

	private void setupKeysEventim() {
		window.getScene().addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
				var event = new KeyGameEvent(e.getCode(), EventType.KEY_PRESSED, e.isShiftDown(), e.isShortcutDown(),
						e.isAltDown());
				fireEvent(event);
			}
		});
	}

	/**
	 * Changes map to a specified map id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void goIn(String id) throws MapGenException {
		window.showSpinner(true);
		GameMap map = GameMapBuilder.build(id);
		log.info("Entering map <{}>", map.getName());
		currentMap.add(map);
		fireEvent(new MapSwitchedGameEvent(this, currentMap.lastElement()));
		window.showSpinner(false);
	}

	public void goOut() throws MapGenException {
		window.showSpinner(true);
		if (currentMap.size() == 1) {
			log.warn("Can't exit World Map!");
		} else {
			GameMap map = currentMap.pop();
			log.info("Exited from map <{}>", map.getName());
		}
		fireEvent(new MapSwitchedGameEvent(this, currentMap.lastElement()));
		window.showSpinner(false);
	}

	/**
	 * Adds event handler to specified event type of Game. If game does not have the
	 * type it will be never fired.
	 * 
	 * @param event Type of event for handler to be fired on.
	 * @param l     event handler
	 */
	public void addListener(EventType event, GameEventHandler l, Object target) {
		if (!listeners.containsKey(event)) {
			listeners.put(event, new HashMap<>());
		}
		var handlers = listeners.get(event);
		if (!handlers.containsKey(target)) {
			handlers.put(target, new HashSet<GameEventHandler>());
		}
		handlers.get(target).add(l);
	}

	/**
	 * Fires events of specified event
	 * 
	 * @param ge
	 */
	public void fireEvent(GameEvent ge) {
		if (!listeners.containsKey(ge.getType())) {
			return;
		}
		var handlers = listeners.get(ge.getType());
		if (ge.getTarget() == null) {
			handlers.values().parallelStream().forEach(s -> s.forEach(eh -> eh.actionPerformed(ge)));
		} else {
			handlers.get(ge.getTarget()).forEach(eh -> eh.actionPerformed(ge));
			if (ge.getTarget() == null) {

			}
		}
	}

	/**
	 * Adds entity to game
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		liveEntities.add(entity);
		entity.initializeEntity(this);
	}

	/**
	 * Removes entity from the game
	 * 
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		liveEntities.remove(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		if (running) {
			return;
		}
		running = true;
		while (!initialized && running) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		log.info("Game Loop started.");
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
		GameMap map = null;
		try {
			map = currentMap.peek();
		} catch (EmptyStackException e) {
			log.error("No map loaded!");
		}
		return map;
	}

	/**
	 * Inside class that
	 * 
	 * @author lluka
	 *
	 */
	private class GameLoop {
		private TickTimer timer = new TickTimer();
		private Semaphore semaphore = new Semaphore(0);

		private void run() throws Exception {
			timer.initTime(TPS);
			while (running) {
				if (timer.isFullCycle()) {
					// System.out.println("TPS: " + timer.getLastTps());
				}
				loop();
				timer.sync();
			}
		}

		/**
		 * Games main loop body
		 * 
		 * @throws InterruptedException
		 */
		private void loop() {
			render();
			update();
		}

		/**
		 * Only renders game map when it is changed
		 */
		private void render() {
			var map = getCurrentMap();
			if (map.isChanged()) {
				Platform.runLater(() -> {
					try {
						MapRenderer mapRend = window.getMapRenderer();
						mapRend.clearLayers();
						mapRend.renderBottomTileLayers(map);
						mapRend.renderBottomObjects(map);
						mapRend.renderUpperTileLayers(map);
						mapRend.renderUpperObjects(map);
					} catch (Exception e) {
						e.printStackTrace();
						log.throwing(e);
					} finally {
						semaphore.release();
						map.setChanged(false);
					}
				});
				waitForFxThread();
			}
		}

		/**
		 * Game Logic Update
		 */
		private void update() {
			var map = getCurrentMap();
			for (Entity entity : liveEntities) {
				entity.updateEntity(map);
			}
			player.updateEntity(map);
		}

		/**
		 * Waits for JavaFx Thread to stop updating view
		 */
		private void waitForFxThread() {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				log.throwing(e);
			}
		}
	}
}
