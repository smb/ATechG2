package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.adv.atech.roboter.gui.core.GUIController;

public class ClientInfoPanel extends JPanel {

	public ClientInfoPanel() {

		setPreferredSize(new Dimension(300, 0));

		init();

		setupPanel();

	}

	public void init() {
		GUIController controller = GUIController.getInstance();

		this.setLayout(new BorderLayout());

	}

	private void setupPanel() {

		GUIController controller = GUIController.getInstance();

		JLabel clientStatus = new JLabel();

		controller.getClientStatusInfo().registerTargetLabel(clientStatus);

		this.add(clientStatus, BorderLayout.NORTH);

		this.add(new CommandReferenceArea(), BorderLayout.CENTER);

	}
}
