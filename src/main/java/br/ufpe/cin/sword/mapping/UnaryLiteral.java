package br.ufpe.cin.sword.mapping;

public class UnaryLiteral implements Literal {
    private final String concept;
    private final Term term;
    private final boolean positive;

    public UnaryLiteral(String concept, Term term) {
        this.concept = concept;
        this.term = term;
        this.positive = true;
    }

    public UnaryLiteral(String concept, Term term, boolean positive) {
        this.concept = concept;
        this.term = term;
        this.positive = positive;
    }

    public String getConcept() {
        return concept;
    }

    public Term getTerm() {
        return term;
    }

    public boolean isPositive() {
        return positive;
    }

    public String toString() {
        return (positive ? concept : "not " + concept) + "("+term+")";
    }
}
