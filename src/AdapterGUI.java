import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdapterGUI extends Application
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
  private Menu editMenu;
  private Menu aboutMenu;

  private MenuItem exitMenuItem;
  private MenuItem aboutMenuItem;

  private CheckMenuItem editTableMenuItem;
  private CheckMenuItem editFieldsMenuItem;

  private static MyTabListener tabListener;

  public static MyTabListener getTabListener()
  {
    return tabListener;
  }

  /**
   * @param window The Stage object that will be displayed
   */
  public void start(Stage window) throws IOException
  {
    window.setTitle("Project Management System");

    adapterProjects = new ProjectListAdapter("Projects.bin");
    adapterEmployee = new EmployeeListAdapter("Employees.bin");

    tabListener = new MyTabListener();

    tabPane = new TabPane();
    tabPane.getSelectionModel().selectedItemProperty().addListener(tabListener);

    employeeListTab = new EmployeeListTab("Employees", adapterProjects,
        adapterEmployee);
    projectListTab = new ProjectListTab("Projects", adapterProjects,
        adapterEmployee, this);
    requirementListTab = new RequirementListTab("Project detail",
        adapterProjects, adapterEmployee, projectListTab, this);
    taskListTab = new TaskListTab("Requirement detail", adapterProjects,
        adapterEmployee);

    requirementListTab.setDisable(true);
    taskListTab.setDisable(true);

    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    tabPane.getTabs().add(employeeListTab);
    tabPane.getTabs().add(projectListTab);
    tabPane.getTabs().add(requirementListTab);
    tabPane.getTabs().add(taskListTab);
    exitMenuItem = new MenuItem("Exit");

    aboutMenuItem = new MenuItem("About");

    editTableMenuItem = new CheckMenuItem("Select in student table");
    editTableMenuItem.setSelected(true);

    editFieldsMenuItem = new CheckMenuItem("Edit name fields");

    fileMenu = new Menu("File");
    editMenu = new Menu("Edit");
    aboutMenu = new Menu("About");

    fileMenu.getItems().add(exitMenuItem);

    editMenu.getItems().add(editTableMenuItem);
    editMenu.getItems().add(editFieldsMenuItem);

    aboutMenu.getItems().add(aboutMenuItem);

    menuBar = new MenuBar();

    menuBar.getMenus().add(fileMenu);
    menuBar.getMenus().add(editMenu);
    menuBar.getMenus().add(aboutMenu);

    mainPane = new VBox();
    mainPane.getChildren().add(menuBar);
    mainPane.getChildren().add(tabPane);

    Scene scene = new Scene(mainPane, 600, 600);

    window.setScene(scene);
    window.setResizable(false);
    window.show();
  }

  public void changeRequirementTabTitle(Project selectedProject)
  {
    requirementListTab.setText(selectedProject.getName() + " project details");
    requirementListTab.setDisable(false);
  }

  public void changeTaskTabTitle(Requirement selectedRequirement)
  {
    taskListTab.setText(selectedRequirement.getName() + " requirement details");
    taskListTab.setDisable(false);
  }

  public void closeRequirementTabTitle()
  {
    requirementListTab.setText("Project details");
    requirementListTab.setDisable(true);
  }

  public void closeTaskTabTitle()
  {
    taskListTab.setText("Requirement details");
    taskListTab.setDisable(true);
  }

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
        requirementListTab.updateRequirementArea();
        requirementListTab
            .setSelectedProject(projectListTab.getSelectedProject());

      }
      else if (newTab == taskListTab)
      {
        taskListTab.updateTaskArea();
        taskListTab.setSelectedRequirement(
            requirementListTab.getSelectedRequirement());
        taskListTab.setSelectedProject(requirementListTab.getSelectedProject());
      }
    }
  }
}
