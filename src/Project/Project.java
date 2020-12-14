package Project;

import Employee.EmployeeList;
import Requirement.Requirement;
import Requirement.RequirementList;

import java.io.Serializable;
/**
 * A class representing a project with a name, requirement list and employee team.
 * @author Krzysztof Pacierz
 */
public class Project implements Serializable
{
  private String name;
  private RequirementList requirements;
  private EmployeeList team;

  /**
   * Two-argument constructor.
   * @param name the project's name
   * @param team the project's employee team
   */
  public Project(String name, EmployeeList team)
  {
    this.name = name;
    this.requirements = new RequirementList();
    this.team = team;
  }

  public void editProject(String name,EmployeeList team )
  {
    this.name = name;
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
   * Sets the project's name.
   * @param name what the project's name will be set to
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the project's requirement list.
   * @return the project's requirement list
   */
  public RequirementList getRequirements()
  {
    return requirements;
  }

  /**
   * Sets the project's requirements.
   * @param requirements what the project's requirement list will be set to
   */
  /**
   * Gets the project's team.
   * @return the project's team
   */
  public EmployeeList getTeam()
  {
    return team;
  }

  /**
   * Sets the project's team.
   * @param team what the project's team will be set to
   */
  public void setTeam(EmployeeList team) {
    this.team = team;
  }


  public String toString(){
    return name;
  }
}
