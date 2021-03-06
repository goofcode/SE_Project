package model.Diff;

import com.sun.istack.internal.NotNull;
import org.assertj.core.internal.Diff;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;
import static org.junit.Assert.*;

public class LCSTest {

    private LCS lcsAlgo = new LCS();

    private String leftString;
    private String rightString;

    private List<DiffLine> diffList;

    private List<String> left;
    private List<String> right;

    @BeforeClass
    public static void start(){

        System.out.println("LCS Unit Test Begin");
    }

    @Before
    public void firstSetUp() throws IOException {
        System.out.println("Start");

        left = new ArrayList<>();
        right = new ArrayList<>();


        File fileA = new File("src/test/inputs/testDiff_a.txt");
        File fileB = new File("src/test/inputs/testDiff_b.txt");

        try {
            Charset charset = StandardCharsets.UTF_8;
            left.addAll(Files.readAllLines(fileA.toPath(), charset));
            right.addAll(Files.readAllLines(fileB.toPath(), charset));

        } catch (IOException e) {
            e.printStackTrace();
        }

        leftString = "Hello World";
        rightString = "Hello World";

    }

    @Test
    public void getDiffByLineStringEqualTest() {
        diffList = lcsAlgo.getDiffByLine(leftString, rightString);

        assertEquals(2, diffList.size());

        assertEquals(1, diffList.get(0).getLine().size());
        assertEquals(1, diffList.get(1).getLine().size());

        assertEquals("Hello World", diffList.get(0).getLine().get(0).getContent());
        assertEquals("Hello World", diffList.get(1).getLine().get(0).getContent());

        assertTrue(diffList.get(0).getMatched());
        assertTrue(diffList.get(1).getMatched());

        System.out.println("getDiffByLineString Equal Test Success");
    }

    @Test
    public void getDiffByLineStringDiffTest() {
        leftString = "My Address is";
        rightString = "My name is";

        diffList = lcsAlgo.getDiffByLine(leftString, rightString);

        assertEquals(2, diffList.size());

        assertEquals(5, diffList.get(0).getLine().size());
        assertEquals(3, diffList.get(1).getLine().size());

        assertEquals("Addr", diffList.get(0).getLine().get(1).getContent());
        assertEquals("nam", diffList.get(1).getLine().get(1).getContent());

        assertFalse(diffList.get(0).getMatched());
        assertFalse(diffList.get(1).getMatched());

        System.out.println("getDiffByLineString Diff Test Success");
    }

    @Test
    public void getDiffTest() {
        List<List<DiffLine>> diffListList = lcsAlgo.getDiff(left, right);

        assertEquals(4, diffListList.get(0).size());
        assertEquals("qwert", diffListList.get(0).get(0).getLine().get(0).getContent());

        System.out.println("getDiff Test Success");
    }
}