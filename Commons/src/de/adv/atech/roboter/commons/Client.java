package de.adv.atech.roboter.commons;

import java.net.InetAddress;

public class Client
{

  protected InetAddress address;

  protected String identifier;

  public Client(String identifier, InetAddress address)
  {
    this.identifier = identifier;
    this.address = address;
  }

  public InetAddress getInetAddress()
  {
    return this.address;
  }

  public String getIdentifier()
  {
    return this.identifier;
  }
}