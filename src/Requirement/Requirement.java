package Requirement;

import Employee.EmployeeList;
import Task.Task;
import Task.TaskList;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class representing a requirement with a name, user story, status, id, estimated working hours,
 * total worked hours, task list, deadline and a employee team.
 *
 * @author Simon Samuel
 */
public class Requirement implements Serializable
{
  private String name, userstory, status, requirementId;
  private int estimatedHours, totalHoursWorked;
  private TaskList tasks;
  private LocalDate deadline;
  private EmployeeList team;

  /**
   * Five-argument constructor.
   *
   * @param name the requirement's name.
   * @param userstory the requirement's user story.
   * @param status the requirement's status.
   * @param deadline the requirement's deadline.
   * @param team the requirement's employee team.
   */
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

  /**
   *
   * @param name
   * @param userStory
   * @param status
   * @param team
   * @param deadline
   */
  public void editRequirement(String name, String userStory , String status, EmployeeList team, LocalDate deadline)
  {
    this.name = name;
    this.userstory = userStory;
    this.status = status;
    this.team = team;
    this.deadline = deadline;
  }

  /**
   * Gets the requirement's status.
   *
   * @return String value of the requirement's status.
   */
  public String getStatus()
  {
    return status;
  }

  /**
   * Gets the requirement's tasks.
   *
   * @return TaskList class object of the requirement's task list.
   */
  public TaskList getTasks()
  {
    return tasks;
  }

  /**
   * Sets the requirement's user story.
   *
   * @param userstory what the requirement's user story will be set to.
   */
  public void setUserStory(String userstory)
  {
    this.userstory = userstory;
  }

  /**
   * Sets the requirement's status.
   *
   * @param status what the requirement's status will be set to.
   */
  public void setStatus(String status)
  {
    this.status = status;
  }

  /**
   * Sets the requirement's deadline.
   *
   * @param deadline what the requirement's deadline will be set to.
   */
  public void setDeadline(LocalDate deadline)
  {
    this.deadline = deadline;
  }

  /**
   * Sets the requirement's team.
   *
   * @param team what the requirement's team will be set to.
   */
  public void setTeam(EmployeeList team)
  {
    this.team = team;
  }

  /**
   * Sets the requirement's name.
   *
   * @param name what the requirement's name will be set to.
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Sets the requirement's amount of estimated hours to finish the requirement.
   *
   * @param estimatedHours what the requirement's estimated hours will be set to.
   */
  public void setEstimatedHours(int estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  /**
   * Sets the requirement's amount of total hours that it took to finish the requirement.
   *
   * @param totalHoursWorked what the requirement's total hours will be set to.
   */
  public void setTotalHoursWorked(int totalHoursWorked)
  {
    this.totalHoursWorked = totalHoursWorked;
  }

  /**
   * Sets the status to "Emded" if all the tasks have the status "Ended".
   */
  public void checkTasks()
  {
    if (!tasks.isEmpty())
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
  }

  /**
   * Removes a specific task from the task list.
   *
   * @param task the removed task.
   */

  public void remove(Task task)
  {
    tasks.removeTask(task);
  }

  /**
   * Gets the requirement's employee team.
   *
   * @return EmployeeList class object of the requirement's team.
   */
  public EmployeeList getTeam()
  {
    return team;
  }

  /**
   * Gets the requirement's deadline.
   *
   * @return LocalDate class object of the requirement's deadline.
   */
  public LocalDate getDeadline()
  {
    return deadline;
  }

  /**
   * Gets the requirement's name.
   *
   * @return String value of the requirement's name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get the requirement's ID.
   *
   * @return String value of the requirement's ID.
   */
  public String getId()
  {
    return requirementId;
  }

  /**
   * Sets the requirement's ID.
   *
   * @param requirementId the requirement's ID.
   */
  public void setId(String requirementId)
  {
    this.requirementId = requirementId;
  }

  /**
   * Gets the total amount of hours worked on each task in the requirement.
   *
   * @return the total hours worked.
   */
  public int getTotalHoursWorked()
  {
    int sum = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      sum += tasks.getTask(i).getTotalHoursWorked();
    }
    return sum;
  }

  /**
   * Gets the total amount of estimated work hours summarized from each task
   * in the task list.
   *
   * @return the total estimated work hours.
   */
  public int getEstimatedHours()
  {
    int sum = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      sum += tasks.getTask(i).getEstimatedHours();
    }
    return sum;
  }

  /**
   * Gets the user story.
   *
   * @return String value of the user story.
   */
  public String getUserstory()
  {
    return userstory;
  }

}
