import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable
{
  private String name, status, taskID;
  private int relatedRequirement, estimatedHours, totalHoursWorked;
  private LocalDate deadline;
  private Employee responsibleEmployee;
  private EmployeeList taskMembers;

  public Task(String name, String taskID,String status, int estimatedHours, LocalDate deadline,
      EmployeeList taskMembers)
  {
    this.deadline = deadline;
    this.name = name;
    this.status = status;
    relatedRequirement = 0;
    this.taskID = taskID;
    this.estimatedHours = estimatedHours;
    totalHoursWorked = 0;
    responsibleEmployee = null;
    this.taskMembers = taskMembers;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getTaskID()
  {
    return taskID;
  }

  public void setTaskID(String taskID)
  {
    this.taskID = taskID;
  }

  public void setDeadline(LocalDate deadline)
  {
    this.deadline = deadline;
  }

  public EmployeeList getTaskMembers()
  {
    return taskMembers;
  }

  public void setEstimatedHours(int estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  public void setTotalHoursWorked(int totalHoursWorked)
  {
    this.totalHoursWorked = totalHoursWorked;
  }

  public void setTaskEmployees(EmployeeList taskMembers)
  {
    this.taskMembers = taskMembers;
  }

  public String getName()
  {
    return name;
  }

  public String getStatus()
  {
    return status;
  }

  public LocalDate getDeadline()
  {
    return deadline;
  }

  public int getEstimatedHours()
  {
    return estimatedHours;
  }

  public int getTotalHoursWorked()
  {
    return totalHoursWorked;
  }
}
