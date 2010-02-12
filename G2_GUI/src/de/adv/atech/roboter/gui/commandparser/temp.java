package de.adv.atech.roboter.gui.commandparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ErrorMessages;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private int codeZeile;
	private Pattern parameterPattern;
	private Map loopMap;

	public CommandParser() {
		this.parameterPattern = Pattern
				.compile(Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);
	}

	/**
	 * Generiert aus einem String von Befehlen und Parametern eine Liste
	 * mit Command Objekten
	 * 
	 * @param commandsString
	 * @return Liste von Commands
	 * @throws IllegalSyntaxException
	 * @throws CommandException
	 */
	public List<Command> getCommandList(String commandsString)
			throws IllegalSyntaxException, CommandException {
		if (commandsString == null || commandsString.isEmpty()) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_STRING_EMPTY);
		}
		List<Command> commandList = new ArrayList<Command>();
		codeZeile = 0;
		loopMap = new TreeMap<Integer, Integer>();

		StringTokenizer stringTokenizer = new StringTokenizer(
				commandsString, Constant.COMMANDPARSER_COMMANDSDELIMITER);
		while (stringTokenizer.hasMoreTokens()) {
			String commandToken = stringTokenizer.nextToken();
			Command command = getCommand(commandToken);
			if (command != null) {
				commandList.add(command);
			}
		}

		return commandList;
	}

	/**
	 * Bekommt eine Zeile übergeben und generiert daraus ein Command
	 * Objekt. Wenn es sich bei der Zeile um ein Editorkommando handelt,
	 * wird null zurückgegeben
	 * 
	 * @param commandString
	 * @return Command Objekt
	 * @throws CommandException
	 * @throws IllegalSyntaxException
	 */
	private Command getCommand(String commandString) throws CommandException, IllegalSyntaxException {
		codeZeile++;
		
		StringTokenizer stringTokenizer = new StringTokenizer(commandString, Constant.COMMANDPARSER_COMMANDDELIMITER);
		String commandName = stringTokenizer.nextToken();

		Command command = null;
		Pattern commandPattern = null;
		
		// Select Befehl
		if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SELECTCLIENT)) {
			commandPattern = getCommandPattern(Constant.COMMANDPARSER_COMMAND_SELECTCLIENT_PARAMETER);
		// Loop Befehl
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOP)) {
			commandPattern = getCommandPattern(Constant.COMMANDPARSER_COMMAND_LOOP_PARAMETER);
		// Roboter Befehl
		} else {
			command = GUIController.getInstance().getClientManager().getActiveClient().getCommandManager().resolveCommand(commandName, false);
			commandPattern = getCommandPattern(command.getParameters().size());
		}
		
		
		if (!commandPattern.matcher(commandString).matches()) {
			throw new IllegalSyntaxException(ErrorMessages.COMMANDPARSER_SYNTAX_ERROR, codeZeile);
		}	
		Matcher parameterMatcher = this.parameterPattern.matcher(commandString);
		
		
		// Select Befehl
		if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SELECTCLIENT)) {
			parameterMatcher.find();
			String value = parameterMatcher.group();
			boolean isActiveClient = GUIController.getInstance().getClientManager().setActiveClient(value);
			if (!isActiveClient) {
				throw new IllegalSyntaxException(ErrorMessages.ACTIVE_CLIENT_NOT_EXIST, codeZeile);
			} 
		// Loop Befehl
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOP)) {
			for (int i = 0; i < Constant.COMMANDPARSER_COMMAND_LOOP_PARAMETER; i++) {
				parameterMatcher.find();
				String value = parameterMatcher.group();
				
			}
		// Roboter Befehl
		} else {
			for (Iterator<Enum<?>> iterator = command.getParameters().keySet().iterator(); iterator.hasNext();) {
				Enum<?> key = (Enum<?>) iterator.next();
				parameterMatcher.find();
				String value = parameterMatcher.group();
				command.setParameter(key, value);
			}
		}
		

		return command;
	}

	/**
	 * Erstellt für eine Anzahl von Parametern ein Pattern
	 * 
	 * @param command
	 * @return Pattern fuer das Command
	 * @throws CommandException
	 */
	private Pattern getCommandPattern(int parameters)
			throws CommandException {
		StringBuilder patternBuilder = new StringBuilder();
		patternBuilder
				.append(Constant.COMMANDPARSER_COMMANDPATTERNSTRING);

		int parameterAnzahl = parameters;
		for (int i = 0; i < parameterAnzahl; i++) {
			if (i == 0) {
				patternBuilder.append(" ");
				patternBuilder.append("\\(");
			}
			patternBuilder
					.append(Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);
			if (i < parameterAnzahl - 1) {
				patternBuilder.append(",");
			} else {
				patternBuilder.append("\\)");
			}
		}
		Pattern pattern = Pattern.compile(patternBuilder.toString());
		return pattern;
	}
}
