package de.adv.atech.roboter.gui.commandparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import de.adv.atech.roboter.commons.CommandManager;
import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private final String COMMANDSDELIMITER = "\n";
	private final String COMMANDDELIMITER = " ";
	private final String COMMANDPATTERNSTRING = "([a-zA-Z]+)";
	private final String PARAMETERPATTERNSTRING = "([a-zA-Z0-9]+)";

	private CommandManager commandManager;
	private Pattern parameterPattern;
	
	public CommandParser() {
		commandManager = new CommandManager();
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
			commandList.add(command);
		}
		
		return commandList;
	}
	
	/**
	 * Bekommt eine Zeile übergeben und generiert daraus ein Command Objekt
	 * @param commandString
	 * @return Command Objekt
	 * @throws CommandException
	 * @throws IllegalSyntaxException 
	 */
	private Command getCommand(String commandString) throws CommandException, IllegalSyntaxException {
		StringTokenizer stringTokenizer = new StringTokenizer(commandString, COMMANDDELIMITER);
		String commandName = stringTokenizer.nextToken();
		// TODO: Warten auf sb
		// Command command = this.commandManager.resolveCommand(null, commandString);
	    Command command = new DefaultMove();
	    
		Pattern commandPattern = this.getCommandPattern(command);
		if (!commandPattern.matcher(commandString).matches()) {
			throw new IllegalSyntaxException("Syntaxfehler in Befehlszeile");
		}

        Matcher parameterMatcher = this.parameterPattern.matcher(commandString);
        while (parameterMatcher.find()) {
        	System.out.println(parameterMatcher.group());
        	//TODO: Parameter in Command schreiben
        }
		
		return command;
	}
	
	/**
	 * Erstellt für ein übergebenes Command Objekt ein Pattern
	 * @param command
	 * @return Pattern für das Command
	 * @throws CommandException
	 */
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
