package de.adv.atech.roboter.gui.commandparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ErrorMessages;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.exceptions.IllegalSyntaxException;

public class CommandParser {

	private String commandString;
	private List<Command> commandList;
	private Pattern[] commandPattern;
	private Pattern parameterPattern;
	private int codeZeile;
	
	public CommandParser() {
		commandList = new ArrayList<Command>();
		initCommandPattern();
		parameterPattern = Pattern
			.compile(Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);
		codeZeile = 0;
	}
	
	private void initCommandPattern() {
		commandPattern = new Pattern[Constant.COMMANDPARSER_PARAMETER_MAX];
		for (int parameters = 0; parameters < Constant.COMMANDPARSER_PARAMETER_MAX; parameters++) {
			StringBuilder patternBuilder = new StringBuilder();
			patternBuilder.append(
				Constant.COMMANDPARSER_COMMANDPATTERNSTRING);
			for (int i = 0; i < parameters; i++) {
				if (i == 0) {
					patternBuilder.append("\\(");
				}
				patternBuilder.append(
					Constant.COMMANDPARSER_PARAMETERPATTERNSTRING);
				if (i == parameters - 1) {
					patternBuilder.append("\\)\\s*$");
				} else {
					patternBuilder.append(",");
				}
			}			
			Pattern pattern = Pattern.compile(patternBuilder.toString());
			commandPattern[parameters] = pattern;
		}
	}

	public List<Command> getCommandList(String commandsString) throws IllegalSyntaxException {
		commandList.clear();
		StringTokenizer stringTokenizer = new StringTokenizer(
			commandsString, Constant.COMMANDPARSER_COMMANDSDELIMITER);
		while (stringTokenizer.hasMoreTokens()) {
			String line = stringTokenizer.nextToken();
			handleLine(line);
		}
		return commandList;
	}
	
	private void handleLine(String line) throws IllegalSyntaxException {
		StringTokenizer stringTokenizer = new StringTokenizer(
			line, Constant.COMMANDPARSER_COMMANDDELIMITER);
		String commandName = stringTokenizer.nextToken();
		if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SELECTCLIENT)) {
			checkCommandPattern(line, Constant.COMMANDPARSER_COMMAND_SELECTCLIENT_PARAMETER);
			handleSelectCommand(line);
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_LOOP)){
			checkCommandPattern(line, Constant.COMMANDPARSER_COMMAND_LOOP_PARAMETER);
			handleLoopCommand(line);
		} else if (commandName.equals(Constant.COMMANDPARSER_COMMAND_SUB)){
			checkCommandPattern(line, Constant.COMMANDPARSER_COMMAND_SUB_PARAMETER);
			handleSubCommand(line);
		} else {
			
			handleRobotCommand(line);
		}
	}

	private void handleSelectCommand(String line) {
		System.out.println(line);
	}

	private void handleLoopCommand(String line) {
		System.out.println(line);
	}

	private void handleSubCommand(String line) {
		List<String> parameterList = getParameters(line);
		System.out.println(parameterList.get(0));
	}
	
	private void handleRobotCommand(String line) {
		System.out.println(line);
	}

	private void checkCommandPattern(String line, int parameter) throws IllegalSyntaxException {
		System.out.println(commandPattern[parameter]);
		System.out.println(line);
		Matcher m = commandPattern[parameter].matcher(line);
		System.out.println(m);
		if (commandPattern[parameter].matcher(line).matches() == false) {
			throw new IllegalSyntaxException(ErrorMessages.COMMANDPARSER_SYNTAX_ERROR, codeZeile);
		}
	}
	
	private List<String> getParameters(String line) {
		List<String> parameterList = new ArrayList<String>();
		Matcher parameterMatcher = parameterPattern.matcher(line);
		while (parameterMatcher.find()) {
			String value = parameterMatcher.group();
			parameterList.add(value);
		}
		return parameterList;
	}
	
	
}
