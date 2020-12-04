import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Requirement implements Serializable
{
  private String name,userstory,status;
  private int estimatedHours,totalHoursWorked;
  private ArrayList<Task> tasks;
  private LocalDate deadline;
  private MemberList team;

  public String getStatus()
  {
    return status;
  }

  public Requirement(String name, String userstory, String status, LocalDate deadline, MemberList team) {
    this.deadline = deadline;
    this.name = name;
    this.userstory = userstory;
    this.status = status;
    this.estimatedHours = 0;
    this.totalHoursWorked = 0;
    this.tasks = new ArrayList<>();
    this.team = team;
  }

  public MemberList getTeam()
  {
    return team;
  }

  public LocalDate getDeadline()
  {
    return deadline;
  }

  public String getName(){
    return name;
  }

  public int getTotalHoursWorked()
  {
    return totalHoursWorked;
  }

  public int getEstimatedHours()
  {
    return estimatedHours;
  }

  public String getUserstory()
  {
    return userstory;
  }


}
