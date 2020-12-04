
package edu.emory.cs.dynamic;

import edu.emory.cs.dynamic.lcs.LCS;
import edu.emory.cs.dynamic.lcs.LCSDynamic;
import edu.emory.cs.dynamic.lcs.LCSQuiz;
import edu.emory.cs.dynamic.lcs.LCSRecursive;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class LCSTest {
    @Test
    public void compare() {
        LCS r = new LCSRecursive();
        LCS d = new LCSDynamic();
        LCSQuiz q = new LCSQuiz();

        String a = "ACGTCGTGT";
        String b = "CTAGTGGAG";

        String x = "ABCBDAB";
        String y = "BDCABA";

        String s = "GAATGTCCTTTCTCTAAGTCCTAAG";
        String t = "GGAGACTTACAGGAAAGAGATTCAGGATTCAGGAGGCCTACCATGAAGATCAAG";

//        assertEquals(r.solve(a, b), d.solve(a, b));
//
//        System.out.println(r.solve(a, b));
//        System.out.println(d.solve(a, b));
//        System.out.println(q.solveAll(a, b));
//
//        LCSQuiz q2 = new LCSQuiz();
//
//        System.out.println(r.solve(x, y));
//        System.out.println(d.solve(x, y));
//        System.out.println(q.solveAll(x, y));

//        LCSQuiz q3 = new LCSQuiz();

//        System.out.println(r.solve(s, t));
//        System.out.println(d.solve(s, t));
        System.out.println(q.solveAll(s, t));
    }
}
