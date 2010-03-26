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
	boolean 					roboterStatus = true;

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

//		 Thread t1 = new Thread(serialWriter);
//		 t1.start(); 

		Thread t2 = new Thread( serialReader );
		t2.start();
	}

	public void addCommand( TimedCommand ssCommand )
	{
		commandList.add( ssCommand );
	}
	
	public void addCommandList (LinkedList<TimedCommand> ssCommandList)
	{
		commandList.addAll( ssCommandList );
	}

	public void writeNextCommand()
	{
		serialWriter.writeCommand( commandList.removeFirst() );
	}

	private void notifyTranslator(String ssMessage)
	{
		rmiTranslator.sendCommand( ssMessage );
		
		// TranslatorToRmi bekommt Fehler oder Positionsmeldung. 
	}
	
	public void setRoboterNotOK()
	{
		roboterStatus = false;
	}
	
	public void setRoboterOK()
	{
		roboterStatus = true;
	}
	
	/**
	 * Wenn Returnwert = true dann ist Liste mit Kommandos leer
	 * @return
	 */
	public boolean commandListisEmpy()
	{
		return commandList.isEmpty();
	}

	@Override
	public void update( Observable arg0, Object arg1 )
	{
		String robotAnswer = null;
		if (arg0 instanceof SerialReader)
		{
			robotAnswer = arg1.toString();
		}
		if(roboterStatus && !commandList.isEmpty())
		{
			if(robotAnswer.equals( "0" ))
			{
				serialWriter.writeCommand( commandList.removeFirst() );
			}
			else
			{
				if (robotAnswer.length() > 1)
				{
					notifyTranslator( robotAnswer );
				}
				
				if (robotAnswer.equals( "1" ))
				{
					String robotMessage = "Fehlerfall 1: Roboter ausschalten und Blockade beheben";
					notifyTranslator( robotMessage );
					setRoboterNotOK();
					System.out.println("Hier Fehler 1");
					
				}
				else if (robotAnswer.equals( "2" ))
				{
					String robotMessage = "Fehlerfall 2: Roboter resetten";
					notifyTranslator( robotMessage );
					setRoboterNotOK();
					System.out.println("Hier Fehler 2");
				}
			}
		}

		
		

	}
	
}
