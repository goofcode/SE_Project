package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import model.FileManager;

import java.io.File;


public class Controller {
    @FXML private ListView<String> left_pannel;
    @FXML private ListView<String> right_pannel;
    private ObservableList<String> left_list;
    private ObservableList<String> right_list;

    private Alert alert;
    static private  int left = 0;
    static private  int right = 1;
    private FileManager fileManager = new FileManager();

    @FXML
    protected void l_load_file(ActionEvent event) {
        File file = fileChooser(event);

        if (file != null) {
            fileManager.loadFile(file,left);
            left_list = FXCollections.observableList(fileManager.getLeftText());
            left_pannel.setItems(left_list);
        }
    }

    @FXML
    protected void r_load_file(ActionEvent event){
        File file = fileChooser(event);

        if(file != null){
            fileManager.loadFile(file,right);
            right_list = FXCollections.observableList(fileManager.getRightText());
            right_pannel.setItems(right_list);
        }
    }

    @FXML
    protected void l_edit_file(ActionEvent event){
        System.out.println("left_edit_file click!");
    }

    @FXML
    protected void l_save_file(ActionEvent event){
        System.out.println("left_save_file click!");
    }



    @FXML
    protected void r_edit_file(ActionEvent event){
        System.out.println("right_edit_file click!");
    }
    @FXML
    protected void r_save_file(ActionEvent event){
        System.out.println("right_save_file click!");
    }

    @FXML
    protected void compare_file(ActionEvent event){
        System.out.println("compare_file click!");
    }

    public File fileChooser(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        return file;
    }
}
