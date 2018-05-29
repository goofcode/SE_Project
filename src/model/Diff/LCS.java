package model.Diff;

public class LCS {
    public static String lcs(String left, String right){
        String l = '0' + left;
        String r = '0' + right;

        int len1 = l.length();
        int len2 = r.length();
        int[][] check = new int[len1][len2];

        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (i == 0 || j == 0) {
                    check[i][j] = 0;
                    continue;
                }

                // 현재 비교하는 값이 서로 같다면, lcs는 + 1
                if (l.charAt(i) == r.charAt(j))
                    check[i][j] = check[i - 1][j - 1] + 1;

                    // 서로 다르다면 LCS의 값을 왼쪽 혹은 위에서 가져온다.
                else {
                    if (check[i - 1][j] > check[i][j - 1])
                        check[i][j] = check[i - 1][j];
                    else
                        check[i][j] = check[i][j - 1];
                }
            }
        }

        int i = len1 - 1;
        int j = len2 - 1;
        StringBuffer sb = new StringBuffer();
        while (check[i][j] != 0) {
            if (check[i][j] == check[i][j - 1])
                j--;
            else if (check[i][j] == check[i - 1][j])
                i--;
            else if (check[i][j] - 1 == check[i - 1][j - 1]) {
                sb.append(l.charAt(i));
                i--;
                j--;
            }

        }
        return sb.reverse().toString();

    }
}
