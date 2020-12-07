import java.io.Serializable;
import java.util.ArrayList;

public class ProjectList implements Serializable
{
  private ArrayList<Project> projects;

  public ProjectList()
  {
    this.projects = new ArrayList<>();
  }

  public void addProject()
  {}

  public void removeProject(Project selectedProject)
  {
    projects.remove(selectedProject);
  }

  public void removeMember(int index, Member member)
  {
    projects.get(index).getTeam().removeMember(member);
  }


  public int getNumbersOfProjects()
  {
    return 0;
  }

  public Project getProject(int index){
    return null;
  }

  public Project getProject(String projectName){
    int k=0 ;
    for (int i = 0; i < projects.size(); i++)
    {
      if (projects.get(i).getName().equals(projectName))
      {
       k=i;
       break;
      }
    };
    return projects.get(k);
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

  public ArrayList<Project> getProjectsWhereMemberIsIn(Member member){

    ArrayList<Project> projectsMemberIsIn = new ArrayList<>();

    for(int i = 0 ; i < projects.size() ; i++){
      for(int j = 0 ; j < projects.get(i).getTeam().size() ; i++){
        if(projects.get(i).getTeam().get(j).equals(member)){
          projectsMemberIsIn.add(projects.get(i));
        }
      }
    }
    return projectsMemberIsIn;
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
