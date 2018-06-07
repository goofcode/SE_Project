package view;

import view.Main;
import com.google.common.util.concurrent.SettableFuture;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.loadui.testfx.GuiTest;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ControllerTest extends GuiTest {
    private static final SettableFuture<Stage> stageFuture = SettableFuture.create();
    public Button btn;

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


    @Override
    protected Parent getRootNode() {
        return stage.getScene().getRoot();
    }



    public static Controller controller;


    @BeforeClass
    public static void makeInstance()throws Exception{
        controller = new Controller();
    }

    @Before
    public void beforeTest()throws Exception{
        System.out.println("before!!!!!!");
    }

    @Test
    public void initialize() {
    }

    @Test
    public void onLoadBtnClicked() {
        btn = find("#lLoadBtn");

    }

    @Test
    public void onEditBtnClicked() {
    }

    @Test
    public void onSaveBtnClicked() {
    }

    @Test
    public void onCompareBtnClicked() {
    }

    @Test
    public void onDiffListViewClicked() {
    }

    @Test
    public void onCopyBtnClicked() {
    }
}