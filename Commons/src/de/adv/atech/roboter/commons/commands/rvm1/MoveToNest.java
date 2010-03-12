package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToCoordinates.Parameter;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class MoveToNest extends AbstractCommand
                        implements Rvm1CommandInternals
{
    public enum Parameter
    {
    }
    
    public MoveToNest()
    {
        super();        
        init(this, Parameter.class);        
        this.commandName = "Nest";
    }
    
    
    @Override
    public List<String> getCommandCodeList()
    {
//        List<String> result = 
//        result.add("NT");
//        return result;
        return null;
    }

    @Override
    public String getCode( Object informationRef ) throws CommandException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
