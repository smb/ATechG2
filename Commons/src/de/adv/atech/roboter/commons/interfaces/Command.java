package de.adv.atech.roboter.commons.interfaces;

import java.lang.reflect.Field;
import java.util.Map;

import de.adv.atech.roboter.commons.exceptions.CommandException;

/**
 * 
 * @author sbu
 * 
 */
public interface Command {

	/**
	 * 
	 * @return
	 */
	public Map<Enum<?>, Field> getParameters() throws CommandException;

	/**
	 * 
	 * @return
	 */
	public String getCommandName();

	/**
	 * 
	 * @param name
	 * @return
	 * @throws CommandException
	 */
	public Object getParameter(Enum<?> name) throws CommandException;

	/**
	 * 
	 * @param name
	 * @param object
	 */
	public void setParameter(Enum<?> name, Object object)
			throws CommandException;

	/**
	 * 
	 * 
	 * @param informationRef
	 * @return
	 * @throws CommandException
	 */
	public String getCode(Object informationRef) throws CommandException;

}
