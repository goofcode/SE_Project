package model.Diff;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiffLineTest {

    private List<DiffBlock> notEmptyA;
    private List<DiffBlock> notEmptyB;
    private List<DiffBlock> emptyA;
    private List<DiffBlock> emptyB;

    private DiffLine sourceSth;
    private DiffLine dstEmpty;
    private DiffLine dstSth;
    private DiffLine sourceEmpty;


    @Before
    public void firstSetUp(){
        notEmptyA = new ArrayList<>();
        notEmptyB = new ArrayList<>();
        emptyA = new ArrayList<>();
        emptyB = new ArrayList<>();

        DiffBlock a = new DiffBlock("asdf",true);
        DiffBlock b = new DiffBlock("f",false);
        DiffBlock c = new DiffBlock("qwer",true);
        DiffBlock d = new DiffBlock("ty",false);
        notEmptyA.add(a);
        notEmptyA.add(b);
        notEmptyB.add(c);
        notEmptyB.add(d);

        sourceSth = new DiffLine(notEmptyA, true);
        dstEmpty = new DiffLine(emptyA, false);
        dstSth = new DiffLine(notEmptyB, true);
        sourceEmpty = new DiffLine(emptyB, false);
    }

    @Test
    public void SthToEmptyCopyToTest() {
        sourceSth.copyTo(dstEmpty);

        assertEquals(1, sourceSth.getLine().size());
        assertEquals(1, dstEmpty.getLine().size());
        assertTrue(dstEmpty.getMatched());

    }


    @Test
    public void EmptyToSthCopyToTest() {
        sourceEmpty.copyTo(dstSth);

        assertEquals(1, sourceEmpty.getLine().size());
        assertEquals(1, dstSth.getLine().size());
        assertTrue(dstSth.getMatched());
    }

    @Test
    public void SthToSthCopyToTest() {

        sourceSth.copyTo(dstSth);

        assertEquals(1, sourceSth.getLine().size());
        assertEquals(1, dstSth.getLine().size());
        assertTrue(dstSth.getMatched());
    }


    @Test
    public void EmptyToEmptyCopyToTest() {
        sourceEmpty.copyTo(dstEmpty);

        assertEquals(1, sourceEmpty.getLine().size());
        assertEquals(1, dstEmpty.getLine().size());
        assertTrue(dstEmpty.getMatched());
    }

}