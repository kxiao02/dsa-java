package edu.emory.cs.algebraic;

public enum Sign {
    POSITIVE('+'),
    NEGATIVE('-');

    private final char value;

    Sign(char value) {
        this.value = value;
    }

    public char value() {
        return value;
    }
}
