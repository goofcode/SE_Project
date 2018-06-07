package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;


    private void setPrimaryStage(Stage stage){
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage(){
        return Main.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        Scene mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Simple Merge");
        primaryStage.setScene(mainScene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
