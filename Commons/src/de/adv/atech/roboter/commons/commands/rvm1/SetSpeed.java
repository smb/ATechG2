package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.ArrayList;
import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToCoordinates.Parameter;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class SetSpeed extends AbstractCommand implements
        Rvm1CommandInternals
{
    public Byte Speed;

    public Boolean FastAcceleration;

    public enum Parameter {
        Speed, FastAcceleration
    }

    public SetSpeed() {
        super();
        init(this, Parameter.class);
        this.commandName = "Speed";
    }

    @Override
    public String getCode(Object informationRef) throws CommandException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getCommandCodeList(Object informationRef) {
        int spd;
        if(Speed <= 1)
        {
            spd = 1;
        }
        else
        {
            if(Speed >= 9)
            {
                spd = 9;
            }
            else
            {
                spd = 1;
            }
        }
        char accel;
        if(FastAcceleration)
        {
            accel = 'h';
        }
        else
        {
            accel = 'l';
        }
        List<String> result = new ArrayList<String>();
        result.add("sp " + spd + "," + accel);
        return result;
    }
}
