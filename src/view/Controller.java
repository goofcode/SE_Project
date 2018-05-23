package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class Controller {
    private Alert alert;

    @FXML
    protected void l_load_file(ActionEvent event){
        System.out.println("left_load_file click!");
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
    protected void r_load_file(ActionEvent event){
        System.out.println("right_load_file click!");
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
}
