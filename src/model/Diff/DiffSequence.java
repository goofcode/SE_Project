package model.Diff;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

public class DiffSequence extends ListCell<String > {

    @Override
    protected void updateItem(String Item, boolean empty) {
        super.updateItem(Item,empty);
        if (empty) {
            setText(null);
            setStyle("");
        } else {
            setText(Item);
            setStyle("-fx-control-inner-background: gray;");
            setTextFill(Color.BLUE);
        }
    }
}
