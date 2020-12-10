import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdapterGUI extends Application
{

  /**
   * @param window The Stage object that will be displayed
   */
  public void start(Stage window) throws IOException
  {
    // Set title of window
    window.setTitle("Projects Management System");

    // Initialise FXMLoader and load the .FXML file type.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("filesFXML/SEPDesign.fxml"));

    // Initialise Scene object.
    Scene scene = new Scene(loader.load());

    // Set scene of window and show window.
    window.setScene(scene);
    window.show();
  }
}
