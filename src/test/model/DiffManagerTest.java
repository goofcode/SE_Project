package model;

import model.Diff.DiffBlock;
import model.Diff.DiffLine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DiffManagerTest {

    private DiffManager diffManager;

    @Before
    public void firstStep() {
        System.out.println("DiffManager Unit test begin");

        List<List<DiffLine>> lcsResult = new ArrayList<>();

        List<DiffLine> lDiffLineList = new ArrayList<>();
        List<DiffLine> rDiffLineList = new ArrayList<>();

        List<DiffBlock> matchLine = new ArrayList<>();
        matchLine.add(new DiffBlock("match", true));
        List<DiffBlock> lMismatchLine = new ArrayList<>();
        lMismatchLine.add(new DiffBlock("", false));
        List<DiffBlock> rMismatchLine = new ArrayList<>();
        rMismatchLine.add(new DiffBlock("mismatch", false));

        /* first line */
        lDiffLineList.add(new DiffLine(new ArrayList<>(matchLine), true));
        rDiffLineList.add(new DiffLine(new ArrayList<>(matchLine), true));

        /* second line */
        lDiffLineList.add(new DiffLine(new ArrayList<>(lMismatchLine), false));
        rDiffLineList.add(new DiffLine(new ArrayList<>(rMismatchLine), false));

        /* third line */
        lDiffLineList.add(new DiffLine(new ArrayList<>(lMismatchLine), false));
        rDiffLineList.add(new DiffLine(new ArrayList<>(rMismatchLine), false));

        /* forth line */
        lDiffLineList.add(new DiffLine(new ArrayList<>(matchLine), true));
        rDiffLineList.add(new DiffLine(new ArrayList<>(matchLine), true));


        lcsResult.add(lDiffLineList);
        lcsResult.add(rDiffLineList);

        diffManager = new DiffManager();

        diffManager.init(lcsResult);
    }

    @Test
    public void selectedBoundAndMergeTest() {
        diffManager.selectsDiffBound(1);
        assertEquals(1, diffManager.getSelectStart());
        assertEquals(2, diffManager.getSelectEnd());

        diffManager.selectsDiffBound(2);
        assertEquals(1, diffManager.getSelectStart());
        assertEquals(2, diffManager.getSelectEnd());

        List<Integer> listInt = diffManager.mergeFrom(0);

        assertEquals(1, listInt.get(0).intValue());

        System.out.println("Test Success");

    }

}