package de.adv.atech.roboter.rvm1.serial;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

public class SerialReader extends Observable implements Runnable
{
	InputStream	in;

	public SerialReader(SerialPort ssSerialPort)
	{
		try
		{
			in = ssSerialPort.getInputStream();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}

	private synchronized String read()
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
				if ( answer != null )
				{
					setChanged();
					notifyObservers( answer );
				}
			}
			System.out.println("SerialReader: nach der while Schleife");
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
			System.out.println("SerialReader: Run gestartet");
			this.read();
		}
	}

}
