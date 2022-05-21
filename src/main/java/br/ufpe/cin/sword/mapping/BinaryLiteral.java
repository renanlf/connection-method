package br.ufpe.cin.sword.mapping;

public class BinaryLiteral implements Literal {
    private final String role;
    private final Term first;
    private final Term second;
    private final boolean positive;

    public BinaryLiteral(String role, Term first, Term second) {
        this.role = role;
        this.first = first;
        this.second = second;
        this.positive = true;
    }

    public BinaryLiteral(String role, Term first, Term second, boolean positive) {
        this.role = role;
        this.first = first;
        this.second = second;
        this.positive = positive;
    }

    public String getRole() {
        return role;
    }

    public Term getFirst() {
        return first;
    }

    public Term getSecond() {
        return second;
    }

    public boolean isPositive() {
        return positive;
    }

    public String toString() {
        return (positive ? role : "not " + role) + "("+first+", " + second + ")";
    }
}
