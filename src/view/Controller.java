package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import model.FileManager;

import java.io.File;


public class Controller {
    @FXML private ListView<String> left_pannel;
    @FXML private ListView<String> right_pannel;
    @FXML private Button l_load_btn = new Button();
    @FXML private Button l_save_btn = new Button();
    @FXML private Button l_edit_btn = new Button();
    @FXML private Button r_load_btn = new Button();
    @FXML private Button r_edit_btn = new Button();
    @FXML private Button r_save_btn = new Button();
    @FXML private Button comp_btn = new Button();
    private ObservableList<String> left_list;
    private ObservableList<String> right_list;
    private FileManager left_fileManager = new FileManager();
    private FileManager right_fileManager = new FileManager();
    private boolean loadFlag[] = new boolean[2];
    private Alert alert;

    public Controller(){
        loadFlag[0] = loadFlag[1] = false;
    }


    @FXML
    protected void l_load_file(ActionEvent event) {
        File file = fileChooser(event);

        if (file != null) {
            left_fileManager.clearText();
            left_fileManager.loadFile(file);
            left_list = FXCollections.observableList(left_fileManager.getText());
            left_pannel.setItems(left_list);
            l_save_btn.setDisable(false);
            l_edit_btn.setDisable(false);
            loadFlag[0] = true;
            loadFlagCheck();
        }
    }

    @FXML
    protected void r_load_file(ActionEvent event){
        File file = fileChooser(event);

        if(file != null){
            right_fileManager.clearText();
            right_fileManager.loadFile(file);
            right_list = FXCollections.observableList(right_fileManager.getText());
            right_pannel.setItems(right_list);
            r_save_btn.setDisable(false);
            r_edit_btn.setDisable(false);
            loadFlag[1] =true;
            loadFlagCheck();

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
    private void loadFlagCheck(){
        if(loadFlag[0]==true && loadFlag[1]==true)
            comp_btn.setDisable(false);
    }
}
