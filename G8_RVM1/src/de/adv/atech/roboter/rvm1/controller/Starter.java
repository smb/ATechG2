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
    private TranslatorToSerial serialTrans;
    
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
            this.rmiServer = new RMIServer();
     
            this.rmiClient = new RMIClient(this.rmiServer);
            
            this.rmiTranslator = new TranslatorToRmi(this.rmiClient);
            
            this.controller = new SerialController(this.rmiTranslator);
            
            this.serialTrans = new TranslatorToSerial(this.controller);
            
            this.rmiServer.setTranslator(this.serialTrans);
            
            Thread t = new Thread(this.rmiClient);
            t.start();
        }
        catch( RemoteException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
