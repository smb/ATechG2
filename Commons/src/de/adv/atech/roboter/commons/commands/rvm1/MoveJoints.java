package de.adv.atech.roboter.commons.commands.rvm1;

import java.util.ArrayList;
import java.util.List;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class MoveJoints extends AbstractCommand implements
        Rvm1CommandInternals
{

    public Integer A;

    public Integer B;

    public Integer C;
    
    public Integer D;
    
    public Integer E;

    public enum Parameter {
        A, B, C, D, E
    }

    public MoveJoints() {
        super();
        init(this, Parameter.class);
        this.commandName = "MoveJoints";
    }

    @Override
    public String getCode(Object informationRef) throws CommandException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getCommandCodeList() {
        String mj = "mj " + A +
                     "," + B + 
                     "," + C +
                     "," + D +
                     "," + E;
        List<String> result = new ArrayList<String>();
        result.add(mj);
        return result;
    }

}
