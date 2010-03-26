package de.adv.atech.roboter.rvm1.serial;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import de.adv.atech.roboter.rvm1.data.TimedCommand;

public class SerialWriter
{
	OutputStream				out;
	LinkedList< TimedCommand >	commandList		= new LinkedList< TimedCommand >();
	boolean						readyToSend		= true;
	String						answerFromRobot	= "";

	public SerialWriter(SerialPort ssSerialPort)
	{
		try
		{
			out = ssSerialPort.getOutputStream();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

	}

//	@Override
//	public void update( Observable observedObject, Object robotAnswer )
//	{
//		if ( observedObject instanceof SerialReader )
//		{
//			readyToSend = true;
//			if ( robotAnswer.toString().length() == 1 )
//			{
//				answerFromRobot = robotAnswer.toString();
//			}
//		}
//	}
	
//	public void appendCommand (LinkedList<TimedCommand> ssCommandList)
//	{
//		commandList.addAll( ssCommandList );
//	}

//	public void appendCommand( TimedCommand ssCommandObject )
//	{
//		commandList.add( ssCommandObject );
//	}

	public void writeCommand( TimedCommand commandObject )
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
				
				byte b1check = coman_arr[i];
				out.write( b1check );
				System.out.println("Habe command geschrieben. Stelle: " + i);
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

//	public void errorRead()
//	{
//		try
//		{
//			Thread.sleep( 500 );
//			String comand = "er\r";
//			byte[] coman_arr = comand.getBytes( "ASCII" );
//			for ( int i = 0; i < coman_arr.length; i++ )
//			{
//				out.write( coman_arr[i] );
//			}
//		}
//		catch ( Exception e )
//		{
//			e.printStackTrace();
//		}
//	}

//	public boolean checkRobot()
//	{
//		boolean robotOK = false;
//		errorRead();
//		if ( answerFromRobot.length() > 0 || !answerFromRobot.equals( "0" ) )
//		{
//			try
//			{
//				String comand2 = "rs\r";
//				byte[] coman_arr2 = comand2.getBytes( "ASCII" );
//				for ( int i = 0; i < coman_arr2.length; i++ )
//				{
//					out.write( coman_arr2[i] );
//				}
//			}
//			catch ( Exception e )
//			{
//				e.printStackTrace();
//			}
//
//			robotOK = true;
//		}
//		return robotOK;
//	}

//	@Override
//	public void run()
//	{
//		System.out.println( "SerialWriter: Run gestartet" );
//		
//		while ( true )
//		{
//			if ( readyToSend )
//			{
//				if ( checkRobot() )
//				{
//					answerFromRobot = "";
//				}
//
//				if ( commandList.size() > 0 )
//				{
//					TimedCommand command = commandList.removeFirst();
//					writeCommand( command );
//					readyToSend = false;
//				}
//
//			}
//
//		}
//	}

}
