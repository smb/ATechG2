package de.adv.atech.roboter.gui.components;

import java.util.Random;

import javax.swing.ImageIcon;

import de.adv.atech.roboter.gui.media.Media;

public class SplashScreenMain {

	SplashScreen screen;

	public SplashScreenMain(String image) {
		splashScreenInit(image);

		/*
		 * Ein paar Sachen addieren um Rechenzeit zu verbrauchen damit der
		 * Splashscreen sichtbar bleibt
		 */
		Random generator = new Random();
		for (int i = 0; i <= 100; i++) {
			int dur = generator.nextInt(40000) + 60000;
			for (long j = 0; j < dur; ++j) {
				String poop = " " + (j + i);
			}
			screen.setProgress("Loading... " + i + "%", i); // progress bar with
			// a message
			// screen.setProgress(i); // progress bar with no message
		}
		splashScreenDestruct();
	}

	/**
	 * SplashScreen 'zerstoeren'
	 */
	private void splashScreenDestruct() {
		screen.setScreenVisible(false);
	}

	/**
	 * @param image
	 */
	private void splashScreenInit(String image) {
		ImageIcon myImage = new ImageIcon(Media.class.getResource(image));
		screen = new SplashScreen(myImage);
		screen.setLocationRelativeTo(null);
		screen.setProgressMax(100);
		screen.setScreenVisible(true);
	}
}