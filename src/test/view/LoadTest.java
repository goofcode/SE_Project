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

import static org.junit.Assert.*;

public class LoadTest extends GuiTest {

    private static final SettableFuture<Stage> stageFuture = SettableFuture.create();

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



    public static Controller controller;

    @BeforeClass
    public static void makeInstance()throws Exception{
        controller = new Controller();
    }


    @Test
    public void onLoadBtnClicked() {
        String file1 = "asd.txt";
        String file2 = "asd.txt.txt";
        assertTrue(GuiTest.find("#lEditBtn").isDisabled());
        click("#lLoadBtn");
        type("D").type(KeyCode.SHIFT,KeyCode.SEMICOLON).type(KeyCode.ENTER);
        type(file1).type(KeyCode.ENTER);
        sleep(1000);
        assertFalse(GuiTest.find("#lEditBtn").isDisabled());
        assertTrue(GuiTest.find("#rEditBtn").isDisabled());

        click("#rLoadBtn");
        type("D").type(KeyCode.SHIFT,KeyCode.SEMICOLON).type(KeyCode.ENTER);
        type(file2).type(KeyCode.ENTER);

        sleep(1000);
        assertFalse(GuiTest.find("#rEditBtn").isDisabled());
    }


}