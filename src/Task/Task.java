package Task;

import Employee.Employee;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class representing a task with a name, status, taskID, estimatedHours, totalHoursWorked, deadline, respo
 *
 * @author Dorin Pascal
 */
public class Task implements Serializable
{
  private String name, status, taskID;
  private int estimatedHours, totalHoursWorked;
  private LocalDate deadline;
  private Employee responsibleEmployee;

  /**
   * Six-argument constructor.
   *
   * @param name                the task's name.
   * @param taskID              the task's taskID.
   * @param status              the task's status.
   * @param deadline            the task's deadline.
   * @param responsibleEmployee the task's responsible employee.
   * @param estimatedHours      the task's estimated hours worked.
   */

  public Task(String name, String taskID, String status, int estimatedHours,
      LocalDate deadline, Employee responsibleEmployee)
  {
    this.deadline = deadline;
    this.name = name;
    this.status = status;
    this.taskID = taskID;
    this.estimatedHours = estimatedHours;
    totalHoursWorked = 0;
    this.responsibleEmployee = responsibleEmployee;
  }

  /**
   * Sets the task's name.
   *
   * @param name what the task's name will be set to.
   */

  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Sets the status's name.
   *
   * @param status what the task's status will be set to.
   */
  public void setStatus(String status)
  {
    this.status = status;
  }

  /**
   * Gets the task's ID.
   *
   * @return Integer value of the task's ID.
   */
  public String getTaskID()
  {
    return taskID;
  }

  /**
   * Sets the task's ID.
   *
   * @param taskID what the task's ID will be set to.
   */
  public void setTaskID(String taskID)
  {
    this.taskID = taskID;
  }

  /**
   * Sets the task's deadline.
   *
   * @param deadline what the task's deadline will be set to.
   */
  public void setDeadline(LocalDate deadline)
  {
    this.deadline = deadline;
  }

  /**
   * Gets the task's responsible employee.
   *
   * @return String value of the task's responsible employee.
   */
  public Employee getResponsibleEmployee()
  {
    return responsibleEmployee;
  }

  /**
   * Sets the task's responsible Employee.
   *
   * @param responsibleEmployee what the task's responsible member will be set to.
   */

  public void setResponsibleEmployee(Employee responsibleEmployee)
  {
    this.responsibleEmployee = responsibleEmployee;
  }

  /**
   * Setter to predefine the employee.
   */

  public void setEmpty()
  {
    Employee empty = new Employee("");
    this.responsibleEmployee = empty;
  }

  /**
   * Sets the task's estimated hours of work.
   *
   * @param estimatedHours what the task's estimated hours  will be set to.
   */
  public void setEstimatedHours(int estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  /**
   * Sets the task's total hours worked
   *
   * @param totalHoursWorked what the task's total hours worked will be set to.
   */

  public void setTotalHoursWorked(int totalHoursWorked)
  {
    this.totalHoursWorked = totalHoursWorked;
  }

  /**
   * Gets the task's name.
   *
   * @return String value of the task's name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Gets the task's status.
   *
   * @return String value of the task's status.
   */

  public String getStatus()
  {
    return status;
  }

  /**
   * Gets the task's deadline.
   *
   * @return LocalDate class object of the task's deadline.
   */
  public LocalDate getDeadline()
  {
    return deadline;
  }

  /**
   * Gets the task's estimated hours.
   *
   * @return Integer value of the task's estimated hours.
   */

  public int getEstimatedHours()
  {
    return estimatedHours;
  }

  /**
   * Gets the task's total hours worked.
   *
   * @return Integer value of the task's total hours worked.
   */

  public int getTotalHoursWorked()
  {
    return totalHoursWorked;
  }
}