import java.io.Serializable;

public class Project implements Serializable
{
  private String name;
  private RequirementList requirements;
  private EmployeeList team;

  public Project(String name, EmployeeList team)
  {
    this.name = name;
    this.requirements = new RequirementList();
    this.team = team;
  }

  public RequirementList getRequirements()
  {
    return requirements;
  }

  public void remove(Requirement requirement)
  {
    requirements.remove(requirement);
  }

  public void add(Requirement requirement)
  {
    requirements.getRequirements().add(requirement);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTeam(EmployeeList team) {
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
  public void setRequirements(RequirementList requirements) {
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
  public EmployeeList getTeam()
  {
    return team;
  }

  public String toString(){
    return name;
  }
}
