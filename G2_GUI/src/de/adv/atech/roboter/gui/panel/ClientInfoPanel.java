package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.adv.atech.roboter.gui.components.ClientInfoSelector;
import de.adv.atech.roboter.gui.core.GUIController;

public class ClientInfoPanel extends JPanel {

	private List<ClientInfoBlock> infoList;

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

		ClientInfoSelector clientStatus = new ClientInfoSelector();

		CommandReferenceArea commandReferenceArea = new CommandReferenceArea();

		clientStatus.addItemListener(commandReferenceArea);

		controller.getClientStatusInfo().registerTargetComponent(clientStatus);

		this.add(clientStatus, BorderLayout.NORTH);

		this.add(new JScrollPane(commandReferenceArea), BorderLayout.CENTER);

	}
}
