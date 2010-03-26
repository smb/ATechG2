package de.adv.atech.roboter.rvm1.controller;

import java.util.List;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;
import de.adv.atech.roboter.rvm1.data.TimedCommand;
import de.adv.atech.roboter.rvm1.serial.SerialController;
import de.adv.atech.roboter.rvm1.serial.SerialWriter;

public class TranslatorToSerial
{
    private SerialWriter serialWriter;
    
    public TranslatorToSerial(SerialController controller)
    {
        this.serialWriter = serialWriter;
    }
    
    public void processCommand(Command comm)
    {
        System.out.println("TranslatorToSerial: Prcessing command: " +
                comm.toString());
        Rvm1CommandInternals rvm1Comm = (Rvm1CommandInternals)comm;
        List<String> commandCodes = rvm1Comm.getCommandCodeList();
        for(String code: commandCodes)
        {
            this.serialWriter.appendCommand(new TimedCommand(code));
        }
        
    }
}
