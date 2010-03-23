import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.LinkedList;

import de.adv.atech.roboter.rvm1.data.TimedCommand;
import de.adv.atech.roboter.rvm1.serial.SerialReader;
import de.adv.atech.roboter.rvm1.serial.SerialWriter;


public class SerialTester
{
	public SerialTester()
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
					
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);

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
		
		SerialReader serialReader = new SerialReader(serialPort);
		SerialWriter serialWriter = new SerialWriter(serialPort);
		
		serialReader.addObserver( serialWriter );
		
		LinkedList<TimedCommand> tl1 = new LinkedList<TimedCommand>();
		
		String testCommand = "og";
		
		TimedCommand t1 = new TimedCommand(testCommand);
		
		tl1.add( t1 );
		
		testCommand = "nt";
		
		t1 = new TimedCommand(testCommand);
		
		tl1.add( t1 );
		
		testCommand = "og";
		
		t1 = new TimedCommand(testCommand);
		
		tl1.add( t1 );
		
		testCommand = "nt";
		
		t1 = new TimedCommand(testCommand);
		
		tl1.add( t1 );
		
		serialWriter.appendCommand( tl1 );
		
		Thread t2 = new Thread(serialWriter);
		t2.start();
		
		Thread t = new Thread(serialReader);
		t.start();
		
		while ( true )
		{
			String befehl = Eingabe.liesString();
			TimedCommand tc2 = new TimedCommand( befehl );
			serialWriter.appendCommand( tc2 );
		}

    }
	
	
    
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		new SerialTester();
	}

}
