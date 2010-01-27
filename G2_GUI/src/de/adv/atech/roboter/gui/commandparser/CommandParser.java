package de.adv.atech.roboter.gui.commandparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import de.adv.atech.roboter.commons.AbstractCommandManager;
import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private final String COMMANDSDELIMITER = "\n";
	private final String COMMANDDELIMITER = " ";
	private final String COMMANDPATTERNSTRING = "([a-zA-Z]+)";
	private final String PARAMETERPATTERNSTRING = "([a-zA-Z0-9]+)";
	
	private final String SELECTCOMMAND = "SELECT";
	private final String LOOPCOMMAND = "LOOP";

	// TODO: siehe unten - getActiveClient
	// private AbstractCommandManager commandManager;
	private Pattern parameterPattern;
	
	public CommandParser() {
		// TODO: siehe unten - getActiveClient
		// commandManager = new AbstractCommandManager();
		this.parameterPattern = Pattern.compile(PARAMETERPATTERNSTRING);
	}

	/**
	 * Generiert aus einem String von Befehlen und Parametern eine Liste mit Command Objekten
	 * @param commandsString
	 * @return Liste von Commands
	 * @throws IllegalSyntaxException 
	 * @throws CommandException 
	 */
	public List<Command> getCommandList(String commandsString) throws IllegalSyntaxException, CommandException {
		if (commandsString == null || commandsString.isEmpty()) {
			throw new IllegalSyntaxException("CommandString ist null oder leer");
		}
		List<Command> commandList = new ArrayList<Command>();
		
		StringTokenizer stringTokenizer = new StringTokenizer(commandsString, COMMANDSDELIMITER);
		while (stringTokenizer.hasMoreTokens()) {
			String commandToken = stringTokenizer.nextToken();
			Command command = getCommand(commandToken);
			if (command!=null) {
				commandList.add(command);
			}
		}
		
		return commandList;
	}
	
	/**
	 * Bekommt eine Zeile übergeben und generiert daraus ein Command Objekt. Wenn es sich bei der Zeile um ein Editorkommando handelt, wird null zurückgegeben
	 * @param commandString
	 * @return Command Objekt
	 * @throws CommandException
	 * @throws IllegalSyntaxException 
	 */
	private Command getCommand(String commandString) throws CommandException, IllegalSyntaxException {
		StringTokenizer stringTokenizer = new StringTokenizer(commandString, COMMANDDELIMITER);
		String commandName = stringTokenizer.nextToken();

		Command command = null;
		Pattern commandPattern = null;
		if (commandName.equals(SELECTCOMMAND)) {
			commandPattern = getCommandPattern(1);
			
			if (!commandPattern.matcher(commandString).matches()) {
				throw new IllegalSyntaxException("Fehler in SELECT Befehl");
			}
			
			Matcher parameterMatcher = this.parameterPattern.matcher(commandString);
			parameterMatcher.find();
			String value = parameterMatcher.group();
			boolean isActiveClient = GUIController.getInstance().getClientManager().setActiveClient(value);
			if (!isActiveClient) {
				throw new IllegalSyntaxException("Client exisitert nicht");
			}
			
		} else {
			command = GUIController.getInstance().getClientManager().getActiveClient().getCommandManager().resolveCommand(commandName, false);
			commandPattern = getCommandPattern(command.getParameters().size());
			
			if (!commandPattern.matcher(commandString).matches()) {
				throw new IllegalSyntaxException("Syntaxfehler in Befehlszeile");
			}
			
			Matcher parameterMatcher = this.parameterPattern.matcher(commandString);
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
	 * @param command
	 * @return Pattern fŸr das Command
	 * @throws CommandException
	 */
	private Pattern getCommandPattern(int parameters) throws CommandException {
		StringBuilder patternBuilder = new StringBuilder();
		patternBuilder.append(COMMANDPATTERNSTRING);
		
		int parameterAnzahl = parameters;
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
