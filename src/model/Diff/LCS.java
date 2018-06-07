package model.Diff;

import java.util.ArrayList;
import java.util.List;

import static model.Constants.LEFT;
import static model.Constants.RIGHT;


public class LCS {

    public List<DiffLine> getDiffByLine(String lLine, String rLine) {

        List<DiffLine> result = new ArrayList<>();

        List<DiffBlock> lDiffBlocks = new ArrayList<>();
        List<DiffBlock> rDiffBlocks = new ArrayList<>();


        // if both lines are empty, consider all matches
        if (lLine.isEmpty() && rLine.isEmpty()) {
            result.add(new DiffLine(lDiffBlocks, true));
            result.add(new DiffLine(rDiffBlocks, true));
            return result;
        }
        // if only one of line is empty
        else if(lLine.isEmpty() || rLine.isEmpty()){

            if (! rLine.isEmpty()) rDiffBlocks.add(new DiffBlock(rLine, false));
            if (! lLine.isEmpty()) lDiffBlocks.add(new DiffBlock(lLine, false));

            result.add(new DiffLine(lDiffBlocks, false));
            result.add(new DiffLine(rDiffBlocks, false));
            return result;
        }

        int lLen = lLine.length();
        int rLen = rLine.length();
        int[][] lcs = new int[lLen + 1][rLen + 1];


        for (int i = 0; i < lLen + 1; i++) lcs[i][0] = 0;
        for (int j = 0; j < rLen + 1; j++) lcs[0][j] = 0;

        for (int i = 1; i < lLen + 1; i++) {
            for (int j = 1; j < rLen + 1; j++) {

                // 현재 비교하는 값이 서로 같다면, lcs는 + 1
                if (lLine.charAt(i - 1) == rLine.charAt(j - 1))
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;

                    // 서로 다르다면 LCS의 값을 왼쪽 혹은 위에서 가져온다.
                else lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
            }
        }

        // manipulate check
        boolean[] lMatch = new boolean[lLen];
        boolean[] rMatch = new boolean[rLen];

        int i = lLen, j = rLen;
        while (lcs[i][j] != 0) {
            if (lcs[i][j] == lcs[i][j - 1]) j--;
            else if (lcs[i][j] == lcs[i - 1][j]) i--;
            else if (lcs[i][j] - 1 == lcs[i - 1][j - 1]) {
                lMatch[i - 1] = rMatch[j - 1] = true;
                i--;
                j--;
            }
        }

        boolean lineMatch = true;
        for (boolean match : lMatch) if(!match) lineMatch = false;
        for (boolean match : rMatch) if(!match) lineMatch = false;


        boolean isMatch;
        int start;

        start = 0;
        isMatch = lMatch[start];
        for (i = 1; i < lLen; i++) {
            if (isMatch != lMatch[i]){
                lDiffBlocks.add(new DiffBlock(lLine.substring(start, i), isMatch));

                start = i;
                isMatch = !isMatch;

            }
        }
        lDiffBlocks.add(new DiffBlock(lLine.substring(start, lLen),isMatch));

        start = 0;
        isMatch = rMatch[start];
        for (i = 1; i < rLen; i++) {
            if (isMatch != rMatch[i]){
                rDiffBlocks.add(new DiffBlock(rLine.substring(start, i), isMatch));

                start = i;
                isMatch = !isMatch;
            }
        }
        rDiffBlocks.add(new DiffBlock(rLine.substring(start, rLen),isMatch));

        result.add(new DiffLine(lDiffBlocks,lineMatch));
        result.add(new DiffLine(rDiffBlocks,lineMatch));

        return result;
    }

    public List<List<DiffLine>> getDiff(List<String> left, List<String> right) {

        ArrayList<DiffLine> lMismatch = new ArrayList<>();
        ArrayList<DiffLine> rMismatch = new ArrayList<>();

        // should be padded by file manager
        assert (left.size() == right.size());

        int size = left.size();


        // construct diff from line diff
        for (int i = 0; i < size; i++) {

            List<DiffLine> lineMismatch = getDiffByLine(left.get(i), right.get(i));

            assert(lineMismatch.get(LEFT).getMatched() == lineMismatch.get(RIGHT).getMatched());
            lMismatch.add(lineMismatch.get(LEFT));
            rMismatch.add(lineMismatch.get(RIGHT));
        }

        List<List<DiffLine>> result = new ArrayList<>();

        result.add(lMismatch);
        result.add(rMismatch);

        return result;
    }
}
