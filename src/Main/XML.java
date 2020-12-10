package Main;

import Project.Project;
import Project.ProjectList;
import Project.ProjectListAdapter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XML {


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
      FileOutputStream fileOut = new FileOutputStream("C:/Users/esben/Desktop/RWD/xml/projectList.xml");
      PrintWriter write = new PrintWriter(fileOut);

      write.println("<?xml version='1.0' encoding='UTF-8'?> ");
      write.println("<projects> ");

      System.out.println("Writing to Main.XML");

      for (int i = 0; i < list.size(); i++)
      {
        write.println("<project>");

        write.println("<project_no>");
        write.println(i + 1);
        write.println("</project_no>");

        write.println("<project_name>");
        write.println(list.get(i).getName());
        write.println("</project_name>");

        write.println("<teamMembers>");
        write.println(list.get(i).getTeam());
        write.println("</teamMembers>");

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