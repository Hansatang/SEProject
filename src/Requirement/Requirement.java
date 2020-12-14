package Requirement;

import Employee.EmployeeList;
import Task.Task;
import Task.TaskList;

import java.io.Serializable;
import java.time.LocalDate;

public class Requirement implements Serializable
{
  private String name, userstory, status;
  private int estimatedHours, totalHoursWorked, id;
  private TaskList tasks;
  private LocalDate deadline;
  private EmployeeList team;

  public String getStatus()
  {
    return status;
  }

  public Requirement(String name, String userstory, String status,
      LocalDate deadline, EmployeeList team)
  {
    this.deadline = deadline;
    this.name = name;
    this.userstory = userstory;
    this.status = status;
    this.estimatedHours = 0;
    this.totalHoursWorked = 0;
    this.tasks = new TaskList();
    this.team = team;
  }

  public void editRequirement(String name, String userStory , String status, EmployeeList team, LocalDate deadline)
  {
    this.name = name;
    this.userstory = userStory;
    this.status = status;
    this.team = team;
    this.deadline = deadline;
  }

  public TaskList getTasks()
  {
    return tasks;
  }

  public void setUserStory(String userstory)
  {
    this.userstory = userstory;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public void setDeadline(LocalDate deadline)
  {
    this.deadline = deadline;
  }

  public void setTeam(EmployeeList team)
  {
    this.team = team;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setEstimatedHours(int estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  public void setTotalHoursWorked(int totalHoursWorked)
  {
    this.totalHoursWorked = totalHoursWorked;
  }

  public void checkTasks()
  {
    int k = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      if (tasks.getTask(i).getStatus().equals("Ended"))
      {
        k++;
      }
    }
    if (k == tasks.size())
    {
      setStatus("Ended");
    }
  }

  public void remove(Task task)
  {
    tasks.removeTask(task);
  }

  public EmployeeList getTeam()
  {
    return team;
  }

  public LocalDate getDeadline()
  {
    return deadline;
  }

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public int getTotalHoursWorked()
  {
    int sum = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      sum += tasks.getTask(i).getTotalHoursWorked();
    }
    return sum;
  }

  public int getEstimatedHours()
  {
    int sum = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      sum += tasks.getTask(i).getEstimatedHours();
    }
    return sum;
  }

  public String getUserstory()
  {
    return userstory;
  }

}
