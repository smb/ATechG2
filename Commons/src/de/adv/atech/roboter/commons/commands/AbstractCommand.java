package de.adv.atech.roboter.commons.commands;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import de.adv.atech.roboter.commons.interfaces.Command;

/**
 * 
 * @author sbu
 * 
 */
public abstract class AbstractCommand implements Command
{

  /**
   * 
   */
  protected String commandName = null;

  Map<Enum< ? >, Field> parameterMap;

  /**
   * 
   */
  private AbstractCommand child;

  private Class enumClass;

  /**
   * 
   */
  public AbstractCommand()
  {
    //
  }

  /**
   * 
   * @param child
   */
  public void init(AbstractCommand child, Class enumClass)
  {
    this.child = child;
    this.enumClass = enumClass;
    this.parameterMap = new HashMap<Enum< ? >, Field>();
    this.parameterMap = findParameters();
  }

  /**
   * 
   */
  public String getCommandName()
  {
    return this.commandName;
  }

  /**
   * 
   */
  public Map<Enum< ? >, Field> getParameters()
  {
    return this.parameterMap;
  }

  private Map<Enum< ? >, Field> findParameters()
  {
    Map<Enum< ? >, Field> returnMap = new HashMap<Enum< ? >, Field>();

    Field[] enumFields = this.enumClass.getFields();

    for (int i = 0; i < enumFields.length; i++)
    {
      Field tmpField = enumFields[i];
      Field classField = null;

      // Passendes Feld zum enumFeld finden...

      try
      {
        classField = this.child.getClass().getField(tmpField.getName());
      }
      catch (SecurityException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (NoSuchFieldException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      returnMap.put(Enum.valueOf(enumClass, tmpField.getName()), classField);
    }

    return returnMap;
  }

  /**
   * 
   */
  public Object getParameter(Enum< ? > name)
  {
    Object returnObject = null;

    if (this.parameterMap.containsKey(name))
    {
      Field parameterField = this.getParameters().get(name);

      try
      {
        returnObject = parameterField.get(this.child);
      }
      catch (IllegalArgumentException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return returnObject;
  }

  /**
   * 
   * @param name
   * @param object
   */
  public void setParameter(Enum< ? > name, Object object)
  {
    if (this.parameterMap.containsKey(name))
    {
      Field parameterField = this.getParameters().get(name);

      try
      {
        parameterField.set(this.child, object);
      }
      catch (IllegalArgumentException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
