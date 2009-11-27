package de.adv.atech.roboter.commons.commands;

/**
 * 
 * @author sbu
 * 
 */
public class DefaultMove extends AbstractCommand
{

  /**
   * 
   */
  public Double X;

  /**
   * 
   */
  public Double Y;

  /**
   * 
   */
  public Double Z;

  /**
   * 
   */
  public String KeinGueltigerParameter;

  /**
   * 
   * @author sbu
   * 
   *         Enum ohne "KeinGueltigerParameter" - liste der "gueltigen"
   *         Parameter
   */
  public enum Parameter
  {
    X, Y, Z
  }

  /**
   * 
   */
  public DefaultMove()
  {
    super();
    init(this, Parameter.class);
    this.commandName = "Move";
  }
}
