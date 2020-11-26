import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdapterGUI extends Application
{

  /**
   * @param window The Stage object that will be displayed
   */
  public void start(Stage window) throws IOException
  {
    window.setTitle("Projects Management System");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("SEPDesign.fxml"));
    Scene scene = new Scene(loader.load());
    window.setScene(scene);
    window.show();
  }
}
