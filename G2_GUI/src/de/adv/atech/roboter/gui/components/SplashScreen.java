package de.adv.atech.roboter.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class SplashScreen extends JWindow {

	JLabel imageLabel = new JLabel();

	JPanel southPanel = new JPanel();

	JProgressBar progressBar = new JProgressBar();

	ImageIcon imageIcon;

	public SplashScreen(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
		initialize();
	}

	private void initialize() {
		imageLabel.setIcon(imageIcon);
		this.getContentPane().setLayout(new BorderLayout());
		southPanel.setLayout(new FlowLayout());

		southPanel.setBackground(new Color(215, 215, 215, 255));
		this.getContentPane().add(imageLabel, BorderLayout.CENTER);
		this.getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.add(progressBar, null);
		this.pack();
	}

	/**
	 * @param maxProgress
	 */
	public void setProgressMax(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	public void setProgress(int progress) {
		final int theProgress = progress;
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				progressBar.setValue(theProgress);
			}
		});
	}

	public void setProgress(String message, int progress) {
		final int theProgress = progress;
		final String theMessage = message;
		setProgress(progress);
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				progressBar.setValue(theProgress);
				setMessage(theMessage);
			}
		});
	}

	public void setScreenVisible(boolean b) {
		final boolean boo = b;
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				setVisible(boo);
			}
		});
	}

	private void setMessage(String message) {
		if (message == null) {
			message = "";
			progressBar.setStringPainted(false);
		}
		else {
			progressBar.setStringPainted(true);
		}
		progressBar.setString(message);
	}

}