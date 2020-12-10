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
  private VBox tabEmployee;

  private TableView<Employee> employeeTableView;
  private TableView.TableViewSelectionModel<Employee> defaultSelectionModel;
  private TableColumn<Employee, String> employeeName;

  private Button addEmployee, editEmployee, removeEmployee;

  private MyActionListener listener;

  private Employee selectedEmployee;

  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  public ProjectList finalProjectList;
  public EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  // Employee JavaFX objects \\
  TextField inputEmployeeName = new TextField();

  //Harcoded values
  final private String name = "Name";
  final private int employeeTableViewHeight = 500;
  final private int employeeTableViewWidth = 598;
  final private String addEmployeeButtonName = "Add employee";
  final private String editEmployeeButtonName = "Edit employee";
  final private String removeEmployeeButtonName = "Remove employee";
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

  public EmployeeListTab(String title, ProjectListAdapter adapterP,
      EmployeeListAdapter adapterE)
  {
    super(title);

    this.adapterProject = adapterP;
    finalProjectList = adapterProject.getAllProjects();
    this.adapterEmployee = adapterE;
    finalEmployeeList = adapterEmployee.getAllEmployees();

    listener = new MyActionListener();

    employeeTableView = new TableView<Employee>();
    defaultSelectionModel = employeeTableView.getSelectionModel();
    employeeTableView.setPrefHeight(employeeTableViewHeight);

    employeeName = new TableColumn<Employee, String>(name);
    employeeName
        .setCellValueFactory(new PropertyValueFactory<Employee, String>(name));
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
   * Updates the allStudentsTable tableView with information from the students file
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
    inputText.setPromptText(textFieldPromptText + labelName.toLowerCase());
    nameContainer.getChildren().addAll(label, inputText);

    return nameContainer;
  }

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

          // Employee name input.
          VBox employeeNameContainer = textFieldWindowPart(inputEmployeeName,
              addEmployeeButtonName);

          inputEmployeeName.setText(selectedEmployee.getName());

          Button closeWithSaveButton = new Button(editEmployeeButtonName);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              if (!(inputEmployeeName.getText().isEmpty() || inputEmployeeName
                  .getText().equals("")))
              {
                window.close();
                Employee employee = new Employee(inputEmployeeName.getText());
                ProjectList projects = adapterProject
                    .getProjectByEmployeeName(selectedEmployee.getName());
                for (int i = 0; i < projects.size(); i++)
                {
                  finalProjectList.getProjectByName(projects.get(i).getName())
                      .getTeam().replaceEmployee(selectedEmployee.getName(),
                      employee.getName());
                }
                adapterProject.saveProjects(finalProjectList);
                finalEmployeeList.getIndexFromName(selectedEmployee.getName());

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
          closingButtons.setPadding(new Insets(10, 40, 10, 50));
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
                if (adapterProject != null)
                {
                  ProjectList projects = adapterProject
                      .getProjectByEmployeeName(selectedEmployee.getName());
                  for (int i = 0; i < projects.size(); i++)
                  {
                    finalProjectList.getProjectByName(projects.get(i).getName())
                        .getTeam().deleteEmployee(selectedEmployee.getName());
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

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }

      }
    }
  }
}



