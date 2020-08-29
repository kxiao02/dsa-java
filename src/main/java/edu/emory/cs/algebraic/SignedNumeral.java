package edu.emory.cs.algebraic;

public abstract class SignedNumeral<T extends SignedNumeral<T>> implements Numeral<T> {
    protected Sign sign;

    public SignedNumeral() {
        this(Sign.POSITIVE);
    }

    public SignedNumeral(Sign sign) {
        this.sign = sign;
    }

    public boolean isPositive() {
        return sign == Sign.POSITIVE;
    }

    public boolean isNegative() {
        return sign == Sign.NEGATIVE;
    }

    public void flipSign() {
        sign = isPositive() ? Sign.NEGATIVE : Sign.POSITIVE;
    }

    public void subtract(T n) {
        n.flipSign();
        add(n);
        n.flipSign();
    }

    public abstract void multiply(T n);
}
