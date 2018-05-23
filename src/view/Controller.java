package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import model.Module;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Controller {
    private Alert alert;
    private Module module;
    static private  int left = 0;
    static private  int right = 1;
    @FXML
    protected void l_load_file(ActionEvent event){
        File file = fileChooser(event);

        if(file != null){

        }
    }

    @FXML
    protected void r_load_file(ActionEvent event){
        File file = fileChooser(event);

        if(file != null){

        }
    }

    public File fileChooser(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        return file;
    }
}
