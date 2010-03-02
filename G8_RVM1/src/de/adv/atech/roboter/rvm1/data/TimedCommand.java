package de.adv.atech.roboter.rvm1.data;

public class TimedCommand
{
	private String commandCode;
	
	//Timeout before sending command
	private long timeout;
	
	public TimedCommand(String ssCommandCode)
	{
		commandCode = ssCommandCode;
		timeout = 0;
	}
	
	public TimedCommand(String ssCommandCode, long ssTimeout)
	{
		commandCode = ssCommandCode;
		timeout = ssTimeout;
	}
	
	public long getTimeout()
	{
		return timeout;
	}
	
	public String getCommandCode()
	{
		return commandCode;
	}
}
