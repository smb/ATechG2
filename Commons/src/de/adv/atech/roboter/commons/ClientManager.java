package de.adv.atech.roboter.commons;

import java.util.List;
import java.util.Vector;

public class ClientManager
{

  List<Client> clientList;

  public ClientManager()
  {
    this.clientList = new Vector<Client>();
  }

  public boolean registerClient(Client client)
  {
    System.out.println("[ClientManager] registered Client: " + client);

    return this.clientList.add(client);
  }

  public boolean unregisterClient(Client client)
  {
    return this.clientList.remove(client);
  }

  public List<Client> getRegisteredClients()
  {
    return this.clientList;
  }

  public boolean isRegisteredClient(Client client)
  {
    return this.clientList.contains(client);
  }

}
