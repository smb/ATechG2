package de.adv.atech.roboter.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;

import de.adv.atech.roboter.gui.components.DateTimeStatusLabel;
import de.adv.atech.roboter.gui.components.PercentLayout;
import de.adv.atech.roboter.gui.components.StatusBarClientInfoLabel;
import de.adv.atech.roboter.gui.core.GUIController;

public class StatusBar extends JComponent {

	/**
	 * The key used to identified the default zone
	 */
	public final static String DEFAULT_ZONE = "default";

	private Hashtable idToZones;

	private Border zoneBorder;

	private DateTimeStatusLabel dateTimeLabel;

	private JLabel textLabel;

	// private ClientStatusLabel clientLabel;

	/**
	 * Construct a new StatusBar
	 * 
	 */
	public StatusBar() {
		GUIController controller = GUIController.getInstance();

		setLayout(new PercentLayout(PercentLayout.HORIZONTAL, 8));
		idToZones = new Hashtable();
		setZoneBorder(BorderFactory.createLineBorder(Color.lightGray));

		// dateTimeLabel = new DateTimeStatusLabel();
		StatusBarClientInfoLabel clientLabel = new StatusBarClientInfoLabel();

		controller.getClientStatusInfo().registerTargetComponent(clientLabel);

		textLabel = new JLabel();

		setZones(new String[] {
				"Client", "Zeit"
		}, new JComponent[] {
				clientLabel, controller.getDateTimeLabel()
		}, new String[] {
				"*", "20%"
		});
	}

	public void setZoneBorder(Border border) {
		zoneBorder = border;
	}

	/**
	 * Adds a new zone in the StatusBar
	 * 
	 * @param id
	 * @param zone
	 * @param constraints
	 *            one of the constraint support by the
	 *            {@link com.l2fprod.common.swing.PercentLayout}
	 */
	public void addZone(String id, Component zone, String constraints) {
		// is there already a zone with this id?
		Component previousZone = getZone(id);
		if (previousZone != null) {
			remove(previousZone);
			idToZones.remove(id);
		}

		if (zone instanceof JComponent) {
			JComponent jc = (JComponent) zone;
			if (jc.getBorder() == null || jc.getBorder() instanceof UIResource) {
				if (jc instanceof JLabel) {
					jc.setBorder(new CompoundBorder(zoneBorder,
							new EmptyBorder(0, 2, 0, 2)));
					((JLabel) jc).setText(" ");
				}
				else {
					jc.setBorder(zoneBorder);
				}
			}
		}

		add(zone, constraints);
		idToZones.put(id, zone);
	}

	public Component getZone(String id) {
		return (Component) idToZones.get(id);
	}

	/**
	 * For example:
	 * 
	 * <code>
	 *  setZones(new String[]{"A","B"},
	 *           new JComponent[]{new JLabel(), new JLabel()},
	 *           new String[]{"33%","*"});
	   * </code>
	 * 
	 * would construct a new status bar with two zones (two JLabels) named A and
	 * B, the first zone A will occupy 33 percents of the overall size of the
	 * status bar and B the left space.
	 * 
	 * @param ids
	 *            a value of type 'String[]'
	 * @param zones
	 *            a value of type 'JComponent[]'
	 * @param constraints
	 *            a value of type 'String[]'
	 */
	public void setZones(String[] ids, Component[] zones, String[] constraints) {
		removeAll();
		idToZones.clear();
		for (int i = 0, c = zones.length; i < c; i++) {
			addZone(ids[i], zones[i], constraints[i]);
		}
	}

}