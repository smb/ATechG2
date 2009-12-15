package de.adv.atech.roboter.commons.rmi;

import java.util.Properties;

/**
 * 
 * @author sbu
 * 
 */
public final class Discovery {

	private static java.util.Properties _props;

	public static final String ANY = "any";

	private Discovery() {
	}

	/**
	 * 
	 * @param props
	 */
	public static void setProperties(Properties props) {
		_props = props;
	}

	/**
	 * 
	 * @param fileName
	 * @throws java.io.IOException
	 */
	public static void setProperties(String fileName)
			throws java.io.IOException {

		java.io.FileInputStream fis = new java.io.FileInputStream(fileName);
		_props = new Properties();
		_props.load(fis);
		fis.close();
		_props.list(System.out);
	}

	/**
	 * 
	 * @return
	 * @throws java.net.UnknownHostException
	 */
	public static java.net.InetAddress getMulticastAddress()
			throws java.net.UnknownHostException {

		String multicastAddress = _props.getProperty("rmi.multicast.address");
		return java.net.InetAddress.getByName(multicastAddress);
	}

	/**
	 * 
	 * @return
	 */
	public static int getMulticastPort() {
		return getIntProperty("rmi.multicast.port");
	}

	/**
	 * 
	 * @return
	 */
	public static int getRMIRegistryPort() {
		return getIntProperty("rmi.registry.port");
	}

	/**
	 * 
	 * @return
	 */
	public static String getProtocolDelim() {
		return _props.getProperty("rmi.protocol.delim");
	}

	/**
	 * 
	 * @return
	 */
	public static String getProtocolHeader() {
		return _props.getProperty("rmi.protocol.header");
	}

	/**
	 * 
	 * @return
	 */
	public static int getUnicastPort() {
		return getIntProperty("rmi.unicast.port");
	}

	/**
	 * 
	 * @return
	 */
	public static int getUnicastPortRange() {
		return getIntProperty("rmi.unicast.portRange");
	}

	/**
	 * 
	 * @return
	 */
	public static String getRegistyUrlPrefix() {
		return _props.getProperty("rmi.registry.urlPrefix");
	}

	private static int getIntProperty(String propertyName) {
		String str = _props.getProperty(propertyName);
		return Integer.parseInt(str);
	}
}