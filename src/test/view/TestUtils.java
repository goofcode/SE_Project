package view;

import javafx.scene.input.KeyCode;
import org.loadui.testfx.GuiTest;

import static model.Constants.LEFT;

public class TestUtils {

    public static final String file1 = "asd.txt";
    public static final String file2 = "asd.txt.txt";

    public static void loadFile(GuiTest test, int side, String filename, boolean cancel){

        if (side == LEFT) test.click("#lLoadBtn");
        else test.click("#rLoadBtn");

        test.type("D").type(KeyCode.SHIFT,KeyCode.SEMICOLON).type(KeyCode.ENTER);

        if (cancel){
            test.type(KeyCode.ESCAPE);
            return;
        }

        test.type(filename).type(KeyCode.ENTER);
        test.sleep(1000);
    }
}
