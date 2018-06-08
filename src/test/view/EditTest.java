package view;

import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
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
import static org.junit.Assert.*;
import static view.TestUtils.*;

public class EditTest extends GuiTest {

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
    public void setupStage() {
        FXTestUtils.launchApp(EditTest.TestProgramInfoWindow.class); // You can add start parameters here
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

    @Before
    public void beforeTest()throws Exception{
        loadFile(this, LEFT, file1, false);
        loadFile(this, RIGHT, file2, false);
    }

    private void checkViewBeforeEdit(){

        assertFalse(GuiTest.find("#lEditBtn").isDisabled());
        assertTrue(GuiTest.find("#lSaveBtn").isDisabled());
        assertTrue(GuiTest.find("#lCopyBtn").isDisabled());

        assertFalse(GuiTest.find("#rEditBtn").isDisabled());
        assertTrue(GuiTest.find("#rSaveBtn").isDisabled());
        assertTrue(GuiTest.find("#rCopyBtn").isDisabled());

        assertFalse(GuiTest.find("#compareBtn").isDisabled());
    }
    private void checkViewAfterEditOn(int side){

        if(side == LEFT) {
            assertFalse(GuiTest.find("#lLoadBtn").isDisabled());
            assertFalse(GuiTest.find("#lSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#lEditTextArea").isVisible());
        }
        else if (side == RIGHT){
            assertFalse(GuiTest.find("#rLoadBtn").isDisabled());
            assertFalse(GuiTest.find("#rSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#rEditTextArea").isVisible());
        }

    }
    private void checkViewAfterEditOff(int side){

        if(side == LEFT) {
            assertFalse(GuiTest.find("#lLoadBtn").isDisabled());
            assertFalse(GuiTest.find("#lSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#lLineListView").isVisible());
        }
        else if (side == RIGHT){
            assertFalse(GuiTest.find("#rLoadBtn").isDisabled());
            assertFalse(GuiTest.find("#rSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#rLineListView").isVisible());
        }
    }
    private void checkViewAfterLoadWhileEdit(int side){

        if(side == LEFT){
            assertFalse(GuiTest.find("#lEditBtn").isDisabled());
            assertTrue(GuiTest.find("#lSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#lCopyBtn").isDisabled());
        }
        else if (side == RIGHT){
            assertFalse(GuiTest.find("#rEditBtn").isDisabled());
            assertTrue(GuiTest.find("#rSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#rCopyBtn").isDisabled());
        }
    }

    @Test
    public void editTest() throws Exception{

        checkViewBeforeEdit();

        click("#lEditBtn");     // toggle on
        sleep(2000);
        checkViewAfterEditOn(LEFT);

        click("#rEditBtn");
        checkViewAfterEditOn(RIGHT);

        click("#lEditBtn");
        checkViewAfterEditOff(LEFT);

        click("#rEditBtn");
        checkViewAfterEditOff(RIGHT);
    }

    @Test
    public void loadWhileEdit() throws Exception {

        // left side
        click("#lEditBtn");
        loadFile(this, LEFT, file1, false);
        checkViewAfterLoadWhileEdit(LEFT);

        // right side
        click("#rEditBtn");
        loadFile(this, RIGHT, file2, false);
        checkViewAfterLoadWhileEdit(RIGHT);
    }
}