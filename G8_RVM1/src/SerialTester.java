import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.LinkedList;

import de.adv.atech.roboter.rvm1.controller.TranslatorToRmi;
import de.adv.atech.roboter.rvm1.data.TimedCommand;
import de.adv.atech.roboter.rvm1.serial.SerialController;
import de.adv.atech.roboter.rvm1.serial.SerialReader;
import de.adv.atech.roboter.rvm1.serial.SerialWriter;


public class SerialTester
{
	public SerialTester()
    {
		
		TranslatorToRmi tr1 = new TranslatorToRmi( null );
		
		SerialController sc1 = new SerialController( tr1 , null);
		
//		SerialPort serialPort = null;
//    	try
//		{
//			CommPortIdentifier portIdentifier = CommPortIdentifier
//					.getPortIdentifier( "COM1" );
//			if ( portIdentifier.isCurrentlyOwned() )
//			{
//				System.out.println( "Error: Port is currently in use" );
//			}
//			else
//			{
//				CommPort commPort = portIdentifier.open( this.getClass()
//						.getName(), 2000 );
//
//				if ( commPort instanceof SerialPort )
//				{
//					serialPort = (SerialPort) commPort;
//					serialPort.setSerialPortParams( 9600,
//							SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
//							SerialPort.PARITY_EVEN );
//					
//					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
//
//				}
//				else
//				{
//					System.out
//							.println( "Error: Only serial ports are handled by this example." );
//				}
//			}
//		}
//		catch ( Exception e )
//		{
//			e.printStackTrace();
//		}
//		
//		SerialReader serialReader = new SerialReader(serialPort);
//		SerialWriter serialWriter = new SerialWriter(serialPort);
//		
//		serialReader.addObserver( serialWriter );
		
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
		
		sc1.addCommandList( tl1 );
		
		while (!sc1.commandListisEmpy())
		{
			sc1.writeNextCommand();
		}

		
		while ( true )
		{
			String befehl = Eingabe.liesString();
			TimedCommand tc2 = new TimedCommand( befehl );
			sc1.addCommand( tc2 );
			sc1.writeNextCommand();
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
