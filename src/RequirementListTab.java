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

public class RequirementListTab extends Tab
{
  private String title;

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
  private ProjectListAdapter adapterProject = new ProjectListAdapter(
      "Projects.bin");

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

  // Requirement JavaFX objects
  TextField inputRequirementName = new TextField();
  TextField inputUserStory = new TextField();
  ComboBox<String> inputTaskStatus = new ComboBox<>();
  DatePicker inputRequirementDeadline = new DatePicker();
  VBox requirementInfoContainer;

  private final ArrayList<String> statusOptions = new ArrayList<>();

  public RequirementListTab(String title, ProjectListAdapter adapterProject,
      EmployeeListAdapter adapterEmployees, ProjectListTab projectListTab,
      AdapterGUI adapterGUI)
  {
    super(title);

    this.adapterGUI = adapterGUI;
    this.projectListTab = projectListTab;
    this.adapterProject = adapterProject;
    this.adapterEmployee = adapterEmployees;
    finalProjectList = adapterProject.getAllProjects();
    finalEmployeeList = adapterEmployee.getAllEmployees();
    statusOptions.add("Approved");
    statusOptions.add("Ended");
    statusOptions.add("Not Started");
    statusOptions.add("Rejected");
    statusOptions.add("Started");

    listener = new MyActionListener();

    requirementTableView = new TableView<>();
    defaultSelectionModel = requirementTableView.getSelectionModel();
    requirementTableView.setPrefHeight(597);

    requirementName = new TableColumn<>(" Name");
    requirementName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    requirementName.setPrefWidth(199);

    requirementTableView.getColumns().add(requirementName);

    requirementStatus = new TableColumn<>(" Status");
    requirementStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    requirementStatus.setPrefWidth(199);

    requirementTableView.getColumns().add(requirementStatus);

    requirementDeadline = new TableColumn<>(" Deadline");
    requirementDeadline.setCellValueFactory(
        new PropertyValueFactory<>("Deadline"));
    requirementDeadline.setPrefWidth(199);

    requirementTableView.getColumns().add(requirementDeadline);

    requirementInfoContainer = new VBox();

    Label infoLabel = new Label("Name:");
    infoLabel.setPrefWidth(150);
    HBox infoBox = new HBox(infoLabel, requirementNameLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Status:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementStatusLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Team:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementTeamLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Deadline::");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementDeadlineLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Estimated time:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementEstimatedLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("Total hours:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementHoursWorkedLabel);
    requirementInfoContainer.getChildren().add(infoBox);
    infoLabel = new Label("User story:");
    infoLabel.setPrefWidth(150);
    infoBox = new HBox(infoLabel, requirementUserStoryLabel);
    requirementInfoContainer.getChildren().add(infoBox);

    addRequirement = new Button("Add requirement");
    addRequirement.setOnAction(listener);

    editRequirement = new Button("Edit requirement");
    editRequirement.setOnAction(listener);

    removeRequirement = new Button("Remove requirement");
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

    finalEmployeeList = adapterEmployee.getAllEmployees();

    if (selectedRequirement != null)
    {
      updateRequirementArea();
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

  public void setSelectedProject(Project selectedProject1)
  {
    selectedProject = selectedProject1;
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
    if (selectedProject != null)
    {
      if (adapterProject != null)
      {
        finalProjectList = adapterProject.getAllProjects();
        if (finalProjectList.getProjectByName(selectedProject.getName())
            != null)
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
        requirementEstimatedLabel.setText("No tasks in this requirement");
        requirementEstimatedLabel.setTextFill(Color.RED);
        requirementHoursWorkedLabel.setText("No tasks in this requirement");
        requirementHoursWorkedLabel.setTextFill(Color.RED);
      }
      requirementUserStoryLabel.setText(selectedRequirement.getUserstory());
    }
  }

  public Requirement getSelectedRequirement()
  {
    System.out.println("Yay");
    return selectedRequirement;
  }

  public Project getSelectedProject()
  {
    System.out.println("Yay");
    return selectedProject;
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
          nameWindow(window, "Add requirement");

          // Requirement name input.
          VBox requirementNameContainer = textFieldWindowPart(
              inputRequirementName, " Requirement name: ");

          // Requirement user story input.
          VBox requirementUserStoryContainer = textFieldWindowPart(
              inputUserStory, "User story: ");

          // Requirement status input.
          VBox statusContainer = statusComboBoxWindowPart();

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
              System.err.println(
                  "Selected date: " + inputRequirementDeadline.getValue());
            }
          });
          inputRequirementDeadline.setPromptText("Set deadline..");

          deadlineContainer.getChildren()
              .addAll(taskDeadline, inputRequirementDeadline);

          // Requirement employee list input.
          VBox employeeListContainer = new VBox();
          employeeListContainer.setPadding(new Insets(0, 10, 0, 10));
          Label employeesLabel = new Label("Select employees: ");
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

              if (inputRequirementName.getText().isEmpty()
                  || inputRequirementName.getText().equals(""))
              {
                errorLabel.setText("ERROR: Fix name");
              }
              else if (inputUserStory.getText().isEmpty() || inputUserStory
                  .getText().equals(""))
              {
                errorLabel.setText("ERROR: Fix user story");
              }
              else if (selectedEmployees.size() == 0)
              {
                errorLabel.setText("ERROR: Fix employees");
              }
              else if (inputRequirementDeadline.getValue() == null)
              {
                errorLabel.setText("ERROR: Fix deadline");
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
        Stage window = new Stage();
        errorLabel.setText("");
        nameWindow(window, "Edit requirement " + selectedRequirement.getName());

        // Requirement name input.
        VBox requirementNameContainer = textFieldWindowPart(
            inputRequirementName, "New Requirement name: ");

        inputRequirementName.setText(selectedRequirement.getName());

        // Requirement user story input.
        VBox requirementUserStoryContainer = textFieldWindowPart(inputUserStory,
            "User story: ");

        inputUserStory.setText(selectedRequirement.getUserstory());

        // Requirement status input.
        VBox statusContainer = statusComboBoxWindowPart();
        inputTaskStatus.setValue(selectedRequirement.getStatus());

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
            System.err.println(
                "Selected date: " + inputRequirementDeadline.getValue());
          }
        });
        inputRequirementDeadline.setPromptText("Set deadline..");
        inputRequirementDeadline.setValue(selectedRequirement.getDeadline());

        deadlineContainer.getChildren()
            .addAll(taskDeadline, inputRequirementDeadline);

        // Requirement employee list input.
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
            if (employeeCheckBoxes[i].getText().equals(
                selectedRequirement.getTeam().getEmployees().get(j).getName()))
            {
              employeeCheckBoxes[i].setSelected(true);
            }
          }
        }
        // Add employee label Node and employee selection Node
        employeeListContainer.getChildren()
            .addAll(employeesLabel, employeeSelectContainer);

        VBox layout = new VBox(10);

        Button closeWithSaveButton = new Button("Add new project");

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

      else if (e.getSource() == removeRequirement)
      {
        if (!(selectedRequirement == null))
        {
          Stage window = new Stage();
          nameWindow(window,
              "Remove requirement " + selectedRequirement.getName());

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
