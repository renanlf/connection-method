package br.ufpe.cin.sword.mapping;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

public final class MatrixMapping implements OWLClassExpressionVisitor {
    private Set<Set<Literal>> matrix;
    private final Term term;

    private final Union unionFunction;
    private final ClausalUnionFunction clausalUnionFunction;

    private MatrixMapping(Term term){
        this.term = term;
        this.unionFunction = new Union();
        this.clausalUnionFunction = new ClausalUnionFunction(unionFunction);
    }

    public static Set<Set<Literal>> visitAndGetMatrix(OWLClassExpression ce, Term term) {
        final MatrixMapping visitor = new MatrixMapping(term);
        ce.accept(visitor);
        return visitor.matrix;
    }

    @Override
    public void visit(OWLClass ce) {
        matrix = Collections.singleton(Collections.singleton(new UnaryLiteral(ce.getIRI().getShortForm(), term)));
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        matrix = Collections.singleton(Collections.singleton(new UnaryLiteral(ce.getOperand().asOWLClass().getIRI().getShortForm(), term, false)));
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        matrix = ce.operands()
            .map((arg0) -> visitAndGetMatrix(arg0, term))
            .reduce(unionFunction::apply).get();
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        matrix = ce.operands()
            .map((arg0) -> visitAndGetMatrix(arg0, term))
            .reduce(clausalUnionFunction::apply).get();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        final OWLObjectPropertyExpression role = ce.getProperty();
        if (!role.isOWLObjectProperty()) throw new IllegalArgumentException();
        final Term newTerm = new Term("b");
        final String roleName = role.asOWLObjectProperty().getIRI().getShortForm();
        matrix = unionFunction.apply(Collections.singleton(Collections.singleton(new BinaryLiteral(roleName, term, newTerm, false)))
            , visitAndGetMatrix(ce.getFiller(), newTerm));
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        final OWLObjectPropertyExpression role = ce.getProperty();
        if (!role.isOWLObjectProperty()) throw new IllegalArgumentException();
        final Term newTerm = new Term("x", true);
        final String roleName = role.asOWLObjectProperty().getIRI().getShortForm();
        matrix = clausalUnionFunction.apply(Collections.singleton(Collections.singleton(new BinaryLiteral(roleName, term, newTerm)))
            , visitAndGetMatrix(ce.getFiller(), newTerm));
    }
}
