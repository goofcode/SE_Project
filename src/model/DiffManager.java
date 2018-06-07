package model;

import model.Diff.DiffLine;

import java.util.List;

import static model.Constants.LEFT;
import static model.Constants.RIGHT;

public class DiffManager {

    private List<List<DiffLine>> diff;
    private int size;
    private int selectStart, selectEnd;

    public void init(List<List<DiffLine>> lcsResult){
        this.diff = lcsResult;
        this.size = diff.get(0).size();
    }

    public List<DiffLine> getLines(int side){
        return diff.get(side);
    }

    public void selectsDiffBound(int selectedLine) {

        resetSelect();

        selectStart = selectEnd = selectedLine;

        // if line is mismatched line
        if(! diff.get(0).get(selectedLine).getMatched()){

            // goes up and down
            while (selectStart >= 0 && !(diff.get(0).get(selectStart).getMatched())) selectStart--;
            while (selectEnd < size && !(diff.get(0).get(selectEnd).getMatched())) selectEnd ++;

            selectStart++; selectEnd--;
        }

        for (int i=selectStart; i<= selectEnd; i++) {
            diff.get(LEFT).get(i).setSelected(true);
            diff.get(RIGHT).get(i).setSelected(true);
        }

    }

    public void merge(int srcSide, FileManager[] fileManagers){

        int dstSide = srcSide == LEFT ? RIGHT : LEFT;

        List<DiffLine> srcDiff = diff.get(srcSide);
        List<DiffLine> dstDiff = diff.get(dstSide);

        FileManager srcFileManager = fileManagers[srcSide];
        FileManager dstFileManager = fileManagers[dstSide];

        for (int i=selectStart; i<=selectEnd; i++){

            // update file manager
            dstFileManager.setLine(i, srcFileManager.getLine(i));

            // update diff manager
            srcDiff.get(i).copyTo(dstDiff.get(i));
        }

        resetSelect();
    }


    private void resetSelect() {
        for (int i = 0; i < size; i++) {
            diff.get(LEFT).get(i).setSelected(false);
            diff.get(RIGHT).get(i).setSelected(false);
        }
    }

}
