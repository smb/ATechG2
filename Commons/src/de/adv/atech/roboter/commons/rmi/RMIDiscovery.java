package de.adv.atech.roboter.commons.rmi;

/*
 * javainpractice.net 1999-2001
 * 
 * This software is intended to be used for educational purposes only.
 * 
 * We make no representations or warranties about the suitability of the
 * software.
 * 
 * Any feedback relating to this software can be sent to info@javainpractice.net
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.MarshalledObject;
import java.rmi.Naming;
import java.rmi.Remote;

/**
 * Utility class used by RMI clients to discover RMI servers in a Jini like way.
 * 
 * e.g Remote server=RMIDiscovery.lookup(interfaceClass,serverName);
 * 
 * @author Phil Bishop
 * @version 1
 * @since 1
 * @see RMILookup
 */
public class RMIDiscovery {

	/**
	 * The interface class object for the server we are tring to discover
	 * 
	 * @since 1
	 */
	private Class _serviceInterface;

	/**
	 * The unique name of the server we are trying to discover
	 */
	private String _serviceName;

	/**
	 * Unicast listener waiting for responses from the RMILookupService
	 */
	private ServerSocket _listener;

	/**
	 * The port the _listener socketing is listening on
	 */
	private int _listenerPort;

	/**
	 * Can either be a Remote object or Exception (if failed)
	 */
	private Object _discoveryResult;

	/**
	 * thread synchronization/notification lock.
	 */
	private Object _lock = new Object();

	private RMIDiscovery(Class serviceInterface, String serviceName) {
		_serviceInterface = serviceInterface;
		_serviceName = serviceName;
		if (_serviceName == null || _serviceName.length() == 0) {
			_serviceName = Discovery.ANY;
		}

	}

	/**
	 * Find first matching services via multicast
	 * 
	 * @param serviceInterface
	 *            Interface that the server we are trying to discover
	 * @param serviceName
	 *            Unique name of server we are trying to discover.
	 * @return The discovered server ref.
	 * @exception java.rmi.ConnectException
	 */
	public static Remote lookup(Class serviceInterface, String serviceName)
			throws java.rmi.ConnectException {

		RMIDiscovery disco = new RMIDiscovery(serviceInterface, serviceName);
		return disco.lookupImpl();
	}

	/**
	 * return matching service via unicast
	 * 
	 * @param serviceName
	 *            Name of service
	 * @param host
	 *            host to attempt lookup on
	 */
	public static Remote lookup(String serviceName, String host) {
		Remote[] remote = lookupAll(serviceName, new String[] {
			host
		}, false);
		return remote[0];
	}

	/**
	 * return the first match service in the host[] via unicast
	 * 
	 * @param serviceName
	 *            Name of service
	 * @param host
	 *            hosts to attempt lookups on
	 * 
	 */
	public static Remote lookup(String serviceName, String[] host) {
		Remote[] remote = lookupAll(serviceName, host, false);
		return remote[0];
	}

	/**
	 * return all matching services via unicast
	 * 
	 * @param serviceName
	 *            Name of service
	 * @param host
	 *            hosts to attempt lookups on
	 */

	public static Remote[] lookupAll(String serviceName, String[] host) {
		return lookupAll(serviceName, host, true);
	}

	// impl
	private static Remote[] lookupAll(String serviceName, String[] host,
			boolean tryAll) {

		String url = "rmi://";
		String imPrefix = Discovery.getRegistyUrlPrefix();
		Remote remote[] = new Remote[host.length];

		for (int i = 0; i < host.length; i++) {
			try {
				String hostAndPort = host[i];
				StringBuffer buf = new StringBuffer();
				buf.append(url);
				buf.append(hostAndPort);
				buf.append("/");
				buf.append(imPrefix);
				buf.append(serviceName);

				System.out.println("RMI discovery: Using unicast url "
						+ buf.toString());

				remote[i] = Naming.lookup(buf.toString());
				if (tryAll == false) {
					return new Remote[] {
						remote[i]
					};
				}

			}
			catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
		return remote;
	}

	private Remote lookupImpl() throws java.rmi.ConnectException {

		startListener();
		startRequester();
		synchronized (_lock) {

			while (_discoveryResult == null) {
				try {
					_lock.wait();
				}
				catch (InterruptedException ex) {
					ex.printStackTrace();
					return null;
				}
			}
		}
		try {
			_listener.close();
		}
		catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
		// check if the result is an exception
		if (_discoveryResult instanceof Exception) {
			throw new java.rmi.ConnectException("RMI discovery exception",
					(Exception) _discoveryResult);
		}
		return (Remote) _discoveryResult;
	}

	private void startListener() {
		int port = Discovery.getUnicastPort();
		int range = Discovery.getUnicastPortRange();

		for (int i = port; _listener == null && i < port + range; i++) {
			try {
				_listener = new ServerSocket(i);
				_listenerPort = i;
			}
			catch (IOException ex) {
				System.err.println("Port " + i + " exception "
						+ ex.getMessage());
			}
		}
		if (_listener == null) {
			throw new RuntimeException(
					"Failed to create listener socket in port range " + port
							+ "-" + (port + range));
		}
		Thread listenerThread = new Thread() {

			public void run() {

				try {
					Socket sock = _listener.accept();
					ObjectInputStream ois = new ObjectInputStream(sock
							.getInputStream());
					MarshalledObject mo = (MarshalledObject) ois.readObject();
					sock.close();
					_discoveryResult = mo.get();
				}
				catch (IOException ex) {
					_discoveryResult = ex;
				}
				catch (ClassNotFoundException ex) {
					_discoveryResult = ex;
				}
				synchronized (_lock) {
					_lock.notify();
				}
			}
		};
		listenerThread.start();
		System.out.println("RMI discovery: Unicast Listener thread started ");
	}

	private void startRequester() {

		Thread requester = new Thread() {

			public void run() {
				try {
					String hostName = InetAddress.getLocalHost().getHostName();

					InetAddress address = Discovery.getMulticastAddress();
					int multicastPort = Discovery.getMulticastPort();
					String header = Discovery.getProtocolHeader();
					String delim = Discovery.getProtocolDelim();

					String outMsg = header + delim + _listenerPort + delim
							+ _serviceInterface.getName() + delim
							+ _serviceName;
					byte[] buf = outMsg.getBytes();

					MulticastSocket socket = new MulticastSocket(multicastPort);
					socket.joinGroup(address);

					int nAttempts = 7;
					for (int nTimes = 0; _discoveryResult == null
							&& nTimes < nAttempts; nTimes++) {

						// can we move this out of the loop?
						DatagramPacket packet = new DatagramPacket(buf,
								buf.length, address, multicastPort);

						System.out.println("RMI discovery: Sending request "
								+ outMsg);
						socket.send(packet);
						Thread.sleep(5000);
					}
					socket.leaveGroup(address);
					socket.close();
					if (_discoveryResult == null) {
						throw new Exception("RMI discovery timed out after "
								+ nAttempts);
					}
				}
				catch (Exception ex) {
					_discoveryResult = ex;
					synchronized (_lock) {
						_lock.notifyAll();
					}
				}
			}
		};
		requester.start();
		System.out.println("RMI discovery: Requester thread started ");
	}

}