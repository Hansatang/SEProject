package Employee;

import Employee.Employee;
import Project.ProjectList;
import Project.ProjectListAdapter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeeListTab extends Tab
{
  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  private VBox tabEmployee;

  private TableView<Employee> employeeTableView;
  private TableView.TableViewSelectionModel<Employee> defaultSelectionModel;
  private TableColumn<Employee, String> employeeName;

  private Button addEmployee, editEmployee, removeEmployee;

  private MyActionListener listener;

  private Employee selectedEmployee;

  public ProjectList finalProjectList;
  public EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  // Employee.Employee JavaFX objects \\
  TextField inputEmployeeName = new TextField();

  //Harcoded values
  final private String name = "Name";
  final private int employeeTableViewHeight = 500;
  final private int employeeTableViewWidth = 598;
  final private String addEmployeeButtonName = "Add employee ";
  final private String editEmployeeButtonName = "Edit employee ";
  final private String removeEmployeeButtonName = "Remove employee ";
  final private String saveAndCloseButtonName = "Save and close";
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

  /**
   * Constructor initializing the GUI components
   * @param title           The title of the tab
   * @param projectAdapter  object used for retrieving and storing project information
   * @param employeeAdapter object used for retrieving and storing employee information
   */
  public EmployeeListTab(String title, ProjectListAdapter projectAdapter,
      EmployeeListAdapter employeeAdapter)
  {
    super(title);

    this.adapterProject = projectAdapter;
    finalProjectList = adapterProject.getAllProjects();
    this.adapterEmployee = employeeAdapter;
    finalEmployeeList = adapterEmployee.getAllEmployees();

    listener = new MyActionListener();

    employeeTableView = new TableView<>();
    defaultSelectionModel = employeeTableView.getSelectionModel();
    employeeTableView.setPrefHeight(employeeTableViewHeight);

    employeeName = new TableColumn<>(name);
    employeeName.setCellValueFactory(new PropertyValueFactory<>(name));
    employeeName.setPrefWidth(employeeTableViewWidth);

    employeeTableView.getColumns().add(employeeName);

    addEmployee = new Button(addEmployeeButtonName);
    addEmployee.setOnAction(listener);

    editEmployee = new Button(editEmployeeButtonName);
    editEmployee.setOnAction(listener);

    removeEmployee = new Button(removeEmployeeButtonName);
    removeEmployee.setOnAction(listener);

    HBox buttonsContainer = new HBox(addEmployee, editEmployee, removeEmployee);
    buttonsContainer.setPrefHeight(buttonsContainerHeight);
    buttonsContainer.setSpacing(buttonsContainerSpacing);
    buttonsContainer.setPadding(
        new Insets(buttonsContainerV1, buttonsContainerV2, buttonsContainerV3,
            buttonsContainerV4));
    buttonsContainer.setAlignment(Pos.CENTER);

    tabEmployee = new VBox(VBoxV);
    tabEmployee.setAlignment(Pos.CENTER);
    tabEmployee.getChildren().add(employeeTableView);
    tabEmployee.getChildren().add(buttonsContainer);

    super.setContent(tabEmployee);

    setSelectedEmployee();

  }

  /**
   * Updates the employeeTableView tableView with information from the employees file
   */
  public void updateEmployeeArea()
  {
    employeeTableView.getItems().clear();
    if (adapterEmployee != null)
    {
      finalEmployeeList = adapterEmployee.getAllEmployees();
      for (int i = 0; i < finalEmployeeList.size(); i++)
      {
        employeeTableView.getItems().add(finalEmployeeList.get(i));
      }
    }
  }

  /**
   * Sets the selectedProject.
   */
  private void setSelectedEmployee()
  {
    employeeTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (employeeTableView.getSelectionModel().getSelectedItem() != null)
            {
              int index = employeeTableView.getSelectionModel()
                  .getSelectedIndex();
              selectedEmployee = employeeTableView.getItems().get(index);
            }
          }
        });
  }

  /**
   * Sets the default values for window entities
   * @param window The window to insert default values
   * @param title  The title of the window
   */
  private void nameWindow(Stage window, String title)
  {
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(windowMinWidth);
    window.setResizable(false);
  }

  /**
   * Creates a VBox container with label and TextField and defines the  values them
   * @param inputText The TextField to set values
   * @param labelName The text in the label
   */
  private VBox textFieldWindowPart(TextField inputText, String labelName)
  {
    VBox nameContainer = new VBox(2);
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label label = new Label(labelName);
    inputText.setText("");
    inputText.setPromptText(textFieldPromptText + labelName.toLowerCase());
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
      if (e.getSource() == addEmployee)
      {
        Stage window = new Stage();

        nameWindow(window, addEmployeeButtonName);

        // Member name input.
        VBox nameContainer = textFieldWindowPart(inputEmployeeName,
            addEmployeeButtonName);

        Label errorMessage = new Label("");

        Button closeWithSaveButton = new Button(addEmployeeButtonName);

        closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
        {
          @Override public void handle(ActionEvent e)
          {
            if (!(inputEmployeeName.getText().isEmpty() || inputEmployeeName
                .getText().equals("")))
            {
              window.close();
              Employee employee = new Employee(inputEmployeeName.getText());
              finalEmployeeList.addEmployee(employee);
              adapterEmployee.saveEmployees(finalEmployeeList);
              updateEmployeeArea();
            }
            else
            {
              errorLabel.setText(errorLabelName);
              errorLabel.setTextFill(Color.RED);
            }
          }
        });

        VBox layout = new VBox(VBoxV);

        layout.getChildren()
            .addAll(nameContainer, errorMessage, closeWithSaveButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(0, 0, 10, 0));

        Scene scene = new Scene(layout);
        window.setResizable(false);
        window.setScene(scene);
        window.showAndWait();

      }
      else if (e.getSource() == editEmployee)
      {
        if (!(selectedEmployee == null))
        {
          Stage window = new Stage();

          nameWindow(window,
              editEmployeeButtonName + selectedEmployee.getName());

          // Employee.Employee name input.
          VBox employeeNameContainer = textFieldWindowPart(inputEmployeeName,
              editEmployeeButtonName);

          inputEmployeeName.setText(selectedEmployee.getName());

          Button closeWithSaveButton = new Button(saveAndCloseButtonName);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              if (!(inputEmployeeName.getText().isEmpty() || inputEmployeeName
                  .getText().equals("")))
              {
                window.close();
                ProjectList projects = adapterProject
                    .getProjectByEmployeeName(selectedEmployee.getName());
                finalProjectList = adapterProject.getAllProjects();
                for (int i = 0; i < projects.size(); i++)
                {
                  finalProjectList.getProjectByName(projects.get(i).getName())
                      .getTeam().replaceEmployee(selectedEmployee.getName(),
                      inputEmployeeName.getText());
                  for (int j = 0;
                       j < projects.getProjectByName(projects.get(i).getName())
                           .getRequirements().size(); j++)
                  {
                    finalProjectList.getProjectByName(projects.get(i).getName())
                        .getRequirements().getRequirement(j).getTeam()
                        .replaceEmployee(selectedEmployee.getName(),
                            inputEmployeeName.getText());
                    for (int k = 0; k < projects
                        .getProjectByName(projects.get(i).getName())
                        .getRequirements().getRequirement(j).getTasks()
                        .size(); k++)
                    {
                      finalProjectList
                          .getProjectByName(projects.get(i).getName())
                          .getRequirements().getRequirement(j).getTasks()
                          .getTask(k).getTaskMembers()
                          .replaceEmployee(selectedEmployee.getName(),
                              inputEmployeeName.getText());
                    }
                  }
                }

                adapterProject.saveProjects(finalProjectList);

                finalEmployeeList.get(finalEmployeeList
                    .getIndexFromName(selectedEmployee.getName()))
                    .setName(inputEmployeeName.getText());

                adapterProject.saveProjects(finalProjectList);
                adapterEmployee.saveEmployees(finalEmployeeList);

                updateEmployeeArea();
              }
              else
              {
                errorLabel.setText(errorLabelName);
                errorLabel.setTextFill(Color.RED);
              }
            }
          });
          VBox layout = new VBox(VBoxV);

          layout.getChildren()
              .addAll(employeeNameContainer, errorLabel, closeWithSaveButton);
          layout.setAlignment(Pos.CENTER);
          layout.setPadding(new Insets(0, 0, 10, 0));

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }
      }
      else if (e.getSource() == removeEmployee)
      {
        if (!(selectedEmployee == null))
        {
          Stage window = new Stage();

          nameWindow(window,
              removeEmployeeButtonName + selectedEmployee.getName());

          // Employee name input.
          HBox nameContainer = new HBox(2);
          nameContainer.setPadding(new Insets(10, 10, 0, 10));
          Label employeeName = new Label(
              "Do you really want to remove: " + selectedEmployee.getName());

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
                System.out.println(selectedEmployee.getName());
                finalEmployeeList.removeEmployee(selectedEmployee);
                adapterEmployee.saveEmployees(finalEmployeeList);
                finalProjectList = adapterProject.getAllProjects();
                if (adapterProject != null)
                {
                  ProjectList projects = adapterProject
                      .getProjectByEmployeeName(selectedEmployee.getName());
                  for (int i = 0; i < projects.size(); i++)
                  {
                    finalProjectList.getProjectByName(projects.get(i).getName())
                        .getTeam().deleteEmployee(selectedEmployee.getName());
                    for (int j = 0; j < projects
                        .getProjectByName(projects.get(i).getName())
                        .getRequirements().size(); j++)
                    {
                      finalProjectList
                          .getProjectByName(projects.get(i).getName())
                          .getRequirements().getRequirement(j).getTeam()
                          .deleteEmployee(selectedEmployee.getName());
                      for (int k = 0; k < projects
                          .getProjectByName(projects.get(i).getName())
                          .getRequirements().getRequirement(j).getTasks()
                          .size(); k++)
                      {
                        finalProjectList
                            .getProjectByName(projects.get(i).getName())
                            .getRequirements().getRequirement(j).getTasks()
                            .getTask(k).getTaskMembers()
                            .deleteEmployee(selectedEmployee.getName());
                      }
                    }
                  }
                  adapterProject.saveProjects(finalProjectList);
                }
                updateEmployeeArea();
                selectedEmployee = null;
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

          VBox layout = new VBox(VBoxV);

          layout.getChildren()
              .addAll(nameContainer, errorMessage, closingButtons);
          layout.setAlignment(Pos.CENTER);
          layout.setPadding(new Insets(0, 0, 10, 0));

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }

      }
    }
  }
}



