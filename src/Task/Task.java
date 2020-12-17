package Task;

import Employee.Employee;

import java.io.Serializable;
import java.time.LocalDate;

/**
    * A class representing a task with a name, status, taskID, relatedRequirement, estimatedHours, totalHoursWorked, deadline, respo
    *
    */
public class Task implements Serializable
{
  private String name, status, taskID;
  private int estimatedHours, totalHoursWorked;
  private LocalDate deadline;
  private Employee responsibleEmployee;

  public Task(String name, String taskID,String status, int estimatedHours, LocalDate deadline,
      Employee responsibleEmployee)
  {
    this.deadline = deadline;
    this.name = name;
    this.status = status;
    this.taskID = taskID;
    this.estimatedHours = estimatedHours;
    totalHoursWorked = 0;
    this.responsibleEmployee = responsibleEmployee;
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

  public Employee getResponsibleEmployee()
  {
    return responsibleEmployee;
  }

  public void setResponsibleEmployee(Employee responsibleEmployee)
  {
    this.responsibleEmployee = responsibleEmployee;
  }

  public void setEmpty()
  {
    Employee empty = new Employee("");
    this.responsibleEmployee = empty;
  }


  public void setEstimatedHours(int estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  public void setTotalHoursWorked(int totalHoursWorked)
  {
    this.totalHoursWorked = totalHoursWorked;
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