import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));

        Scene mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(getClass().getResource("view/style.css").toExternalForm());

        primaryStage.setTitle("Simple Merge");
        primaryStage.setScene(mainScene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
