package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Diff.DiffLine;
import model.Diff.LCS;
import model.FileManager;

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

    private ObservableList<String>[] list;
    private FileManager[] fileManager = new FileManager[2];
    private boolean loadFlag[] = new boolean[2];
    private boolean editFlag[] = new boolean[2];

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
                swapListViewToTextArea(RIGHT);
            } else {
                System.out.println("right_edit_file toggle off!");
                editFlag[RIGHT] = true;
                swapTextAreaToListView(RIGHT);
            }
        }
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
    protected void copy_file(ActionEvent event) {
        String btn_name = ((Node) event.getSource()).getId();
        if (btn_name.equals("l_copy_btn")) {
            System.out.println("left_copy click!");
        } else {
            System.out.println("right_copy click!");
        }
    }



    @FXML
    protected void compare_file(ActionEvent event) {

        System.out.println("compare_file click!");

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
        if (side == LEFT) {
            area = left_textarea;
            panel = left_pannel;
        } else {
            area = right_textarea;
            panel = right_pannel;
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
        panel.setVisible(true);
    }

    private void swapListViewToTextArea(int side) {
        ListView<String> panel;
        TextArea area;
        if (side == LEFT) {
            panel = left_pannel;
            area = left_textarea;
        } else {
            panel = right_pannel;
            area = right_textarea;
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

}
