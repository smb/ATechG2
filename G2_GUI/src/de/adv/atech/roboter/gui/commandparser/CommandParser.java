package de.adv.atech.roboter.gui.commandparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.adv.atech.roboter.commons.CommandManager;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private final String COMMANDDELIMITER = "\n";
	private final String COMMANDPATTERNSTRING = "([a-zA-Z]+)";
	private final String PARAMETERPATTERNSTRING = "([a-zA-Z0-9]+)";

	private CommandManager commandManager;
	
	public CommandParser() {
		commandManager = new CommandManager();
	}

	/**
	 * Generiert aus einem String von Befehlen und Parametern eine Liste mit Command Objekten
	 * @param commandString
	 * @return Liste von Commands
	 * @throws IllegalSyntaxException 
	 * @throws CommandException 
	 */
	public List<Command> getCommandList(String commandString) throws IllegalSyntaxException, CommandException {
		if (commandString == null || commandString.isEmpty()) {
			throw new IllegalSyntaxException("CommandString ist null oder leer");
		}
		List<Command> commandList = new ArrayList<Command>();
		StringTokenizer stringTokenizer = new StringTokenizer(commandString, COMMANDDELIMITER);
		while (stringTokenizer.hasMoreTokens()) {
			String commandToken = stringTokenizer.nextToken();
			System.out.println(commandToken);
			Command command = getCommand(commandToken);
			commandList.add(command);
		}
		return commandList;
	}
	
	private Command getCommand(String commandToken) throws CommandException {
		Pattern pattern = Pattern.compile("([a-zA-Z]+).*");
		Matcher matcher = pattern.matcher(commandToken);
		matcher.find();
		String commandString = matcher.group(0);
		// TODO: Nullpointer weil Befehle nicht registriert
		Command command = this.commandManager.resolveCommand(null, commandString);
		Pattern pattern2 = getCommandPattern(command);
		System.out.println(pattern2.matcher(commandToken).matches());
		return null;
	}
	
	private Pattern getCommandPattern(Command command) throws CommandException {
		StringBuilder patternBuilder = new StringBuilder();
		patternBuilder.append(COMMANDPATTERNSTRING);
		int parameterAnzahl = command.getParameters().size();
		for (int i = 0; i < parameterAnzahl; i++) {
			if (i==0){
				patternBuilder.append(" ");
				patternBuilder.append("\\(");
			}
			patternBuilder.append(PARAMETERPATTERNSTRING);
			if (i<parameterAnzahl-1) {
				patternBuilder.append(",");
			} else {
				patternBuilder.append("\\)");
			}
		}
		Pattern pattern = Pattern.compile(patternBuilder.toString());
		return pattern;
	}
}
