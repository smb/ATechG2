package de.adv.atech.roboter.commons.interfaces;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 
 * @author sbu
 * 
 */
public interface Command
{

  /**
   * 
   * @return
   */
  public Map<Enum< ? >, Field> getParameters();

  /**
   * 
   * @return
   */
  public String getCommandName();

  /**
   * 
   * @param name
   * @return
   */
  public Object getParameter(Enum< ? > name);

  /**
   * 
   * @param name
   * @param object
   */
  public void setParameter(Enum< ? > name, Object object);

}
