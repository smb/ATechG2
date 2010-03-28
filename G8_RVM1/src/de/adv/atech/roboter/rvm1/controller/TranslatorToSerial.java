package de.adv.atech.roboter.rvm1.controller;

import java.util.List;

import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;
import de.adv.atech.roboter.rvm1.data.RoboterState;
import de.adv.atech.roboter.rvm1.data.TimedCommand;
import de.adv.atech.roboter.rvm1.serial.SerialController;

public class TranslatorToSerial implements Runnable
{
    private SerialController serialController;
    private List<Command> storedCommand;
    private RoboterState robotState;
    
    public TranslatorToSerial(SerialController controller,
                              RoboterState info)
    {
        this.serialController = controller;
        this.robotState = info;
    }
    
    public void processCommand(List<Command> commandList)
    {
        this.storedCommand = commandList;
    }

    @Override
    public void run()
    {
        for(Command comm: this.storedCommand)
        {
        System.out.println("TranslatorToSerial: Processing command: " +
                comm.toString());
        
        Rvm1CommandInternals rvm1Comm = (Rvm1CommandInternals)comm;
        
        List<String> commandCodes = rvm1Comm.getCommandCodeList(this.robotState);
        
            for(String code: commandCodes)
            {
                if (!code.equals( "rs" ))
                {
                    this.serialController.addCommand(new TimedCommand(code));
                }
                else
                {
                    serialController.setRoboterOK();
                }
                
            }
            
            serialController.writeNextCommand();
        }
    }
}
