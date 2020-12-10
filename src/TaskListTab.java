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

public class TaskListTab extends Tab
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

  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  private ProjectList finalProjectList;
  private EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  private Label taskNameLabel = new Label();
  private Label taskStatusLabel = new Label();
  private Label taskDeadlineLabel = new Label();
  private Label taskIDLabel = new Label();
  private Label taskEstimatedHoursLabel = new Label();
  private Label taskTotalWorkLabel = new Label();

  //Task JavaFx objects
  TextField inputTaskName = new TextField();
  TextField inputTaskID = new TextField();
  ComboBox<String> inputTaskStatus = new ComboBox<>();
  TextField inputTaskEstimation = new TextField();
  DatePicker inputTaskDeadline = new DatePicker();
  ComboBox<Integer> inputTotalHoursWorked = new ComboBox<>();
  RadioButton[] employeeRadioButtons;
  VBox taskInfoContainer;

  //Hardoded values
  final private String NAME = "Name";
  final private String STATUS = "Status";
  final private String DEADLINE = "Deadline";
  final private String ID = "Id";
  final private String ESTIMATEDHOURS = "Estimated Hours";
  final private String TOTALHOURS = "Total Hours";
  final private String ADD = "Add task";
  final private String EDIT = "Edit task";
  final private String REMOVE = "Remove task";

  final private int taskTableViewHeight = 597;
  final private int taskTableViewColumnWIdth = 199;
  final private int infoLabelWidth = 150;

  private final ArrayList<String> statusOptions = new ArrayList<>();

  public TaskListTab(String title, ProjectListAdapter adapterProjects,
      EmployeeListAdapter adapterEmployees)
  {
    super(title);

    statusOptions.add("Approved");
    statusOptions.add("Ended");
    statusOptions.add("Not Started");
    statusOptions.add("Rejected");
    statusOptions.add("Started");

    this.adapterProject = adapterProjects;
    finalProjectList = adapterProject.getAllProjects();
    this.adapterEmployee = adapterEmployees;
    finalEmployeeList = adapterEmployee.getAllEmployees();

    listener = new MyActionListener();

    taskTableView = new TableView<>();
    defaultSelectionModel = taskTableView.getSelectionModel();
    taskTableView.setPrefHeight(taskTableViewHeight);

    taskName = new TableColumn<>(NAME);
    taskName.setCellValueFactory(new PropertyValueFactory<>(NAME));
    taskName.setPrefWidth(taskTableViewColumnWIdth);

    taskTableView.getColumns().add(taskName);

    taskStatus = new TableColumn<>(STATUS);
    taskStatus.setCellValueFactory(new PropertyValueFactory<>(STATUS));
    taskStatus.setPrefWidth(taskTableViewColumnWIdth);

    taskTableView.getColumns().add(taskStatus);

    taskDeadline = new TableColumn<>(DEADLINE);
    taskDeadline.setCellValueFactory(new PropertyValueFactory<>(DEADLINE));
    taskDeadline.setPrefWidth(taskTableViewColumnWIdth);

    taskTableView.getColumns().add(taskDeadline);

    taskInfoContainer = new VBox();

    Label infoLabel = new Label(NAME + ":");
    infoLabel.setPrefWidth(infoLabelWidth);
    HBox infoBox = new HBox(infoLabel, taskNameLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(STATUS + ":");
    infoLabel.setPrefWidth(infoLabelWidth);
    infoBox = new HBox(infoLabel, taskStatusLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(DEADLINE + ":");
    infoLabel.setPrefWidth(infoLabelWidth);
    infoBox = new HBox(infoLabel, taskDeadlineLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(ID + ":");
    infoLabel.setPrefWidth(infoLabelWidth);
    infoBox = new HBox(infoLabel, taskIDLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(ESTIMATEDHOURS + ":");
    infoLabel.setPrefWidth(infoLabelWidth);
    infoBox = new HBox(infoLabel, taskEstimatedHoursLabel);
    taskInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(TOTALHOURS + ":");
    infoLabel.setPrefWidth(infoLabelWidth);
    infoBox = new HBox(infoLabel, taskTotalWorkLabel);
    taskInfoContainer.getChildren().add(infoBox);

    addTask = new Button(ADD);
    addTask.setOnAction(listener);
    addTask.setOnAction(listener);

    editTask = new Button(EDIT);
    editTask.setOnAction(listener);

    removeTask = new Button(REMOVE);
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

    finalEmployeeList = adapterEmployee.getAllEmployees();
    finalProjectList = adapterProject.getAllProjects();

    if (selectedTask != null)
    {
      updateTaskArea();
    }
  }

  private void nameWindow(Stage window, String str)
  {
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(str);
    window.setMinWidth(300);
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
    statusContainer.getChildren().addAll(status, inputTaskStatus);

    return statusContainer;
  }

  public void setSelectedRequirement(Requirement selectedRequirement1)
  {
    selectedRequirement = selectedRequirement1;
    updateTaskArea();
  }

  public void setSelectedProject(Project selectedProject1)
  {
    selectedProject = selectedProject1;
    updateTaskArea();
  }

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

  public void updateTaskArea()
  {
    taskTableView.getItems().clear();
    if (selectedProject != null)
    {
      if (selectedRequirement != null)
      {
        if (adapterProject != null)
        {
          finalProjectList = adapterProject.getAllProjects();
          selectedRequirement = finalProjectList
              .getProjectByName(selectedProject.getName()).getRequirements()
              .getRequirementsByName(selectedRequirement.getName());
          if (selectedRequirement.getTasks() != null)
          {
            for (int i = 0; i < selectedRequirement.getTasks().size(); i++)
            {
              System.out.println(selectedRequirement.getTasks().size());
              taskTableView.getItems()
                  .add(selectedRequirement.getTasks().getTask(i));
            }
          }
        }
      }
    }
  }

  private void updateTaskLabels()
  {
    taskNameLabel.setText(selectedTask.getName());
    taskIDLabel.setText(selectedTask.getTaskID());
    taskStatusLabel.setText(selectedTask.getStatus());
    taskDeadlineLabel.setText(selectedTask.getDeadline()+"");
    taskEstimatedHoursLabel
        .setText(selectedTask.getEstimatedHours()+"");
    taskTotalWorkLabel
        .setText(selectedTask.getTotalHoursWorked()+"");
  }

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
        inputTaskDeadline.setOnAction(new EventHandler()
        {
          public void handle(Event t)
          {
            System.err
                .println("Selected date: " + inputTaskDeadline.getValue());
          }
        });
        inputTaskDeadline.setPromptText("Set deadline..");

        deadlineContainer.getChildren().addAll(taskDeadline, inputTaskDeadline);

        //Task employeeList input

        VBox employeeListContainer = new VBox();
        employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
        Label employeesLabel = new Label("Select employees: ");
        GridPane employeeSelectContainer = new GridPane();
        CheckBox[] employeeCheckBoxes = new CheckBox[selectedRequirement
            .getTeam().size()];

        for (int i = 0; i < employeeCheckBoxes.length; i++)
        {
          employeeCheckBoxes[i] = new CheckBox(
              selectedRequirement.getTeam().get(i).getName());
          employeeSelectContainer.add(employeeCheckBoxes[i], i % 2, i / 2);
          employeeCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
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

            EmployeeList selectedEmployees = new EmployeeList();
            for (int i = 0; i < employeeCheckBoxes.length; i++)
            {
              if (employeeCheckBoxes[i].isSelected())
              {
                selectedEmployees.addEmployee(finalEmployeeList.get(i));
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
              errorLabel.setText("ERROR: Fix taxID");
            }
            else if (inputTaskEstimation.getText().isEmpty()
                || inputTaskEstimation.getText().equals(""))
            {
              errorLabel.setText("ERROR: Fix estimated hours");
            }
            else if (inputTaskDeadline.getValue() == null)
            {
              errorLabel.setText("ERROR: Fix deadline");
            }
            else if (selectedEmployees.size() == 0)
            {
              errorLabel.setText("ERROR: Fix employees");
            }
            else
            {
              window.close();

              Task task = new Task(inputTaskName.getText(),
                  inputTaskID.getText(), inputTaskStatus.getValue(),
                  Integer.parseInt(inputTaskEstimation.getText()),
                  inputTaskDeadline.getValue(), selectedEmployees);
              finalProjectList.getProjectByName(selectedProject.getName())
                  .getRequirements()
                  .getRequirementsByName(selectedRequirement.getName())
                  .getTasks().addTask(task);
              ;
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
        totalHoursContainer.getChildren().addAll(totalHours, inputTaskWorkDone);

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

        deadlineContainer.getChildren().addAll(taskDeadline, inputTaskDeadline);

        // Task employee list input.
        VBox employeeListContainer = new VBox();
        employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
        Label employeesLabel = new Label("Select employees: ");
        GridPane employeeSelectContainer = new GridPane();

        CheckBox[] employeeCheckBoxes = new CheckBox[selectedProject.getTeam()
            .size()];

        for (int i = 0; i < employeeCheckBoxes.length; i++)
        {
          employeeCheckBoxes[i] = new CheckBox(
              selectedProject.getTeam().get(i).getName());
          employeeSelectContainer.add(employeeCheckBoxes[i], i % 2, i / 2);
          employeeCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));

          for (int j = 0; j < selectedRequirement.getTeam().size(); j++)
          {
            if (employeeCheckBoxes[i].getText()
                .equals(selectedRequirement.getTeam().get(j).getName()))
            {
              employeeCheckBoxes[i].setSelected(true);
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
            for (int i = 0; i < employeeCheckBoxes.length; i++)
            {
              if (employeeCheckBoxes[i].isSelected())
              {
                selectedEmployees.addEmployee(selectedProject.getTeam().get(i));
              }
            }
            // Edit new team from selected checkboxes
            selectedTask.setTaskEmployees(selectedEmployees);
            // Edit estimated hours
            selectedTask.setEstimatedHours(
                Integer.parseInt(inputTaskEstimation.getText()));
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
              .addAll(nameContainer, errorLabel, closeWithSaveButton,
                  closeWithOutSaveButton);
          layout.setAlignment(Pos.CENTER);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }

      }
    }
  }
}
