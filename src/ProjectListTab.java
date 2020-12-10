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

public class ProjectListTab extends Tab
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
  private AdapterGUI adapterGUI;

  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  public ProjectList finalProjectList;
  public EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  TextField inputProjectName = new TextField();

  //Harcoded values
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

  public ProjectListTab(String title, ProjectListAdapter adapterP,
      EmployeeListAdapter adapterE, AdapterGUI adapterGUI)
  {
    super(title);
    this.adapterGUI = adapterGUI;
    this.adapterProject = adapterP;
    finalProjectList = adapterProject.getAllProjects();
    this.adapterEmployee = adapterE;
    finalEmployeeList = adapterEmployee.getAllEmployees();

    listener = new MyActionListener();

    projectTableView = new TableView<>();
    defaultSelectionModel = projectTableView.getSelectionModel();
    projectTableView.setPrefHeight(projectTableViewHeight);

    projectName = new TableColumn<>(name);
    projectName
        .setCellValueFactory(new PropertyValueFactory<>(name));
    projectName.setPrefWidth(projectColumnTableViewWidth);

    projectTeam = new TableColumn<>(team);
    projectTeam
        .setCellValueFactory(new PropertyValueFactory<>(team));
    projectTeam.setPrefWidth(projectColumnTableViewWidth);

    searchByName = new RadioButton();
    searchByName.setText(searchRadioButtonName);
    searchByEmployee = new RadioButton();
    searchByEmployee.setText(searchRadioButtonEmployee);
    searchingType = new ToggleGroup();
    searchingType.getToggles().add(searchByName);
    searchingType.getToggles().add(searchByEmployee);
    searchField = new TextField();
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

  public Project getSelectedProject()
  {
    return selectedProject;
  }

  private void setSelectedProject()
  {
    projectTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (projectTableView.getSelectionModel().getSelectedItem() != null)
            {
              int index = projectTableView.getSelectionModel()
                  .getSelectedIndex();
              selectedProject = projectTableView.getItems().get(index);
              adapterGUI.changeRequirementTabTitle(selectedProject);
            }
          }
        });
  }

  /**
   * Updates the ProjectList objects the TreeView<Project> on the GUI
   *
   * @param //args Command line arguments
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

  private void nameWindow(Stage window, String str)
  {
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(str);
    window.setMinWidth(windowMinWidth);
    window.setResizable(false);
  }

  private VBox textFieldWindowPart(TextField inputText, String labelName)
  {
    VBox nameContainer = new VBox(2);
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label label = new Label(labelName);
    inputText.setText("");
    inputText.setPromptText("Enter " + labelName.toLowerCase());
    nameContainer.getChildren().addAll(label, inputText);

    return nameContainer;
  }

  private class MyActionListener implements EventHandler<ActionEvent>
  {
    public void handle(ActionEvent e)
    {
      if (e.getSource() == addProject)
      {

        Stage window = new Stage();
        nameWindow(window, addProjectButtonName);

        // Project name input.
        VBox projectNameContainer = textFieldWindowPart(inputProjectName,
            addProjectButtonName);

        // Project employee list input.
        VBox employeeListContainer = new VBox();
        employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
        Label employeesLabel = new Label("Select employees: ");
        GridPane employeeSelectContainer = new GridPane();
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
              finalProjectList.add(project);
              adapterProject.saveProjects(finalProjectList);

              updateProjectArea();
              adapterGUI.changeRequirementTabTitle(selectedProject);
            }
          }
        });

        VBox layout = new VBox(10);

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

          nameWindow(window, "Edit project" + selectedProject.getName());

          // Project name input.
          VBox projectNameContainer = textFieldWindowPart(inputProjectName,
              "New Project name: ");
          inputProjectName.setText(selectedProject.getName());

          // Project employee list input.
          VBox employeeListContainer = new VBox();
          employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
          Label employeesLabel = new Label("Select employees: ");
          GridPane employeeSelectContainer = new GridPane();
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

          Button closeWithSaveButton = new Button("Add new project");

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
                selectedProject.setName(inputProjectName.getText());
                selectedProject.setTeam(selectedEmployees);
                adapterProject.saveProjects(finalProjectList);
                updateProjectArea();
                adapterGUI.changeRequirementTabTitle(selectedProject);
              }
            }
          });

          VBox layout = new VBox(10);

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

          nameWindow(window, "Remove employee" + selectedProject.getName());

          // Employee name input.
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
          closingButtons.setPadding(new Insets(10, 40, 10, 50));
          closingButtons.setSpacing(50);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              {
                window.close();
                System.out.println(selectedProject.getName());
                finalProjectList.removeProject(selectedProject);
                if (adapterProject != null)
                {
                  ProjectList projects = adapterProject
                      .getProjectByEmployeeName(selectedProject.getName());
                  for (int i = 0; i < projects.size(); i++)
                  {
                    finalProjectList.getProjectByName(projects.get(i).getName())
                        .getTeam().deleteEmployee(selectedProject.getName());
                  }
                  adapterProject.saveProjects(finalProjectList);
                }
                updateProjectArea();
                adapterGUI.closeRequirementTabTitle();
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

          VBox layout = new VBox(10);

          layout.getChildren()
              .addAll(nameContainer, errorMessage, closingButtons);
          layout.setAlignment(Pos.CENTER);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }

      }
      if (e.getSource() == searchButton)
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


