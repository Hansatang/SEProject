import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable
{
  private String name;
  private ArrayList<Requirement> requirements;
  private MemberList team;

  public Project(String name, MemberList team)
  {
    this.name = name;
    this.requirements = new ArrayList<>();
    this.team = team;
  }

  public ArrayList<Requirement> getRequirements()
  {
    return requirements;
  }

  public void remove(Requirement requirement)
  {
    requirements.remove(requirement);
  }

  public void add(Requirement requirement)
  {
    requirements.add(requirement);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTeam(MemberList team) {
    this.team = team;
  }

  /**
   * Gets the project's name.
   * @return the project's name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the project's requirements.
   * @return void
   */
  public void setRequirements(ArrayList<Requirement> requirements) {
    this.requirements = requirements;
  }
  public String getValue()
  {
    String str = "";
    for (int i = 0; i < team.size(); i++)
    {
      str+= team.get(i).getName()+" ,";
    }
    return str;
  }


  /**
   * Gets the project's team.
   * @return the project's team
   */
  public MemberList getTeam()
  {
    return team;
  }

  public String toString(){
    return name;
  }
}
