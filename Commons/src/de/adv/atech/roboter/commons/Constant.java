package de.adv.atech.roboter.commons;

public class Constant {

	public static String G_VERSION = "$Revision$";

	public static String G_BUILD = "$Date$";

	public static final String RMI_SERVER_GUI = "RMI_SERVER_GUI";

	public static final String CLIENT_SELF = "CLIENT_SELF";

	public static final String TEXT_EDITOR_TAB = "Editor";

	public static final String TEXT_CONTROL_TAB = "Manuelle Steuerung";

	public static final int MESSAGE_TYPE_INFO = 0;

	public static final int MESSAGE_TYPE_ERROR = 1;

	public static final int MESSAGE_TYPE_POPUP = 2;

	public static final String COMMANDPARSER_COMMANDSDELIMITER = "\n";

	public static final String COMMANDPARSER_WHITESPACESPATTERN = "\\s*";

	public static final String COMMANDPARSER_COMMANDPATTERNSTRING = "([a-zA-Z]+)";

	// TODO: Parameter RegEx so anpassen, dass auch Punkte für Double Werte
	// akzeptiert werden

	public static final String COMMANDPARSER_PARAMETERPATTERNSTRING = "([a-zA-Z0-9-\\.]+)";

	public static final String COMMANDPARSER_COMMAND_SELECTCLIENT = "SELECT";

	public static final int COMMANDPARSER_COMMAND_SELECTCLIENT_PARAMETER = 1;

	public static final String COMMANDPARSER_COMMAND_LOOP = "LOOP";

	public static final String COMMANDPARSER_COMMAND_LOOP_STRING = "LOOP (int loops)";

	public static final int COMMANDPARSER_COMMAND_LOOP_PARAMETER = 1;

	public static final String COMMANDPARSER_COMMAND_LOOPEND = "LOOPEND";

	public static final String COMMANDPARSER_COMMAND_LOOPEND_STRING = "LOOPEND";

	public static final int COMMANDPARSER_COMMAND_LOOPEND_PARAMETER = 0;

	public static final String COMMANDPARSER_COMMAND_SUB = "SUB";

	public static final String COMMANDPARSER_COMMAND_SUB_STRING = "SUB (String filename)";

	public static final int COMMANDPARSER_COMMAND_SUB_PARAMETER = 1;

	public static final int COMMANDPARSER_PARAMETER_MAX = 10;

	public static final int COMMAND_SYNTAX_STYLE_COMMAND_REFERENCE = 0;

	public static final int COMMAND_SYNTAX_STYLE_EDITOR = 1;
	
	public static final String EDITOR_LOADFILE_FILTER_DESCRIPTION = "Robotercodedatei (*.rob)";
	
	public static final String EDITOR_LOADFILE_FILTER_ENDING = ".rob";

}
