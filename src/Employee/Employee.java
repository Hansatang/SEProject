package Employee;

import java.io.Serializable;

public class Employee implements Serializable
{
  private String name;

  /**
   * One-argument constructor.
   * @param name the employee's name
   */
  public Employee(String name)
  {
    this.name = name;
  }

  /**
   * Gets the employee's name.
   *
   * @return the employee's name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the employee's name.
   *
   * @param name what the employee's name will be set to
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Returns a string representation of the employee.
   * @return a string representation of the employee in the format: "name"
   */
  @Override
  public String toString() {
    return name;
  }
}
