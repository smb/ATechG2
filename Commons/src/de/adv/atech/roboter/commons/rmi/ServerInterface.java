/**
 * 
 */
package de.adv.atech.roboter.commons.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import de.adv.atech.roboter.commons.Client;
import de.adv.atech.roboter.commons.interfaces.Command;

/**
 * @author buehls
 * 
 */
public interface ServerInterface extends Remote
{

  public void processCommand(List<Command> commandList) throws RemoteException;

  public boolean ping();

  public void initConnection(Client client);
}
