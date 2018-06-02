package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Diff.DiffBlock;
import model.Diff.DiffLine;

import java.io.IOException;
import java.util.ArrayList;

public class DiffLineListViewCell extends ListCell<DiffLine>{

    private FXMLLoader mLLoader;
    private ArrayList<DiffBlock> blocks;

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
            blocks = item.getLine();

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.DARKGRAY);

            for (DiffBlock block : blocks){

                Text textBlock = new Text(block.getContent());

                // TODO: need to be changed to background color
                if(block.getMatch()){
                    textBlock.setFill(Color.GRAY);
                }
                else{
                    textBlock.setFill(Color.YELLOW);
                    textBlock.setFont(Font.font(null, FontWeight.BOLD, 13));
                    textBlock.setEffect(dropShadow);
                }

                flowPane.getChildren().add(textBlock);
            }

            setBackgroundColors();
            flowPane.needsLayoutProperty().addListener((obs,d,d1)->setBackgroundColors());

            setText(null);
            setGraphic(flowPane);
        }
    }

    private void setBackgroundColors(){
        final StringBuilder sbColors = new StringBuilder();

        flowPane.getChildrenUnmodifiable().forEach(n->{

            for (DiffBlock block: blocks){
                if(!block.getMatch())
                    sbColors.append("rgb(255, 187, 153)");
            }

        });

        flowPane.setStyle("-fx-background-color: "+sbColors.toString()+";");
    }
}

