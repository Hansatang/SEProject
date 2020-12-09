import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XML
{

  public static void main(String[] args)
  {
    ProjectListAdapter adapterProjects = new ProjectListAdapter("Projects.bin");
    ProjectList projectList = adapterProjects.getAllProjects();
    ArrayList<Project> list = new ArrayList<>();

    for (int i = 0; i < projectList.size(); i++)
    {
      list.add(projectList.get(i));
    }
    try
    {
      FileOutputStream fileOut = new FileOutputStream(
          "C:\\Users\\User\\OneDrive\\Desktop\\RWD\\xml\\projectList.xml");
      PrintWriter write = new PrintWriter(fileOut);

      write.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
      write.println("<projects> ");

      for (int i = 0; i < list.size(); i++)
      {
        write.println("<project>");

        write.println("<project_no>");
        write.println(i + 1);
        write.println("</project_no>");

        write.println("<project_name>");
        write.println(list.get(i).getName());
        write.println("</project_name>");

        write.println("<requirement>");
        for (int j = 0; j < list.get(i).getRequirements().size(); j++)
        {

          write.println("<requirement_name>");
          write.println(list.get(i).getRequirements().getRequirements().get(j).getName());
          write.println("</requirement_name>");

          write.println("<requirement_ID>");
          write.println(list.get(i).getRequirements().getRequirements().get(j).getId());
          write.println("</requirement_ID>");


          write.println("<requirement_status>");
          write.println(list.get(i).getRequirements().getRequirements().get(j).getStatus());
          write.println("</requirement_status>");

          write.println("<requirement_deadline>");
          write.println(list.get(i).getRequirements().getRequirements().get(j).getDeadline());
          write.println("</requirement_deadline>");
          write.println("");
          write.println();
          write.println();

        }
        write.println("</requirement>");
        write.println("</project>");

      } write.println("</projects>");
      write.close();
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
      System.exit(1);
    }

  }
}
