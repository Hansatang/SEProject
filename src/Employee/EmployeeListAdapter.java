package Employee;

import Main.MyFileIO;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * An adapter to the students file, making it easy to retrieve and store information.
 * @author Krzysztof PAcierz
 * @version 1.0
 */
public class EmployeeListAdapter {
  private MyFileIO mfio;
  private String fileName;

  /**
   * 1-argument constructor setting the file name.
   * @param fileName the name and path of the file where members will be saved and retrieved
   */
  public EmployeeListAdapter(String fileName)
  {
    mfio = new MyFileIO();
    this.fileName = fileName;
  }

  /**
   * Uses the Main.MyFileIO class to retrieve a MemberList object with all members.
   * @return a MemberList object with all stored members
   */
  public EmployeeList getAllMembers()
  {
    EmployeeList employeeList = new EmployeeList();

    try
    {
      employeeList = (EmployeeList)mfio.readObjectFromFile(fileName);
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
      System.out.println("Class Not Found: ");
    }
    return employeeList;
  }



  /**
   * Use the Main.MyFileIO class to save members.
   * @param members the list of members that will be saved
   */
  public void saveMembers(EmployeeList members)
  {
    try
    {
      mfio.writeToFile(fileName, members);
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
