package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;


public class Controller {

    private Alert alert;
    static private  int left = 0;
    static private  int right = 1;

    @FXML
    protected void l_load_file(ActionEvent event) {
        File file = fileChooser(event);

        if (file != null) {

        }
    }

    @FXML
    protected void r_load_file(ActionEvent event){
        File file = fileChooser(event);

        if(file != null){

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
