package Requirement;

import Task.Task;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class containing a list of Requirement objects
 *
 * @author Simon Samuel
 */
public class RequirementList implements Serializable
{

  private ArrayList<Requirement> requirements;

  /**
   * No-argument constructor initializing the ArrayList of Requirements
   */
  public RequirementList()
  {
    this.requirements = new ArrayList<>();
  }

  /**
   * Adds a Requirement to the ArrayList requirements.
   *
   * @param requirement the Requirement to be added to the ArrayList requirements.
   */
  public void addRequirement(Requirement requirement)
  {
    requirements.add(requirement);
  }

  /**
   * Removes a Requirement from the ArrayList requirements.
   *
   * @param requirement the Requirement to remove from the ArrayList requirements.
   */
  public void removeRequirement(Requirement requirement)
  {
    requirements.remove(requirement);
  }

  /**
   * Gets how many Requirement object are in the ArrayList requirements.
   *
   * @return the number of Requirement objects in the ArrayList requirements.
   */
  public int size()
  {
    return requirements.size();
  }

  /**
   * Gets a Requirement object with a given index from the ArrayList of Requirement objects.
   *
   * @param index the index number of the Requirement object
   * @return the Requirement object with the given index if it is in the requirement list.
   */
  public Requirement getRequirement(int index)
  {
    return requirements.get(index);
  }

  /**
   * Gets the first Requirement object that has the same String as the parameter.
   *
   * @param requirementName the name to check the ArrayList requirements through.
   * @return a Requirement object that has the name equal to requirementName.
   */
  public Requirement getRequirementsByName(String requirementName)
  {
    int k = 0;
    for (int i = 0; i < requirements.size(); i++)
    {
      if (requirements.get(i).getName().equals(requirementName))
      {
        k = i;
        break;
      }
    }
    return requirements.get(k);
  }

  /**
   * Gets ArrayList object requirements which contains all Requirement objects.
   *
   * @return the ArrayList requirements containing all requirements.
   */
  public ArrayList<Requirement> getRequirements()
  {
    return requirements;
  }

  /**
   * Checks if another object is equal to this one.
   *
   * @param obj Object that are being checked.
   * @return boolean whether it is true or false that obj is equal to this object.
   */
  public boolean equals(Object obj)
  {

    if (obj instanceof ArrayList)
    {
      if (getRequirements().equals((ArrayList) obj))
      {
        return true;
      }
    }
    return false;
  }


}
