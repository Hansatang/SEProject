import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class Controller
{
  @FXML private RadioButton searchByName, searchByEmployee;
  @FXML private TextField searchField;
  @FXML private Tab projectDetailsTab;

  @FXML private TableView<Member> employeeField;
  @FXML private TableColumn<Member, String> employeeName;

  @FXML private TableView<Project> projectField;
  @FXML private TableColumn<Project, String> projectName;
  @FXML private TableColumn<Project, String> projectTeam;

  // Project JavaFX objects
  TextField inputProjectName = new TextField();
  CheckBox[] memberCheckBoxes;

  // Member JavaFX objects
  TextField inputMemberName = new TextField();

  // General JavaFX objects
  Label errorLabel = new Label("");
  HashMap<String, Button> closeAndSaveButton = new HashMap<>();

  // Adapters
  private ProjectListAdapter adapterProjects;
  private EmployeeListAdapter adapterEmployee;
  private MemberList finalMemberList;

  // Class list Objects
  private MemberList selectedMembers;
  private ProjectList finalProjectList;

  // Selected objects
  private Member selectedMember;
  private Project selectedProject;
  private Requirement selectedRequirement;
  private Task selectedTask;

  /**
   * Runs one time before the GUI is shown
   * @param //args Command line arguments
   */
  public void initialize()
  {
    employeeName
        .setCellValueFactory(new PropertyValueFactory<Member, String>("Name"));
    projectName
        .setCellValueFactory(new PropertyValueFactory<Project, String>("Name"));
    projectTeam
        .setCellValueFactory(new PropertyValueFactory<Project, String>("Team"));
    adapterProjects = new ProjectListAdapter("Projects.bin");
    adapterEmployee = new EmployeeListAdapter("Employees.bin");
    updateEmployeeArea();
    updateProjectArea();
    setSelectedMember();
    setSelectedProject();
    //      updateProjectDetailsArea();
    errorLabel.setTextFill(Color.RED);
    errorLabel.setWrapText(true);
    errorLabel.setPadding(new Insets(0, 0, 50, 0));

    closeAndSaveButton.put("addEmployee", new Button("Add new member"));
    closeAndSaveButton.put("editEmployee", new Button("Save and close"));

    closeAndSaveButton.put("addProject", new Button("Add new project"));
    closeAndSaveButton.put("editProject", new Button("Save and close"));


  }

  /**
   * Method used to select a member with the mouse in the TableView so the member later can be edited or removed.
   * @param //args Command line arguments
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
              selectedMember = employeeField.getItems().get(index);
              System.out.println(selectedMember);
            }
          }
        });
  }

  /**
   * Method used to select a project with the mouse in the TableView so the member later can be edited or removed.
   * @param //args Command line arguments
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
              System.out.println(selectedProject);
            }
          }
        });
  }

  /**
   * Updates the ProjectList objects the TreeView<Project> on the GUI
   * @param //args Command line arguments
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
   * Updates the MemberList objects the TreeView<Member> on the GUI
   * @param //args Command line arguments
   */
  private void updateEmployeeArea()
  {
    employeeField.getItems().clear();
    if (adapterEmployee != null)
    {
      finalMemberList = adapterEmployee.getAllMembers();
      for (int i = 0; i < finalMemberList.size(); i++)
      {
        employeeField.getItems().add(finalMemberList.get(i));
      }
    }
  }

  /**
   * FXML method to the button which add a new employee
   * @param //args Command line arguments
   */
  @FXML public void addEmployeeClick()
  {

    errorLabel.setText("");
    Stage window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Add new member");
    window.setMinWidth(300);

    // Member name input.
    HBox nameContainer = new HBox(2);
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label memberName = new Label("Member name: ");
    inputMemberName.setPromptText("Enter member name");
    nameContainer.getChildren().addAll(memberName, inputMemberName);


    closeAndSaveButton.get("addEmployee").setOnAction(new PopupListener(window));

    VBox layout = new VBox(10);

    layout.getChildren()
        .addAll(nameContainer, errorLabel, closeAndSaveButton.get("addEmployee"));
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setResizable(false);
    window.setScene(scene);
    window.showAndWait();

  }

  /**
   * FXML method to the button which edits a selected employee
   * @param //args Command line arguments
   */
  @FXML public void editEmployeeClick()
  {
    if (!(selectedMember == null))
    {
      Stage window = new Stage();

      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Edit member: " + selectedMember.getName());
      window.setMinWidth(300);

      // Member name input.
      HBox nameContainer = new HBox(2);
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label memberName = new Label("New name: ");
      TextField inputMemberName = new TextField();
      inputMemberName.setText(selectedMember.getName());
      nameContainer.getChildren().addAll(memberName, inputMemberName);

      Label errorMessage = new Label("");

      Button closeWithSaveButton = new Button("Save changes");

      closeAndSaveButton.get("editEmployee").setOnAction(new PopupListener(window));

      closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
      {
        @Override public void handle(ActionEvent e)
        {
          if (!(inputMemberName.getText().isEmpty() || inputMemberName.getText()
              .equals("")))
          {
            window.close();
            Member member = new Member(inputMemberName.getText());
            System.out.println(member.getName());
            finalMemberList.getIndexFromName(selectedMember.getName());
            finalMemberList
                .get(finalMemberList.getIndexFromName(selectedMember.getName()))
                .setName(inputMemberName.getText());
            adapterEmployee.saveMembers(finalMemberList);
            updateEmployeeArea();
            updateProjectArea();
          }
          else
          {
            errorMessage.setText("ERROR: invalid project name");
            errorMessage.setTextFill(Color.RED);
          }
        }
      });

      VBox layout = new VBox(10);

      layout.getChildren()
          .addAll(nameContainer, errorMessage, closeWithSaveButton);
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setResizable(false);
      window.setScene(scene);
      window.showAndWait();

    }

  }

  /**
   * FXML method to the button which removes a selected employee
   * @param //args Command line arguments
   */
  @FXML public void removeEmployeeClick()
  {
    if (!(selectedMember == null))
    {
      Stage window = new Stage();

      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Remove member: " + selectedMember.getName());
      window.setMinWidth(300);

      // Member name input.
      HBox nameContainer = new HBox(2);
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label memberName = new Label(
          "Do you really want to remove: " + selectedMember.getName());

      nameContainer.getChildren().addAll(memberName);

      Label errorMessage = new Label("");

      Button closeWithSaveButton = new Button("Yes, please");

      Button closeWithOutSaveButton = new Button("No, I'm sorry");

      closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
      {
        @Override public void handle(ActionEvent e)
        {
          {
            window.close();
            finalMemberList.removeMember(selectedMember);
            adapterEmployee.saveMembers(finalMemberList);
            updateEmployeeArea();
            selectedMember = null;
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
          .addAll(nameContainer, errorMessage, closeWithSaveButton,
              closeWithOutSaveButton);
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setResizable(false);
      window.setScene(scene);
      window.showAndWait();

    }

  }

  /**
   * FXML method to the button which adds a new project
   * @param //args Command line arguments
   */
  @FXML public void addProjectClick()
  {
    Stage window = new Stage();
    errorLabel.setText("");

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Add new project");
    window.setMinWidth(300);

    // Project name input.
    VBox nameContainer = new VBox();
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label projectName = new Label("Project name: ");
    inputProjectName = new TextField();
    inputProjectName.setPromptText("Enter project name");
    nameContainer.getChildren().addAll(projectName, inputProjectName);

    // Project member list input.
    VBox memberListContainer = new VBox();
    memberListContainer.setPadding(new Insets(0, 10, 0, 10));
    Label membersLabel = new Label("Select members: ");
    GridPane memberSelectContainer = new GridPane();
    memberCheckBoxes = new CheckBox[finalMemberList.size()];

    for (int i = 0; i < memberCheckBoxes.length; i++)
    {
      memberCheckBoxes[i] = new CheckBox(finalMemberList.get(i).getName());
      memberSelectContainer.add(memberCheckBoxes[i], i % 2, i / 2);
      memberCheckBoxes[i].setPadding(new Insets(3, 50, 3, 3));
    }

    // Add member label Node and member selection Node
    memberListContainer.getChildren()
        .addAll(membersLabel, memberSelectContainer);

    // Config save and close botton
    closeAndSaveButton.get("addProject").setOnAction(new PopupListener(window));

    VBox layout = new VBox(10);

    layout.getChildren()
        .addAll(nameContainer, memberListContainer, closeAndSaveButton.get("addProject"),
            errorLabel);

    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setResizable(false);
    window.setScene(scene);
    window.showAndWait();

  }

  /**
   * FXML method to the button which edits a selected project
   * @param //args Command line arguments
   */
  @FXML public void editProjectClick()
  {
    if (!(selectedProject == null))
    {
      Stage window = new Stage();
      errorLabel.setText("");

      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Edit project");
      window.setMinWidth(300);

      // Project name input.
      VBox nameContainer = new VBox();
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label projectName = new Label("Project name: ");
      inputProjectName = new TextField();
      inputProjectName.setText(selectedProject.getName());
      nameContainer.getChildren().addAll(projectName, inputProjectName);

      // Project member list input.
      VBox memberListContainer = new VBox();
      memberListContainer.setPadding(new Insets(0, 10, 0, 10));
      Label membersLabel = new Label("Select members: ");
      GridPane memberSelectContainer = new GridPane();
      memberCheckBoxes = new CheckBox[finalMemberList.size()];

      for (int i = 0; i < memberCheckBoxes.length; i++)
      {
        memberCheckBoxes[i] = new CheckBox(finalMemberList.get(i).getName());
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

      closeAndSaveButton.get("editProject").setOnAction(new PopupListener(window));

      VBox layout = new VBox(10);

      layout.getChildren().addAll(
              nameContainer,
              memberListContainer,
              closeAndSaveButton.get("editProject"),
              errorLabel);

      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setResizable(false);
      window.setScene(scene);
      window.showAndWait();
    }
  }

  /**
   * FXML method to the button which removes a selected project
   * @param //args Command line arguments
   */
  @FXML public void removeProjectClick()
  {
    if (!(selectedProject == null))
    {
      Stage window = new Stage();

      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Remove Project: " + selectedProject.getName());
      window.setMinWidth(300);

      // Project name input.
      HBox nameContainer = new HBox(2);
      nameContainer.setPadding(new Insets(10, 10, 0, 10));
      Label projectName = new Label(
          "Do you really want to remove: " + selectedProject.getName());

      nameContainer.getChildren().addAll(projectName);

      Button closeWithSaveButton = new Button("Yes, please");

      Button closeWithOutSaveButton = new Button("No, I'm sorry");

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

      layout.getChildren()
          .addAll(nameContainer, errorLabel, closeWithSaveButton,
              closeWithOutSaveButton);
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      window.setResizable(false);
      window.setScene(scene);
      window.showAndWait();

    }

  }

  /**
   * FXML method to the search TextField.
   *  Description missing yet
   * @param //args Command line arguments
   */
  @FXML public void search()
  {
    if (searchField.getText().equals("") || searchField.getText() == null)
    {
      updateProjectArea();
    }
  }

  @FXML public void searchClick()
  {
    if (searchByName.isSelected())
    {
      projectField.getItems().clear();
      if (adapterProjects != null)
      {
        ProjectList projects = adapterProjects
            .getProjectByName(searchField.getText());
        for (int i = 0; i < projects.size(); i++)
        {
          projectField.getItems().add(projects.get(i));
        }
      }
    }
    if (searchByEmployee.isSelected())
    {
      projectField.getItems().clear();
      if (adapterProjects != null)
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
      if (actionEvent.getSource() == closeAndSaveButton.get("addEmployee")){
        if (!(inputMemberName.getText().isEmpty() || inputMemberName.getText()
                .equals("")))
        {
          window.close();
          Member member = new Member(inputMemberName.getText());
          finalMemberList.addMember(member);
          adapterEmployee.saveMembers(finalMemberList);
          updateEmployeeArea();
          updateProjectArea();
        }
        else
        {
          errorLabel.setText("ERROR: invalid member name");
          errorLabel.setTextFill(Color.RED);
        }
      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("editEmployee")){
        if (!(inputMemberName.getText().isEmpty() || inputMemberName.getText()
                .equals("")))
        {
          window.close();
          Member member = new Member(inputMemberName.getText());
          System.out.println(member.getName());
          finalMemberList.getIndexFromName(selectedMember.getName());
          finalMemberList
                  .get(finalMemberList.getIndexFromName(selectedMember.getName()))
                  .setName(inputMemberName.getText());
          adapterEmployee.saveMembers(finalMemberList);
          updateEmployeeArea();
          updateProjectArea();
        }
        else
        {
          errorLabel.setText("ERROR: invalid project name");
          errorLabel.setTextFill(Color.RED);
        }
      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("addProject")){
        selectedMembers = new MemberList();
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(finalMemberList.get(i));
            System.out.println(
                    "Member " + finalMemberList.get(i) + " has been added to "
                            + inputProjectName.getText());
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
          System.out.println("Added project " + project);
          updateProjectArea();
        }
      }
      else if (actionEvent.getSource() == closeAndSaveButton.get("editProject")) {

        // Make team of the new selected members
        selectedMembers = new MemberList();
        for (int i = 0; i < memberCheckBoxes.length; i++)
        {
          if (memberCheckBoxes[i].isSelected())
          {
            selectedMembers.addMember(finalMemberList.get(i));
            System.out.println(
                    "Member " + finalMemberList.get(i) + " has been added to "
                            + inputProjectName.getText());
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
        } else {

          window.close();

          selectedProject.setName(inputProjectName.getText());
          selectedProject.setTeam(selectedMembers);
          adapterProjects.saveProjects(finalProjectList);
          updateProjectArea();
        }
      }
    }
  }
}


