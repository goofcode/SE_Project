package view;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotImage;
import com.sun.javafx.robot.impl.BaseFXRobot;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;

public class ControllerTest extends ApplicationTest {



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
        clickOn("#lLoadBtn");

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