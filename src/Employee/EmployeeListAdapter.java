package Employee;

import Main.MyFileIO;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * An adapter to the EmployeeList file, making it easy to retrieve and store information.
 * @author Krzysztof Pacierz
 * @version 1.0
 */
public class EmployeeListAdapter
{
  private MyFileIO mfio;
  private String fileName;

  /**
   * 1-argument constructor setting the file name.
   * @param fileName the name and path of the file where employees will be saved and retrieved
   */
  public EmployeeListAdapter(String fileName)
  {
    mfio = new MyFileIO();
    this.fileName = fileName;
  }

  /**
   * Uses the Main.MyFileIO class to retrieve a Employee.EmployeeList object with all employees.
   * @return a Employee.EmployeeList object with all stored employees
   */
  public EmployeeList getAllEmployees()
  {
    EmployeeList employees = new EmployeeList();

    try
    {
      employees = (EmployeeList) mfio.readObjectFromFile(fileName);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File: " + fileName + " not found");
    }
    catch (IOException e)
    {
      System.out.println("IO Error reading file: " + fileName);
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("Class Not Found: " + e.getClass().toString());
    }
    return employees;
  }



  /**
   * Use the Main.MyFileIO class to save employees.
   * @param employees the list of employees that will be saved
   */
  public void saveEmployees(EmployeeList employees)
  {
    try
    {
      mfio.writeToFile(fileName, employees);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found: " + fileName);
    }
    catch (IOException e)
    {
      System.out.println("IO Error writing to file: " + fileName);
    }
  }


}
