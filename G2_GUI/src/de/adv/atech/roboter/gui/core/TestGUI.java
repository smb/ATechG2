/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.gui.core;

import de.adv.atech.roboter.gui.MainFrame;

public class TestGUI {

	public static void main(String[] args) {
		try {
			new MainFrame();

			CommController comm = new CommController();

			Thread commThread = new Thread(comm);

			commThread.start();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
