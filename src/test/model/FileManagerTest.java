package model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    private FileManager fileManagerA;
    private FileManager fileManagerB;

    @BeforeClass
    public static void setup(){

        System.out.println("Start");
    }

    @Before
    public void createFile() throws URISyntaxException {

        fileManagerA = new FileManager(new File ("src/test/inputs/test_a.txt"));
        fileManagerB = new FileManager(new File ("src/test/inputs/test_b.txt"));
    }

    @Test
    public void synchronizeSizeTest() {

        fileManagerB.synchronizeSize(fileManagerA);
        assertEquals(6, fileManagerB.getLines().size());
    }


    @Test
    public void getLinesAsOneStringTest() {

        String confirmString = fileManagerA.getLinesAsOneString();
        assertEquals("asdf\nasdf\nasdf\nasdf\nasdf", confirmString);
    }


    @Test
    public void setLinesFromOneStringTest() {

        fileManagerB.setLinesFromOneString("Hello\nWorld\nGoodbye\nMy\nLife");

        assertEquals(5, fileManagerB.getLines().size());
        assertEquals("Hello", fileManagerB.getLine(0));
    }

}