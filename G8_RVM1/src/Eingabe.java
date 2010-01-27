import java.io.*;

public class Eingabe
{
  public static String liesString()
  {
    String ergebnis = new String();
    
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader ein = new BufferedReader(isr);
    
    try
    {
      ergebnis = ein.readLine();
    }
    catch(IOException x)
    {
      System.err.println("Fehler: " + x + " wir tun aber nichts!");
    }
    return ergebnis;
  }
  
  public static int liesInteger()
  {
    String zahlWort = new String();
    int ergebnis = 0;
    
    zahlWort = liesString();

    try
    {
      ergebnis = Integer.parseInt(zahlWort);
    }
    catch(NumberFormatException y)
    {
      System.err.println("Fehler: " + y + " wir tun aber nichts!");
    }
    
    return ergebnis;
  }
  
  public static double liesDouble()
  {
    String zahlWort = new String();
    double ergebnis = 0.0;

    zahlWort = liesString();

    try
    {
      ergebnis = Double.parseDouble(zahlWort);
    }
    catch(NumberFormatException y)
    {
      System.err.println("Fehler: " + y + " wir tun aber nichts!");
    }

    return ergebnis;
  }
}
