package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.security.Key;

import static org.junit.Assert.*;

public class MainTest extends ApplicationTest{

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void AppTest() throws Exception {

        // failed to load on left side
        clickOn("#lLoadBtn");
        type(KeyCode.ESCAPE);
        type(KeyCode.ENTER);

        // load on left side
        String file1 = "asd.txt";
        clickOn("#lLoadBtn");
        write("D").type(KeyCode.SHIFT,KeyCode.SEMICOLON)
                .type(KeyCode.ENTER).write(file1).type(KeyCode.ENTER);

        // edit left side
        clickOn("#lEditBtn");
        TextArea lEditTextArea = lookup("#lEditTextArea").query();
        String contents = lEditTextArea.getText();
        lEditTextArea.setText(contents+"\n\n");

        // save modified
        clickOn("#lSaveBtn");

        System.out.println("left file saved");

        // load same file on right side
        String file2 = "asd.txt.txt";
        clickOn("#rLoadBtn");
        write("D").type(KeyCode.SHIFT,KeyCode.SEMICOLON)
                .type(KeyCode.ENTER).write(file2).type(KeyCode.ENTER);


        // compare two files
        clickOn("#compareBtn");

        // copy to left
        clickOn((Node)lookup(".mismatch").query());
        clickOn("#rCopyBtn");

        // save left
        clickOn("#lSaveBtn");

    }
}