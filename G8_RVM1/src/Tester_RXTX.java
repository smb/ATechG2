import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Tester_RXTX implements SerialPortEventListener
{
    public Tester_RXTX()
    {
        super();
    }

    void connect( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(portName);
        if( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass()
                    .getName(), 2000);

            if( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;

                serialPort.addEventListener(this);
//                serialPort.notifyOnCTS(true);
                
                serialPort.notifyOnDSR(true);
                //serialPort.notifyOnDataAvailable(false);
                
                serialPort.setSerialPortParams(9600,
                        SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
                        SerialPort.PARITY_EVEN);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);

//                serialPort.enableReceiveFraming(1);
//                serialPort.enableReceiveThreshold(1);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in, serialPort))).start();
                (new Thread(new SerialWriter(out, in))).start();

            }
            else
            {
                System.out
                        .println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    /** */
    public static class SerialReader implements Runnable
    {
        InputStream in;
        SerialPort serialPort;

        public SerialReader(InputStream in, SerialPort serialPort)
        {
            this.in = in;
            this.serialPort = serialPort;
        }

        public void run()
        {
            
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                System.out.println("Start Lesen");
                while( true )
                {
                    len = this.in.read(buffer);
                    if( len > 0 )
                    {
                        System.out.print(new String(buffer, 0, len));
                    }
                }
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
            System.out.println("ich lese heut nichts mehr");
        }
    }

    /** */
    public static class SerialWriter implements Runnable
    {
        OutputStream out;
        InputStream  in;

        public SerialWriter(OutputStream out, InputStream in)
        {
            this.out = out;
            this.in = in;
        }

        public void run()
        {
            try
            {
                while( true )
                {
                    String comand = Eingabe.liesString();
                    comand = comand + "\r";

                    byte[] coman_arr = comand.getBytes("ASCII");
                    for( int i = 0; i < coman_arr.length; i++ )
                    {
                        this.out.write(coman_arr[i]);
                    }
                }
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args )
    {
        try
        {
            (new Tester_RXTX()).connect("COM1");
        }
        catch( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void serialEvent( SerialPortEvent arg0 )
    {
        System.out.println("Event: " + arg0.getEventType());
        if( arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE)
            System.out.println();

    }
}
