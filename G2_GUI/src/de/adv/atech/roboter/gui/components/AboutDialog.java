package de.adv.atech.roboter.gui.components;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.gui.core.GUIController;
import edu.stanford.ejalbert.BrowserLauncher;

/**
 * @author sb
 * 
 */
public class AboutDialog extends JDialog implements HyperlinkListener {

	JButton cancelButton = null;

	JButton gcButton = null;

	JScrollPane scrollPaneTable = null;

	JEditorPane myTextArea = null;

	JScrollPane exportScrollPane = null;

	Action actionListener;

	public AboutDialog(Frame owner) {
		super(owner, true);
		this.setTitle("About");
		initUI();
	}

	protected JRootPane createRootPane() {

		actionListener = new AbstractAction() {

			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
			}
		};

		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

		InputMap inputMap = rootPane
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", actionListener);

		return rootPane;
	}

	public void initUI() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = null;

		// ScrollPane + TextArea mit dem exportierten Text
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(15, 10, 10, 10);
		getContentPane().add(getExportScrollPane(), gridBagConstraints);

		// Cancel Button
		cancelButton = new JButton("Schliessen");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
		cancelButton.addActionListener(this.actionListener);

		getContentPane().add(cancelButton, gridBagConstraints);

		/*
		 * gcButton = new JButton("Garbage Collection"); gridBagConstraints =
		 * new java.awt.GridBagConstraints(); gridBagConstraints.gridx = 1;
		 * gridBagConstraints.gridy = 1; gridBagConstraints.weightx = 1;
		 * gridBagConstraints.anchor = GridBagConstraints.WEST;
		 * gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
		 * 
		 * getContentPane().add(gcButton, gridBagConstraints);
		 */
	}

	public JScrollPane getExportScrollPane() {
		if (exportScrollPane == null) {
			exportScrollPane = new JScrollPane();
			exportScrollPane.setPreferredSize(new Dimension(600, 350));

			exportScrollPane.setViewportView(getMyTextArea());

		}
		return exportScrollPane;
	}

	public JEditorPane getMyTextArea() {
		if (myTextArea == null) {
			myTextArea = new JEditorPane();
			myTextArea.setEditable(false);
			myTextArea.setEditorKit(new MyHTMLEditorKit());
			String BGColor = Integer.toHexString(this.getBackground().getRed())
					+ Integer.toHexString(this.getBackground().getGreen())
					+ Integer.toHexString(this.getBackground().getBlue());
			myTextArea.setAlignmentY(JLabel.TOP_ALIGNMENT);
			myTextArea.addHyperlinkListener(this);
			myTextArea.setBorder(null);
			String formText = "<html>" + "<body bgcolor=\""
					+ BGColor
					+ "\">"
					+ "<font face=\"Verdana\" size=\"6\"><b>Roboter GUI</b></font><br>"
					+ "<hr><img src=\"about_img.jpg\" height=\"125\" width=\"245\"><br><br>"
					+ "<font face=\"Verdana\" size=\"3\">"
					+ "<b>Autoren:</b><br>"
					+ "<a href=\"mailto:mail@emanuel-egger.de\">Emanuel Egger</a><br>"
					+ "<a href=\"mailto:sbuehl@gmail.com\">Steffen Buehl</a><br>"
					+ "<br>" + "<b>Version:</b> " + Constant.G_VERSION + "<br>"
					+ "<b>Build Date:</b> " + Constant.G_BUILD + "<br>"
					+ "<b>Used Memory:</b> " + GUIController.getUsedMemory()
					+ "kB <br>" + "<b>Free Memory:</b> "
					+ GUIController.getFreeMemory() + "kB <br>"
					+ "<b>Total Memory:</b> " + GUIController.getTotalMemory()
					+ "kB <br>" + "<br>" + "</font></body></html>";
			myTextArea.setText(formText);
		}
		return myTextArea;
	}

	public void garbageCollection() {

	}

	public void hyperlinkUpdate(HyperlinkEvent e) {

		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				BrowserLauncher launcher = new BrowserLauncher();
				launcher.openURLinBrowser(e.getURL().toString());
			}
			catch (Exception r) {
			}
			;
		}
	}

}
