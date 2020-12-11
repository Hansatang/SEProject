package Requirement;

import Employee.EmployeeList;
import Employee.EmployeeListAdapter;
import Main.AdapterGUI;
import Project.ProjectListTab;
import Project.Project;
import Project.ProjectList;
import Project.ProjectListAdapter;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class RequirementListTab extends Tab
{

  private VBox tabRequirement;

  private TableView<Requirement> requirementTableView;
  private TableView.TableViewSelectionModel<Requirement> defaultSelectionModel;
  private TableColumn<Requirement, String> requirementName;
  private TableColumn<Requirement, String> requirementStatus;
  private TableColumn<Requirement, String> requirementDeadline;

  private Button addRequirement, editRequirement, removeRequirement;

  private MyActionListener listener;

  private Requirement selectedRequirement;
  private Project selectedProject;

  private ProjectListTab projectListTab;
  private AdapterGUI adapterGUI;

  private EmployeeListAdapter adapterEmployee;
  private ProjectListAdapter adapterProject;

  private ProjectList finalProjectList;
  private EmployeeList finalEmployeeList;

  Label errorLabel = new Label("");

  private Label requirementNameLabel = new Label();
  private Label requirementStatusLabel = new Label();
  private Label requirementTeamLabel = new Label();
  private Label requirementDeadlineLabel = new Label();
  private TextArea requirementUserStoryLabel = new TextArea();
  private Label requirementEstimatedLabel = new Label();
  private Label requirementHoursWorkedLabel = new Label();

  // Requirement.Requirement JavaFX objects
  TextField inputRequirementName = new TextField();
  TextField inputUserStory = new TextField();
  ComboBox<String> inputTaskStatus = new ComboBox<>();
  DatePicker inputRequirementDeadline = new DatePicker();
  VBox requirementInfoContainer;

  private final ArrayList<String> statusOptions = new ArrayList<>(
      Arrays.asList("Approved", "Ended", "Not Started", "Rejected", "Started"));

  private final String name = "Name";
  private final String status = "Status";
  private final String team = "Team";
  private final String deadline = "Deadline";
  private final String userStory = "Userstory";
  private final String estimatedHours = "Estimated worked hours";
  private final String totalWorkedHours = "Total worked hours";

  private final String requirementNameString = "Requirement name: ";
  private final String selectedDate = "Selected date: ";
  private final String selectDate = "Select date...";
  private final String getSelectEmployeesString = "Select employees: ";

  private final String addRequirementString = "Add requirement ";
  private final String editRequirementString = "Edit requirement ";
  private final String removeRequirementString = "Remove requirement ";
  private final String removeString = "Remove requirement";
  private final String confirmRemoveString = "Do you really want to remove: ";
  private final String saveAndCloseString = "Save and close";
  private final String notSaveAndCloseString = "Save without closing";

  private final String noTaskMessage = "No tasks in this requirement";
  private final String selectEmployeeString = "Select employee";
  private final String newRequirementName = "New Requirement name: ";

  private final String errorDeadlineString = "ERROR: Fix deadline";
  private final String errorName = "ERROR: Fix name";
  private final String errorUserStory = "ERROR: Fix user story";
  private final String errorEmployees = "ERROR: Fix employees";

  public RequirementListTab(String title, ProjectListAdapter adapterProjects,
      EmployeeListAdapter adapterEmployees, ProjectListTab projectListTab,
      AdapterGUI adapterGUI)
  {
    super(title);

    this.adapterGUI = adapterGUI;
    this.projectListTab = projectListTab;
    this.adapterProject = adapterProjects;
    this.adapterEmployee = adapterEmployees;

    listener = new MyActionListener();

    requirementTableView = new TableView<>();
    defaultSelectionModel = requirementTableView.getSelectionModel();
    requirementTableView.setPrefHeight(597);

    requirementName = new TableColumn<>(name);
    requirementName.setCellValueFactory(new PropertyValueFactory<>(name));
    requirementName.setPrefWidth(199);

    requirementTableView.getColumns().add(requirementName);

    requirementStatus = new TableColumn<>(status);
    requirementStatus.setCellValueFactory(new PropertyValueFactory<>(status));
    requirementStatus.setPrefWidth(199);
    requirementTableView.getColumns().add(requirementStatus);

    requirementDeadline = new TableColumn<>(deadline);
    requirementDeadline
        .setCellValueFactory(new PropertyValueFactory<>(deadline));
    requirementDeadline.setPrefWidth(199);
    requirementTableView.getColumns().add(requirementDeadline);

    requirementInfoContainer = new VBox();

    Label infoLabel = new Label(name);
    infoLabel.setPrefWidth(150);
    HBox infoBox = new HBox(infoLabel, requirementNameLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(status);
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementStatusLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(team);
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementTeamLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(deadline);
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementDeadlineLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(estimatedHours);
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementEstimatedLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(totalWorkedHours);
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementHoursWorkedLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label(userStory);
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementUserStoryLabel);
    requirementInfoContainer.getChildren().add(infoBox);

    addRequirement = new Button(addRequirementString);
    addRequirement.setOnAction(listener);

    editRequirement = new Button(editRequirementString);
    editRequirement.setOnAction(listener);

    removeRequirement = new Button(removeRequirementString);
    removeRequirement.setOnAction(listener);

    HBox buttonContainer = new HBox(addRequirement, editRequirement,
        removeRequirement);
    buttonContainer.setPrefHeight(30);
    buttonContainer.setSpacing(50);
    buttonContainer.setPadding(new Insets(0, 10, 10, 10));
    buttonContainer.setAlignment(Pos.CENTER);

    tabRequirement = new VBox(10);
    tabRequirement.setAlignment(Pos.CENTER);
    tabRequirement.getChildren().add(requirementTableView);
    tabRequirement.getChildren().add(requirementInfoContainer);
    tabRequirement.getChildren().add(buttonContainer);

    super.setContent(tabRequirement);

    setSelectedRequirement();
  }

  public Requirement getSelectedRequirement()
  {
    return selectedRequirement;
  }

  public Project getSelectedProject()
  {
    return selectedProject;
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
    Label status = new Label("Status");

    inputTaskStatus = new ComboBox();
    for (String statusOption : statusOptions)
    {
      inputTaskStatus.getItems().add(statusOption);
    }
    inputTaskStatus.getSelectionModel().select(0);
    statusContainer.getChildren().addAll(status, inputTaskStatus);

    return statusContainer;
  }

  public void setSelectedProject(Project projectSelected)
  {
    selectedProject = projectSelected;
    updateRequirementArea();
  }

  private void setSelectedRequirement()
  {
    requirementTableView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener()
        {
          public void changed(ObservableValue observableValue, Object oldValue,
              Object newValue)
          {
            if (requirementTableView.getSelectionModel().getSelectedItem()
                != null)
            {
              int index = requirementTableView.getSelectionModel()
                  .getSelectedIndex();

              selectedRequirement = requirementTableView.getItems().get(index);
              adapterGUI.changeTaskTabTitle(selectedRequirement);
              updateRequirementLabels();
            }
          }
        });
  }

  public void updateRequirementArea()
  {
    requirementTableView.getItems().clear();
    finalProjectList = adapterProject.getAllProjects();
    if (finalProjectList.getProjectByName(selectedProject.getName()) != null)
    {
      for (int i = 0;
           i < finalProjectList.getProjectByName(selectedProject.getName())
               .getRequirements().size(); i++)
      {
        requirementTableView.getItems().add(
            finalProjectList.getProjectByName(selectedProject.getName())
                .getRequirements().getRequirement(i));
      }
    }
  }

  private void updateRequirementLabels()
  {
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
        requirementEstimatedLabel.setText(noTaskMessage);
        requirementEstimatedLabel.setTextFill(Color.RED);
        requirementHoursWorkedLabel.setText(noTaskMessage);
        requirementHoursWorkedLabel.setTextFill(Color.RED);
      }
      requirementUserStoryLabel.setText(selectedRequirement.getUserstory());
    }
  }

  private class MyActionListener implements EventHandler<ActionEvent>
  {
    public void handle(ActionEvent e)
    {
      if (e.getSource() == addRequirement)
      {
        {
          Stage window = new Stage();
          errorLabel.setText("");
          nameWindow(window, addRequirementString);

          // Requirement.Requirement name input.
          VBox requirementNameContainer = textFieldWindowPart(
              inputRequirementName, requirementNameString);

          // Requirement.Requirement user story input.
          VBox requirementUserStoryContainer = textFieldWindowPart(
              inputUserStory, userStory);

          // Requirement.Requirement status input.
          VBox statusContainer = statusComboBoxWindowPart();

          // Requirement.Requirement deadline input.
          VBox deadlineContainer = new VBox();
          deadlineContainer.setPadding(new Insets(10, 10, 0, 10));
          Label taskDeadline = new Label(deadline);
          inputRequirementDeadline.setShowWeekNumbers(false);
          inputRequirementDeadline.setValue(null);
          final DatePicker datePicker = new DatePicker();
          datePicker.setOnAction(new EventHandler()
          {
            public void handle(Event t)
            {
              LocalDate date = datePicker.getValue();
              System.err.println(selectedDate + date);
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
                  .println(selectedDate + inputRequirementDeadline.getValue());
            }
          });
          inputRequirementDeadline.setPromptText(selectDate);

          deadlineContainer.getChildren()
              .addAll(taskDeadline, inputRequirementDeadline);

          // Requirement.Requirement employee list input.
          VBox employeeListContainer = new VBox();
          employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
          Label employeesLabel = new Label(selectEmployeeString);
          GridPane employeeSelectContainer = new GridPane();
          CheckBox[] employeeCheckBoxes = new CheckBox[finalProjectList
              .getProjectByName(selectedProject.getName()).getTeam().size()];

          for (int i = 0; i < employeeCheckBoxes.length; i++)
          {
            employeeCheckBoxes[i] = new CheckBox(
                selectedProject.getTeam().get(i).getName());
            employeeSelectContainer.add(employeeCheckBoxes[i], i % 2, i / 2);
            employeeCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
          }

          // Add employee label Node and employee selection Node
          employeeListContainer.getChildren()
              .addAll(employeesLabel, employeeSelectContainer);

          VBox layout = new VBox(10);

          Button closeWithSaveButton = new Button(saveAndCloseString);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              EmployeeList selectedEmployees = new EmployeeList();
              finalEmployeeList = adapterEmployee.getAllEmployees();
              for (int i = 0; i < employeeCheckBoxes.length; i++)
              {
                if (employeeCheckBoxes[i].isSelected())
                {
                  selectedEmployees.addEmployee(finalEmployeeList.get(i));
                }
              }
              if (inputRequirementName.getText().isEmpty()
                  || inputRequirementName.getText().equals(""))
              {
                errorLabel.setText(errorName);
              }
              else if (inputUserStory.getText().isEmpty() || inputUserStory
                  .getText().equals(""))
              {
                errorLabel.setText(errorUserStory);
              }
              else if (selectedEmployees.size() == 0)
              {
                errorLabel.setText(errorEmployees);
              }
              else if (inputRequirementDeadline.getValue() == null)
              {
                errorLabel.setText(errorDeadlineString);
              }
              else
              {
                window.close();
                Requirement requirement = new Requirement(
                    inputRequirementName.getText(), inputUserStory.getText(),
                    inputTaskStatus.getValue(),
                    inputRequirementDeadline.getValue(), selectedEmployees);
                finalProjectList.getProjectByName(selectedProject.getName())
                    .getRequirements().addRequirement(requirement);
                adapterProject.saveProjects(finalProjectList);
                updateRequirementArea();
              }
            }
          });

          layout.getChildren()
              .addAll(requirementNameContainer, requirementUserStoryContainer,
                  statusContainer, employeeListContainer, deadlineContainer,
                  closeWithSaveButton, errorLabel);

          layout.setAlignment(Pos.CENTER);
          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        }
      }
      else if (e.getSource() == editRequirement)
      {
        if (!(selectedRequirement == null))
        {
          Stage window = new Stage();
          errorLabel.setText("");
          nameWindow(window,
              editRequirementString + selectedRequirement.getName());

          // Requirement.Requirement name input.
          VBox requirementNameContainer = textFieldWindowPart(
              inputRequirementName, newRequirementName);

          inputRequirementName.setText(selectedRequirement.getName());

          // Requirement.Requirement user story input.
          VBox requirementUserStoryContainer = textFieldWindowPart(
              inputUserStory, userStory);

          inputUserStory.setText(selectedRequirement.getUserstory());

          // Requirement.Requirement status input.
          VBox statusContainer = statusComboBoxWindowPart();
          inputTaskStatus.setValue(selectedRequirement.getStatus());

          // Requirement.Requirement deadline input.
          VBox deadlineContainer = new VBox();
          deadlineContainer.setPadding(new Insets(10, 10, 0, 10));
          Label taskDeadline = new Label(deadline);
          inputRequirementDeadline.setShowWeekNumbers(false);
          final DatePicker datePicker = new DatePicker();
          datePicker.setOnAction(new EventHandler()
          {
            public void handle(Event t)
            {
              LocalDate date = datePicker.getValue();
              System.err.println(selectedDate + date);
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
          inputRequirementDeadline.setPromptText(selectDate);
          inputRequirementDeadline.setValue(selectedRequirement.getDeadline());

          deadlineContainer.getChildren()
              .addAll(taskDeadline, inputRequirementDeadline);

          // Requirement.Requirement employee list input.
          VBox employeeListContainer = new VBox();
          employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
          Label employeesLabel = new Label(getSelectEmployeesString);
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
              if (employeeCheckBoxes[i].getText().equals(
                  selectedRequirement.getTeam().getEmployees().get(j)
                      .getName()))
              {
                employeeCheckBoxes[i].setSelected(true);
              }
            }
          }

          // Add employee label Node and employee selection Node
          employeeListContainer.getChildren()
              .addAll(employeesLabel, employeeSelectContainer);

          VBox layout = new VBox(10);

          Button closeWithSaveButton = new Button("Save and Close");

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override public void handle(ActionEvent e)
            {
              // Edit new name
              selectedRequirement.setName(inputRequirementName.getText());
              // Edit new user story
              selectedRequirement.setUserstory(inputUserStory.getText());
              // Edit new status
              selectedRequirement.setStatus(inputTaskStatus.getValue());
              // New Employee.EmployeeList object to replace the old one
              EmployeeList selectedEmployees = new EmployeeList();
              // Run loop to check which employees to add and which to not add
              for (int i = 0; i < employeeCheckBoxes.length; i++)
              {
                if (employeeCheckBoxes[i].isSelected())
                {
                  selectedEmployees
                      .addEmployee(selectedProject.getTeam().get(i));
                }
              }
              // Edit new team from selected checkboxes
              selectedRequirement.setTeam(selectedEmployees);
              // Edit new deadline
              selectedRequirement
                  .setDeadline(inputRequirementDeadline.getValue());
              // Close window
              window.close();
              // Save all changes
              adapterProject.saveProjects(finalProjectList);
              // Update GUI table with requirements to show changes
              updateRequirementArea();
              adapterGUI.changeTaskTabTitle(selectedRequirement);
              updateRequirementLabels();
              // END of editing requirement
            }
          });

          layout.getChildren()
              .addAll(requirementNameContainer, requirementUserStoryContainer,
                  statusContainer, employeeListContainer, deadlineContainer,
                  closeWithSaveButton, errorLabel);
          layout.setAlignment(Pos.CENTER);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();
        }
      }

      else if (e.getSource() == removeRequirement)
      {
        if (!(selectedRequirement == null))
        {
          Stage window = new Stage();
          nameWindow(window, removeString + selectedRequirement.getName());

          HBox nameContainer = new HBox(2);
          nameContainer.setPadding(new Insets(10, 10, 0, 10));
          Label projectName = new Label(
              confirmRemoveString + selectedRequirement.getName());

          nameContainer.getChildren().addAll(projectName);

          Button closeWithSaveButton = new Button(saveAndCloseString);

          Button closeWithOutSaveButton = new Button(notSaveAndCloseString);

          closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
          {
            public void handle(ActionEvent e)
            {
              {
                window.close();
                finalProjectList.getProjectByName(selectedProject.getName())
                    .remove(selectedRequirement);
                adapterProject.saveProjects(finalProjectList);
                updateRequirementArea();
                adapterGUI.closeTaskTabTitle();
                selectedRequirement = null;
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
