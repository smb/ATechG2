package de.adv.atech.roboter.gui.components;

import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;

import de.adv.atech.roboter.gui.media.Media;

public class StatusBarClientInfoLabel extends JLabel implements
		ClientInfoGUIComponentInterface {

	String activeItem;

	List<String> itemList;

	public void setActiveItem(String item) {
		this.activeItem = item;

		updateItems();
	}

	@Override
	public void setItem(String item) {
		setText(item);
	}

	@Override
	public void setItemList(List<String> itemList) {

		this.itemList = itemList;

		updateItems();
	}

	@Override
	public void setInfoIcon(Icon icon) {
		setIcon(icon);
	}

	public void updateItems() {

		StringBuffer connectedClients = new StringBuffer();
		String currentLabel;
		Icon currentIcon;

		if (itemList != null) {

			for (Iterator<String> it = itemList.iterator(); it.hasNext();) {

				String clientIdentifier = it.next();

				if (connectedClients.length() > 0) {
					connectedClients.append(" / ");
				}

				if (clientIdentifier == this.activeItem) {
					connectedClients.append("*" + clientIdentifier + "*");
				}
				else {
					connectedClients.append(clientIdentifier);
				}
			}

			if (connectedClients != null && connectedClients.length() > 0) {
				currentLabel = "Verbundene Clients: "
						+ connectedClients.toString();
				currentIcon = Media.getIcon("accept");
			}
			else {
				currentLabel = "Keine Clients verbunden";
				currentIcon = Media.getIcon("stop");
			}

			this.setText(currentLabel);
			this.setIcon(currentIcon);

		}
	}

}
