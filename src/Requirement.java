import java.io.Serializable;
import java.util.ArrayList;

public class Requirement implements Serializable
{
  private String name,userstory,status;
  private int estimatedHours,totalHoursWorked,requirementID;
  private Deadline deadline;
  private ArrayList<Task> tasks;

  public Requirement(String name, String userstory, String status,
      int estimatedHours, int totalHoursWorked, int requirementID,
      Deadline deadline, ArrayList<Task> tasks)
  {
    this.name = name;
    this.userstory = userstory;
    this.status = status;
    this.estimatedHours = estimatedHours;
    this.totalHoursWorked = totalHoursWorked;
    this.requirementID = requirementID;
    this.deadline = deadline;
    this.tasks = tasks;
  }

  public String getName(){
    return name;
  }


}
