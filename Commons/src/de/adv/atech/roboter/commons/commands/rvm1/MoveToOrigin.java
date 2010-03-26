package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.ArrayList;
import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class MoveToOrigin extends AbstractCommand implements
        Rvm1CommandInternals
{
    public enum Parameter {}

    public MoveToOrigin()
    {
        super();
        init(this, Parameter.class);
        this.commandName = "Origin";
    }

    @Override
    public List<String> getCommandCodeList()
    {
        List<String> result = new ArrayList<String>();
        result.add("og");
        return result;
    }

    @Override
    public String getCode(Object informationRef) throws CommandException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
