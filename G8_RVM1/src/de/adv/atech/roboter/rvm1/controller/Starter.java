package de.adv.atech.roboter.rvm1.controller;

import java.rmi.RemoteException;

import de.adv.atech.roboter.rvm1.rmi.RMIClient;
import de.adv.atech.roboter.rvm1.rmi.RMIServer;
import de.adv.atech.roboter.rvm1.serial.SerialReader;
import de.adv.atech.roboter.rvm1.serial.SerialWriter;

public class Starter
{
    private SerialReader serialReader;
    private SerialWriter serialWriter;
    private RMIServer rmiServer;
    private RMIClient rmiClient;
    private TranslatorToRmi rmiTranslator;
    private TranslatorToSerial serialTranslator;
    
    /**
     * @param args
     */
    public static void main( String[] args )
    {
        Starter me = new Starter();
    }
    
    public Starter()
    {
        try
        {
//            this.serialReader = new SerialReader();
//            this.serialWriter = new SerialWriter();
            this.rmiTranslator = new TranslatorToRmi();
            this.serialTranslator = new TranslatorToSerial(this.serialWriter);
            this.rmiServer = new RMIServer(this.rmiTranslator, this.serialTranslator);
            this.rmiClient = new RMIClient(this.rmiServer);
            this.serialReader.addObserver(this.serialWriter);
            this.serialReader.addObserver(this.rmiTranslator);
            
            Thread t = new Thread(this.serialReader);
            t.run();
            
            t = new Thread(this.serialWriter);
            t.run();
            
        }
        catch( RemoteException e )
        {
            // TODO logging

            e.printStackTrace();
        }
    }
}
