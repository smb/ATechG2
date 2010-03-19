package de.adv.atech.roboter.commons.interfaces;

import java.util.EventListener;

public interface CommandChangedListener extends EventListener {

	public void commandChanged(Client client);

}
