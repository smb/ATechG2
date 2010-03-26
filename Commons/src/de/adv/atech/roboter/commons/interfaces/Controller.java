package de.adv.atech.roboter.commons.interfaces;

public interface Controller {

	public void message(int messageType, String... text);

	public void debug(String text);

	public void shutdown();
}
