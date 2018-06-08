package view;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Diff.DiffBlock;
import model.Diff.DiffLine;

import java.io.IOException;

public class DiffLineListViewCell extends ListCell<DiffLine>{

    private FXMLLoader mLLoader;

    @FXML private FlowPane flowPane;

    private static PseudoClass MATCH = PseudoClass.getPseudoClass("match");
    private static PseudoClass MATCH_SELECTED = PseudoClass.getPseudoClass("match-selected");
    private static PseudoClass MISMATCH = PseudoClass.getPseudoClass("mismatch");
    private static PseudoClass MISMATCH_SELECTED = PseudoClass.getPseudoClass("mismatch-selected");

    private static Color TEXT_MATCH = Color.BLACK;
    private static Color TEXT_MISMATCH_MATCH = Color.GREEN;
    private static Color TEXT_MISMATCH_MISMATCH = Color.WHITE;

    @Override
    protected void updateItem(DiffLine line, boolean empty) {

        super.updateItem(line, empty);

        resetClass();

        if (empty || line == null){

            setText(null);
            setGraphic(null);
        }


        else{

            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/LineCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            flowPane.getChildren().clear();

            // line matches
            if (line.getMatched()){

                if (line.getSelected()) setBackGroundColor(MATCH_SELECTED);
                else setBackGroundColor(MATCH);

                if(line.getLine().size() != 0){
                    Text textBlock = new Text(line.getLine().get(0).getContent());
                    textBlock.setFill(TEXT_MATCH);
                    flowPane.getChildren().add(textBlock);
                }

            }
            // line does not match
            else{
                if(line.getSelected()) setBackGroundColor(MISMATCH_SELECTED);
                else setBackGroundColor(MISMATCH);

                for (DiffBlock block : line.getLine()){

                    Text textBlock = new Text(block.getContent());

                    if (block.getMatch()) textBlock.setFill(TEXT_MISMATCH_MATCH);
                    else textBlock.setFill(TEXT_MISMATCH_MISMATCH);

                    flowPane.getChildren().add(textBlock);
                }
            }


            setText(null);
            setGraphic(flowPane);

        }
    }

    private void setBackGroundColor(PseudoClass pseudoClass){
        pseudoClassStateChanged(MATCH, pseudoClass == MATCH);
        pseudoClassStateChanged(MATCH_SELECTED, pseudoClass == MATCH_SELECTED);
        pseudoClassStateChanged(MISMATCH, pseudoClass == MISMATCH);
        pseudoClassStateChanged(MISMATCH_SELECTED, pseudoClass == MISMATCH_SELECTED);
    }
    private void resetClass(){
        pseudoClassStateChanged(MATCH, false);
        pseudoClassStateChanged(MATCH_SELECTED, false);
        pseudoClassStateChanged(MISMATCH, false);
        pseudoClassStateChanged(MISMATCH_SELECTED, false);

    }
}


