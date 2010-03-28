package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.ArrayList;
import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToOrigin.Parameter;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class ResetRvm1 extends AbstractCommand implements
        Rvm1CommandInternals
{
    public enum Parameter {}

    public ResetRvm1()
    {
        super();
        init(this, Parameter.class);
        this.commandName = "Reset";
    }

    @Override
    public List<String> getCommandCodeList(Object informationRef)
    {
        List<String> result = new ArrayList<String>();
        result.add("rs");
        return result;
    }

    @Override
    public String getCode(Object informationRef) throws CommandException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
