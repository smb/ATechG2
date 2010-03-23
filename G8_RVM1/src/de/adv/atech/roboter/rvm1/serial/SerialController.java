package de.adv.atech.roboter.rvm1.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import de.adv.atech.roboter.rvm1.controller.TranslatorToRmi;
import de.adv.atech.roboter.rvm1.data.TimedCommand;

public class SerialController implements Observer
{
	TranslatorToRmi				rmiTranslator;
	SerialWriter				serialWriter;
	SerialReader				serialReader;
	LinkedList< TimedCommand >	commandList	= new LinkedList< TimedCommand >();
	boolean						readyToSend	= false;

	public SerialController(TranslatorToRmi ssTranslator)
	{
		rmiTranslator = ssTranslator;

		initSerial();

	}

	private void initSerial()
	{
		SerialPort serialPort = null;
		try
		{
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier( "COM1" );
			if ( portIdentifier.isCurrentlyOwned() )
			{
				System.out.println( "Error: Port is currently in use" );
			}
			else
			{
				CommPort commPort = portIdentifier.open( this.getClass()
						.getName(), 2000 );

				if ( commPort instanceof SerialPort )
				{
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams( 9600,
							SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
							SerialPort.PARITY_EVEN );

					serialPort
							.setFlowControlMode( SerialPort.FLOWCONTROL_RTSCTS_IN );

				}
				else
				{
					System.out
							.println( "Error: Only serial ports are handled by this example." );
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		serialReader = new SerialReader( serialPort );
		serialWriter = new SerialWriter( serialPort );

		serialReader.addObserver( this );

		// Thread t1 = new Thread(serialWriter);
		// t1.start();

		Thread t2 = new Thread( serialReader );
		t2.start();
	}

	public void addCommand( TimedCommand ssCommand )
	{
		commandList.add( ssCommand );
	}

	public void writeNextCommand()
	{
		// hier wird das nächste Command geschrieben wenn Roboter ReadyToSend
		// ist
	}

	private void notifyTranslator()
	{
		// TranslatorToRmi bekommt Fehler oder Positionsmeldung. 
	}

	@Override
	public void update( Observable arg0, Object arg1 )
	{
		// Hier muss die Auswertung rein.

	}
	
}
