package de.adv.atech.roboter.rvm1.controller;

import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.rvm1.serial.SerialWriter;

public class TranslatorToSerial
{
    private SerialWriter serialWriter;
    
    public TranslatorToSerial(SerialWriter serialWriter)
    {
        this.serialWriter = serialWriter;
    }
    
    public void processCommand(Command comm)
    {
        System.out.println(comm.toString());
    }
}
