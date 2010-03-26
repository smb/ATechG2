package de.adv.atech.roboter.rvm1.controller;

import java.rmi.RemoteException;

import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.interfaces.Controller;
import de.adv.atech.roboter.rvm1.rmi.RMIClient;
import de.adv.atech.roboter.rvm1.rmi.RMIServer;
import de.adv.atech.roboter.rvm1.serial.SerialController;

public class Starter implements Controller
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
            ControllerManager.getInstance().setController(this);
            
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

    @Override
    public void debug( String text )
    {
        System.out.println(text);
    }

    @Override
    public void message( int messageType, String... text )
    {
         System.out.println(text);   
    }

    @Override
    public void shutdown()
    {
        System.exit(0);
    }
}
