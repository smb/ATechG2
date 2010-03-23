package de.adv.atech.roboter.gui.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.gui.media.Media;

public class DateTimeStatusLabel extends JLabel implements Runnable {

	boolean shutdown;

	SimpleDateFormat sdf = null;

	String currentLabel;

	private final static String dateFormat = "dd.MM.yyyy / HH:mm:ss";

	public DateTimeStatusLabel() {
		Locale locale = Locale.GERMAN;

		this.sdf = new SimpleDateFormat(dateFormat, locale);

		this.setIcon(Media.getIcon("time"));

		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * 
	 */
	private void setDateTime() {
		currentLabel = sdf.format(new Date());

		this.setText(currentLabel);
	}

	public void run() {
		// Idle-Cycle
		while (!this.shutdown) {
			try {
				setDateTime();

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
}
