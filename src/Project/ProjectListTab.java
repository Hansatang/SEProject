package Project;

import Employee.EmployeeList;
import Employee.EmployeeListAdapter;
import Main.GUI;
import Main.GUIParts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectListTab extends Tab implements GUIParts
{
  private VBox tabProjects;

  private RadioButton searchByName, searchByEmployee;
  private ToggleGroup searchingType;
  private TextField searchField;

  private TableView<Project> projectTableView;
  private TableView.TableViewSelectionModel<Project> defaultSelectionModel;
  private TableColumn<Project, String> projectName;
  private TableColumn<Project, String> projectTeam;

  private Button searchButton, addProject, editProject, removeProject;

  private MyActionListener listener;

  private Project selectedProject;
  private GUI GUI;

  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  public ProjectList finalProjectList;
  public EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  TextField inputProjectName = new TextField();

  //Hardcoded values
  private String name = "Name";
  private String team = "Team";
  final private int projectTableViewHeight = 500;
  final private int projectColumnTableViewWidth = 298;
  final private int searchFieldWidth = 380;
  final private String addProjectButtonName = "Add project";
  final private String editProjectButtonName = "Edit project";
  final private String removeProjectButtonName = "Remove project";
  final private String searchButtonName = "Search";
  final private String textFieldPromptText = "Enter ";
  final private int buttonsContainerHeight = 30;
  final private int buttonsContainerSpacing = 50;
  final private int buttonsContainerV1 = 0;
  final private int buttonsContainerV2 = 10;
  final private int buttonsContainerV3 = 0;
  final private int buttonsContainerV4 = 10;
  final private int VBoxV = 10;
  final private int windowMinWidth = 300;
  final private String errorLabelName = "ERROR: invalid project name";
  private String searchRadioButtonName = "Search by name";
  private String searchRadioButtonEmployee = "Search by employee";

  /**
   * Constructor initializing the GUI components
   * @param title           The title of the tab
   * @param projectAdapter  object used for retrieving and storing project information
   * @param employeeAdapter object used for retrieving and storing employee information
   * @param GUI      object used for communication between Tabs
   */
  public ProjectListTab(String title, ProjectListAdapter projectAdapter,
      EmployeeListAdapter employeeAdapter, GUI GUI)
  {
    super(title);
    this.GUI = GUI;
    this.adapterProject = projectAdapter;
    finalProjectList = adapterProject.getAllProjects();
    this.adapterEmployee = employeeAdapter;
    finalEmployeeList = adapterEmployee.getAllEmployees();
    listener = new MyActionListener();

    projectTableView = new TableView<>();
    defaultSelectionModel = projectTableView.getSelectionModel();
    projectTableView.setPrefHeight(projectTableViewHeight);

    projectName = new TableColumn<>(name);
    projectName.setCellValueFactory(new PropertyValueFactory<>(name));
    projectName.setPrefWidth(projectColumnTableViewWidth);

    projectTeam = new TableColumn<>(team);
    projectTeam.setCellValueFactory(new PropertyValueFactory<>(team));
    projectTeam.setPrefWidth(projectColumnTableViewWidth);

    searchByName = new RadioButton();
    searchByName.setText(searchRadioButtonName);
    searchByName.setSelected(true);
    searchByEmployee = new RadioButton();
    searchByEmployee.setText(searchRadioButtonEmployee);
    searchingType = new ToggleGroup();
    searchingType.getToggles().add(searchByName);
    searchingType.getToggles().add(searchByEmployee);
    searchField = new TextField();
    searchField.setOnAction(listener);
    searchField.setPrefWidth(searchFieldWidth);

    projectTableView.getColumns().add(projectName);
    projectTableView.getColumns().add(projectTeam);

    searchButton = new Button(searchButtonName);
    searchButton.setOnAction(listener);

    VBox searchBarV = new VBox(searchByName, searchByEmployee);
    searchBarV.setPrefWidth(140);
    HBox searchBarH = new HBox(searchBarV, searchField, searchButton);
    searchBarH.setAlignment(Pos.CENTER);
    searchBarH.setPadding(new Insets(10, 10, 0, 10));

    addProject = new Button(addProjectButtonName);
    addProject.setOnAction(listener);

    editProject = new Button(editProjectButtonName);
    editProject.setOnAction(listener);

    removeProject = new Button(removeProjectButtonName);
    removeProject.setOnAction(listener);

    HBox buttonContainer = new HBox(addProject, editProject, removeProject);
    buttonContainer.setPrefHeight(30);
    buttonContainer.setSpacing(50);
    buttonContainer.setPadding(new Insets(0, 10, 10, 10));
    buttonContainer.setAlignment(Pos.CENTER);

    tabProjects = new VBox(10);
    tabProjects.setAlignment(Pos.CENTER);
    tabProjects.getChildren().add(searchBarH);
    tabProjects.getChildren().add(projectTableView);
    tabProjects.getChildren().add(buttonContainer);

    super.setContent(tabProjects);

    setSelectedProject();

    updateProjectArea();

    finalEmployeeList = adapterEmployee.getAllEmployees();

  }

  /**
   * Gets the selectedProject Project.
   * @return the selectedProject Project
   */
  public Project getSelectedProject()
  {
    return selectedProject;
  }

  /**
   * Sets the selectedProject.
   */
  private void setSelectedProject()
  {
    defaultSelectionModel.selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (defaultSelectionModel.getSelectedItem() != null)
            {
              int index = projectTableView.getSelectionModel()
                  .getSelectedIndex();
              selectedProject = projectTableView.getItems().get(index);
              GUI.changeRequirementTabTitle(selectedProject);
            }
          }
        });
  }

  /**
   * Updates the projectTableView tableView with information from the projects file
   */
  public void updateProjectArea()
  {
    projectTableView.getItems().clear();
    if (adapterProject != null)
    {
      finalProjectList = adapterProject.getAllProjects();
      for (int i = 0; i < finalProjectList.size(); i++)
      {
        projectTableView.getItems().add(finalProjectList.get(i));
      }
    }
  }

  /**
   * Sets the default values for window entities
   *
   * @param window The window to insert default values
   * @param title  The title of the window
   */
  public void nameWindow(Stage window, String title)
  {
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(windowMinWidth);
    window.setResizable(false);
  }

  /**
   * Creates a VBox container with label and TextField and defines the  values them
   *
   * @param inputText The TextField to set values
   * @param labelName The text in the label
   */
  public VBox textFieldWindowPart(TextField inputText, String labelName)
  {
    VBox nameContainer = new VBox(2);
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label label = new Label(labelName);
    inputText.setText("");
    inputText.setPromptText("Enter " + labelName.toLowerCase());
    nameContainer.getChildren().addAll(label, inputText);

    return nameContainer;
  }

  /*
   * Inner action listener class
   * @author
   */
  private class MyActionListener implements EventHandler<ActionEvent>
  {
    public void handle(ActionEvent e)
    {
      if (e.getSource() == addProject)
      {

        Stage window = new Stage();
        nameWindow(window, addProjectButtonName);

        // Project.Project name input.
        VBox projectNameContainer = textFieldWindowPart(inputProjectName,
            addProjectButtonName);

        // Project.Project employee list input.
        VBox employeeListContainer = new VBox();
        employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
        Label employeesLabel = new Label("Select employees: ");
        GridPane employeeSelectContainer = new GridPane();
        finalEmployeeList = adapterEmployee.getAllEmployees();
        CheckBox[] employeeCheckBoxes = new CheckBox[finalEmployeeList.size()];

        for (int i = 0; i < employeeCheckBoxes.length; i++)
        {
          employeeCheckBoxes[i] = new CheckBox(
              finalEmployeeList.get(i).getName());
          employeeSelectContainer.add(employeeCheckBoxes[i], i % 2, i / 2);
          employeeCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
        }

        // Add employee label Node and employee selection Node
        employeeListContainer.getChildren()
            .addAll(employeesLabel, employeeSelectContainer);

        // Config save and close button

        Label errorMessage = new Label("");

        Button closeWithSaveButton = new Button("Add new project");

        closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
        {
          @Override public void handle(ActionEvent e)
          {
            EmployeeList selectedEmployees = new EmployeeList();
            for (int i = 0; i < employeeCheckBoxes.length; i++)
            {
              if (employeeCheckBoxes[i].isSelected())
              {
                selectedEmployees.addEmployee(finalEmployeeList.get(i));
              }
            }

            if (inputProjectName.getText().isEmpty() || inputProjectName
                .getText().equals(""))
            {
              errorLabel.setText("ERROR: Fix name");
            }
            else if (selectedEmployees.size() == 0)
            {
              errorLabel.setText("ERROR: Fix employees");
            }
            else
            {
              window.close();

              Project project = new Project(inputProjectName.getText(),
                  selectedEmployees);
              finalProjectList.addProject(project);
              adapterProject.saveProjects(finalProjectList);

              updateProjectArea();
            }
          }
        });

        VBox layout = new VBox(5);
        layout.setPadding(new Insets(0, 0, 10, 0));

        layout.getChildren()
            .addAll(projectNameContainer, employeeListContainer, errorMessage,
                closeWithSaveButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setResizable(false);
        window.setScene(scene);
        window.showAndWait();

        errorLabel.setText("");

      }
      else if (e.getSource() == editProject)
      {
        if (!(selectedProject == null))
        {
          Stage window = new Stage();
          errorLabel.setText("");

          nameWindow(window, "Edit project " + selectedProject.getName());

          // Project.Project name input.
          VBox projectNameContainer = textFieldWindowPart(inputProjectName,
              "New Project name: ");
          inputProjectName.setText(selectedProject.getName());

          // Project.Project employee list input.
          VBox employeeListContainer = new VBox();
          employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
          Label employeesLabel = new Label("Select employees: ");
          GridPane employeeSelectContainer = new GridPane();
          finalEmployeeList = adapterEmployee.getAllEmployees();
          CheckBox[] employeeCheckBoxes = new CheckBox[finalEmployeeList
              .size()];

          for (int i = 0; i < employeeCheckBoxes.length; i++)
          {
            employeeCheckBoxes[i] = new CheckBox(
                finalEmployeeList.get(i).getName());
            employeeSelectContainer.add(employeeCheckBoxes[i], i % 2, i / 2);

            for (int j = 0; j < selectedProject.getTeam().size(); j++)
            {
              if (employeeCheckBoxes[i].getText()
                  .equals(selectedProject.getTeam().get(j).getName()))
              {
                employeeCheckBoxes[i].setSelected(true);
              }
            }
            employeeCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
          }
          // Add employee label Node and employee selection Node
          employeeListContainer.getChildren()
              .addAll(employeesLabel, employeeSelectContainer);

          Button closeWithSaveButton = new Button("Save and close");

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              // Make team of the new selected employees
              EmployeeList selectedEmployees = new EmployeeList();
              for (int i = 0; i < employeeCheckBoxes.length; i++)
              {
                if (employeeCheckBoxes[i].isSelected())
                {
                  selectedEmployees.addEmployee(finalEmployeeList.get(i));
                }
              }

              //Check for errors

              if (inputProjectName.getText().isEmpty() || inputProjectName
                  .getText().equals(""))
              {
                errorLabel.setText("ERROR: Fix name");
              }
              else if (selectedEmployees.size() == 0)
              {
                errorLabel.setText("ERROR: Fix employees");
              }
              else
              {
                window.close();
                selectedProject
                    .editProject(inputProjectName.getText(), selectedEmployees);
                adapterProject.saveProjects(finalProjectList);
                updateProjectArea();
              }
            }
          });

          VBox layout = new VBox(5);
          layout.setPadding(new Insets(0, 0, 10, 0));

          layout.getChildren()
              .addAll(projectNameContainer, employeeListContainer,
                  closeWithSaveButton, errorLabel);

          layout.setAlignment(Pos.CENTER);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();
        }
      }
      else if (e.getSource() == removeProject)
      {
        if (!(selectedProject == null))
        {
          Stage window = new Stage();

          nameWindow(window, "Remove project" + selectedProject.getName());

          // Employee.Employee name input.
          HBox nameContainer = new HBox(2);
          nameContainer.setPadding(new Insets(10, 10, 0, 10));
          Label employeeName = new Label(
              "Do you really want to remove: " + selectedProject.getName());

          nameContainer.getChildren().addAll(employeeName);

          Label errorMessage = new Label("");

          Button closeWithSaveButton = new Button("Save and close");

          Button closeWithOutSaveButton = new Button("Close without saving");

          HBox closingButtons = new HBox(closeWithSaveButton,
              closeWithOutSaveButton);
          closingButtons.setPadding(new Insets(10, 40, 0, 50));
          closingButtons.setSpacing(50);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              {
                window.close();
                System.out.println(selectedProject.getName());
                finalProjectList.removeProject(selectedProject);
                adapterProject.saveProjects(finalProjectList);
                updateProjectArea();
                GUI.closeRequirementTabTitle();
                GUI.closeTaskTabTitle();
                selectedProject = null;
              }
            }
          });

          closeWithOutSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              {
                window.close();
              }
            }
          });

          VBox layout = new VBox(5);
          layout.setPadding(new Insets(0, 0, 10, 0));

          layout.getChildren()
              .addAll(nameContainer, errorMessage, closingButtons);
          layout.setAlignment(Pos.CENTER);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();
        }
      }

      if (e.getSource() == searchButton || e.getSource() == searchField)
      {
        projectTableView.getItems().clear();
        if (adapterProject != null)
        {
          if (searchByName.isSelected())
          {
            ProjectList projects = adapterProject
                .getProjectByName(searchField.getText());
            for (int i = 0; i < projects.size(); i++)
            {
              projectTableView.getItems().add(projects.get(i));
            }
          }
          else if (searchByEmployee.isSelected())
          {
            ProjectList projects = adapterProject
                .getProjectByEmployeeName(searchField.getText());
            for (int i = 0; i < projects.size(); i++)
            {
              projectTableView.getItems().add(projects.get(i));
            }
          }
        }
      }
    }
  }
}


