package Task;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class containing a list of Task objects
 *
 * @author Dorin Pascal
 */

public class TaskList implements Serializable
{

  private ArrayList<Task> tasks;


  /**
   * No-argument constructor initializing the ArrayList of Task
   */

  public TaskList()
  {
    tasks = new ArrayList<>();
  }


  /**
   * Adds a Task to the ArrayList of tasks.
   * @param task the Task to be added to the ArrayList of tasks.
   */
  public void addTask(Task task)
  {
    tasks.add(task);
  }

  /**
   * Removes a Task from the ArrayList of tasks.
   * @param task the Task to be removed from the ArrayList of tasks.
   */
  public void removeTask(Task task)
  {
    tasks.remove(task);
  }


  /**
   * Gets a Taskobject with a given index from the ArrayList of Task objects.
   *
   * @param index the index number of the Task object
   * @return the Task object with the given index if it is in the task list.
   */
  public Task getTask(int index)
  {
    return tasks.get(index);
  }


  /**
   * Gets how many Task object are in the ArrayList tasks.
   * @return the number of Task objects in the ArrayList of tasks.
   */
  public int size()
  {
    return tasks.size();
  }


  /**
   * @param name the name to check the ArrayList task through.
   *
   * @return a Integer index that has the name equal to taskName.
   */
  public int getIndexFromName(String name)
  {
    for (int i = 0; i < tasks.size(); i++)
    {
      if (tasks.get(i).getName().equals(name))
      {
        return i;
      }
    }
    return -1;
  }


  /**
   * Gets the taskList's total estimated hours.
   *
   * @return Integer value of the taskList's total estimated hours .
   */
  public int getTotalEstimatedHours()
  {
    int sum = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      sum += tasks.get(i).getEstimatedHours();
    }
    return sum;
  }


  /**
   * Gets the taskList's total worked hours.
   *
   * @return Integer value of the taskList's total worked hours .
   */
  public int getTotalWorkedHours()
  {
    int sum = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      sum += tasks.get(i).getTotalHoursWorked();
    }
    return sum;
  }

  /**
   * Checks if task is empty
   *
   * @return Boolean value if the task is empty or not.
   */
  public boolean isEmpty()
  {
    return tasks.isEmpty();
  }


  /**
   * Gets ArrayList object tasks which contains all Task objects.
   * @return the ArrayList tasks containing all tasks.
   */
  public ArrayList<Task> getTasks()
  {
    return tasks;
  }


  /**
   * Gets the first Task object that has the same String as the parameter.
   * @param taskName the name to check the ArrayList tasks through.
   * @return a Task object that has the name equal to taskName.
   */
  public Task getTask(String taskName)
  {
    int k = 0;
    for (int i = 0; i < tasks.size(); i++)
    {
      if (tasks.get(i).getName().equals(taskName))
      {
        k = i;
        break;
      }
    }
    ;
    return tasks.get(k);
  }
}
