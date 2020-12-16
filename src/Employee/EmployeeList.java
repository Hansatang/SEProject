package Employee;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeList implements Serializable {
  private ArrayList<Employee> employees;

  public EmployeeList()
  {
    this.employees = new ArrayList<>();
  }

 /** Gets how many Employees objects are in the list.
   * @return integer of the number of Employee objects in the list
   */
  public int size()
  {
    return employees.size();
  }

 /** Gets an Employee object from position index from the list.
   * @param index the position in the list of the Employee.Employee object
   * @return the Employee.Employee object at position index if one exists, else null
   */
  public Employee get(int index) {
    return employees.get(index);
  }

  /**
   * Adds a Employee to the list.
   * @param employee the Employee to add to the list
   */
  public void addEmployee(Employee employee) {
    employees.add(employee);
  }

  /**
   * Removes a Employee from the list.
   * @param employee the Employee to remove from the list
   */
  public void removeEmployee(Employee employee)
  {
    employees.remove(employee);
  }

  /**
   * Removes a Employee from the list.
   * @param name the  name of Employee to remove from the list
   */
  public void deleteEmployee(String name)
  {
    employees.remove(getIndexFromName(name));
  }

  /**
   * Changes the employee's name.
   * @param  name of which employee name will be changes
   * @param newName what the employee's name will be set to
   */
  public void replaceEmployee(String name,String newName)
  {
    employees.get(getIndexFromName(name)).setName(newName);
  }

  /**
   * Gets a index of the given  name  of Employee from the list.
   * @param name the  name of the Employee object
   * @return the index of Employee with specific name if one exists
   */
  public int getIndexFromName(String name)
  {
    for (int i = 0; i < employees.size(); i++)
    {
      if (employees.get(i).getName().equals(name))
      {
        return i;
      }
    }
    return -1;
  }

  /**
   * Gets the employee objects in ArrayList.
   *
   * @return the ArrayList of employees
   */
  public ArrayList<Employee> getEmployees()
  {
    return employees;
  }

  public boolean equals(Object obj)
  {
    if (obj instanceof EmployeeList)
    {
      if (this.getEmployees().equals(((EmployeeList) obj).getEmployees()))
      {
        return true;
      }
    }
    return false;
  }

  public String toString()
  {
    String str = "";
    if (employees.size()!=0)
    {
      for (int i = 0; i < employees.size(); i++)
      {
        str += employees.get(i).getName() + " , ";
      }
      str = str.substring(0, str.length() - 2);
    }
    return str;
  }
}
