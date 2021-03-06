/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.gui.core;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import de.adv.atech.roboter.commons.ActiveClientCommandManager;
import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.IncidentInfo;
import de.adv.atech.roboter.commons.LocalClient;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Controller;
import de.adv.atech.roboter.gui.ActionManager;
import de.adv.atech.roboter.gui.components.ClientStatusInfo;
import de.adv.atech.roboter.gui.components.DateTimeStatusLabel;
import de.adv.atech.roboter.gui.components.ExceptionDialog;
import de.adv.atech.roboter.gui.components.MainFrame;
import de.adv.atech.roboter.gui.panel.DebugArea;
import de.adv.atech.roboter.gui.panel.EditorPanel;

/**
 * 
 * @author sbu
 * 
 */
public class GUIController implements Controller {

	private static final Runtime sRuntime = Runtime.getRuntime();

	private ClientManager clientManager;

	private DebugArea debugArea;

	private CommController commController;

	private ActionManager actionManager;

	private EventDispatcher eventDispatcher;

	private ActionHandler actionCore;

	private ActiveClientCommandManager activeClientCommandManager;

	/*
	 * Threads
	 */
	private Thread threadCommController;

	private DateTimeStatusLabel dateTimeLabel; // = new DateTimeStatusLabel();

	private ClientStatusInfo clientStatusInfo; // = new ClientStatusLabel();

	/*
	 * GUI Conent
	 */
	private JPanel rootPanel;

	private JToolBar toolBar; // Toolbar, siehe Rootpanel

	private JMenuBar menuBar; // Menuebar

	private JTabbedPane mainPanel; // Center Panel (Tabbed)

	private JPanel centerPanel; // Center Panel (Tabbed)

	private JPanel statusPanel; // Status Panel mit Statusbar und DebugPanel

	private JPanel editorPanel; // Editor

	private JComponent statusBar; // StatusBar

	private JScrollPane debugPanel;

	private JFrame mainFrame;

	/**
	 * @return the editorPanel
	 */
	public EditorPanel getEditorPanel() {
		return (EditorPanel) this.editorPanel;
	}

	/**
	 * @param editorPanel
	 *            the editorPanel to set
	 */
	public void setEditorPanel(JPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	/**
	 * @return the controlPanel
	 */
	public JPanel getControlPanel() {
		return this.controlPanel;
	}

	/**
	 * @param controlPanel
	 *            the controlPanel to set
	 */
	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	private JPanel controlPanel; // Manuelle Steuerkontrollen

	/**
	 * @return the toolBar
	 */
	public JToolBar getToolBar() {
		return this.toolBar;
	}

	/**
	 * @param toolBar
	 *            the toolBar to set
	 */
	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	/**
	 * @return the menuBar
	 */
	public JMenuBar getMenuBar() {
		return this.menuBar;
	}

	/**
	 * @param menuBar
	 *            the menuBar to set
	 */
	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * @return the mainPanel
	 */
	public JTabbedPane getMainPanel() {
		return this.mainPanel;
	}

	/**
	 * @param mainPanel
	 *            the mainPanel to set
	 */
	public void setMainPanel(JTabbedPane mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * @return the statusPanel
	 */
	public JPanel getStatusPanel() {
		return this.statusPanel;
	}

	/**
	 * @param statusPanel
	 *            the statusPanel to set
	 */
	public void setStatusPanel(JPanel statusPanel) {
		this.statusPanel = statusPanel;
	}

	/**
   * 
   */
	private static GUIController _instance;

	public static String ApplicationImagePath = "";

	GUIController() {

		ControllerManager.getInstance().setController(this);

		this.clientManager = new ClientManager();

		this.activeClientCommandManager = new ActiveClientCommandManager(
				this.clientManager);
	}

	public void initGUI() {

		this.debugArea = new DebugArea();

		this.actionManager = new ActionManager();

		this.dateTimeLabel = new DateTimeStatusLabel();

		this.clientStatusInfo = new ClientStatusInfo();

		this.setMainFrame(new MainFrame());
	}

	public boolean init() {
		try {
			this.actionCore = new ActionHandler();

			this.eventDispatcher = new EventDispatcher();

			Client localClient = new LocalClient();

			this.clientManager.registerClient(localClient);

			this.commController = new CommController();

			this.threadCommController = new Thread(this.commController);

			threadCommController.start();

			this.eventDispatcher.registerEvent(this.actionCore, "Send",
					EventDispatcher.TYPE_ACTION);

		}
		catch (Exception ex) {

			showException("Kritischer Fehler beim Programmstart", ex, true);

			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	public static GUIController getInstance() {
		if (GUIController._instance == null) {
			GUIController._instance = new GUIController();
		}

		return GUIController._instance;
	}

	/**
	 * @param clientManager
	 *            the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager() {
		return this.clientManager;
	}

	/**
	 * 
	 * @return
	 */
	public ActiveClientCommandManager getActiveClientCommandManager() {
		return this.activeClientCommandManager;
	}

	/**
	 * @return the clientManager
	 */
	public ActionManager getActionManager() {
		return this.actionManager;
	}

	/**
	 * @param debugArea
	 *            the debugPanel to set
	 */
	public void setDebugArea(DebugArea debugArea) {
		this.debugArea = debugArea;
	}

	/**
	 * @return the debugPanel
	 */
	public DebugArea getDebugArea() {
		return this.debugArea;
	}

	public void message(int messageType, String... text) {
		if (messageType == Constant.MESSAGE_TYPE_INFO) {
			debug(text[0]);
		}
		if (messageType == Constant.MESSAGE_TYPE_ERROR) {
			debug("ERROR: " + text[0]);
		}
		if (messageType == Constant.MESSAGE_TYPE_POPUP) {
			ExceptionDialog.showDialog(getMainFrame(), new IncidentInfo(
					text[0], text[1], text[2]));
		}
	}

	public void debug(String text) {
		if (this.debugArea != null) {
			this.debugArea.addText("[GUIController]" + text);
		}
		else {
			System.out.println("[GUIController]" + text);
		}
	}

	/**
	 * @param centerPanel
	 *            the centerPanel to set
	 */
	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	/**
	 * @return the centerPanel
	 */
	public JPanel getCenterPanel() {
		return centerPanel;
	}

	/**
	 * @param statusBar
	 *            the statusBar to set
	 */
	public void setStatusBar(JComponent statusBar) {
		this.statusBar = statusBar;
	}

	/**
	 * @return the statusBar
	 */
	public JComponent getStatusBar() {
		return statusBar;
	}

	/**
	 * @param debugPanel
	 *            the debugPanel to set
	 */
	public void setDebugPanel(JScrollPane debugPanel) {
		this.debugPanel = debugPanel;
	}

	/**
	 * @return the debugPanel
	 */
	public JScrollPane getDebugPanel() {
		return debugPanel;
	}

	/**
	 * @param rootPanel
	 *            the rootPanel to set
	 */
	public void setRootPanel(JPanel rootPanel) {
		this.rootPanel = rootPanel;
	}

	/**
	 * @return the rootPanel
	 */
	public JPanel getRootPanel() {
		return rootPanel;
	}

	@Override
	public void shutdown() {
		if (this.commController != null) {
			this.commController.shutdown();
		}
		System.exit(0);
	}

	public static int getFreeMemory() {
		return (int) (sRuntime.freeMemory() / 1024);
	}

	public static int getTotalMemory() {
		return (int) (sRuntime.totalMemory() / 1024);
	}

	public static int getUsedMemory() {
		return getTotalMemory() - getFreeMemory();
	}

	/**
	 * @param mainFrame
	 *            the mainFrame to set
	 */
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * @return the mainFrame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	/**
	 * @param dateTimeLabel
	 *            the dateTimeLabel to set
	 */
	public void setDateTimeLabel(DateTimeStatusLabel dateTimeLabel) {
		this.dateTimeLabel = dateTimeLabel;
	}

	/**
	 * @return the dateTimeLabel
	 */
	public DateTimeStatusLabel getDateTimeLabel() {
		return dateTimeLabel;
	}

	/**
	 * @return the clientLabel
	 */
	public ClientStatusInfo getClientStatusInfo() {
		return clientStatusInfo;
	}

	/**
	 * 
	 * @param title
	 * @param exception
	 * @param shutdown
	 */
	public void showException(String title, Throwable exception,
			boolean shutdown) {
		ExceptionDialog.showDialog(GUIController.getInstance().getMainFrame(),
				new IncidentInfo("Kritischer Fehler", exception));
		if (shutdown) {
			this.shutdown();
		}
	}

}
