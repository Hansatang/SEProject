import java.io.Serializable;

public class Task implements Serializable
{
  private String name,status;
  private int relatedRequirement,taskID, estimatedHours,totalHoursWorked;
  private Member responsibleMember;
  private  MemberList taskMembers;

  public Task(String name, String status, int relatedRequirement, int taskID,
      int estimatedHours, int totalHoursWorked, Member responsibleMember,
      MemberList taskMembers)
  {
    this.name = name;
    this.status = status;
    this.relatedRequirement = relatedRequirement;
    this.taskID = taskID;
    this.estimatedHours = estimatedHours;
    this.totalHoursWorked = totalHoursWorked;
    this.responsibleMember = responsibleMember;
    this.taskMembers = taskMembers;
  }

  public String getName() {
    return name;
  }
}
