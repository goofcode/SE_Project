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
import java.util.List;


public class Controller {
    private static final int LEFT = 0;
    private static final int RIGHT =1;
    @FXML private ListView<String> left_pannel;
    @FXML private ListView<String> right_pannel;
    @FXML private TextArea left_textarea;
    @FXML private TextArea right_textarea;
    @FXML private Button l_load_btn, l_save_btn, l_edit_btn ,r_load_btn, r_copy_btn, l_copy_btn, r_edit_btn, r_save_btn,comp_btn;

    private ObservableList<String>[] list;
    private FileManager[] fileManager =new FileManager[2];
    private boolean loadFlag[] = new boolean[2];
    private boolean editFlag[] = new boolean[2];

    private Alert alert;

    public Controller(){
        loadFlag[LEFT] = loadFlag[RIGHT] = false;
        editFlag[LEFT] = editFlag[RIGHT] = true;
        list = new ObservableList[2];
        fileManager[0] = new FileManager();
        fileManager[1] = new FileManager();
    }


    @FXML
    protected void l_load_file(ActionEvent event) {
        if(loadFile(event,LEFT)){
            l_save_btn.setDisable(false);
            l_edit_btn.setDisable(false);
        }
    }

    @FXML
    protected void r_load_file(ActionEvent event){
        if(loadFile(event,RIGHT)){
            r_save_btn.setDisable(false);
            r_edit_btn.setDisable(false);
        }
    }

    @FXML
    protected void l_edit_file(ActionEvent event){

        if(editFlag[LEFT]) {
            System.out.println("left_edit_file toggle on!");
            editFlag[LEFT] = false;
            swapListViewToTextArea( LEFT);
        }
        else{
            System.out.println("left_edit_file toggle off!");
            editFlag[LEFT] = true;
            swapTextAreaToListView( LEFT);
        }
    }

    @FXML
    protected void l_save_file(ActionEvent event){
        System.out.println("left_save_file click!");
    }



    @FXML
    protected void r_edit_file(ActionEvent event){

        if(editFlag[RIGHT]) {
            System.out.println("right_edit_file toggle on!");
            editFlag[RIGHT] = false;
            swapListViewToTextArea(RIGHT);
        }
        else{
            System.out.println("right_edit_file toggle off!");
            editFlag[RIGHT] = true;
            swapTextAreaToListView(RIGHT);
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

    private File fileChooser(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    }
    private void loadFlagCheck(){
        if(loadFlag[LEFT] && loadFlag[RIGHT])
            comp_btn.setDisable(false);
    }
    private void swapTextAreaToListView(int side){
        TextArea area;
        ListView<String> panel;
        if(side == 0){
            area = left_textarea;
            panel = left_pannel;
        }
        else{
            area = right_textarea;
            panel = right_pannel;
        }
        ArrayList<String> k = new ArrayList<String>();
        for(CharSequence sequence :  area.getParagraphs()){
            k.add(sequence.toString());
        }

        fileManager[side].setText(k);

        list[side] = FXCollections.observableList(fileManager[side].getText());
        panel.setItems(list[side]);
        area.setEditable(false);
        area.setVisible(false);
        panel.setVisible(true);
    }
    private void swapListViewToTextArea(int side){
        ListView<String> panel;
        TextArea area;
        if(side ==0){
            panel = left_pannel;
            area = left_textarea;
        }
        else{
            panel = right_pannel;
            area = right_textarea;
        }

        panel.setVisible(false);
        StringBuilder text = new StringBuilder();
        for(int i =0; i < panel.getItems().size();i++){
            if(i <  panel.getItems().size()-1)
                text.append(fileManager[side].getText().get(i)).append("\n");
            else
                text.append(fileManager[side].getText().get(i));
        }

        area.setText(text.toString());
        area.setVisible(true);
        area.setEditable(true);
    }

    private Boolean loadFile(ActionEvent event, int side){
        ListView<String> panel;
        if(side ==0)
            panel = left_pannel;
        else
            panel = right_pannel;
        File file = fileChooser(event);
        if(file != null){
            fileManager[side].clearText();
            fileManager[side].loadFile(file);
            list[side] = FXCollections.observableList(fileManager[side].getText());
            panel.setItems(list[side]);
            loadFlag[side] = true;
            loadFlagCheck();
            return true;
        }
       else
           return false;
    }

}
