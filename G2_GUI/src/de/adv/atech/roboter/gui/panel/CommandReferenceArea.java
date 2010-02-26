package de.adv.atech.roboter.gui.panel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

public class CommandReferenceArea extends JLabel {

	private StringBuffer commandRef;

	private String stringFormat = "<html><br>%s</html>";

	public CommandReferenceArea() {
		super();

		this.commandRef = new StringBuffer();

		this.commandRef.append("Hallo<br>HUND<br>im<br><b>BUERO</b><br>");

		this.setVerticalAlignment(SwingConstants.TOP);

		this.setText(String.format(this.stringFormat, this.commandRef
				.toString()));
	}
}
