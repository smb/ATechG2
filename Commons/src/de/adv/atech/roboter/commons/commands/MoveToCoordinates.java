package de.adv.atech.roboter.commons.commands;

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
}
