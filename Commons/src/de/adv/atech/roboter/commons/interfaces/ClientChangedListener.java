package de.adv.atech.roboter.commons.interfaces;

import java.util.EventListener;

import de.adv.atech.roboter.commons.ClientManager;

public interface ClientChangedListener extends EventListener {

	public void clientChanged(ClientManager clientManager, Object object);

	public void activeClientChanged(ClientManager clientManager, Object object);

}
