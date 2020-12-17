package Project;

import Main.MyFileIO;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * An adapter to the projects file, to retrieve and store information.
 *
 * @author Krzysztof Pacierz
 */
public class ProjectListAdapter
{
  private MyFileIO mfio;
  private String fileName;

  /**
   * 1-argument constructor setting the file name.
   *
   * @param fileName the name and path of the file where projects will be saved and retrieved
   */
  public ProjectListAdapter(String fileName)
  {
    mfio = new MyFileIO();
    this.fileName = fileName;
  }

  /**
   * Uses the MyFileIO class to retrieve a ProjectList object with all Projects.
   *
   * @return a ProjectList object with all stored projects
   */
  public ProjectList getAllProjects()
  {
    ProjectList projects = new ProjectList();

    try
    {
      projects = (ProjectList) mfio.readObjectFromFile(fileName);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.out.println("IO Error reading file");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("Class Not Found");
    }
    return projects;
  }

  /**
   * Use the MMyFileIO class to retrieve all projects with specified name.
   *
   * @param searchPhrase the name to retrieve projects with
   * @return a ProjectList object with projects with specified name
   */
  public ProjectList getProjectByName(String searchPhrase)
  {
    ProjectList projects = new ProjectList();

    try
    {
      ProjectList result = (ProjectList) mfio.readObjectFromFile(fileName);

      for (int i = 0; i < result.size(); i++)
      {
        if (result.getProjectByIndex(i).getName().contains(searchPhrase))
        {
          projects.addProject(result.getProjectByIndex(i));
        }
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.out.println("IO Error reading file");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("Class Not Found");
    }

    return projects;
  }

  /**
   * Use the MyFileIO class to save projects.
   *
   * @param projects the list of projects that will be saved
   */
  public void saveProjects(ProjectList projects)
  {
    try
    {
      mfio.writeToFile(fileName, projects);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.out.println("IO Error writing to file");
    }
  }

  /**
   * Use the MyFileIO class to retrieve all projects with specified name of employee.
   *
   * @param searchPhrase the name to retrieve projects with specified employee
   * @return a ProjectList object with projects with specified  employee name
   */
  public ProjectList getProjectByEmployeeName(String searchPhrase)
  {
    ProjectList projects = new ProjectList();
    try
    {
      ProjectList result = (ProjectList) mfio.readObjectFromFile(fileName);
      if (searchPhrase.equals(""))
      {
        return result;
      }
      else
      {
        for (int i = 0; i < result.size(); i++)
        {
          for (int j = 0; j < result.getProjectByIndex(i).getTeam().size(); j++)
          {
            if (result.getProjectByIndex(i).getTeam().getEmployee(j).getName()
                .contains(searchPhrase))
            {
              projects.addProject(result.getProjectByIndex(i));
            }
          }

        }
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("File not found: " + fileName);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.out.println("IO Error reading file: " + fileName);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      System.out.println("Class Not Found " + e.getClass().toString());
    }

    return projects;
  }
}
