
package edu.emory.cs.dynamic.lcs;

import java.util.TreeSet;
import java.util.Set;

public class LCSQuiz extends LCSDynamic {

    private int[][] table;
    private final Set<String> result = new TreeSet<>();

    /**
     * @param a the first string.
     * @param b the second string.
     * @return a set of all longest common sequences between the two strings.
     */
    public Set<String> solveAll(String a, String b) {
        char[] c = a.toCharArray();
        char[] d = b.toCharArray();
        int i = a.length();
        int j = b.length();
        create(c, d, i, j);
        backTrack(c, d, i, j, "");
        return result;
    }

    private void create(char[]c, char[] d, int N, int M) {
        table = new int[N + 1][M + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M + 1; j++) {
                if (i == 0 || j == 0) table[i][j] = 0;
                else if (c[i - 1] == d[j - 1]) table[i][j] = table[i - 1][j - 1] + 1;
                else table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
            }
        }
    }

    private void backTrack(char[]c, char[] d, int i, int j, String str) {
        StringBuilder LCSSB = new StringBuilder(str);
        while (i > 0 && j > 0) {
            if (c[i - 1] == d[j - 1]) {
                LCSSB.append(c[i - 1]);
                i--;
                j--;
            } else {
                if (table[i - 1][j] > table[i][j - 1]) i--;
                else if (table[i - 1][j] < table[i][j - 1]) j--;
                else {
                    backTrack(c, d, i - 1, j, LCSSB.toString());
                    backTrack(c, d, i, j - 1, LCSSB.toString());
                    return;
                }
            }
        }
        result.add(LCSSB.reverse().toString());
    }
}

