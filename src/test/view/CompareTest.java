package view;


import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import java.util.concurrent.TimeUnit;

import static model.Constants.LEFT;
import static model.Constants.RIGHT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static view.TestUtils.*;

public class CompareTest extends GuiTest {

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
        FXTestUtils.launchApp(CompareTest.TestProgramInfoWindow.class); // You can add start parameters here
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

    private void checkViewBeforeCompare(){

        assertFalse(find("#lEditBtn").isDisabled());
        assertTrue(find("#lSaveBtn").isDisabled());
        assertTrue(find("#lCopyBtn").isDisabled());

        assertFalse(find("#rEditBtn").isDisabled());
        assertTrue(find("#rSaveBtn").isDisabled());
        assertTrue(find("#rCopyBtn").isDisabled());

        assertFalse(find("#compareBtn").isDisabled());
    }
    private void checkViewAfterCompare(){

        assertFalse(find("#lEditBtn").isDisabled());
        assertTrue(find("#lSaveBtn").isDisabled());
        assertFalse(find("#lCopyBtn").isDisabled());

        assertFalse(find("#rEditBtn").isDisabled());
        assertTrue(find("#rSaveBtn").isDisabled());
        assertFalse(find("#rCopyBtn").isDisabled());

        assertFalse(find("#compareBtn").isDisabled());

        assertTrue(find("#lDiffListView").isVisible());

        assertTrue(find("#rDiffListView").isVisible());
    }
    private void checkViewAfterCompareWhileEdit(int side){
        if(side == LEFT) {
            assertFalse(find("#lEditBtn").isDisabled());
            assertFalse(find("#lSaveBtn").isDisabled());
            assertTrue(find("#lCopyBtn").isDisabled());
            assertTrue(find("#lEditTextArea").isVisible());
        }
        else if (side == RIGHT){
            assertFalse(find("#rEditBtn").isDisabled());
            assertFalse(find("#rSaveBtn").isDisabled());
            assertTrue(find("#rCopyBtn").isDisabled());
            assertTrue(find("#rEditTextArea").isVisible());
        }




        assertTrue(find("#compareBtn").isDisabled());







    }

    @Before
    public void beforeTest()throws Exception {
        loadFile(this, LEFT, file1, false);
        loadFile(this, RIGHT, file2, false);
    }


    @Test
    public void compareTest() throws Exception {

        checkViewBeforeCompare();
        click("#compareBtn");
        checkViewAfterCompare();
    }

    @Test
    public void compareWhileLeftEditTest() throws Exception {

        checkViewBeforeCompare();
        click("#lEditBtn");
        click("#compareBtn");
        checkViewAfterCompareWhileEdit(LEFT);
    }

    @Test
    public void compareWhileRightEditTest() throws Exception {

        checkViewBeforeCompare();
        click("#rEditBtn");
        click("#compareBtn");
        checkViewAfterCompareWhileEdit(RIGHT);
    }

    @Test
    public void compareWhileBothEditTest() throws Exception {

        checkViewBeforeCompare();
        click("#lEditBtn");
        click("#rEditBtn");
        click("#compareBtn");
        checkViewAfterCompareWhileEdit(LEFT);
        checkViewAfterCompareWhileEdit(RIGHT);

    }
}