import java.util.ArrayList;

public class TaskList
{

    private ArrayList<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }

    public Task getTask(int index){
        return tasks.get(index);
    }

    public int getIndexFromName(String name){
        for(int i = 0 ; i < tasks.size() ; i++){
            if(tasks.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

}
