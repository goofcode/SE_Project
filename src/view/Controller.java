package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.FileManager;
import sun.font.GlyphLayout;

import java.io.File;
import java.util.ArrayList;


public class Controller {
    @FXML private ListView<String> left_pannel;
    @FXML private ListView<String> right_pannel;
    @FXML private TextArea left_textarea;
    @FXML private TextArea right_textarea;
    @FXML private Button l_load_btn = new Button();
    @FXML private Button l_save_btn = new Button();
    @FXML private Button l_edit_btn = new Button();
    @FXML private Button r_load_btn = new Button();
    @FXML private Button r_copy_btn = new Button();
    @FXML private Button l_copy_btn = new Button();

    @FXML private Button r_edit_btn = new Button();
    @FXML private Button r_save_btn = new Button();
    @FXML private Button comp_btn = new Button();
    private ObservableList<String> left_list;
    private ObservableList<String> right_list;
    private FileManager left_fileManager = new FileManager();
    private FileManager right_fileManager = new FileManager();
    private boolean loadFlag[] = new boolean[2];
    private boolean editFlag[] = new boolean[2];
    private int edited = -1;
    private Alert alert;

    public Controller(){
        loadFlag[0] = loadFlag[1] = false;
        editFlag[0] = editFlag[1] = false;
    }


    @FXML
    protected void l_load_file(ActionEvent event) {
        File file = fileChooser(event);
        left_textarea.setVisible(false);
        left_pannel.setVisible(true);
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

        if(editFlag[0]){
            System.out.println("left_edit_file toggle off!");
            editFlag[0] = false;
            left_pannel.setEditable(false);
        }
        else
            editFlag[0] = true;

        if(editFlag[0]) {
            System.out.println("left_edit_file toggle on!");
            ListTotxtArea(left_pannel,left_fileManager,left_textarea);
        }
        else if(!editFlag[0]){
            txtAreaToList(left_textarea, left_fileManager, left_list, left_pannel);

        }
    }

    @FXML
    protected void l_save_file(ActionEvent event){
        System.out.println("left_save_file click!");
    }



    @FXML
    protected void r_edit_file(ActionEvent event){
        if(editFlag[1]){
            System.out.println("right_edit_file toggle off!");
            editFlag[1] = false;
            right_pannel.setEditable(false);
        }
        else
            editFlag[1] = true;

        if(editFlag[1]) {
            System.out.println("right_edit_file toggle on!");
            ListTotxtArea(right_pannel,right_fileManager,right_textarea);
        }
        else if(!editFlag[1]) {
            txtAreaToList(right_textarea, right_fileManager, right_list, right_pannel);
        }


    }
    @FXML
    protected void l_copy_file(ActionEvent event){
        System.out.println("left_copy click!");
    }
    @FXML
    protected void r_save_file(ActionEvent event){
        System.out.println("right_save_file click!");
    }
    @FXML
    protected void r_copy_file(ActionEvent event){
        System.out.println("right_copy click!");
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
    private void txtAreaToList(TextArea area, FileManager manager,ObservableList<String> list,ListView<String> pannel){
        ArrayList<String> k = new ArrayList<String>();
        for(CharSequence sequence :  area.getParagraphs()){
            k.add(sequence.toString());
        }

        manager.setText(k);

        list = FXCollections.observableList(manager.getText());
        pannel.setItems(list);
        area.setEditable(false);
        area.setVisible(false);
        pannel.setVisible(true);
    }
    private void ListTotxtArea(ListView<String> pannel, FileManager manager, TextArea area){

        pannel.setVisible(false);
        String text ="";
        for(int i =0; i < pannel.getItems().size();i++){
            if(i <  pannel.getItems().size()-1)
                text += manager.getText().get(i)+"\n";
            else
                text += manager.getText().get(i);
        }

        area.setText(text);
        area.setVisible(true);
        area.setEditable(true);
    }

}
