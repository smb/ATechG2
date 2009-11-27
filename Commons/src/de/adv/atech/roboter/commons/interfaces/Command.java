package de.adv.atech.roboter.commons.interfaces;

import java.lang.reflect.Field;
import java.util.Map;

public interface Command {

	public Map<String, Field> getParameters();

	public String getCommandName();

	public Object getParameter(String name);

	public void setParameter(String name, Object object);

}
