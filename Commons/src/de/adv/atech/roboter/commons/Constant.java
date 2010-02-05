package de.adv.atech.roboter.commons;

public class Constant {

	public static final String RMI_SERVER_GUI = "RMI_SERVER_GUI";

	public static final String CLIENT_SELF = "CLIENT_SELF";

	public static final String TEXT_EDITOR_TAB = "Editor";
	
	public static final String TEXT_CONTROL_TAB = "Manuelle Steuerung";

	// CommandParser
	public static final String COMMANDPARSER_COMMANDSDELIMITER = "\n";
	public static final String COMMANDPARSER_COMMANDDELIMITER = " ";
	public static final String COMMANDPARSER_COMMANDPATTERNSTRING = "([a-zA-Z]+)";
	public static final String COMMANDPARSER_PARAMETERPATTERNSTRING = "([a-zA-Z0-9]+)";
	
	public static final String COMMANDPARSER_COMMAND_SELECTCLIENT = "SELECT";
	public static final int COMMANDPARSER_COMMAND_SELECTCLIENT_PARAMETER = 1;
	public static final String COMMANDPARSER_COMMAND_LOOP = "LOOP";
	public static final int COMMANDPARSER_COMMAND_LOOP_PARAMETER = 2;
	
}
