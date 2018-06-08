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

public class LoadTest extends GuiTest {

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
        FXTestUtils.launchApp(LoadTest.TestProgramInfoWindow.class); // You can add start parameters here
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

    private void checkBtnActivationBeforeLoad(int side){

        if(side == LEFT) {
            assertTrue(GuiTest.find("#lEditBtn").isDisabled());
            assertTrue(GuiTest.find("#lSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#lCopyBtn").isDisabled());
        }
        else if (side == RIGHT){
            assertTrue(GuiTest.find("#rEditBtn").isDisabled());
            assertTrue(GuiTest.find("#rSaveBtn").isDisabled());
            assertTrue(GuiTest.find("#rCopyBtn").isDisabled());
        }
        assertTrue(GuiTest.find("#compareBtn").isDisabled());
    }
    private void checkBtnActivationAfterLoad(int side){

        if(side == LEFT) {
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
    private void checkBtnActivationAfterLoadsOnBothSides(){
        assertFalse(GuiTest.find("#compareBtn").isDisabled());
    }
    private void checkBtnActivationAfterLoadsNotOnBothSides(){
        assertTrue(GuiTest.find("#compareBtn").isDisabled());
    }

    @Test
    public void loadFileTest() {

        System.out.println("Success!!!!!!!!!!!!!!!!!!!!!!!");
        checkBtnActivationBeforeLoad(LEFT);
        loadFile(this, LEFT, file1, false);
        checkBtnActivationAfterLoad(LEFT);

        checkBtnActivationBeforeLoad(RIGHT);
        loadFile(this, RIGHT, file2, false);
        checkBtnActivationAfterLoad(RIGHT);

        checkBtnActivationAfterLoadsOnBothSides();


    }

    @Test
    public void loadFileFailTest() throws Exception {

        checkBtnActivationBeforeLoad(LEFT);
        loadFile(this, LEFT, file1, true);
        type(KeyCode.ENTER);
        checkBtnActivationBeforeLoad(LEFT);

        checkBtnActivationBeforeLoad(RIGHT);
        loadFile(this, RIGHT, file2, false);
        checkBtnActivationAfterLoad(RIGHT);

        checkBtnActivationAfterLoadsNotOnBothSides();



    }

}