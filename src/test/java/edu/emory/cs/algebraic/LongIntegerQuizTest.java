package edu.emory.cs.algebraic;

import edu.emory.cs.algebraic.LongIntegerQuiz;
import edu.emory.cs.algebraic.LongInteger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LongIntegerQuizTest {
    @Test
    public void test0() {
        LongInteger a = new LongIntegerQuiz("123456789");
        a.add(new LongIntegerQuiz("-11223344"));
        assertEquals("112233445", a.toString());
    }
    @Test
    public void test1() {
        LongInteger a = new LongIntegerQuiz("123456789");
        a.add(new LongIntegerQuiz("-1122334455"));
        assertEquals("-998877666", a.toString());
    }
    @Test
    public void test2() {
        LongInteger a = new LongIntegerQuiz("123456789");
        a.add(new LongIntegerQuiz("-1234567898765432112233445566778899"));
        assertEquals("-1234567898765432112233445443322110", a.toString());
    }
    @Test
    public void test3() {
        LongInteger a = new LongIntegerQuiz("123456789");
        a.add(new LongIntegerQuiz("-123456789"));
        assertEquals("0", a.toString());
    }
    @Test
    public void test4() {
        LongInteger a = new LongIntegerQuiz("-123456789");
        a.add(new LongIntegerQuiz("123456789"));
        assertEquals("-0", a.toString());
    }
}
