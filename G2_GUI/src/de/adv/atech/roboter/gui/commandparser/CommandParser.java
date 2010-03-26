package de.adv.atech.roboter.gui.commandparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ErrorMessages;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.CommandManager;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private List<Command> commandList;

	private Stack<Loop> loopQueue;

	private Pattern[] commandPattern;

	private Pattern commandNamePattern;

	private Pattern parameterPattern;

	private int codeLine;

	public CommandParser() {
		commandList = new ArrayList<Command>();
		loopQueue = new Stack<Loop>();
		initCommandPattern();
		commandNamePattern = Pattern
				.compile(Constant.COMMANDPARSER_COMMANDPATTERNSTRING);
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
			patternBuilder.append(Constant.COMMANDPARSER_WHITESPACESPATTERN);
			patternBuilder.append(Constant.COMMANDPARSER_COMMANDPATTERNSTRING);
			patternBuilder.append(Constant.COMMANDPARSER_WHITESPACESPATTERN);
			for (int i = 0; i < parameters; i++) {
				if (i == 0) {
					patternBuilder.append("\\(");
				}
				patternBuilder
						.append(Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);

				patternBuilder
						.append(Constant.COMMANDPARSER_WHITESPACESPATTERN);

				if (i == parameters - 1) {
					patternBuilder.append("\\)");
				}
				else {
					patternBuilder.append(",");
				}
				patternBuilder
						.append(Constant.COMMANDPARSER_WHITESPACESPATTERN);
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
	 * @throws ClientException 
	 */
	public List<Command> getCommandList(String codeLines)
			throws IllegalSyntaxException, CommandException, ClientException {
		commandList.clear();
		loopQueue.clear();
		codeLine = 0;
		handleCodeLines(codeLines, false);
		return commandList;
	}

	/**
	 * Spaltet den Code in Zeilen. Wenn isRcursive == true, dann handelt es sich
	 * um einen rekursiven Aufruf, bei dem die Codezeilen nicht mitgezählt
	 * werden.
	 * 
	 * @param codeLines
	 * @param isRecursive
	 * @throws CommandException
	 * @throws IllegalSyntaxException
	 * @throws ClientException 
	 */
	private void handleCodeLines(String codeLines, boolean isRecursive)
			throws CommandException, IllegalSyntaxException, ClientException {
		StringTokenizer stringTokenizer = new StringTokenizer(codeLines,
				Constant.COMMANDPARSER_COMMANDSDELIMITER);
		while (stringTokenizer.hasMoreTokens()) {
			String line = stringTokenizer.nextToken();
			handleLine(line, false);
		}
	}

	/**
	 * Liest aus einer Codezeile den Befehlsnamen und weist ihn der passenden
	 * Methode zu. Wenn isRcursive == true, dann handelt es sich um einen
	 * rekursiven Aufruf, bei dem die Codezeilen nicht mitgezählt werden.
	 * 
	 * @param line
	 * @param isRecursive
	 * @throws IllegalSyntaxException
	 * @throws CommandException
	 * @throws ClientException 
	 */
	private void handleLine(String line, boolean isRecursive)
			throws IllegalSyntaxException, CommandException, ClientException {
		if (!isRecursive) {
			codeLine++;
		}

		String commandName = null;
		Matcher commandNameMatcher = commandNamePattern.matcher(line);
		commandNameMatcher.find();
		commandName = commandNameMatcher.group();

		if (commandName == null) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_SYNTAX_ERROR, codeLine);
		}
		else if (commandName
				.equals(Constant.COMMANDPARSER_COMMAND_SELECTCLIENT)) {
			handleSelectCommand(line);
		}
		else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOP)) {
			handleLoopCommand(line);
		}
		else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOPEND)) {
			handleLoopEndCommand(line);
		}
		else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SUB)) {
			handleSubCommand(line);
		}
		else {
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
		boolean isActiveClient = GUIController.getInstance().getClientManager()
				.setActiveClient(parameterList.get(0));
		if (!isActiveClient) {
			throw new IllegalSyntaxException(
					ErrorMessages.ACTIVE_CLIENT_NOT_EXIST, codeLine);
		}
	}

	/**
	 * Methode verarbeitet einen LOOP Befehl. Dabei wird sich der letzte Befehl
	 * und die Wiederholung gemerkt. Beim passenden LOOPEND wird der LOOP dann
	 * verarbeitet.
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
	}

	/**
	 * Methode verarbeitet einen LOOPEND Befehl. Sucht den passenden LOOP und
	 * klont die relevanten Command Objekte
	 * 
	 * @param line
	 * @throws IllegalSyntaxException
	 */
	private void handleLoopEndCommand(String line)
			throws IllegalSyntaxException {
		checkCommandPattern(line,
				Constant.COMMANDPARSER_COMMAND_LOOPEND_PARAMETER);
		Loop loop;
		try {
			loop = loopQueue.pop();
		}
		catch (EmptyStackException e) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_ILLEGAL_PARAMETER, codeLine);
		}
		int lastCommandListIndex = commandList.size();
		for (int i = 0; i < loop.getLoopLength() - 1; i++) {
			for (int j = loop.getCommandListIndex(); j < lastCommandListIndex; j++) {
				Command command = commandList.get(j).clone();
				commandList.add(command);
			}
		}
	}

	/**
	 * Verarbeitet einen SUB Befehl. Eine Datei wird eingelesen und der Inhalt
	 * rekursiv verarbeitet.
	 * 
	 * @param line
	 * @throws IllegalSyntaxException
	 * @throws CommandException
	 * @throws ClientException
	 */
	private void handleSubCommand(String line) throws IllegalSyntaxException,
			CommandException, ClientException {
		checkCommandPattern(line, Constant.COMMANDPARSER_COMMAND_SUB_PARAMETER);
		List<String> parameterList = getParameters(line);
		String filename = parameterList.get(0);
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
		}
		catch (FileNotFoundException e) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_SUBFILE_NOT_FOUND, codeLine);
		}
		StringBuffer stringBuffer = new StringBuffer();
		String fileLine;
		try {
			while ((fileLine = bufferedReader.readLine()) != null) {
				stringBuffer.append(fileLine);
				stringBuffer.append("\n");
			}
		}
		catch (IOException e) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_SUBFILE_ERROR, codeLine);
		}
		String subLines = stringBuffer.toString();
		handleCodeLines(subLines, true);
	}

	private void handleRobotCommand(String commandName, String line)
			throws CommandException, IllegalSyntaxException, ClientException {
		ClientManager cm = GUIController.getInstance().getClientManager();

		Client activeClient = cm.getActiveClient();

		CommandManager commandManager = activeClient.getCommandManager();
		Command command = commandManager.resolveCommand(commandName, false);

		if (command == null) {
			throw new IllegalSyntaxException(
					ErrorMessages.COMMANDPARSER_COMMAND_NOT_FOUND, codeLine);
		}

		int parameter = command.getParameters().size();
		checkCommandPattern(line, parameter);
		List<String> parameterList = getParameters(line);

		int i = 0;
		Set<Enum<?>> parameterSet = command.getParameters().keySet();
		for (Iterator<Enum<?>> iterator = parameterSet.iterator(); iterator
				.hasNext();) {
			Enum<?> parameterKey = (Enum<?>) iterator.next();
			Class fieldClass = command.getParameterClass(command
					.getParameters().get(parameterKey));
			if (fieldClass == Double.class) {
				command.setParameter(parameterKey, Double.valueOf(parameterList
						.get(i)));
			}
			else if (fieldClass == Integer.class) {
				command.setParameter(parameterKey, Integer
						.valueOf(parameterList.get(i)));
			}
			else {
				command.setParameter(parameterKey, parameterList.get(i));
			}
			i++;
		}
		commandList.add(command);
	}

	private void checkCommandPattern(String line, int parameter)
			throws IllegalSyntaxException {

		System.out.println("Matcher: "
				+ commandPattern[parameter].matcher(line));
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
		}
		catch (NumberFormatException e) {
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
