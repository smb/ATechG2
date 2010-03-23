package de.adv.atech.roboter.gui.components;

import java.util.List;

import javax.swing.Icon;

public interface ClientInfoGUIComponentInterface {

	public void setItem(String item);

	public void setActiveItem(String item);

	public void setItemList(List<String> itemList);

	public void setInfoIcon(Icon icon);

}
