package de.adv.atech.roboter.gui.components;

import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComboBox;

public class ClientInfoSelector extends JComboBox implements
		ClientInfoGUIComponentInterface {

	@Override
	public void setActiveItem(String item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInfoIcon(Icon icon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setItem(String item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setItemList(List<String> itemList) {
		this.removeAllItems();

		if (itemList != null) {

			for (Iterator<String> it = itemList.iterator(); it.hasNext();) {

				String clientIdentifier = it.next();

				this.addItem(clientIdentifier);
			}
		}
	}

}
