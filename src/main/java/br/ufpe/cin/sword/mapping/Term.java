package br.ufpe.cin.sword.mapping;

public class Term {
    private final String value;
    private final boolean variable;

    public Term(String value) {
        this.value = value;
        this.variable = false;
    }

    public Term(String value, boolean variable) {
        this.value = value;
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public boolean isVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return value;
    }
}
