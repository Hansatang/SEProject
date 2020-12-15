package Main;

import Employee.EmployeeListAdapter;
import Employee.EmployeeListTab;
import Project.Project;
import Project.ProjectListAdapter;
import Project.ProjectListTab;
import Requirement.Requirement;
import Requirement.RequirementListTab;
import Task.TaskListTab;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application
{
  // Adapters
  private ProjectListAdapter adapterProjects;
  private EmployeeListAdapter adapterEmployee;

  private VBox mainPane;

  private TabPane tabPane;
  private EmployeeListTab employeeListTab;
  private ProjectListTab projectListTab;
  private RequirementListTab requirementListTab;
  private TaskListTab taskListTab;

  private MenuBar menuBar;

  private Menu fileMenu;
  private Menu aboutMenu;

  private MenuItem exitMenuItem;
  private MenuItem aboutMenuItem;

  private MyTabListener tabListener;

  /**
   * @param window The Stage object that will be displayed
   */
  public void start(Stage window) {
    window.setTitle("Project Management System");

    adapterProjects = new ProjectListAdapter("Projects.bin");
    adapterEmployee = new EmployeeListAdapter("Employees.bin");

    tabListener = new MyTabListener();

    tabPane = new TabPane();
    tabPane.getSelectionModel().selectedItemProperty().addListener(tabListener);

    employeeListTab = new EmployeeListTab("Employees", adapterProjects, adapterEmployee);
    projectListTab = new ProjectListTab("Projects", adapterProjects, adapterEmployee, this);
    requirementListTab = new RequirementListTab("Project detail", adapterProjects, adapterEmployee, projectListTab, this);
    taskListTab = new TaskListTab("Requirement detail", adapterProjects, adapterEmployee);

    requirementListTab.setDisable(true);
    taskListTab.setDisable(true);

    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    tabPane.getTabs().add(employeeListTab);
    tabPane.getTabs().add(projectListTab);
    tabPane.getTabs().add(requirementListTab);
    tabPane.getTabs().add(taskListTab);
    exitMenuItem = new MenuItem("Exit");
    exitMenuItem.setOnAction(e->window.close());

    aboutMenuItem = new MenuItem("About");

    fileMenu = new Menu("File");
    aboutMenu = new Menu("About");

    fileMenu.getItems().add(exitMenuItem);

    aboutMenu.getItems().add(aboutMenuItem);

    menuBar = new MenuBar();

    menuBar.getMenus().add(fileMenu);
    menuBar.getMenus().add(aboutMenu);

    mainPane = new VBox();
    mainPane.getChildren().add(menuBar);
    mainPane.getChildren().add(tabPane);

    Scene scene = new Scene(mainPane, 600, 600);

    window.setScene(scene);
    window.setResizable(false);
    window.show();

    window.setOnCloseRequest(e -> XML.run());
  }

  /**
   * Updates the name of the requirementListTable Tab and set it's state to not disabled
   */
  public void changeRequirementTabTitle(Project selectedProject)
  {
    requirementListTab.setText(selectedProject.getName() + " project details");
    requirementListTab.setDisable(false);
  }

  /**
   * Updates the name of the taskListTable Tab and set it's state to not disabled
   */
  public void changeTaskTabTitle(Requirement selectedRequirement)
  {
    taskListTab.setText(selectedRequirement.getName() + " requirement details");
    taskListTab.setDisable(false);
  }

  /**
   * Updates the name of the requirementListTable Tab and set it's state to disabled
   */
  public void closeRequirementTabTitle()
  {
    requirementListTab.setText("Project details");
    requirementListTab.setDisable(true);
  }

  /**
   * Updates the name of the taskListTable Tab and set it's state to disabled
   */
  public void closeTaskTabTitle()
  {
    taskListTab.setText("Requirement details");
    taskListTab.setDisable(true);
  }

  /*
   * Inner change listener class
   * @author Krzysztof Pacierz
   */
  private class MyTabListener implements ChangeListener<Tab>
  {
    public void changed(ObservableValue<? extends Tab> tab, Tab oldTab,
        Tab newTab)
    {
      if (newTab == employeeListTab)
      {
        employeeListTab.updateEmployeeArea();
      }
      else if (newTab == projectListTab)
      {
        projectListTab.updateProjectArea();
      }
      else if (newTab == requirementListTab)
      {
        requirementListTab
            .setSelectedProject(projectListTab.getSelectedProject());
        requirementListTab.updateRequirementArea();
        requirementListTab.updateRequirementLabels();
      }
      else if (newTab == taskListTab)
      {
        taskListTab.setSelectedProject(requirementListTab.getSelectedProject());
        taskListTab.setSelectedRequirement(
            requirementListTab.getSelectedRequirement());
        taskListTab.updateTaskArea();
      }
    }
  }
}
