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

public class Controller
{
  @FXML private TableView<Member> employeeField;
  @FXML private TableColumn<Member, String> employeeName;

  // Project JavaFX objects
  TextField inputProjectName = new TextField();
  CheckBox[] memberCheckBoxes;
  Label nameErrorMessage = new Label("");
  Button closeWithSaveButtonProject = new Button("Add new project");


  private ProjectListAdapter adapterProjects;
  private EmployeeListAdapter adapterEmployee;
  private MemberList finalMemberList;

  // Project Objects
  private MemberList selectedMembers;
  private ProjectList finalProjectList;

  private Member selectedMember;
  private Project selectedProject;
  private Requirement selectedRequirement;
  private Task selectedTask;

  public void initialize()
  {
    employeeName
        .setCellValueFactory(new PropertyValueFactory<Member, String>("Name"));
    adapterProjects = new ProjectListAdapter("Projects.bin");
    adapterEmployee = new EmployeeListAdapter("Employees.bin");
    updateEmployeeArea();
    updateProjectArea();
    setSelectedMember();
    //      updateProjectDetailsArea();
  }

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

  private void updateProjectArea()
  {
    if (adapterProjects != null)
    {
      ProjectList projects = adapterProjects.getAllProjects();

    }
  }

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

  @FXML public void addEmployeeClick()
  {
    Stage window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Add new member");
    window.setMinWidth(300);

    // Member name input.
    HBox nameContainer = new HBox(2);
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label memberName = new Label("Member name: ");
    TextField inputMemberName = new TextField();
    inputMemberName.setPromptText("Enter member name");
    nameContainer.getChildren().addAll(memberName, inputMemberName);

    Label errorMessage = new Label("");

    Button closeWithSaveButton = new Button("Add new member");

    closeWithSaveButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override public void handle(ActionEvent e)
      {
        if (!(inputMemberName.getText().isEmpty() || inputMemberName.getText()
            .equals("")))
        {
          window.close();
          Member member = new Member(inputMemberName.getText());
          finalMemberList.addMember(member);
          System.out.println("A");
          adapterEmployee.saveMembers(finalMemberList);
          System.out.println("B");
          updateEmployeeArea();
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
            System.out.println("A");
            adapterEmployee.saveMembers(finalMemberList);
            System.out.println("B");
            updateEmployeeArea();
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

  @FXML public void removeEmployeeClick()
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
            System.out.println("A");
            adapterEmployee.saveMembers(finalMemberList);
            System.out.println("B");
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

  @FXML public void addProjectClick()
  {
    Stage window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Add new project");
    window.setMinWidth(300);

    // Project name input.
    HBox nameContainer = new HBox(2);
    nameContainer.setPadding(new Insets(10, 10, 0, 10));
    Label projectName = new Label("Project name: ");
    inputProjectName = new TextField();
    inputProjectName.setPromptText("Enter project name");
    nameContainer.getChildren().addAll(projectName, inputProjectName);

    nameErrorMessage = new Label("");



    HBox memberContainer = new HBox(2);

    Label membersName = new Label("Members: ");
    GridPane memberNameContainer = new GridPane();
    memberNameContainer.setPadding(new Insets(10, 10, 0, 10));

    memberCheckBoxes = new CheckBox[finalMemberList.size()];
    selectedMembers = new MemberList();

    for(int i = 0 ; i < memberCheckBoxes.length ; i++){
      memberCheckBoxes[i] = new CheckBox(finalMemberList.get(i).getName());
      memberNameContainer.add(memberCheckBoxes[i], i%2, i/2);
    }

    closeWithSaveButtonProject.setOnAction(new PopupListener(window));

    VBox layout = new VBox(10);

    layout.getChildren()
            .addAll(nameContainer, nameErrorMessage, membersName, memberContainer, closeWithSaveButtonProject);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setResizable(false);
    window.setScene(scene);
    window.showAndWait();

  }

  @FXML public void editProjectClick()
  {

  }

  @FXML public void removeProjectClick()
  {

  }

  @FXML public void search()
  {

  }

  @FXML public void searchClick()
  {

  }

  private class PopupListener implements EventHandler<ActionEvent>{

    private Stage window;

    public PopupListener(Stage window){
      this.window = window;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
      if(actionEvent.getSource() == closeWithSaveButtonProject ){
        if (!(inputProjectName.getText().isEmpty() || inputProjectName.getText()
                .equals("")))
        {
          window.close();
          Project project = new Project(inputProjectName.getText(), selectedMembers);
          finalProjectList = new ProjectList();
          finalProjectList.add(project);
          adapterProjects.saveProjects(finalProjectList);
          System.out.println("Added project " + project);
          updateProjectArea();
        }
        else
        {
          nameErrorMessage.setText("ERROR: invalid project name");
          nameErrorMessage.setTextFill(Color.RED);
        }
      }
    }
  }

  private class Listener implements EventHandler<ActionEvent>{

    @Override
    public void handle(ActionEvent actionEvent) {

    }
  }

}


