import java.util.ArrayList;

public class ProjectList
{
  private ArrayList<Project> projects;

  public ProjectList()
  {
    this.projects = new ArrayList<>();
  }

  public void addProject()
  {}

  public void removeProject(Project selectedProject)
  {}

  public int getNumbersOfProjects()
  {
    return 0;
  }

  public Project getProject(int index){
    return null;
  }

  public Project getProject(String projectName){
    return null;
  }

  /**
   * Gets how many Project objects are in the list.
   * @return the number of Project objects in the list
   */
  public int size()
  {
    return projects.size();
  }

  /**
   * Gets a Project object from position index from the list.
   * @param index the position in the list of the Project object
   * @return the Project object at position index if one exists, else null
   */
  public Project get(int index)
  {
    if(index<projects.size())
    {
      return projects.get(index);
    }
    else
    {
      return null;
    }
  }

  // Should be bullshit but...
  public int[] getIndexesFromInput(String name){

    ArrayList<Integer> searchResultIndexesArrList = new ArrayList<>();

    for(int i = 0 ; i < projects.size() ; i++){

      if(projects.get(i).getName().contains(name)){
        searchResultIndexesArrList.add(i);
      }
    }

    int[] searchResultIndexesArr = new int[searchResultIndexesArrList.size()];
    for(int i = 0 ; i < searchResultIndexesArr.length ; i++){
      searchResultIndexesArr[i] = searchResultIndexesArrList.get(i);
    }

    return searchResultIndexesArr;
  }

  /**
   * Adds a Project to the list.
   * @param project the project to add to the list
   */
  public void add(Project project)
  {
    projects.add(project);
  }

}
