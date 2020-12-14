package Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XML
{

  public static void run()
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

      write.println("<Projects>");
      for (int i = 0; i < list.size(); i++)
      {

        write.println("<project>");
        write.println("<project_number>");
        write.println(i+1);
        write.println("</project_number>");

        write.println("<project_name>");
        write.println(list.get(i).getName());
        write.println("</project_name>");

        write.println("<teamMembers>");
        write.println(list.get(i).getTeam());
        write.println("</teamMembers>");
        for (var j=0;i<list.get(i).getRequirements().size();i++)
        {
          write.println("<requirement_id>");
          write.println(list.get(i).getRequirements().getRequirements().get(i).getId());
          write.println("</requirement_id>");

          write.println("<requirement_status>");
          write.println(list.get(i).getRequirements().getRequirements().get(i).getStatus());
          write.println("</requirement_status>");

          write.println("<requirement_deadline>");
          write.println(
              list.get(i).getRequirements().getRequirements().get(i).getDeadline());
          write.println("</requirement_deadline>");

        }


        write.println("</project>");

      }
      write.println("</Projects>");
      write.close();
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
      System.exit(1);
    }

  }
}
}