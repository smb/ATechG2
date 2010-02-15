package de.adv.atech.roboter.gui.commandparser;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ErrorMessages;
import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private List<Command> commandList;
	private Stack<Loop> loopQueue;
	private Pattern[] commandPattern;
	private Pattern parameterPattern;
	private int codeLine;

	public CommandParser() {
		commandList = new ArrayList<Command>();
		loopQueue = new Stack<Loop>();
		initCommandPattern();
		parameterPattern = Pattern
				.compile(Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);
	}

	/**
	 * Methode erstellt für alle Befehle, mit verschiedener Anzahl von
	 * Parametern, Regular Expressions und speichert sie in einem Array ab.
	 */
	private void initCommandPattern() {
		commandPattern = new Pattern[Constant.COMMANDPARSER_PARAMETER_MAX];
		for (int parameters = 0; parameters < Constant.COMMANDPARSER_PARAMETER_MAX; parameters++) {
			StringBuilder patternBuilder = new StringBuilder();
			patternBuilder.append(Constant.COMMANDPARSER_COMMANDPATTERNSTRING);
			for (int i = 0; i < parameters; i++) {
				if (i == 0) {
					patternBuilder.append("\\(");
				}
				patternBuilder
						.append(Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);
				if (i == parameters - 1) {
					patternBuilder.append("\\)\\s*");
				} else {
					patternBuilder.append(",");
				}
			}
			Pattern pattern = Pattern.compile(patternBuilder.toString());
			commandPattern[parameters] = pattern;
		}
	}

	/**
	 * Methode erstellt aus Codezeilen eine Liste von Command Objekten
	 * 
	 * @param codeLines
	 * @return commandList
	 * @throws IllegalSyntaxException
	 * @throws CommandException
	 */
	public List<Command> getCommandList(String codeLines)
			throws IllegalSyntaxException, CommandException {
		commandList.clear();
		loopQueue.clear();
		codeLine = 0;
		handleCodeLines(codeLines);
		return commandList;
	}

	/**
	 * Methode spaltet den Code in Zeilen
	 * 
	 * @param codeLines
	 * @throws CommandException
	 * @throws IllegalSyntaxException
	 */
	private void handleCodeLines(String codeLines) throws CommandException,
			IllegalSyntaxException {
		StringTokenizer stringTokenizer = new StringTokenizer(codeLines,
				Constant.COMMANDPARSER_COMMANDSDELIMITER);
		while (stringTokenizer.hasMoreTokens()) {
			String line = stringTokenizer.nextToken();
			handleLine(line);
		}
	}

	/**
	 * Methode liest den Befehl der Codezeile und weist ihn der passenden
	 * Methode zu
	 * 
	 * @param line
	 * @throws IllegalSyntaxException
	 * @throws CommandException
	 */
	private void handleLine(String line) throws IllegalSyntaxException,
			CommandException {
		
		// Debug:
		System.out.println("handleLine: " + line);
		
		StringTokenizer stringTokenizer = new StringTokenizer(line,
				Constant.COMMANDPARSER_COMMANDDELIMITER);
		String commandName = stringTokenizer.nextToken();
		if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SELECTCLIENT)) {
			handleSelectCommand(line);
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOP)) {
			handleLoopCommand(line);
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOPEND)) {
			handleLoopEndCommand(line);
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SUB)) {
			handleSubCommand(line);
		} else {
			handleRobotCommand(commandName, line);
		}
	}

	/**
	 * Methode verarbeitet einen SELECT Befehl um den Roboter festzulegen
	 * 
	 * @param line
	 * @throws IllegalSyntaxException
	 */
	private void handleSelectCommand(String line) throws IllegalSyntaxException {
		checkCommandPattern(line,
				Constant.COMMANDPARSER_COMMAND_SELECTCLIENT_PARAMETER);
		List<String> parameterList = getParameters(line);
		// TODO: aktivieren
		// boolean isActiveClient =
		// GUIController.getInstance().getClientManager()
		// .setActiveClient(parameterList.get(0));
		// if (!isActiveClient) {
		// throw new IllegalSyntaxException(
		// ErrorMessages.ACTIVE_CLIENT_NOT_EXIST, codeZeile);
		// }
	}

	/**
	 * Methode verarbeitet einen LOOP Befehl
	 * 
	 * @param line
	 * @throws IllegalSyntaxException
	 */
	private void handleLoopCommand(String line) throws IllegalSyntaxException {
		checkCommandPattern(line, Constant.COMMANDPARSER_COMMAND_LOOP_PARAMETER);
		List<String> parameterList = getParameters(line);
		Loop loop = new Loop();
		loop.setCommandListIndex(commandList.size());
		loop.setLoopLength(getIntValue(parameterList.get(0)));
		loopQueue.push(loop);
		System.out.println(line);
	}

	
	private void handleLoopEndCommand(String line)
			throws IllegalSyntaxException {
		checkCommandPattern(line,
				Constant.COMMANDPARSER_COMMAND_LOOPEND_PARAMETER);
		Loop loop;
		try {
			loop = loopQueue.pop();
		} catch (EmptyStackException e) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_ILLEGAL_PARAMETER, codeLine);
		}	
		int lastCommandListIndex = commandList.size();
		for (int i = 0; i < loop.getLoopLength()-1; i++) {
			for (int j = loop.getCommandListIndex(); j < lastCommandListIndex; j++) {
				Command command = commandList.get(j).clone();
				commandList.add(command);
			}
		}
	}

	
	private void handleSubCommand(String line) throws IllegalSyntaxException {
		checkCommandPattern(line, Constant.COMMANDPARSER_COMMAND_SUB_PARAMETER);
		List<String> parameterList = getParameters(line);
		System.out.println(parameterList.get(0));
	}

	private void handleRobotCommand(String commandName, String line)
			throws CommandException, IllegalSyntaxException {
		// TODO: aktivieren
//		Command command = GUIController.getInstance().getClientManager()
//				.getActiveClient().getCommandManager().resolveCommand(
//						commandName, false);
		Command command = new DefaultMove();
		int parameter = command.getParameters().size();
		checkCommandPattern(line, parameter);
		List<String> parameterList = getParameters(line);
		
		int i = 0;
		Set<Enum<?>> parameterSet = command.getParameters().keySet();
		for (Iterator<Enum<?>> iterator = parameterSet.iterator(); iterator.hasNext();) {
			Enum<?> parameterKey = (Enum<?>) iterator.next();
			Class fieldClass = command.getParameterClass(command.getParameters().get(parameterKey));
			if (fieldClass == Double.class) {
				command.setParameter(parameterKey, Double.valueOf(parameterList.get(i)));
			} else if (fieldClass == Integer.class) {
				command.setParameter(parameterKey, Integer.valueOf(parameterList.get(i)));
			} else {
				command.setParameter(parameterKey, parameterList.get(i));
			}
			i++;
		}
		commandList.add(command);
	}

	private void checkCommandPattern(String line, int parameter)
			throws IllegalSyntaxException {
		
		System.out.println("Matcher: " + commandPattern[parameter].matcher(line));
		System.out.println("Line: " + line);
		
		if (commandPattern[parameter].matcher(line).matches() == false) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_SYNTAX_ERROR, codeLine);
		}
	}

	private int getIntValue(String string) throws IllegalSyntaxException {
		int intValue = 0;
		try {
			intValue = Integer.valueOf(string);
		} catch (NumberFormatException e) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_ILLEGAL_PARAMETER, codeLine);
		}
		return intValue;
	}

	private List<String> getParameters(String line) {
		List<String> parameterList = new ArrayList<String>();
		Matcher parameterMatcher = parameterPattern.matcher(line);
		boolean first = true;
		while (parameterMatcher.find()) {
			String value = parameterMatcher.group();
			// Commandname nicht berücksichtigen
			if (!first) {
				parameterList.add(value);
			}
			first = false;
		}
		return parameterList;
	}

}
