package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Diff.DiffBlock;
import model.Diff.DiffLine;

import java.io.IOException;
import java.util.ArrayList;


public class DiffLineListViewCell extends ListCell<DiffLine>{

    private FXMLLoader mLLoader;

    @FXML private FlowPane flowPane;

    @Override
    protected void updateItem(DiffLine item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null){
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

            ArrayList<DiffBlock> blocks = item.getLine();


            for (DiffBlock block : blocks){

                Text textBlock = new Text(block.getContent());

                // TODO: need to be changed to background color
                if(block.getMatch()) textBlock.setFill(Color.GRAY);
                else textBlock.setFill(Color.YELLOW);

                flowPane.getChildren().add(textBlock);
            }

            setText(null);
            setGraphic(flowPane);
        }
    }
}

