package model.Diff;

import java.util.ArrayList;
import java.util.HashMap;

public class LCS {

    public HashMap<String, DiffLine> getDiffByLine(String lLine, String rLine) {
        boolean islinematch=true;
        // if both lines are empty, consider all matches
        if (lLine.isEmpty() && rLine.isEmpty()) {
            HashMap<String, DiffLine> result = new HashMap<>();
            result.put("left", new DiffLine(new ArrayList<>()));
            result.put("right", new DiffLine(new ArrayList<>()));
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

        ArrayList<DiffBlock> lDiffBlocks = new ArrayList<>();
        ArrayList<DiffBlock> rDiffBlocks = new ArrayList<>();

        boolean isMatch;
        int start;

        start = 0;
        isMatch = lMatch[start];
        for (i = 1; i < lLen; i++) {
            if (isMatch != lMatch[i]){
                lDiffBlocks.add(new DiffBlock(lLine.substring(start, i), isMatch));
                islinematch=false;

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
                islinematch=false;
            }
        }
        rDiffBlocks.add(new DiffBlock(rLine.substring(start, rLen),isMatch));

        HashMap<String, DiffLine> result = new HashMap<>();
        result.put("left", new DiffLine(lDiffBlocks,islinematch));
        result.put("right", new DiffLine(rDiffBlocks,islinematch));

        return result;
    }

    public HashMap<String, ArrayList<DiffLine>> getDiff(ArrayList<String> left, ArrayList<String> right) {

        ArrayList<DiffLine> lMismatch = new ArrayList<>();
        ArrayList<DiffLine> rMismatch = new ArrayList<>();

        for (int i = 0; i < Math.max(left.size(), right.size()); i++) {
            HashMap<String, DiffLine> lineMismatch = getDiffByLine(left.get(i), right.get(i));

            lMismatch.add(lineMismatch.get("left"));
            rMismatch.add(lineMismatch.get("right"));
        }


        HashMap<String, ArrayList<DiffLine>> result = new HashMap<>();

        result.put("left", lMismatch);
        result.put("right", rMismatch);
        return result;
    }
}
