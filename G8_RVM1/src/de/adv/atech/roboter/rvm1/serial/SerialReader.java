package de.adv.atech.roboter.rvm1.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

public class SerialReader extends Observable implements Runnable
{
	InputStream	in;

	public SerialReader()
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

					in = serialPort.getInputStream();

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

	private String read()
	{
		String answer = null;

		byte[] buffer = new byte[1024];
		int len = -1;
		try
		{
			while ( ( len = this.in.read( buffer ) ) > -1 )
			{
				if ( len > 0 )
				{
					answer = new String( buffer, 0, len );
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		return answer;
	}

	@Override
	public void run()
	{
		while ( true )
		{
			String answerFromRobot = read();

			if ( answerFromRobot != null )
			{
				notifyObservers( answerFromRobot );
			}
		}
	}

}
