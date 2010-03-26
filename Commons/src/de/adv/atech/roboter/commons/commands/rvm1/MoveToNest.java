package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.ArrayList;
import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class MoveToNest extends AbstractCommand
                        implements Rvm1CommandInternals
{

	public enum Parameter {}

	public MoveToNest()
	{
		super();
		init(this, Parameter.class);
		this.commandName = "Nest";
	}

	@Override
	public List<String> getCommandCodeList()
	{
		List<String> result = new ArrayList<String>();
		result.add("NT");
		return result;
	}

	@Override
	public String getCode(Object informationRef) throws CommandException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
