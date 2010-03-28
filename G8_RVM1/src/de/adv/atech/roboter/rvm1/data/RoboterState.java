package de.adv.atech.roboter.rvm1.data;

public class RoboterState
{
    private Boolean HandOpen = true;
    public Boolean getHandOpen()
    {
        return HandOpen;
    }
    public void setHandOpen(Boolean newValue)
    {
        HandOpen = newValue;
    }    
    
    // x coordinate
    private Double X = 0.0;
    public Double getX()
    {
        return X;
    }
    public void setX(Double newValue)
    {
        X = newValue;
    }
    
    // y coordinate
    private Double Y = 0.0;
    public Double getY()
    {
        return Y;
    }
    public void setY(Double newValue)
    {
        Y = newValue;
    }
    
    // z coordinate    
    private Double Z = 0.0;
    public Double getZ()
    {
        return Z;
    }
    public void setZ(Double newValue)
    {
        Z = newValue;
    }
    
    private Integer Pitch = 0;
    public Integer getPitch()
    {
        return Pitch;
    }
    public void setPitch(Integer newValue)
    {
        Pitch = newValue;
    }
    
    private Integer Tilt = 0;
    public Integer getTilt()
    {
        return Tilt;
    }
    public void setTilt(Integer newValue)
    {
        Tilt = newValue;
    }
    
    // error code
    private Integer State = 0;
    public Integer getState()
    {
        return State;
    }
    public void setState(Integer newValue)
    {
        switch(newValue)
        {
            case 0:
            case 1:
            case 2: State = newValue;
                    break;
            default: State = newValue;
        }
        
    }
}
