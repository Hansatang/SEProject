import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable
{
  private String name, status;
  private int relatedRequirement, estimatedHours, totalHoursWorked, id;
  private LocalDate deadline;
  private Employee responsibleEmployee;

  public Task(String name,String status, int estimatedHours, LocalDate deadline, Employee responsibleEmployee)
  {
    this.deadline = deadline;
    this.name = name;
    this.status = status;
    relatedRequirement = 0;
    this.estimatedHours = estimatedHours;
    totalHoursWorked = 0;
    responsibleEmployee = null;
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

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public void setDeadline(LocalDate deadline)
  {
    this.deadline = deadline;
  }

  public Employee getResponsibleEmployee()
  {
    return responsibleEmployee;
  }

  public void setEstimatedHours(int estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  public void setTotalHoursWorked(int totalHoursWorked)
  {
    this.totalHoursWorked = totalHoursWorked;
  }

  public void setResponsibleEmployee(Employee responsibleEmployee)
  {
    this.responsibleEmployee = responsibleEmployee;
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