package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger{

    public LongIntegerQuiz(String n) {
        super(n);
    }

    @Override
    protected void addDifferentSign(LongInteger n) {
        int  m = Math.max(digits.length, n.digits.length), tmp = compareAbs(n), e;
        byte[] result = new byte[m], big = digits, small = n.digits;
        if (tmp < 0){
            big = n.digits;
            small = digits;
            this.sign = n.sign;
        }
        System.arraycopy(big, 0, result, 0, big.length);

        for (int i = 0; i < small.length; i++) {
            result[i] -= small[i];
            if (result[i] < 0) {
                result[i] += 10;
                result[i + 1] -= 1;
            }
        }

        for (e = m - 1; e > 0; e--)
            if (result[e] != 0) break;
        digits = ++e < m ? Arrays.copyOf(result, e) : result;
    }
}
