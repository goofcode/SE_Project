package view;


import com.google.common.util.concurrent.SettableFuture;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static model.Constants.LEFT;
import static model.Constants.RIGHT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static view.TestUtils.*;

public class CopyTest extends GuiTest {

    private static final SettableFuture<Stage> stageFuture = SettableFuture.create();

    public static Controller controller;

    protected static class TestProgramInfoWindow extends Main {
        public TestProgramInfoWindow() {
            super();
        }
        @Override
        public void start(Stage primaryStage) throws Exception {
            super.start(primaryStage);
            stageFuture.set(primaryStage);
        }
    }

    @Before
    @Override
    public void setupStage()  {
        FXTestUtils.launchApp(CopyTest.TestProgramInfoWindow.class); // You can add start parameters here
        try {
            stage = targetWindow(stageFuture.get(25, TimeUnit.SECONDS));
            FXTestUtils.bringToFront(stage);
        } catch (Exception e) {
            throw new RuntimeException("Unable to show stage", e);
        }
    }

    @Override
    protected Parent getRootNode() {
        return stage.getScene().getRoot();
    }

    @BeforeClass
    public static void makeInstance()throws Exception{
        controller = new Controller();

    }


    private void checkViewAfterCopy(){

        assertFalse(find("#lEditBtn").isDisabled());
        assertTrue(find("#lSaveBtn").isDisabled());
        assertFalse(find("#lCopyBtn").isDisabled());

        assertFalse(find("#rEditBtn").isDisabled());
        assertTrue(find("#rSaveBtn").isDisabled());
        assertFalse(find("#rCopyBtn").isDisabled());

        assertFalse(find("#compareBtn").isDisabled());
    }




    @Before
    public void beforeTest()throws Exception{
        loadFile(this, LEFT, file1, false);
        loadFile(this, RIGHT, file2, false);
    
        click("#compareBtn");
    }

    @Test
    public void copyTest() throws Exception {

        Node matchCell = null;
            for(Node node : findAll(".list-cell")){
                if(node.getPseudoClassStates().contains(PseudoClass.getPseudoClass("mismatch"))){
                    matchCell = node;
                    break;
                }
            }
            if (matchCell != null) {
            click(matchCell);
        }
        click("#lCopyBtn");

            sleep(1000);


    }
}