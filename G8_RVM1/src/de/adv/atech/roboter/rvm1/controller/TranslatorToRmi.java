package de.adv.atech.roboter.rvm1.controller;

import java.util.Observable;
import java.util.Observer;

import de.adv.atech.roboter.rvm1.rmi.RMIClient;

public class TranslatorToRmi implements Observer
{
    private RMIClient rmiClient;
    
    public TranslatorToRmi(RMIClient rmiClient)
    {
        this.rmiClient = rmiClient;
    }

    @Override
    public void update( Observable o, Object arg )
    {
        // TODO Auto-generated method stub
        System.out.println("TranslatorToRmi: Notification recieved.");
    }

}
