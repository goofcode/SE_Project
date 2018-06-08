package model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    private FileManager fileManagerA;
    private FileManager fileManagerB;

    @BeforeClass
    public static void setup(){

        System.out.println("FileManager Unit Test Begin");
    }

    @Before
    public void createFile() {

        System.out.println("Start");
        fileManagerA = new FileManager(new File ("src/test/inputs/test_a.txt"));
        fileManagerB = new FileManager(new File ("src/test/inputs/test_b.txt"));
    }

    @Test
    public void synchronizeSizeTest() {

        fileManagerB.synchronizeSize(fileManagerA);
        assertEquals(6, fileManagerB.getLines().size());

        System.out.println("synchronizeSize Test Success");
    }


    @Test
    public void getLinesAsOneStringTest() {

        String confirmString = fileManagerA.getLinesAsOneString();
        assertEquals("asdf\nasdf\nasdf\nasdf\nasdf", confirmString);

        System.out.println("getLinesAsOneString Test Success");
    }


    @Test
    public void setLinesFromOneStringTest() {

        fileManagerB.setLinesFromOneString("Hello\nWorld\nGoodbye\nMy\nLife");

        assertEquals(5, fileManagerB.getLines().size());
        assertEquals("Hello", fileManagerB.getLine(0));

        System.out.println("setLineFromOneString Test Success");
    }

    @Test
    public void copyToTest(){
        List<Integer> bound = new ArrayList<>();
        bound.add(1);
        bound.add(4);

        fileManagerA.copyTo(fileManagerB, bound);

        assertEquals(fileManagerA.getLines().get(1),fileManagerB.getLines().get(1));
        assertEquals(fileManagerA.getLines().get(4),fileManagerB.getLines().get(4));

        System.out.println("copyTo Test Success");
    }

}