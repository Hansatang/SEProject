import java.io.Serializable;
import java.util.ArrayList;

public class ProjectList implements Serializable
{
  private ArrayList<Project> projects;

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
   * Remove a Project to the list.
   *
   * @param selectedProject the project to remove from the list
   */
  public void removeProject(Project selectedProject)
  {
    projects.remove(selectedProject);
  }

  /**
   * Gets a a Project with specified name.
   *
   * @param projectName the String of wanted project
   * @return the Project with specified name
   */
  public Project getProject(String projectName)
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
   * @return the Project object at position index if one exists, else null
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

}
