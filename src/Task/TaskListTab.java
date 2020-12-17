package Task;

import Employee.Employee;
import Employee.EmployeeList;
import Employee.EmployeeListAdapter;
import Main.GUIParts;
import Project.Project;
import Project.ProjectList;
import Project.ProjectListAdapter;
import Requirement.Requirement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A class containing GUI elements for the Task tab.
 *
 * @author Dorin Pascal
 */
public class TaskListTab extends Tab implements GUIParts
{
  private VBox tabTask;

  private TableView<Task> taskTableView;
  private TableView.TableViewSelectionModel<Task> defaultSelectionModel;
  private TableColumn<Task, String> taskName;
  private TableColumn<Task, String> taskStatus;
  private TableColumn<Task, String> taskDeadline;

  private Button addTask, editTask, removeTask;

  private MyActionListener listener;

  private Project selectedProject;
  private Requirement selectedRequirement;
  private Task selectedTask;
  private  Employee selectedEmployee;

  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  private ProjectList finalProjectList;
  private EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  private Label taskNameLabel = new Label();
  private Label taskStatusLabel = new Label();
  private Label taskDeadlineLabel = new Label();
  private Label taskIDLabel = new Label();
  private Label taskEmployeeLabel = new Label();
  private Label taskEstimatedHoursLabel = new Label();
  private Label taskTotalWorkLabel = new Label();

  //Task.Task JavaFx objects
  TextField inputTaskName = new TextField();
  TextField inputTaskID = new TextField();
  ComboBox<String> inputTaskStatus = new ComboBox<>();
  TextField inputTaskEstimation = new TextField();
  DatePicker inputTaskDeadline = new DatePicker();
  ComboBox<Integer> inputTotalHoursWorked = new ComboBox<>();
  RadioButton[] employeeRadioButtons;
  VBox taskInfoContainer;

  private final ArrayList<String> statusOptions = new ArrayList<>(
      Arrays.asList("Approved", "Ended", "Not Started", "Rejected", "Started"));


  /**
   * Constructor initializing the GUI components
   * @param title           The title of the tab
   * @param adapterProject  object used for retrieving and storing project information
   * @param adapterEmployees object used for retrieving and storing employee information
   */
  public TaskListTab(String title, ProjectListAdapter adapterProject,
      EmployeeListAdapter adapterEmployees)
  {
    super(title);

    this.adapterProject = adapterProject;
    finalProjectList = adapterProject.getAllProjects();
    this.adapterEmployee = adapterEmployees;
    finalEmployeeList = adapterEmployee.getAllEmployees();

    listener = new MyActionListener();

    taskTableView = new TableView<>();
    defaultSelectionModel = taskTableView.getSelectionModel();
    taskTableView.setPrefHeight(597);

    taskName = new TableColumn<>(" Name");
    taskName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    taskName.setPrefWidth(199);

    taskTableView.getColumns().add(taskName);

    taskStatus = new TableColumn<>(" Status");
    taskStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    taskStatus.setPrefWidth(199);

    taskTableView.getColumns().add(taskStatus);

    taskDeadline = new TableColumn<>(" Deadline");
    taskDeadline.setCellValueFactory(new PropertyValueFactory<>("Deadline"));
    taskDeadline.setPrefWidth(199);

    taskTableView.getColumns().add(taskDeadline);

    taskInfoContainer = new VBox();

    Label infoLabel = new Label("Name:");
    infoLabel.setPrefWidth(150);
    HBox infoBox = new HBox(infoLabel, taskNameLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Status:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, taskStatusLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Deadline:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, taskDeadlineLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Responsible Employees:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, taskEmployeeLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("ID:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, taskIDLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Estimated hours:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, taskEstimatedHoursLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Total hours:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, taskTotalWorkLabel);
    taskInfoContainer.getChildren().add(infoBox);

    addTask = new Button("Add Task");
    addTask.setOnAction(listener);
    addTask.setOnAction(listener);

    editTask = new Button("Edit Task");
    editTask.setOnAction(listener);

    removeTask = new Button("Remove Task");
    removeTask.setOnAction(listener);

    HBox buttonContainer = new HBox(addTask, editTask, removeTask);
    buttonContainer.setPrefHeight(30);
    buttonContainer.setSpacing(50);
    buttonContainer.setPadding(new Insets(0, 10, 10, 10));
    buttonContainer.setAlignment(Pos.CENTER);

    tabTask = new VBox(10);
    tabTask.setAlignment(Pos.CENTER);
    tabTask.getChildren().add(taskTableView);
    tabTask.getChildren().add(taskInfoContainer);
    tabTask.getChildren().add(buttonContainer);

    super.setContent(tabTask);

    setSelectedTask();
  }

  /**
   * Sets the default values for window entities
   * @param window The window to insert default values
   * @param title  The title of the window
   */
  public void nameWindow(Stage window, String title)
  {
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(300);
    window.setResizable(false);
  }

  /**
   * Creates a VBox container with label and TextField and defines the  values them
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

  /**
   * Creates a VBox container with label and ComboBox and defines the  values them
   * @return the VBox containing label and ComboBox for selecting status
   */
  private VBox statusComboBoxWindowPart()
  {

    VBox statusContainer = new VBox();
    statusContainer.setPadding(new Insets(10, 10, 0, 10));
    Label status = new Label("Status: ");
    inputTaskStatus = new ComboBox();
    for (int i = 0; i < statusOptions.size(); i++)
    {
      inputTaskStatus.getItems().add(statusOptions.get(i));
    }
    inputTaskStatus.getSelectionModel().select(0);
    statusContainer.getChildren().addAll(status, inputTaskStatus);

    return statusContainer;
  }

  /**
   * Sets the selectedRequirement.
   *
   * @param requirementSelected The requirementSelected Requirement
   */
  public void setSelectedRequirement(Requirement requirementSelected)
  {
    selectedRequirement = requirementSelected;
  }

  /**
   * Sets the selectedProject.
   *
   * @param projectSelected The projectSelected Project
   */
  public void setSelectedProject(Project projectSelected)
  {
    selectedProject = projectSelected;
  }

  /**
   * Sets the selectedProject.
   */
  private void setSelectedTask()
  {
    taskTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (taskTableView.getSelectionModel().getSelectedItem() != null)
            {
              int index = taskTableView.getSelectionModel().getSelectedIndex();
              selectedTask = taskTableView.getItems().get(index);
              updateTaskLabels();
            }
          }
        });
  }

  /**
   * Updates the taskTableView tableView with information from the projects file
   */
  public void updateTaskArea()
  {
    taskTableView.getItems().clear();
    finalProjectList = adapterProject.getAllProjects();
    selectedRequirement = finalProjectList
        .getProjectByName(selectedProject.getName()).getRequirements()
        .getRequirementsByName(selectedRequirement.getName());
    if (selectedRequirement.getTasks() != null)
    {
      for (int i = 0; i < selectedRequirement.getTasks().size(); i++)
      {
        taskTableView.getItems().add(selectedRequirement.getTasks().getTask(i));
      }
    }
  }

  /**
   * Updates the labels in the taskInfoContainer with information from the projects file
   */
  private void updateTaskLabels()
  {
    taskNameLabel.setText(selectedTask.getName());
    taskIDLabel.setText(selectedTask.getTaskID());
    taskEmployeeLabel.setText(selectedTask.getResponsibleEmployee().getName());
    taskStatusLabel.setText(selectedTask.getStatus());
    taskDeadlineLabel.setText(selectedTask.getDeadline() + "");
    taskEstimatedHoursLabel.setText(selectedTask.getEstimatedHours() + "");
    taskTotalWorkLabel.setText(selectedTask.getTotalHoursWorked() + "");
  }

  /*
   * Inner action listener class
   * @author
   */
  private class MyActionListener implements EventHandler<ActionEvent>
  {
    public void handle(ActionEvent e)
    {
      if (e.getSource() == addTask)
      {
        Stage window = new Stage();
        errorLabel.setText("");
        nameWindow(window, "Add Task");

        // Task name input.
        VBox taskNameContainer = textFieldWindowPart(inputTaskName,
            " Task name: ");

        // task estimated hours input.
        VBox taskEstimatedHoursContainer = textFieldWindowPart(
            inputTaskEstimation, "Estimated hours: ");

        //Task status input
        VBox statusContainer = statusComboBoxWindowPart();

        //Task ID input
        VBox taskIDContainer = textFieldWindowPart(inputTaskID, "Task ID: ");

        // Task deadline input.
        VBox deadlineContainer = new VBox();
        deadlineContainer.setPadding(new Insets(10, 10, 0, 10));
        Label taskDeadline = new Label("Deadline:");
        inputTaskDeadline.setShowWeekNumbers(false);
        inputTaskDeadline.setValue(null);
        final DatePicker datePicker = new DatePicker();
        datePicker.setOnAction(new EventHandler()
        {
          public void handle(Event t)
          {
            LocalDate date = datePicker.getValue();
            System.err.println("Selected date: " + date);
          }
        });
        inputTaskDeadline.setDayCellFactory(picker -> new DateCell()
        {
          public void updateItem(LocalDate date, boolean empty)
          {
            super.updateItem(date, empty);
            setDisable(empty || date.compareTo(LocalDate.now()) < 1
                || date.compareTo(selectedRequirement.getDeadline()) > 0);
          }
        });
        inputTaskDeadline.setPromptText("Set deadline..");

        deadlineContainer.getChildren().addAll(taskDeadline, inputTaskDeadline);

        //Task employeeList input

        VBox employeeListContainer = new VBox();
        employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
        Label employeesLabel = new Label("Select employees: ");
        GridPane employeeSelectContainer = new GridPane();

        ToggleGroup employeeToggleGroup = new ToggleGroup();
        RadioButton[] employeeRadioButtons = new RadioButton[selectedRequirement.getTeam().size()];

        for (int i = 0 ; i < employeeRadioButtons.length ; i++){
          employeeRadioButtons[i] = new RadioButton(selectedRequirement.getTeam().getEmployee(i).getName());
          employeeSelectContainer.add(employeeRadioButtons[i], i % 2, i / 2);
          employeeRadioButtons[i].setPadding(new Insets(3, 50, 3, 3));
          employeeRadioButtons[i].setToggleGroup(employeeToggleGroup);
        }

        // Add employee label Node and employee selection Node
        employeeListContainer.getChildren()
            .addAll(employeesLabel, employeeSelectContainer);

        VBox layout = new VBox(10);

        Button closeWithSaveButton = new Button("Add task");

        closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
        {
          public void handle(ActionEvent e)
          {

            for (int i = 0; i < employeeRadioButtons.length; i++)
            {
              if (employeeRadioButtons[i].isSelected())
              {
                selectedEmployee = selectedRequirement.getTeam().getEmployee(i);
              }
            }
            if (inputTaskName.getText().isEmpty() || inputTaskName.getText()
                .equals(""))
            {
              errorLabel.setText("ERROR: Fix name");
            }
            else if (inputTaskID.getText().isEmpty() || inputTaskID.getText()
                .equals(""))
            {
              errorLabel.setText("ERROR: Fix task ID");
            }
            else if (!inputTaskID.getText().matches("[0-9]+"))
            {
              errorLabel.setText("ERROR: Fix task ID");
            }
            else if (inputTaskEstimation.getText().isEmpty()
                || inputTaskEstimation.getText().equals(""))
            {
              errorLabel.setText("ERROR: Fix estimated hours");
            }
            else if (!inputTaskEstimation.getText().matches("[0-9]+"))
            {
              errorLabel.setText("ERROR: Fix estimated hours");
            }
            else if (inputTaskDeadline.getValue() == null)
            {
              errorLabel.setText("ERROR: Fix deadline");
            }
            else if (selectedEmployee == null)
            {
              errorLabel.setText("ERROR: Fix employee");
            }
            else
            {
              window.close();
              Task task = new Task(inputTaskName.getText(),
                  inputTaskID.getText(), inputTaskStatus.getValue(),
                  Integer.parseInt(inputTaskEstimation.getText()),
                  inputTaskDeadline.getValue(), selectedEmployee);
              finalProjectList.getProjectByName(selectedProject.getName())
                  .getRequirements()
                  .getRequirementsByName(selectedRequirement.getName())
                  .getTasks().addTask(task);
              adapterProject.saveProjects(finalProjectList);
              updateTaskArea();
            }
          }
        });

        layout.getChildren()
            .addAll(taskNameContainer, statusContainer, taskIDContainer,
                employeeListContainer, taskEstimatedHoursContainer,
                deadlineContainer, closeWithSaveButton, errorLabel);

        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
      }

      else if (e.getSource() == editTask)
      {
        if (!(selectedTask == null))
        {
          Stage window = new Stage();
          errorLabel.setText("");
          nameWindow(window, "Edit task " + selectedTask.getName());

          // Task name input.
          VBox taskNameContainer = textFieldWindowPart(inputTaskName,
              "New Task name: ");

          inputTaskName.setText(selectedTask.getName());

          // task estimated hours input.
          VBox taskEstimatedHoursContainer = textFieldWindowPart(
              inputTaskEstimation, "Estimated hours: ");
          inputTaskEstimation
              .setText(String.valueOf(selectedTask.getEstimatedHours()));

          //Task total hours input

          VBox totalHoursContainer = new VBox();
          totalHoursContainer.setPadding(new Insets(10, 10, 0, 10));
          Label totalHours = new Label("Total hours: ");

          ComboBox inputTaskWorkDone = new ComboBox();
          inputTaskWorkDone.getItems().add(0);
          inputTaskWorkDone.getSelectionModel().select(0);
          inputTaskWorkDone.setOnMouseClicked(new EventHandler()
          {
            public void handle(Event t)
            {
              inputTaskWorkDone.getItems()
                  .remove(1, inputTaskWorkDone.getItems().size());
              for (int i = 1;
                   i <= Integer.parseInt(inputTaskEstimation.getText()); i++)
              {
                inputTaskWorkDone.getItems().add(i);
              }
            }

          });
          totalHoursContainer.getChildren()
              .addAll(totalHours, inputTaskWorkDone);

          //Task ID input
          VBox taskIDContainer = textFieldWindowPart(inputTaskID, "Task ID: ");
          inputTaskID.setText(String.valueOf(selectedTask.getTaskID()));

          // Task status input.
          VBox statusContainer = statusComboBoxWindowPart();
          inputTaskStatus.setValue(selectedTask.getStatus());

          // Task deadline input.
          VBox deadlineContainer = new VBox();
          deadlineContainer.setPadding(new Insets(10, 10, 0, 10));
          Label taskDeadline = new Label("Deadline:");
          inputTaskDeadline.setShowWeekNumbers(false);
          final DatePicker datePicker = new DatePicker();
          datePicker.setOnAction(new EventHandler()
          {
            public void handle(Event t)
            {
              LocalDate date = datePicker.getValue();
              System.err.println("Selected date: " + date);
            }
          });
          inputTaskDeadline.setDayCellFactory(picker -> new DateCell()
          {
            public void updateItem(LocalDate date, boolean empty)
            {
              super.updateItem(date, empty);
              setDisable(empty || date.compareTo(LocalDate.now()) < 1
                  || date.compareTo(selectedRequirement.getDeadline()) > 0);
            }
          });
          inputTaskDeadline.setPromptText("Set deadline..");
          inputTaskDeadline.setValue(selectedTask.getDeadline());

          deadlineContainer.getChildren()
              .addAll(taskDeadline, inputTaskDeadline);

          // Task employee list input.
          VBox employeeListContainer = new VBox();
          employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
          Label employeesLabel = new Label("Select employees: ");
          GridPane employeeSelectContainer = new GridPane();

          ToggleGroup employeeToggleGroup = new ToggleGroup();
          RadioButton[] employeeRadioButtons = new RadioButton[selectedRequirement.getTeam().size()];

          for (int i = 0 ; i < employeeRadioButtons.length ; i++){
            employeeRadioButtons[i] = new RadioButton(selectedRequirement.getTeam().getEmployee(i).getName());
            employeeSelectContainer.add(employeeRadioButtons[i], i % 2, i / 2);
            employeeRadioButtons[i].setPadding(new Insets(3, 50, 3, 3));
            employeeRadioButtons[i].setToggleGroup(employeeToggleGroup);
            for (int j = 0; j < selectedRequirement.getTeam().size(); j++)
            {
              if (employeeRadioButtons[i].getText().equals(selectedTask.getResponsibleEmployee().getName()))
              {
                employeeRadioButtons[i].setSelected(true);
              }

            }
          }

          // Add employee label Node and employee selection Node
          employeeListContainer.getChildren()
              .addAll(employeesLabel, employeeSelectContainer);

          VBox layout = new VBox(10);

          Button closeWithSaveButton = new Button("Save and close");

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            public void handle(ActionEvent e)
            {
              // Edit new name
              selectedTask.setName(inputTaskName.getText());
              // Edit new ID
              selectedTask.setTaskID(inputTaskID.getText());
              // Edit new status
              selectedTask.setStatus(inputTaskStatus.getValue());
              // New EmployeeList object to replace the old one
              EmployeeList selectedEmployees = new EmployeeList();
              // Run loop to check which employees to add and which to not add
              for (int i = 0; i < employeeRadioButtons.length; i++)
              {
                if (employeeRadioButtons[i].isSelected())
                {
                  selectedEmployee = selectedRequirement.getTeam().getEmployee(i);
                }
              }

              // Edit new team from selected radiobuttons
              selectedTask.setResponsibleEmployee(selectedEmployee);
              // Edit estimated hours
              if (!inputTaskEstimation.getText().matches("[0-9]+"))
              {
                selectedTask.setEstimatedHours(selectedTask.getEstimatedHours());
                errorLabel.setText("ERROR: Fix estimated hours");
              }
              else
              {
                selectedTask.setEstimatedHours(Integer.parseInt(inputTaskEstimation.getText()));
              }
              // Edit total hours
              selectedTask
                  .setTotalHoursWorked((Integer) inputTaskWorkDone.getValue());
              // Edit new deadline
              selectedTask.setDeadline(inputTaskDeadline.getValue());
              // Close window
              window.close();
              // Save all changes
              adapterProject.saveProjects(finalProjectList);
              // Update GUI table with requirements to show changes
              updateTaskArea();
              updateTaskLabels();
              // END of editing task
            }
          });

          layout.getChildren()
              .addAll(taskNameContainer, taskIDContainer, statusContainer,
                  employeeListContainer, taskEstimatedHoursContainer,
                  totalHoursContainer, deadlineContainer, closeWithSaveButton,
                  errorLabel);

          layout.setAlignment(Pos.CENTER);
          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();
        }
      }

      else if (e.getSource() == removeTask)
      {
        if (!(selectedTask == null))
        {
          Stage window = new Stage();
          nameWindow(window, "Remove task " + selectedTask.getName());

          HBox nameContainer = new HBox(2);
          nameContainer.setPadding(new Insets(10, 10, 0, 10));
          Label projectName = new Label(
              "Do you really want to remove: " + selectedTask.getName());

          nameContainer.getChildren().addAll(projectName);

          Button closeWithSaveButton = new Button("Save and close");
          Button closeWithOutSaveButton = new Button("Save without closing");
          HBox closingButtons = new HBox(closeWithSaveButton,
              closeWithOutSaveButton);
          closingButtons.setPadding(new Insets(10, 40, 10, 50));
          closingButtons.setSpacing(50);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            public void handle(ActionEvent e)
            {
              {
                window.close();
                selectedRequirement.getTasks().removeTask(selectedTask);
                finalProjectList.getProjectByName(selectedProject.getName())
                    .getRequirements()
                    .getRequirementsByName(selectedRequirement.getName())
                    .remove(selectedTask);
                adapterProject.saveProjects(finalProjectList);
                updateTaskArea();
                selectedTask = null;
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
              .addAll(nameContainer, errorLabel, closingButtons);

          layout.setAlignment(Pos.CENTER);
          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }
      }
    }
  }
}
