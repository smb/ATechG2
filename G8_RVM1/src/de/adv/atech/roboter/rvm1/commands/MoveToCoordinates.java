package de.adv.atech.roboter.rvm1.commands;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;

public class MoveToCoordinates extends AbstractCommand
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
    
//    public String getCommandCode(Object status)
//    {
//        return this.X + ", " + this.Y + ", " + this.Z + ", " + stauts.rollwinkel + ", " + status.neigungswinkel + "\r"; 
//    }
}
