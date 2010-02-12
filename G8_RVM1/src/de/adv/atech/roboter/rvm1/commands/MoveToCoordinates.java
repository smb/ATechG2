package de.adv.atech.roboter.rvm1.commands;

import de.adv.atech.roboter.commons.commands.AbstractCommand;

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
    
//    public String getCommandCode(Object status)
//    {
//        return this.X + ", " + this.Y + ", " + this.Z + ", " + stauts.rollwinkel + ", " + status.neigungswinkel + "\r"; 
//    }
}
