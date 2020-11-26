import java.util.ArrayList;

public class Project
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
