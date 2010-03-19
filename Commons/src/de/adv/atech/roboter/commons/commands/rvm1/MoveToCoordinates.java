package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class MoveToCoordinates extends AbstractCommand
                               implements Rvm1CommandInternals
{
    protected Double X;

    protected Double Y;

    protected Double Z;
    
    public enum Parameter
    {
        X, Y, Z
    }
    
    public MoveToCoordinates()
    {
        super();        
        init(this, Parameter.class);        
        this.commandName = "MoveToXYZ";
    }

    @Override
    public String getCode( Object informationRef ) throws CommandException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getCommandCodeList()
    {
        // TODO Auto-generated method stub
        return null;
    }
}