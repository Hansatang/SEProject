import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable
{
  private String name, status, taskID;
  private int relatedRequirement, estimatedHours, totalHoursWorked;
  private LocalDate deadline;
  private Member responsibleMember;
  private MemberList taskMembers;

  public Task(String name, String taskID,String status, LocalDate deadline,
      MemberList taskMembers)
  {
    this.deadline = deadline;
    this.name = name;
    this.status = status;
    relatedRequirement = 0;
    this.taskID = taskID;
    estimatedHours = 0;
    totalHoursWorked = 0;
    responsibleMember = null;
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
