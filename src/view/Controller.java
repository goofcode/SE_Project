package view;

import com.sun.istack.internal.Nullable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Diff.DiffLine;
import model.Diff.LCS;
import model.FileManager;
import model.DiffManager;

import java.io.File;
import java.net.URL;
import java.util.*;

import static model.Constants.LEFT;
import static model.Constants.RIGHT;

public class Controller implements Initializable {

    /* FXML view components */
    @FXML private ListView<String> lLineListView, rLineListView;
    @FXML private ListView<DiffLine> lDiffListView, rDiffListView;
    @FXML private TextArea lEditTextArea, rEditTextArea;
    @FXML private Button lLoadBtn, rLoadBtn;
    @FXML private Button lEditBtn, rEditBtn;
    @FXML private Button lSaveBtn, rSaveBtn;
    @FXML private Button lCopyBtn, rCopyBtn;
    @FXML private Button compareBtn;

    /* view component lists for easier control over them */
    private List<ListView<String>> lineListView;
    private List<ListView<DiffLine>> diffListView;
    private List<TextArea> editTextArea;
    private List<Button> loadBtn, editBtn, saveBtn, copyBtn;

    /* models */
    private FileManager[] fileManagers;
    private DiffManager diffManager;

    /* states */
    private boolean[] isLoaded = new boolean[]{false, false};
    private boolean[] isEditToggled = new boolean[]{false, false};
    private boolean[] isModified = new boolean[]{false, false};
    private boolean isCompared = false;
    private boolean isSelected = false;


    private Alert alert;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // allocate lists
        lineListView = new ArrayList<>(Arrays.asList(lLineListView, rLineListView));
        diffListView = new ArrayList<>(Arrays.asList(lDiffListView, rDiffListView));
        editTextArea = new ArrayList<>(Arrays.asList(lEditTextArea, rEditTextArea));
        loadBtn = new ArrayList<>(Arrays.asList(lLoadBtn, rLoadBtn));
        editBtn = new ArrayList<>(Arrays.asList(lEditBtn, rEditBtn));
        saveBtn = new ArrayList<>(Arrays.asList(lSaveBtn, rSaveBtn));
        copyBtn = new ArrayList<>(Arrays.asList(lCopyBtn, rCopyBtn));

        fileManagers = new FileManager[2];
        diffManager = new DiffManager();


    }

    @FXML
    protected void onLoadBtnClicked(ActionEvent e) {

        int side = e.getSource() == loadBtn.get(LEFT) ? LEFT : RIGHT;
        int other = side == LEFT? RIGHT: LEFT;

        // get file from user selection
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());

        if(file == null) {
            alertInfo("File not selected", "pleas choose file to load");
            return;
        }

        fileManagers[side] = new FileManager(file);

        if (fileManagers[other] != null)
            fileManagers[side].synchronizeSize(fileManagers[other]);

        isLoaded[side] = true;
        isEditToggled[side] = false;
        isModified[side] = false;
        isCompared = false;
        isSelected = false;

        refresh(LEFT);
        refresh(RIGHT);
    }

    public void onEditBtnClicked(ActionEvent e) {

        int side = e.getSource() == editBtn.get(LEFT) ? LEFT : RIGHT;
        int other = side == LEFT? RIGHT: LEFT;

        // if not toggled
        if (!isEditToggled[side])
            isEditToggled[side] = true;

        // if already toggled
        else {
            isEditToggled[side] = false;
            fileManagers[side].setLinesFromOneString(editTextArea.get(side).getText());
            if (fileManagers[other] != null)
                fileManagers[side].synchronizeSize(fileManagers[other]);
        }

        isModified[side] = true;
        isCompared = false;
        isSelected = false;

        refresh(LEFT);
        refresh(RIGHT);
    }

    public void onSaveBtnClicked(ActionEvent e) {

        int side = e.getSource() == saveBtn.get(LEFT) ? LEFT : RIGHT;

        // save file
        if(isEditToggled[side])
            fileManagers[side].setLinesFromOneString(editTextArea.get(side).getText());
        fileManagers[side].save();

        // change to line list view mode
        isLoaded[side] = true;
        isEditToggled[side] = false;
        isModified[side] = false;
        isCompared = false;
        isSelected = false;

        refresh(LEFT);
        refresh(RIGHT);
    }

    public void onCompareBtnClicked(ActionEvent e) {

        diffManager.init((new LCS()).getDiff(fileManagers[LEFT].getLines(), fileManagers[RIGHT].getLines()));

        isLoaded[LEFT] = isLoaded[RIGHT] = true;
        isEditToggled[LEFT] = isEditToggled[RIGHT] = false;
        isCompared = true;
        isSelected = false;

        refresh(LEFT);
        refresh(RIGHT);
    }


    public void onDiffListViewClicked(MouseEvent e) {

        int side = e.getSource() == diffListView.get(LEFT)? LEFT: RIGHT;
        int selected = diffListView.get(side).getSelectionModel().getSelectedIndex();

        if (selected == -1) return;
        System.out.println(selected);

        diffManager.selectsDiffBound(selected);

        isSelected = true;

        refresh(LEFT);
        refresh(RIGHT);
    }

    public void onCopyBtnClicked(ActionEvent e) {

        int src = e.getSource() == copyBtn.get(LEFT) ? LEFT : RIGHT;
        int dst = src == LEFT? RIGHT: LEFT;

        if (!isSelected){
            alertInfo("Block to be copied not selected", "please select block to copy");
            return;
        }

        isModified[dst] = true;
        
        List<Integer> bound = diffManager.mergeFrom(src);
        fileManagers[src].copyTo(fileManagers[dst], bound);

        refresh(LEFT);
        refresh(RIGHT);
    }



    // update ui components based on state rule
    private void refresh(int side){

        compareBtn.setDisable(!(isLoaded[LEFT] && isLoaded[RIGHT] && !isEditToggled[LEFT] && !isEditToggled[RIGHT]));
        saveBtn.get(side).setDisable(!isModified[side]);

        if(isCompared){

            diffListView.get(LEFT).getItems().clear();
            diffListView.get(RIGHT).getItems().clear();

            diffListView.get(LEFT).setItems(FXCollections.observableArrayList(diffManager.getLines(LEFT)));
            diffListView.get(LEFT).setCellFactory(diffList-> new DiffLineListViewCell());
            diffListView.get(RIGHT).setItems(FXCollections.observableArrayList(diffManager.getLines(RIGHT)));
            diffListView.get(RIGHT).setCellFactory(diffList-> new DiffLineListViewCell());

            showDiffListView(LEFT);
            showDiffListView(RIGHT);

            // scroll bar binding
            Node leftScrollBar = diffListView.get(LEFT).lookup(".scroll-bar");
            Node rightScrollBar = diffListView.get(RIGHT).lookup(".scroll-bar");
            if (leftScrollBar instanceof ScrollBar && rightScrollBar instanceof ScrollBar){
                ((ScrollBar) leftScrollBar).valueProperty()
                        .bindBidirectional(((ScrollBar) rightScrollBar).valueProperty());
            }

            diffListView.get(LEFT).refresh();
            diffListView.get(RIGHT).refresh();

            activateBtn(LEFT, true, true, true);
            activateBtn(RIGHT, true, true, true);
        }

        // if loaded, not editing, not compared
        else if (isLoaded[side] && !isEditToggled[side]) {

            lineListView.get(side).setItems(FXCollections.observableArrayList(fileManagers[side].getLines()));

            showLineListView(side);

            Node leftScrollBar = lineListView.get(LEFT).lookup(".scroll-bar");
            Node rightScrollBar = lineListView.get(RIGHT).lookup(".scroll-bar");
            if (leftScrollBar instanceof ScrollBar && rightScrollBar instanceof ScrollBar){
                ((ScrollBar) leftScrollBar).valueProperty()
                        .bindBidirectional(((ScrollBar) rightScrollBar).valueProperty());
            }

            activateBtn(side, true, true, false);

            lineListView.get(side).refresh();
        }


        else if(isLoaded[side] && isEditToggled[side]){

            editTextArea.get(side).setText(fileManagers[side].getLinesAsOneString());

            showEditTextArea(side);
            activateBtn(side, true, true, false);
        }


    }

    private void showLineListView(int side){
        lineListView.get(side).setVisible(true);
        editTextArea.get(side).setVisible(false);
        diffListView.get(side).setVisible(false);
    }
    private void showEditTextArea(int side){
        lineListView.get(side).setVisible(false);
        editTextArea.get(side).setVisible(true);
        diffListView.get(side).setVisible(false);
    }
    private void showDiffListView(int side){
        lineListView.get(side).setVisible(false);
        editTextArea.get(side).setVisible(false);
        diffListView.get(side).setVisible(true);
    }
    private void activateBtn(int side, boolean load, boolean edit, boolean copy){
        loadBtn.get(side).setDisable(!load);
        editBtn.get(side).setDisable(!edit);
        copyBtn.get(side).setDisable(!copy);
    }


    private void alertInfo(@Nullable String title, String msg){
        alert = new Alert(Alert.AlertType.INFORMATION);

        if(title != null) alert.setHeaderText(title);
        alert.setContentText(msg);

        alert.showAndWait();
    }


}


