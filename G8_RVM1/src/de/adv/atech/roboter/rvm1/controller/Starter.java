package de.adv.atech.roboter.rvm1.controller;

import java.rmi.RemoteException;

import de.adv.atech.roboter.rvm1.rmi.RMIClient;
import de.adv.atech.roboter.rvm1.rmi.RMIServer;
import de.adv.atech.roboter.rvm1.serial.SerialController;

public class Starter
{
    private RMIServer rmiServer;
    private RMIClient rmiClient;
    private TranslatorToRmi rmiTranslator;
    private TranslatorToSerial serialTranslator;
    
    private SerialController controller;
    
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
            
            this.rmiTranslator = new TranslatorToRmi();// parameter rmiclient needed
            
            this.controller = new SerialController(this.rmiTranslator);
            
            this.serialTranslator = new TranslatorToSerial(this.controller);
            
            this.rmiServer = new RMIServer(this.serialTranslator);
            
            this.rmiClient = new RMIClient(this.rmiServer);
            
        }
        catch( RemoteException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
