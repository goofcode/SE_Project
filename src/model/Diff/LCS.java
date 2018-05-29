package model.Diff;

import java.util.ArrayList;
import java.util.HashMap;

public class LCS {

    private class Pos {
        private int row, col;
        Pos(int row, int col){this.row = row; this.col = col;}
        public int getRow() {return this.row;}
        public int getCol() {return this.col;}
    }

    public HashMap<String, ArrayList<Seq>> getMismatchSeqByLine(String lLine, String rLine) {

        // if both lines are empty, consider all matches
        if (lLine.isEmpty() && rLine.isEmpty())
        {
            HashMap<String, ArrayList<Seq>> result = new HashMap<>();

            ArrayList<Seq> lMatch = new ArrayList<>();
            ArrayList<Seq> rMatch = new ArrayList<>();
            lMatch.add(new Seq(0, lLine.length()));
            rMatch.add(new Seq(0, rLine.length()));
            result.put("left", lMatch);
            result.put("right", rMatch);

            return result;
        }


        int len1 = lLine.length();
        int len2 = rLine.length();
        int[][] check = new int[len1 + 1][len2 + 1];

        ArrayList<Pos> posMatch = new ArrayList<>();

        for (int i = 0; i < len1 + 1; i++)
            for (int j = 0; j < len2 + 1; j++)
                check[i][j] = 0;

        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {

                // 현재 비교하는 값이 서로 같다면, lcs는 + 1
                if (lLine.charAt(i) == rLine.charAt(j)) {
                    check[i][j] = check[i - 1][j - 1] + 1;
                    posMatch.add(new Pos(i-1,j-1));
                }

                // 서로 다르다면 LCS의 값을 왼쪽 혹은 위에서 가져온다.
                else check[i][j] = Math.max(check[i-1][j], check[i][j-1]);
            }
        }

        // if there is no match at all
        if (posMatch.size() == 0)
        {
            HashMap<String, ArrayList<Seq>> result = new HashMap<>();

            result.put("left", new ArrayList<>());
            result.put("right", new ArrayList<>());

            return result;
        }


        ArrayList<Seq> lNotMatch = new ArrayList<>();
        ArrayList<Seq> rNotMatch = new ArrayList<>();

        int begin, end;

        // for line 1
        begin = 0;
        for(Pos pos: posMatch) {
            end = pos.getCol();
            if (begin < end)
                lNotMatch.add(new Seq(begin, end));
        }

        // for line 2
        begin =0;
        for (Pos pos : posMatch) {
            end = pos.getRow();
            if (begin < end)
                rNotMatch.add(new Seq(begin, end));
        }

        HashMap<String, ArrayList<Seq>> result = new HashMap<>();
        result.put("left", lNotMatch);
        result.put("right", rNotMatch);

        return result;
    }

    public HashMap<String, ArrayList<ArrayList<Seq>>> getDismatch(ArrayList<String> left, ArrayList<String> right) {

        ArrayList<ArrayList<Seq>> lMismatch = new ArrayList<>();
        ArrayList<ArrayList<Seq>> rMismatch = new ArrayList<>();

        for(int i=0;i<Math.max(left.size(), right.size()); i++){

            HashMap<String, ArrayList<Seq>> lineMismatch = getMismatchSeqByLine(left.get(i), right.get(i));
            lMismatch.add(lineMismatch.get("left"));
            rMismatch.add(lineMismatch.get("right"));
        }


        HashMap<String, ArrayList<ArrayList<Seq>>> result = new HashMap<>();

        result.put("left", lMismatch);
        result.put("right", rMismatch);
        return result;
    }
}
