package de.adv.atech.roboter.rvm1.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import de.adv.atech.roboter.rvm1.data.TimedCommand;

public class SerialWriter implements Observer, Runnable
{
	OutputStream				out;
	LinkedList< TimedCommand >	commandList	= new LinkedList< TimedCommand >();
	boolean						readyToSend;
	String						answerFromRobot	= null;

	public SerialWriter()
	{
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
					SerialPort serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams( 9600,
							SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
							SerialPort.PARITY_EVEN );

					out = serialPort.getOutputStream();

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

	}

	@Override
	public void update( Observable observedObject, Object robotAnswer )
	{
		if ( observedObject instanceof SerialReader )
		{
			readyToSend = true;
			if ( robotAnswer.toString().length() == 1 )
			{
				answerFromRobot = robotAnswer.toString();
			}
		}

	}

	public void appendCommand( TimedCommand ssCommandObject )
	{
		commandList.add( ssCommandObject );
	}

	private void writeCommand( TimedCommand commandObject )
	{

		if ( commandObject.getTimeout() != 0 )
		{
			try
			{
				this.wait( commandObject.getTimeout() );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}

		try
		{
			String comand = commandObject.getCommandCode();
			comand = comand + "\r";
			byte[] coman_arr = comand.getBytes( "ASCII" );
			for ( int i = 0; i < coman_arr.length; i++ )
			{
				out.write( coman_arr[i] );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while ( true )
		{
			if ( readyToSend )
			{
				if (answerFromRobot != null || !answerFromRobot.equals( "0" ))
				{
					try
					{
						String comand =  "rs\r";
						byte[] coman_arr = comand.getBytes( "ASCII" );
						for ( int i = 0; i < coman_arr.length; i++ )
						{
							out.write( coman_arr[i] );
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
				else if(answerFromRobot == null)
				{
					
				}
			}
			// if(liste != empy && senden == geht)
			// {
			// schicke nächstes command
			// }
		}
	}

}
