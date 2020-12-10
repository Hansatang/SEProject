import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeList implements Serializable
{
  private ArrayList<Employee> employees;

  public EmployeeList()
  {
    this.employees = new ArrayList<>();
  }

  /**
   * Gets how many Employees objects are in the list.
   *
   * @return the number of Employees objects in the list
   */
  public int size()
  {
    return employees.size();
  }

  /**
   * Gets a Employee object from position index from the list.
   *
   * @param index the position in the list of the Employee object
   * @return the Employee object at position index if one exists, else null
   */
  public Employee get(int index)
  {
    return employees.get(index);
  }

  public void addEmployee(Employee employee)
  {
    employees.add(employee);
  }


  public void removeEmployee(Employee employee)
  {
    employees.remove(employee);
  }

  public void deleteEmployee(String name)
  {
    employees.remove(getIndexFromName(name));
  }

  public void replaceEmployee(String name,String newName)
  {
    employees.get(getIndexFromName(name)).setName(newName);
  }

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
