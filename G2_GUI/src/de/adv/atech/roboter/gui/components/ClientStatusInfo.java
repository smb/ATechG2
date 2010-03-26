package de.adv.atech.roboter.gui.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.NetworkClient;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.ClientChangedListener;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.media.Media;

public class ClientStatusInfo implements Runnable, ClientChangedListener {

	boolean shutdown;

	String currentLabel;

	Icon currentIcon;

	List<ClientInfoGUIComponentInterface> targetComponent;

	public ClientStatusInfo() {
		targetComponent = new ArrayList<ClientInfoGUIComponentInterface>();

		currentLabel = "Kein Client ausgewaehlt";

		// this.setText(currentLabel);

		currentIcon = Media.getIcon("stop");

		ClientManager cm = GUIController.getInstance().getClientManager();

		cm.registerClientChangedListener(this, null);

		// Thread t = new Thread(this);
		// t.start();
	}

	public boolean registerTargetComponent(ClientInfoGUIComponentInterface label) {
		return targetComponent.add(label);
	}

	/**
	 *
	 * 
	 */
	private void setClientLabel() {

		// Get selected Client
		ClientManager cm = GUIController.getInstance().getClientManager();

		List clientList = new ArrayList<ClientStatusInfo>();
		String activeClient = null;

		if (cm != null) {
			Map<String, Client> clientMap = cm.getRegisteredClients();

			for (Iterator<String> it = clientMap.keySet().iterator(); it
					.hasNext();) {

				String clientIdentifier = it.next();
				NetworkClient client = cm.getNetworkClient(clientIdentifier);

				if (client != null) { // Network Client gefunden

					clientList.add(clientIdentifier);

					try {
						if (client == cm.getActiveClient()) {
							activeClient = clientIdentifier;
						}
					}
					catch (ClientException e) {
					}
				}
			}
		}

		// UpdateLabels
		for (Iterator<ClientInfoGUIComponentInterface> it = this.targetComponent
				.iterator(); it.hasNext();) {
			ClientInfoGUIComponentInterface cic = it.next();

			cic.setItemList(clientList);
			cic.setActiveItem(activeClient);

			// JComponent tmpLabel = it.next();
			// tmpLabel.setIcon(currentIcon);
			// tmpLabel.setText(currentLabel);
		}
	}

	public void run() {
		// Idle-Cycle
		while (!this.shutdown) {
			try {
				setClientLabel();

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			try {

				Thread.sleep(1 * 1000);
			}
			catch (InterruptedException e) {
				ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
						"[DateTimeStatusLabel] Shutdown");
			}

		}
	}

	public void shutdown() {
		this.shutdown = true;

	}

	@Override
	public void clientChanged(ClientManager clientManager, Object object) {
		// Aenderungen erkennen
		setClientLabel();
	}
}
