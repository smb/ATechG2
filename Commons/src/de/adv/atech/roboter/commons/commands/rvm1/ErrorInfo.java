package de.adv.atech.roboter.commons.commands.rvm1;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToNest.Parameter;
import de.adv.atech.roboter.commons.exceptions.CommandException;

public class ErrorInfo extends AbstractCommand
{
    protected String Message;
    
    public enum Parameter
    {
        Message
    }
    
    public ErrorInfo()
    {
        super();        
        init(this, Parameter.class);        
        this.commandName = "ErrorInfo";
    }
    
    @Override
    public String getCode( Object informationRef ) throws CommandException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
