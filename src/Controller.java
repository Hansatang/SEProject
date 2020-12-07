import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller
{
  @FXML private RadioButton searchByName, searchByEmployee;
  @FXML private TextField searchField;
  @FXML private Tab projectDetailsTab;
  @FXML private Tab requirementDetailsTab;

  @FXML private TableView<Employee> employeeField;
  @FXML private TableColumn<Employee, String> employeeName;

  @FXML private TableView<Project> projectField;
  @FXML private TableColumn<Project, String> projectName;
  @FXML private TableColumn<Project, String> projectTeam;

  @FXML private TableView<Requirement> requirementField;
  @FXML private TableColumn<Requirement, String> requirementName;
  @FXML private TableColumn<Requirement, String> requirementStatus;
  @FXML private TableColumn<Requirement, String> requirementDeadline;

  @FXML private Label requirementNameLabel;
  @FXML private Label requirementStatusLabel;
  @FXML private Label requirementTeamLabel;
  @FXML private Label requirementDeadlineLabel;
  @FXML private TextArea requirementUserStoryLabel;
  @FXML private Label requirementEstimatedLabel;
  @FXML private Label requirementHoursWorkedLabel;

  @FXML private Label taskNameLabel;
  @FXML private Label taskStatusLabel;
  @FXML private Label taskDeadlineLabel;
  @FXML private Label taskIDLabel;
  @FXML private Label taskEstimatedHoursLabel;
  @FXML private Label taskTotalWorkLabel;

  @FXML private TableView<Task> taskField;
  @FXML private TableColumn<Task, String> taskName;
  @FXML private TableColumn<Task, String> taskStatus;
  @FXML private TableColumn<Task, String> taskDeadline;
  //@FXML private

  // Project JavaFX objects \\
  TextField inputProjectName = new TextField();
  CheckBox[] memberCheckBoxes;

  // Employee JavaFX objects \\
  TextField inputMemberName = new TextField();

  // Requirement JavaFX objects
  TextField inputRequirementName = new TextField();
  TextField inputUserStory = new TextField();
  ComboBox<String> inputStatus = new ComboBox<>();
  DatePicker inputRequirementDeadline = new DatePicker();

  //Task JavaFx objects
  TextField inputTaskName = new TextField();
  TextField inputTaskID = new TextField();
  TextField inputTaskEstimatedHours = new TextField();
  ComboBox<Integer> inputTotalHoursWorked = new ComboBox<>();
  DatePicker inputTaskDeadline = new DatePicker();

  // General JavaFX objects \\
  Label errorLabel = new Label("");
  HashMap<String, Button> closeAndSaveButton = new HashMap<>();

  // Adapters
  private ProjectListAdapter adapterProjects;
  private EmployeeListAdapter adapterEmployee;

  // Class list Objects
  private EmployeeList selectedMembers;
  private ProjectList finalProjectList;
  private EmployeeList finalEmployeeList;

  // Selected objects
  private Employee selectedEmployee;
  private Project selectedProject;
  private Requirement selectedRequirement;
  private Task selectedTask;

  private final ArrayList<String> statusOptions = new ArrayList<>();

  /**
   * Runs one time before the GUI is shown
   */
  public void initialize()
  {
    statusOptions.add("Approved");
    statusOptions.add("Ended");
    statusOptions.add("Not Started");
    statusOptions.add("Rejected");
    statusOptions.add("Started");
    employeeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    projectName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    projectTeam.setCellValueFactory(new PropertyValueFactory<>("Team"));
    requirementName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    requirementStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    requirementDeadline.setCellValueFactory(new PropertyValueFactory<>("Deadline"));
    taskName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    taskStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    taskDeadline.setCellValueFactory(new PropertyValueFactory<>("Deadline"));
    adapterProjects = new ProjectListAdapter("Projects.bin");
    adapterEmployee = new EmployeeListAdapter("Employees.bin");

    // Update all GUI
    updateEmployeeArea();
    updateProjectArea();
    setSelectedMember();
    setSelectedProject();
    setSelectedRequirement();
    setSelectedTask();

    //updateProjectDetailsArea();
    errorLabel.setTextFill(Color.RED);
    errorLabel.setWrapText(true);
    errorLabel.setPadding(new Insets(0, 0, 50, 0));

    closeAndSaveButton.put("addEmployee", new Button("Add new member"));
    closeAndSaveButton.put("editEmployee", new Button("Save and close"));

    closeAndSaveButton.put("addProject", new Button("Add new project"));
    closeAndSaveButton.put("editProject", new Button("Save and close"));

    closeAndSaveButton.put("addRequirement", new Button("Add new requirement"));
    closeAndSaveButton.put("editRequirement", new Button("Save and close"));

    closeAndSaveButton.put("addTask", new Button("Add new task"));
    closeAndSaveButton.put("editTask", new Button("Save and close"));

  }

  /**
   * Method used to select a member with the mouse in the TableView so the member later can be edited or removed.
   */
  private void setSelectedMember()
  {
    employeeField.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (employeeField.getSelectionModel().getSelectedItem() != null)
            {
              int index = employeeField.getSelectionModel().getSelectedIndex();
              selectedEmployee = employeeField.getItems().get(index);
            }
          }
        });
  }

  /**
   * Method used to select a project with the mouse in the TableView so the member later can be edited or removed.
   */
  private void setSelectedProject()
  {
    projectField.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (projectField.getSelectionModel().getSelectedItem() != null)
            {
              int index = projectField.getSelectionModel().getSelectedIndex();
              selectedProject = projectField.getItems().get(index);
              projectDetailsTab
                  .setText(selectedProject.getName() + " project details");
              projectDetailsTab.setDisable(false);
              updateRequirementArea();
            }
          }
        });
  }

  /**
   * Method used to select a requirement with the mouse in the TableView so the requirement later can be edited or removed.
   */
  private void setSelectedRequirement()
  {
    requirementField.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (requirementField.getSelectionModel().getSelectedItem() != null)
            {
              int index = requirementField.getSelectionModel()
                  .getSelectedIndex();

              selectedRequirement = requirementField.getItems().get(index);
              requirementDetailsTab.setText(
                  selectedRequirement.getName() + " requirement details");
              requirementDetailsTab.setDisable(false);
              updateRequirementArea();
              updateTaskArea();
            }
          }
        });
  }

  /**
   * Updates the EmployeeList objects the TreeView<Employee> on the GUI
   */
  private void updateEmployeeArea()
  {
    employeeField.getItems().clear();
    if (adapterEmployee != null)
    {
      finalEmployeeList = adapterEmployee.getAllMembers();
      for (int i = 0; i < finalEmployeeList.size(); i++)
      {
        employeeField.getItems().add(finalEmployeeList.get(i));
      }
    }
  }

  /**
   * Updates the ProjectList objects the TreeView<Project> on the GUI
   */
  private void updateProjectArea()
  {
    projectField.getItems().clear();
    if (adapterProjects != null)
    {
      finalProjectList = adapterProjects.getAllProjects();
      for (int i = 0; i < finalProjectList.size(); i++)
      {
        projectField.getItems().add(finalProjectList.get(i));
      }
    }
  }

  /**
   * Updates the Requirement objects on the TreeView<Project> on the GUI
   */
  private void updateRequirementArea()
  {
    requirementField.getItems().clear();
    if (adapterProjects != null)
    {
      for (int i = 0; i < selectedProject.getRequirements().size(); i++)
      {
        requirementField.getItems()
            .add(selectedProject.getRequirements().getRequirement(i));
      }
    }

    if (selectedRequirement != null)
    {
      requirementNameLabel.setText(selectedRequirement.getName());
      requirementStatusLabel.setText(selectedRequirement.getStatus());
      requirementDeadlineLabel
          .setText(selectedRequirement.getDeadline().toString());
      requirementTeamLabel.setText(selectedRequirement.getTeam().toString());
      if (!selectedRequirement.getTasks().isEmpty())
      {
        requirementEstimatedLabel.setText(
            selectedRequirement.getTasks().getTotalEstimatedHours() + "");
        requirementEstimatedLabel.setTextFill(Color.BLACK);
        requirementHoursWorkedLabel
            .setText(selectedRequirement.getTasks().getTotalWorkedHours() + "");
        requirementHoursWorkedLabel.setTextFill(Color.BLACK);
      }
      else
      {
        requirementEstimatedLabel.setText("No tasks in this requirement");
        requirementEstimatedLabel.setTextFill(Color.RED);
        requirementHoursWorkedLabel.setText("No tasks in this requirement");
        requirementHoursWorkedLabel.setTextFill(Color.RED);
      }
      requirementUserStoryLabel.setText(selectedRequirement.getUserstory());
    }
  }


  private void updateTaskLabels()
  {
    taskNameLabel.setText(" Name: " + selectedTask.getName());
    taskIDLabel.setText(" Id: " + selectedTask.getTaskID());
    taskStatusLabel.setText(" Status: " + selectedTask.getStatus());
    taskDeadlineLabel.setText(" Deadline: " + selectedTask.getDeadline());
    taskEstimatedHoursLabel.setText(" Estimated hours: " + selectedTask.getEstimatedHours());
    taskTotalWorkLabel.setText(" Total work: " + selectedTask.getTotalHoursWorked());
  }

  /**
   * Method used to select a task with the mouse in the TableView so the requirement later can be edited or removed.
   *
   * @param //args Command line arguments
   */
  private void setSelectedTask()
  {
    taskField.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (taskField.getSelectionModel().getSelectedItem() != null)
            {
              int index = taskField.getSelectionModel().getSelectedIndex();
              selectedTask = taskField.getItems().get(index);

              updateTaskLabels();
            }
          }
        });
  }

  private void updateTaskArea()
  {
    taskField.getItems().clear();
    if (adapterProjects != null)
    {
      for (int i = 0; i < selectedRequirement.getTasks().size(); i++)
      {
        taskField.getItems().add(selectedRequirement.getTasks().getTask(i));
      }
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
    inputText.setPromptText("Enter " + labelName.toLowerCase());
    nameContainer.getChildren().addAll(label, inputText);

    return nameContainer;
  }

  /**
   * FXML method to the button which add a new employee
   *
   * @param //args Command line arguments
   */
  @FXML public void addEmployeeClick()
  {
    errorLabel.setText("");
    Stage window = new Stage();

    nameWindow(window, "Add a new member");

    VBox employeeNameContainer = textFieldWindowPart(inputMemberName,
        "Employee name: ");

    closeAndSaveButton.get("addEmployee")
        .setOnAction(new PopupListener(window));

    VBox layout = new VBox(10);

    layout.getChildren().addAll(employeeNameContainer, errorLabel,
        closeAndSaveButton.get("addEmployee"));
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();

  }

  /**
   * FXML method to the button which edits a selected employee
   *
   * @param //args Command line arguments
   */
  @FXML public void editEmployeeClick()
  {
    if (!(selectedEmployee == null))
    {
      Stage window = new Stage();

      nameWindow(window, "Edit employee" + selectedEmployee.getName());

      // Employee name input.
      VBox employeeNameContainer = textFieldWindowPart(inputMemberName,
          "New Employee name: ");

      inputMemberName.setText(selectedEmployee.getName());

      closeAndSaveButton.get("editEmployee")
          .setOnAction(new PopupListener(window));

      VBox layout = new VBox(10);

      layout.getChildren().addAll(employeeNameContainer, errorLabel,
          closeAndSaveButton.get("editEmployee"));
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setScene(scene);
      window.showAndWait();

    }
  }

  /**
   * FXML method to the button which removes a selected employee
   *
   * @param //args Command line arguments
   */
  @FXML public void removeEmployeeClick()
  {
    if (!(selectedEmployee == null))
    {
      Stage window = new Stage();

      nameWindow(window, "Remove employee" + selectedEmployee.getName());

      // Employee name input.
      HBox nameContainer = new HBox(2);
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label memberName = new Label(
          "Do you really want to remove: " + selectedEmployee.getName());

      nameContainer.getChildren().addAll(memberName);

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
            ProjectList projects = adapterProjects
                .getProjectByEmployeeName(selectedEmployee.getName());
            for (int i = 0; i < projects.size(); i++)
            {
              finalProjectList.getProject(projects.get(i).getName()).getTeam()
                  .deleteMember(selectedEmployee.getName());
            }

            finalEmployeeList.removeMember(selectedEmployee);
            adapterEmployee.saveMembers(finalEmployeeList);
            adapterProjects.saveProjects(finalProjectList);
            updateEmployeeArea();
            updateProjectArea();
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

      VBox layout = new VBox(10);

      layout.getChildren().addAll(nameContainer, errorMessage, closingButtons);
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setScene(scene);
      window.showAndWait();

    }

  }

  /**
   * FXML method to the button which adds a new project
   *
   * @param //args Command line arguments
   */
  @FXML public void addProjectClick()
  {
    Stage window = new Stage();
    errorLabel.setText("");

    nameWindow(window, "Add project");

    // Project name input.
    VBox projectNameContainer = textFieldWindowPart(inputProjectName,
        "Project name: ");

    // Project member list input.
    VBox memberListContainer = new VBox();
    memberListContainer.setPadding(new Insets(0, 10, 0, 10));
    Label membersLabel = new Label("Select members: ");
    GridPane memberSelectContainer = new GridPane();
    memberCheckBoxes = new CheckBox[finalEmployeeList.size()];

    for (int i = 0; i < memberCheckBoxes.length; i++)
    {
      memberCheckBoxes[i] = new CheckBox(finalEmployeeList.get(i).getName());
      memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);
      memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
    }

    // Add member label Node and member selection Node
    memberListContainer.getChildren()
        .addAll(membersLabel, memberSelectContainer);

    // Config save and close button
    closeAndSaveButton.get("addProject").setOnAction(new PopupListener(window));

    VBox layout = new VBox(10);

    layout.getChildren().addAll(projectNameContainer, memberListContainer,
        closeAndSaveButton.get("addProject"), errorLabel);

    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);

    window.setScene(scene);
    window.showAndWait();

  }

  /**
   * FXML method to the button which edits a selected project
   *
   * @param //args Command line arguments
   */
  @FXML public void editProjectClick()
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

      // Project member list input.
      VBox memberListContainer = new VBox();
      memberListContainer.setPadding(new Insets(0, 10, 0, 10));
      Label membersLabel = new Label("Select members: ");
      GridPane memberSelectContainer = new GridPane();
      memberCheckBoxes = new CheckBox[finalEmployeeList.size()];

      for (int i = 0; i < memberCheckBoxes.length; i++)
      {
        memberCheckBoxes[i] = new CheckBox(finalEmployeeList.get(i).getName());
        memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);

        for (int j = 0; j < selectedProject.getTeam().size(); j++)
        {
          if (memberCheckBoxes[i].getText()
              .equals(selectedProject.getTeam().get(j).getName()))
          {
            memberCheckBoxes[i].setSelected(true);
          }
        }
        memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
      }
      // Add member label Node and member selection Node
      memberListContainer.getChildren()
          .addAll(membersLabel, memberSelectContainer);

      closeAndSaveButton.get("editProject")
          .setOnAction(new PopupListener(window));

      VBox layout = new VBox(10);

      layout.getChildren().addAll(projectNameContainer, memberListContainer,
          closeAndSaveButton.get("editProject"), errorLabel);

      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setScene(scene);
      window.showAndWait();
    }
  }

  /**
   * FXML method to the button which removes a selected project
   *
   * @param //args Command line arguments
   */
  @FXML public void removeProjectClick()
  {
    if (!(selectedProject == null))
    {
      Stage window = new Stage();
      nameWindow(window, "Remove project" + selectedProject.getName());

      // Project name input.
      HBox nameContainer = new HBox(2);
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label projectName = new Label(
          "Do you really want to remove: " + selectedProject.getName());

      nameContainer.getChildren().addAll(projectName);

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
            finalProjectList.removeProject(selectedProject);
            adapterProjects.saveProjects(finalProjectList);
            updateProjectArea();
            selectedProject = null;
            projectDetailsTab.setText("Project details");
            projectDetailsTab.setDisable(true);
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

      layout.getChildren().addAll(nameContainer, errorLabel, closingButtons);
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setScene(scene);
      window.showAndWait();

    }

  }

  /**
   * FXML method to the button which adds a new requirement
   *
   * @param //args Command line arguments
   */
  @FXML public void addRequirementClick()
  {
    Stage window = new Stage();
    errorLabel.setText("");
    nameWindow(window, "Add requirement");

    // Requirement name input.

    VBox requirementNameContainer = textFieldWindowPart(inputRequirementName,
        " Requirement name: ");

    // Requirement user story input.
    VBox requirementUserStoryContainer = textFieldWindowPart(inputUserStory,
        "User story: ");

    // Requirement status input.
    VBox statusContainer = new VBox();
    statusContainer.setPadding(new Insets(10, 10, 0, 10));
    Label status = new Label("Status: ");

    inputStatus = new ComboBox();
    for (int i = 0; i < statusOptions.size(); i++)
    {
      inputStatus.getItems().add(statusOptions.get(i));
    }
    statusContainer.getChildren().addAll(status, inputStatus);

    // Requirement deadline input.
    VBox deadlineContainer = new VBox();
    deadlineContainer.setPadding(new Insets(10, 10, 0, 10));
    Label taskDeadline = new Label("Deadline:");
    inputRequirementDeadline.setShowWeekNumbers(false);
    final DatePicker datePicker = new DatePicker();
    datePicker.setOnAction(new EventHandler()
    {
      public void handle(Event t)
      {
        LocalDate date = datePicker.getValue();
        System.err.println("Selected date: " + date);
      }
    });
    inputRequirementDeadline.setDayCellFactory(picker -> new DateCell()
    {
      public void updateItem(LocalDate date, boolean empty)
      {
        super.updateItem(date, empty);
        setDisable(empty || date.compareTo(LocalDate.now()) < 1);
      }
    });
    inputRequirementDeadline.setOnAction(new EventHandler()
    {
      public void handle(Event t)
      {
        System.err
            .println("Selected date: " + inputRequirementDeadline.getValue());
      }
    });
    inputRequirementDeadline.setPromptText("Set deadline..");

    deadlineContainer.getChildren()
        .addAll(taskDeadline, inputRequirementDeadline);

    // Requirement member list input.
    VBox memberListContainer = new VBox();
    memberListContainer.setPadding(new Insets(0, 10, 0, 10));
    Label membersLabel = new Label("Select members: ");
    GridPane memberSelectContainer = new GridPane();
    memberCheckBoxes = new CheckBox[selectedProject.getTeam().size()];

    for (int i = 0; i < memberCheckBoxes.length; i++)
    {
      memberCheckBoxes[i] = new CheckBox(
          selectedProject.getTeam().get(i).getName());
      memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);
      memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
    }

    // Add member label Node and member selection Node
    memberListContainer.getChildren()
        .addAll(membersLabel, memberSelectContainer);

    VBox layout = new VBox(10);

    closeAndSaveButton.get("addRequirement")
        .setOnAction(new PopupListener(window));

    layout.getChildren()
        .addAll(requirementNameContainer, requirementUserStoryContainer,
            statusContainer, memberListContainer, deadlineContainer,
            closeAndSaveButton.get("addRequirement"), errorLabel);

    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();

  }

  @FXML public void editRequirementClick()
  {
    Stage window = new Stage();
    errorLabel.setText("");
    nameWindow(window, "Edit requirement " + selectedRequirement.getName());

    // Requirement name input.
    VBox requirementNameContainer = textFieldWindowPart(inputRequirementName,
        "New Requirement name: ");

    inputRequirementName.setText(selectedRequirement.getName());

    // Requirement user story input.
    VBox requirementUserStoryContainer = textFieldWindowPart(inputUserStory,
        "User story: ");

    inputUserStory.setText(selectedRequirement.getUserstory());

    // Requirement status input.
    VBox statusContainer = new VBox();
    statusContainer.setPadding(new Insets(10, 10, 0, 10));
    Label status = new Label("Status: ");

    inputStatus = new ComboBox();
    for (int i = 0; i < statusOptions.size(); i++)
    {
      inputStatus.getItems().add(statusOptions.get(i));
    }
    inputStatus.setValue(selectedRequirement.getStatus());
    statusContainer.getChildren().addAll(status, inputStatus);

    // Requirement deadline input.
    VBox deadlineContainer = new VBox();
    deadlineContainer.setPadding(new Insets(10, 10, 0, 10));
    Label taskDeadline = new Label("Deadline:");
    inputRequirementDeadline.setShowWeekNumbers(false);
    final DatePicker datePicker = new DatePicker();
    datePicker.setOnAction(new EventHandler()
    {
      public void handle(Event t)
      {
        LocalDate date = datePicker.getValue();
        System.err.println("Selected date: " + date);
      }
    });
    inputRequirementDeadline.setDayCellFactory(picker -> new DateCell()
    {
      public void updateItem(LocalDate date, boolean empty)
      {
        super.updateItem(date, empty);
        setDisable(empty || date.compareTo(LocalDate.now()) < 1);
      }
    });
    inputRequirementDeadline.setOnAction(new EventHandler()
    {
      public void handle(Event t)
      {
        System.err
            .println("Selected date: " + inputRequirementDeadline.getValue());
      }
    });
    inputRequirementDeadline.setPromptText("Set deadline..");
    inputRequirementDeadline.setValue(selectedRequirement.getDeadline());

    deadlineContainer.getChildren()
        .addAll(taskDeadline, inputRequirementDeadline);

    // Requirement member list input.
    VBox memberListContainer = new VBox();
    memberListContainer.setPadding(new Insets(0, 10, 0, 10));
    Label membersLabel = new Label("Select members: ");
    GridPane memberSelectContainer = new GridPane();
    memberCheckBoxes = new CheckBox[selectedProject.getTeam().size()];

    for (int i = 0; i < memberCheckBoxes.length; i++)
    {
      memberCheckBoxes[i] = new CheckBox(
          selectedProject.getTeam().get(i).getName());
      memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);
      memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
      for (int j = 0; j < selectedRequirement.getTeam().size(); j++)
      {
        if (memberCheckBoxes[i].getText().equals(
            selectedRequirement.getTeam().getMembers().get(j).getName()))
        {
          memberCheckBoxes[i].setSelected(true);
        }
      }
    }

    // Add member label Node and member selection Node
    memberListContainer.getChildren()
        .addAll(membersLabel, memberSelectContainer);

    VBox layout = new VBox(10);

    closeAndSaveButton.get("editRequirement")
        .setOnAction(new PopupListener(window));

    layout.getChildren()
        .addAll(requirementNameContainer, requirementUserStoryContainer,
            statusContainer, memberListContainer, deadlineContainer,
            closeAndSaveButton.get("editRequirement"), errorLabel);

    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  @FXML public void removeRequirementClick()
  {
    if (!(selectedRequirement == null))
    {
      Stage window = new Stage();
      nameWindow(window, "Remove requirement " + selectedRequirement.getName());

      HBox nameContainer = new HBox(2);
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label projectName = new Label(
          "Do you really want to remove: " + selectedRequirement.getName());

      nameContainer.getChildren().addAll(projectName);

      Button closeWithSaveButton = new Button("Save and close");

      Button closeWithOutSaveButton = new Button("Save without closing");

      closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
      {
        public void handle(ActionEvent e)
        {
          {
            window.close();
            String temp = selectedProject.getName();
            finalProjectList.getProject(temp).remove(selectedRequirement);
            adapterProjects.saveProjects(finalProjectList);
            updateRequirementArea();
            selectedRequirement = null;
            requirementDetailsTab.setText("Requirement details");
            requirementDetailsTab.setDisable(true);
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

  /**
   * FXML method to the button which adds a new task
   *
   * @param //args Command line arguments
   */

  @FXML public void addTaskClick()
  {
    Stage window = new Stage();
    errorLabel.setText("");
    nameWindow(window, "Add Task");

    // Task name input.
    VBox taskNameContainer = textFieldWindowPart(inputTaskName, " Task name: ");

    // task estimated hours input.
    VBox taskEstimatedHoursContainer = textFieldWindowPart(
        inputTaskEstimatedHours, "Estimated hours: ");

    //Task status input

    VBox statusContainer = new VBox();
    statusContainer.setPadding(new Insets(10, 10, 0, 10));
    Label status = new Label("Status: ");

    inputStatus = new ComboBox();
    for (int i = 0; i < statusOptions.size(); i++)
    {
      inputStatus.getItems().add(statusOptions.get(i));
    }
    statusContainer.getChildren().addAll(status, inputStatus);

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
        System.err.println("Selected date: " + inputTaskDeadline.getValue());
      }
    });
    inputTaskDeadline.setPromptText("Set deadline..");

    deadlineContainer.getChildren().addAll(taskDeadline, inputTaskDeadline);

    //Task memberList input

    VBox memberListContainer = new VBox();
    memberListContainer.setPadding(new Insets(0, 10, 0, 10));
    Label membersLabel = new Label("Select members: ");
    GridPane memberSelectContainer = new GridPane();
    memberCheckBoxes = new CheckBox[selectedRequirement.getTeam().size()];

    for (int i = 0; i < memberCheckBoxes.length; i++)
    {
      memberCheckBoxes[i] = new CheckBox(
          selectedRequirement.getTeam().get(i).getName());
      memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);
      memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
    }

    // Add member label Node and member selection Node
    memberListContainer.getChildren()
        .addAll(membersLabel, memberSelectContainer);

    VBox layout = new VBox(10);

    closeAndSaveButton.get("addTask").setOnAction(new PopupListener(window));

    layout.getChildren()
        .addAll(taskNameContainer, statusContainer, taskIDContainer,
            memberListContainer, taskEstimatedHoursContainer, deadlineContainer,
            closeAndSaveButton.get("addTask"), errorLabel);

    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  @FXML public void editTaskClick()
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
        inputTaskEstimatedHours, "Estimated hours: ");
    inputTaskEstimatedHours
        .setText(String.valueOf(selectedTask.getEstimatedHours()));

    //Task total hours input

    VBox totalHoursContainer = new VBox();
    totalHoursContainer.setPadding(new Insets(10, 10, 0, 10));
    Label totalHours = new Label("Total hours: ");

    inputTotalHoursWorked = new ComboBox();

    inputTotalHoursWorked.setOnMouseClicked(new EventHandler()
    {
      public void handle(Event t)
      {
        inputTotalHoursWorked.getItems()
            .remove(0, inputTotalHoursWorked.getItems().size());
        for (int i = 0;
             i <= Integer.parseInt(inputTaskEstimatedHours.getText()); i++)
        {
          inputTotalHoursWorked.getItems().add(i);
        }

      }

    });
    totalHoursContainer.getChildren().addAll(totalHours, inputTotalHoursWorked);

    //Task ID input
    VBox taskIDContainer = textFieldWindowPart(inputTaskID, "Task ID: ");
    inputTaskID.setText(selectedTask.getTaskID());

    // Task status input.
    VBox statusContainer = new VBox();
    statusContainer.setPadding(new Insets(10, 10, 0, 10));
    Label status = new Label("Status: ");

    inputStatus = new ComboBox();
    for (int i = 0; i < statusOptions.size(); i++)
    {
      inputStatus.getItems().add(statusOptions.get(i));
    }
    inputStatus.setValue(selectedRequirement.getStatus());
    statusContainer.getChildren().addAll(status, inputStatus);

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

    // Task member list input.
    VBox memberListContainer = new VBox();
    memberListContainer.setPadding(new Insets(0, 10, 0, 10));
    Label membersLabel = new Label("Select members: ");
    GridPane memberSelectContainer = new GridPane();

    memberCheckBoxes = new CheckBox[selectedProject.getTeam().size()];

    for (int i = 0; i < memberCheckBoxes.length; i++)
    {
      memberCheckBoxes[i] = new CheckBox(
          selectedProject.getTeam().get(i).getName());
      memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);
      memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));

      for (int j = 0; j < selectedRequirement.getTeam().size(); j++)
      {
        if (memberCheckBoxes[i].getText()
            .equals(selectedRequirement.getTeam().get(j).getName()))
        {
          memberCheckBoxes[i].setSelected(true);
        }
      }

    }

    // Add member label Node and member selection Node
    memberListContainer.getChildren()
        .addAll(membersLabel, memberSelectContainer);

    VBox layout = new VBox(10);

    closeAndSaveButton.get("editTask").setOnAction(new PopupListener(window));

    layout.getChildren()
        .addAll(taskNameContainer, taskIDContainer, statusContainer,
            memberListContainer, taskEstimatedHoursContainer, totalHoursContainer,
            deadlineContainer, closeAndSaveButton.get("editTask"), errorLabel);

    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  @FXML public void removeTaskClick()
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
            String temp = selectedProject.getName();
            selectedRequirement.getTasks().removeTask(selectedTask);
            finalProjectList.getProject(temp).getRequirements()
                .remove(selectedTask);
            adapterProjects.saveProjects(finalProjectList);
            updateRequirementArea();
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

  /**
   * FXML method to the search TextField.
   * Description missing yet
   *
   * @param //args Command line arguments
   */
  @FXML public void searchClick()
  {
    projectField.getItems().clear();
    if (adapterProjects != null)
    {
      if (searchByName.isSelected())
      {
        ProjectList projects = adapterProjects
            .getProjectByName(searchField.getText());
        for (int i = 0; i < projects.size(); i++)
        {
          projectField.getItems().add(projects.get(i));
        }
      }
      else if (searchByEmployee.isSelected())
      {
        ProjectList projects = adapterProjects
            .getProjectByEmployeeName(searchField.getText());
        for (int i = 0; i < projects.size(); i++)
        {
          projectField.getItems().add(projects.get(i));
        }
      }
    }

  }

  /**
   * Class to handle events from popup windows.
   * Popup windows includes the buttons which adds/edits employees, projects, requirements and tasks.
   *
   * @param
   */
  private class PopupListener implements EventHandler<ActionEvent>
  {

    private Stage window;

    public PopupListener(Stage window)
    {
      this.window = window;
    }

    @Override public void handle(ActionEvent actionEvent)
    {
      if (actionEvent.getSource() == closeAndSaveButton.get("addEmployee"))
      {
        if (!(inputMemberName.getText().isEmpty() || inputMemberName.getText()
            .equals("")))
        {
          window.close();
          Employee employee = new Employee(inputMemberName.getText());
          finalEmployeeList.addMember(employee);
          adapterEmployee.saveMembers(finalEmployeeList);
          updateEmployeeArea();
          updateProjectArea();
        }
        else
        {
          errorLabel.setText("ERROR: invalid member name");
          errorLabel.setTextFill(Color.RED);
        }
      }
      else if (actionEvent.getSource() == closeAndSaveButton
          .get("editEmployee"))
      {
        if (!(inputMemberName.getText().isEmpty() || inputMemberName.getText()
            .equals("")))
        {
          window.close();
          Employee employee = new Employee(inputMemberName.getText());
          ProjectList projects = adapterProjects
              .getProjectByEmployeeName(selectedEmployee.getName());
          for (int i = 0; i < projects.size(); i++)
          {
            finalProjectList.getProject(projects.get(i).getName()).getTeam()
                .replaceMember(selectedEmployee.getName(), employee.getName());
          }
          adapterProjects.saveProjects(finalProjectList);
          finalEmployeeList.getIndexFromName(selectedEmployee.getName());

          finalEmployeeList.get(
              finalEmployeeList.getIndexFromName(selectedEmployee.getName()))
              .setName(inputMemberName.getText());
          adapterProjects.saveProjects(finalProjectList);

          adapterEmployee.saveMembers(finalEmployeeList);

          updateEmployeeArea();
          updateProjectArea();
        }
        else
        {
          errorLabel.setText("ERROR: invalid project name");
          errorLabel.setTextFill(Color.RED);
        }
      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("addProject"))
      {
        selectedMembers = new EmployeeList();
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(finalEmployeeList.get(i));
          }
        }

        if (inputProjectName.getText().isEmpty() || inputProjectName.getText()
            .equals(""))
        {
          errorLabel.setText("ERROR: Fix name");
        }
        else if (selectedMembers.size() == 0)
        {
          errorLabel.setText("ERROR: Fix members");
        }
        else
        {
          window.close();

          Project project = new Project(inputProjectName.getText(),
              selectedMembers);
          finalProjectList.add(project);
          adapterProjects.saveProjects(finalProjectList);
          updateProjectArea();
        }
      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("editProject"))
      {

        // Make team of the new selected members
        selectedMembers = new EmployeeList();
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(finalEmployeeList.get(i));
          }
        }

        //Check for errors

        if (inputProjectName.getText().isEmpty() || inputProjectName.getText()
            .equals(""))
        {
          errorLabel.setText("ERROR: Fix name");
        }
        else if (selectedMembers.size() == 0)
        {
          errorLabel.setText("ERROR: Fix members");
        }
        else
        {

          window.close();

          selectedProject.setName(inputProjectName.getText());
          selectedProject.setTeam(selectedMembers);
          adapterProjects.saveProjects(finalProjectList);
          updateProjectArea();
        }
      }

      else if (actionEvent.getSource() == closeAndSaveButton
          .get("addRequirement"))
      {
        selectedMembers = new EmployeeList();
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(finalEmployeeList.get(i));
          }
        }

        if (inputRequirementName.getText().isEmpty() || inputRequirementName
            .getText().equals(""))
        {
          errorLabel.setText("ERROR: Fix name");
        }
        else if (inputUserStory.getText().isEmpty() || inputUserStory.getText()
            .equals(""))
        {
          errorLabel.setText("ERROR: Fix user story");
        }
        else if (selectedMembers.size() == 0)
        {
          errorLabel.setText("ERROR: Fix members");
        }
        else
        {
          window.close();

          Requirement requirement = new Requirement(
              inputRequirementName.getText(), inputUserStory.getText(),
              inputStatus.getValue(), inputRequirementDeadline.getValue(),
              selectedMembers);
          selectedProject.add(requirement);
          adapterProjects.saveProjects(finalProjectList);
          updateRequirementArea();
        }

      }
      else if (actionEvent.getSource() == closeAndSaveButton
          .get("editRequirement"))
      {
        // Edit new name
        selectedRequirement.setName(inputRequirementName.getText());
        // Edit new userstory
        selectedRequirement.setUserstory(inputUserStory.getText());
        // Edit new status
        selectedRequirement.setStatus(inputStatus.getValue());
        // New EmployeeList object to replace the old one
        selectedMembers = new EmployeeList();
        // Run loop to check which members to add and which to not add
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(selectedProject.getTeam().get(i));
          }
        }
        // Edit new team from selected checkboxes
        selectedRequirement.setTeam(selectedMembers);
        // Edit new deadline
        selectedRequirement.setDeadline(inputRequirementDeadline.getValue());
        // Close window
        window.close();
        // Update GUI table with requirements to show changes
        updateRequirementArea();
        // Save all changes
        adapterProjects.saveProjects(finalProjectList);
        // END of editing requirement
      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("addTask"))
      {

        selectedMembers = new EmployeeList();
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(finalEmployeeList.get(i));
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
        else if (inputTaskEstimatedHours.getText().isEmpty()
            || inputTaskEstimatedHours.getText().equals(""))
        {
          errorLabel.setText("ERROR: Fix estimated hours");
        }
        else if (selectedMembers.size() == 0)
        {
          errorLabel.setText("ERROR: Fix members");
        }
        else
        {
          window.close();

          Task task = new Task(inputTaskName.getText(), inputTaskID.getText(),
              inputStatus.getValue(),
              Integer.parseInt(inputTaskEstimatedHours.getText()),
              inputTaskDeadline.getValue(), selectedMembers);
          selectedRequirement.getTasks().addTask(task);
          adapterProjects.saveProjects(finalProjectList);
          updateTaskArea();
        }

      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("editTask"))
      {
        // Edit new name
        selectedTask.setName(inputTaskName.getText());
        // Edit new ID
        selectedTask.setTaskID(inputTaskID.getText());
        // Edit new status
        selectedTask.setStatus(inputStatus.getValue());
        // New EmployeeList object to replace the old one
        selectedMembers = new EmployeeList();
        // Run loop to check which members to add and which to not add
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(selectedProject.getTeam().get(i));
          }
        }
        // Edit new team from selected checkboxes
        selectedTask.setTaskMembers(selectedMembers);
        // Edit estimated hours
        selectedTask.setEstimatedHours(
            Integer.parseInt(inputTaskEstimatedHours.getText()));
        // Edit total hours
        selectedTask.setTotalHoursWorked(inputTotalHoursWorked.getValue());
        // Edit new deadline
        selectedTask.setDeadline(inputTaskDeadline.getValue());
        // Close window
        window.close();
        // Update GUI table with requirements to show changes
        updateTaskArea();
        updateTaskLabels();
        // Save all changes
        adapterProjects.saveProjects(finalProjectList);
        // END of editing task
      }
    }
  }

}


