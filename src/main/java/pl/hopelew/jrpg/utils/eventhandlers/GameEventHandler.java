package pl.hopelew.jrpg.utils.eventhandlers;

@FunctionalInterface
public interface GameEventHandler {
	void actionPerformed(GameEvent ge);
}
