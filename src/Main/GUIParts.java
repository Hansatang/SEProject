package Main;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A interface containing method for creating textFields and default window values.
 *
 * @author Krzysztof Pacierz
 */
public interface GUIParts
{
  void nameWindow(Stage window, String title);
  VBox textFieldWindowPart(TextField inputText, String labelName);
}
