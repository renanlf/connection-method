package br.ufpe.cin.sword;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import br.ufpe.cin.sword.mapping.Literal;
import br.ufpe.cin.sword.mapping.MatrixMapping;
import br.ufpe.cin.sword.mapping.Term;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLClass c = df.getOWLClass("C");
        OWLClass d = df.getOWLClass("D");
        OWLClass e = df.getOWLClass("E");
        OWLClass f = df.getOWLClass("F");
        OWLClass g = df.getOWLClass("G");
        OWLClass h = df.getOWLClass("H");
        OWLObjectProperty r = df.getOWLObjectProperty("r");
        Term t = new Term("a");

        OWLClassExpression cAndd = df.getOWLObjectIntersectionOf(c, d);
        OWLClassExpression eOuNotf = df.getOWLObjectUnionOf(e, df.getOWLObjectComplementOf(f));
        OWLClassExpression forallrd = df.getOWLObjectAllValuesFrom(r, d);
        OWLClassExpression andAll = df.getOWLObjectIntersectionOf(cAndd, eOuNotf, g, h, forallrd);

        Set<Set<Literal>> matrix = MatrixMapping.visitAndGetMatrix(andAll, t);
        System.err.println(matrix);
    }
}
