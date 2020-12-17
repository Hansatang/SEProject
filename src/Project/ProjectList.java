package Project;

import Employee.Employee;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class containing a list of Project objects.
 *
 * @author Krzysztof Pacierz
 */
public class ProjectList implements Serializable
{
  private ArrayList<Project> projects;

  /**
   * No-argument constructor initializing the ProjectList.
   */
  public ProjectList()
  {
    this.projects = new ArrayList<>();
  }

  /**
   * Adds a Project to the list.
   *
   * @param project the project to add to the list
   */
  public void addProject(Project project)
  {
    projects.add(project);
  }

  /**
   * Removes a Project from the list.
   *
   * @param selectedProject the project to remove from the list
   */
  public void removeProject(Project selectedProject)
  {
    projects.remove(selectedProject);
  }
  /**
   * Gets a Project object with the given  name from the list.
   *
   * @param projectName the  name of the Project object
   * @return the Project object with the given  name if one exists
   */
  public Project getProjectByName(String projectName)
  {
    int k = 0;
    for (int i = 0; i < projects.size(); i++)
    {
      if (projects.get(i).getName().equals(projectName))
      {
        k = i;
        break;
      }
    }
    return projects.get(k);
  }

  /**
   * Gets how many Project objects are in the list.
   *
   * @return the number of Project objects in the list
   */
  public int size()
  {
    return projects.size();
  }

  /**
   * Gets a Project object from position index from the list.
   *
   * @param index the position in the list of the Project object
   * @return the Project.Project object at position index if one exists, else null
   */
  public Project getProjectByIndex(int index)
  {
    if (index < projects.size())
    {
      return projects.get(index);
    }
    else
    {
      return null;
    }
  }

  /**
   * Removes a Employee from the Project inside list.
   *
   * @param index the project index to remove employee
   * @param employee the Employee to remove
   */
  public void removeEmployee(int index, Employee employee)
  {
    projects.get(index).getTeam().removeEmployee(employee);
  }





}
