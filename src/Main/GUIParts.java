package Main;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface GUIParts
{
  public void nameWindow(Stage window, String title);
  public VBox textFieldWindowPart(TextField inputText, String labelName);
}
