package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Diff.DiffBlock;
import model.Diff.DiffLine;
import model.Diff.LCS;
import model.FileManager;
import model.Merge.Merger;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Controller {
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    @FXML private ListView<String> left_pannel;
    @FXML private ListView<String> right_pannel;

    @FXML private ListView<DiffLine> left_diff_panel;
    @FXML private ListView<DiffLine> right_diff_panel;

    @FXML private TextArea left_textarea;
    @FXML private TextArea right_textarea;

    @FXML
    private Button l_load_btn, l_save_btn, l_edit_btn, r_load_btn, r_copy_btn, l_copy_btn, r_edit_btn, r_save_btn, comp_btn;

    private Merger merger;
    private ObservableList<String>[] list;
    private FileManager[] fileManager = new FileManager[2];
    private boolean loadFlag[] = new boolean[2];
    private boolean editFlag[] = new boolean[2];
    private int block_up_index[] = new int[2];
    private int block_down_index[] = new int[2];

    private Alert alert;
    public Controller() {
        loadFlag[LEFT] = loadFlag[RIGHT] = false;
        editFlag[LEFT] = editFlag[RIGHT] = true;
        list = new ObservableList[2];
        fileManager[LEFT] = new FileManager();
        fileManager[RIGHT] = new FileManager();
    }


    @FXML
    protected void load_file(ActionEvent event) {

        String btn_name = ((Node) event.getSource()).getId();
        if (btn_name.equals("l_load_btn")) {
            if (loadFile(event, LEFT)) {

                l_save_btn.setDisable(false);
                l_edit_btn.setDisable(false);

            }
        } else {
            if (loadFile(event, RIGHT)) {
                r_save_btn.setDisable(false);
                r_edit_btn.setDisable(false);
            }
        }
    }


    @FXML
    protected void edit_file(ActionEvent event) {
        String btn_name = ((Node) event.getSource()).getId();
        if (btn_name.equals("l_edit_btn")) {
            if (editFlag[LEFT]) {
                System.out.println("left_edit_file toggle on!");
                editFlag[LEFT] = false;
                comp_btn.setDisable(true);
                l_copy_btn.setDisable(true);
                r_copy_btn.setDisable(true);
                swapListViewToTextArea(LEFT);
            } else {
                System.out.println("left_edit_file toggle off!");
                editFlag[LEFT] = true;
                swapTextAreaToListView(LEFT);
            }
        } else {
            if (editFlag[RIGHT]) {
                System.out.println("right_edit_file toggle on!");
                editFlag[RIGHT] = false;
                comp_btn.setDisable(true);
                l_copy_btn.setDisable(true);
                r_copy_btn.setDisable(true);
                swapListViewToTextArea(RIGHT);
            } else {
                System.out.println("right_edit_file toggle off!");
                editFlag[RIGHT] = true;

                swapTextAreaToListView(RIGHT);
            }
        }
        if(editFlag[LEFT]&&editFlag[RIGHT])
            comp_btn.setDisable(false);

    }

    @FXML
    protected void save_file(ActionEvent event) {
        String btn_name = ((Node) event.getSource()).getId();
        if (btn_name.equals("l_save_btn")) {
            System.out.println("left_save_file click!");
            if (saveFile(LEFT))
                System.out.println("save done!");
        } else {
            System.out.println("right_save_file click!");
            if (saveFile(RIGHT))
                System.out.println("save done!");
        }
    }
    @FXML
    protected void selected_item(javafx.scene.input.MouseEvent event) {
        int clickedLine = -1;

        clickedLine = left_diff_panel.getSelectionModel().getSelectedIndex();
        if(clickedLine==-1){
        }
        else {
            int[] temp = new int[2];
            temp = merger.search_diff_lines(clickedLine,LEFT);
            block_up_index[LEFT]=temp[0];
            block_down_index[LEFT] = temp[1];

            temp =merger.search_diff_lines(clickedLine,RIGHT);
            block_up_index[RIGHT]=temp[0];
            block_down_index[RIGHT] = temp[1];
            // use diff line index to change back ground
        }



    }

    @FXML
    protected void copy_file(ActionEvent event) {
        String btn_name = ((Node) event.getSource()).getId();
        int clickLine = -1;

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirm Copy");

        if (btn_name.equals("l_copy_btn")) {
            System.out.println("left_copy click!");
            System.out.println(left_diff_panel.getSelectionModel().getSelectedIndex());
            copying(LEFT);
        } else {
            System.out.println("right_copy click!");
            copying(RIGHT);

        }
    }



    @FXML
    protected void compare_file(ActionEvent event) {

        System.out.println("compare_file click!");

        for(int i=0;i<2;i++){
            block_down_index[i]=block_up_index[i]=-1;
        }

        left_pannel.setVisible(false);
        right_pannel.setVisible(false);
        left_diff_panel.setVisible(true);
        right_diff_panel.setVisible(true);
        l_copy_btn.setDisable(false);
        r_copy_btn.setDisable(false);

        ArrayList<String> lText = fileManager[LEFT].getText();
        ArrayList<String> rText = fileManager[RIGHT].getText();

        // execute lcs algorithm
        LCS lcs = new LCS();
        HashMap<String, ArrayList<DiffLine>> result = lcs.getDiff(lText, rText);
        merger = new Merger(result);


        // create diff line observable list
        ObservableList<DiffLine> lDiffLineObservableList = FXCollections.observableArrayList();
        ObservableList<DiffLine> rDiffLineObservableList = FXCollections.observableArrayList();

        for (int i=0; i< lText.size(); i++)
            lDiffLineObservableList.add(result.get("left").get(i));
        for (int i=0; i< rText.size(); i++)
            rDiffLineObservableList.add(result.get("right").get(i));


        // set list
        left_diff_panel.setItems(lDiffLineObservableList);
        right_diff_panel.setItems(rDiffLineObservableList);
        left_diff_panel.setCellFactory(diffLineListView -> new DiffLineListViewCell());
        right_diff_panel.setCellFactory(diffLineListView -> new DiffLineListViewCell());
        l_copy_btn.setDisable(false);
        r_copy_btn.setDisable(false);
    }

    private File fileChooser(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    }

    private void loadFlagCheck() {
        if (loadFlag[LEFT] && loadFlag[RIGHT])
            comp_btn.setDisable(false);
    }

    private void swapTextAreaToListView(int side) {
        TextArea area;
        ListView<String> panel;
        ListView<DiffLine> diff_panel;
        if (side == LEFT) {
            area = left_textarea;
            panel = left_pannel;
            diff_panel = left_diff_panel;
        } else {
            area = right_textarea;
            panel = right_pannel;
            diff_panel = right_diff_panel;
        }
        ArrayList<String> k = new ArrayList<String>();
        for (CharSequence sequence : area.getParagraphs()) {
            k.add(sequence.toString());
        }

        fileManager[side].setText(k);

        list[side] = FXCollections.observableList(fileManager[side].getText());
        panel.setItems(list[side]);
        area.setEditable(false);
        area.setVisible(false);
        diff_panel.setVisible(false);
        panel.setVisible(true);
    }

    private void swapListViewToTextArea(int side) {
        ListView<String> panel;
        TextArea area;
        ListView<DiffLine> diff_panel;
        if (side == LEFT) {
            area = left_textarea;
            panel = left_pannel;
            diff_panel = left_diff_panel;
        } else {
            area = right_textarea;
            panel = right_pannel;
            diff_panel = right_diff_panel;
        }

        panel.setVisible(false);
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < panel.getItems().size(); i++) {
            if (i < panel.getItems().size() - 1)
                text.append(fileManager[side].getText().get(i)).append("\n");
            else
                text.append(fileManager[side].getText().get(i));
        }

        area.setText(text.toString());
        area.setVisible(true);
        area.setEditable(true);
        diff_panel.setVisible(false);

    }

    private Boolean loadFile(ActionEvent event, int side) {
        ListView<String> panel;

        if (side == LEFT) panel = left_pannel;
        else panel = right_pannel;

        File file = fileChooser(event);

        if (file != null) {
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

    private Boolean saveFile(int side) {
        try {
            fileManager[side].saveFile(fileManager[side].getFile());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void synchronize(HashMap<String,ArrayList<DiffLine>> result,int side){
        ListView<DiffLine> diff_panel;
        ListView<String> panel;

        ArrayList<String> lText = fileManager[LEFT].getText();
        ArrayList<String> rText = fileManager[RIGHT].getText();

        ObservableList<DiffLine> lDiffLineObservableList = FXCollections.observableArrayList();
        ObservableList<DiffLine> rDiffLineObservableList = FXCollections.observableArrayList();

        for (int i=0; i< lText.size(); i++)
            lDiffLineObservableList.add(result.get("left").get(i));
        for (int i=0; i< rText.size(); i++)
            rDiffLineObservableList.add(result.get("right").get(i));


        // set list
        left_diff_panel.setItems(lDiffLineObservableList);
        right_diff_panel.setItems(rDiffLineObservableList);
        left_diff_panel.setCellFactory(diffLineListView -> new DiffLineListViewCell());
        right_diff_panel.setCellFactory(diffLineListView -> new DiffLineListViewCell());

        if(side == LEFT){
            diff_panel = right_diff_panel;
            panel = right_pannel;
        }
        else {
            diff_panel = left_diff_panel;
            panel = left_pannel;
        }


        for(int i = block_up_index[side]; i<=block_down_index[side];i++){
            for(int j=0;j<diff_panel.getItems().get(i).getLine().size();j++){
                StringBuilder temp = new StringBuilder();
                for(DiffBlock a : diff_panel.getItems().get(i).getLine()){
                    temp.append(a.getContent());
                }
                panel.getItems().set(i, temp.toString());
            }
        }
        ArrayList<String> tmp = new ArrayList<String>(panel.getItems());
        fileManager[side].setText(tmp);

        right_pannel.setVisible(true);
        right_diff_panel.setVisible(false);
        left_pannel.setVisible(true);
        left_diff_panel.setVisible(false);

    }


    private void copying(int side){
        ListView<DiffLine> diff_panel;
        int clickLine;
        if(side == LEFT){
            diff_panel = left_diff_panel;
        }else {
            diff_panel = right_diff_panel;
        }

        clickLine =block_up_index[side];


        if (diff_panel.getItems().get(clickLine).getIsMatch()) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Not different line");
            alert.setContentText("똑같은데?");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Would you like to copy and paste?");
            alert.setContentText("합치쉴?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                synchronize( merger.mergefunction(side,block_up_index[LEFT],block_down_index[LEFT],block_up_index[RIGHT],block_down_index[RIGHT]),LEFT);

                //use result to sync

                System.out.println("ok!");
            } else {
                System.out.println("cancel");
            }
        }

    }
}
