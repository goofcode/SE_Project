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
    private static final int LEFT = 0;
    private static final int RIGHT =1;
    private Alert alert;

    public Controller(){
        loadFlag[LEFT] = loadFlag[RIGHT] = false;
        editFlag[LEFT] = editFlag[RIGHT] = true;
    }


    @FXML
    protected void l_load_file(ActionEvent event) {
        if(loadFile(event,left_fileManager,left_list,left_pannel,LEFT)){
            l_save_btn.setDisable(false);
            l_edit_btn.setDisable(false);
        }
    }

    @FXML
    protected void r_load_file(ActionEvent event){
        if(loadFile(event,right_fileManager,right_list,right_pannel,RIGHT)){
            r_save_btn.setDisable(false);
            r_edit_btn.setDisable(false);
        }
    }

    @FXML
    protected void l_edit_file(ActionEvent event){

        if(editFlag[LEFT]) {
            System.out.println("left_edit_file toggle on!");
            editFlag[LEFT] = false;
            swapListViewToTextArea(left_pannel,left_fileManager,left_textarea);
        }
        else if(!editFlag[LEFT]){
            System.out.println("left_edit_file toggle off!");
            editFlag[LEFT] = true;
            swapTextAreaToListView(left_textarea, left_fileManager, left_list, left_pannel);
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
            swapListViewToTextArea(right_pannel,right_fileManager,right_textarea);
        }
        else if(!editFlag[RIGHT]) {
            System.out.println("right_edit_file toggle off!");
            editFlag[RIGHT] = true;
            swapTextAreaToListView(right_textarea, right_fileManager, right_list, right_pannel);
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
    private void swapTextAreaToListView(TextArea area, FileManager manager,ObservableList<String> list,ListView<String> pannel){
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
    private void swapListViewToTextArea(ListView<String> pannel, FileManager manager, TextArea area){

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

    private Boolean loadFile(ActionEvent event,FileManager manager, ObservableList<String > list,ListView<String> pannel, int side){
        File file = fileChooser(event);
        if(file != null){
            manager.clearText();
            manager.loadFile(file);
            list = FXCollections.observableList(manager.getText());
            pannel.setItems(list);
            loadFlag[side] = true;
            loadFlagCheck();
            return true;
        }
       else
           return false;
    }

}
