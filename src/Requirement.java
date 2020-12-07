import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Requirement implements Serializable
{
  private String name,userstory,status;
  private int estimatedHours,totalHoursWorked;
  private TaskList tasks;
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
    this.tasks = new TaskList();
    this.team = team;
  }

  public TaskList getTasks()
  {
    return tasks;
  }

  public void setUserstory(String userstory) {
    this.userstory = userstory;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public void setTeam(MemberList team) {
    this.team = team;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void remove(Task task)
  {
    tasks.removeTask(task);
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
    int sum = 0;
    for(int i = 0 ; i < tasks.size() ; i++){
      sum += tasks.getTask(i).getTotalHoursWorked();
    }
    return sum;
  }

  public int getEstimatedHours()
  {
    int sum = 0;
    for(int i = 0 ; i < tasks.size() ; i++){
      sum += tasks.getTask(i).getEstimatedHours();
    }
    return sum;
  }



  public String getUserstory()
  {
    return userstory;
  }


}
