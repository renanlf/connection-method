package br.ufpe.cin.sword.mapping;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class ClausalUnionFunction implements BinaryOperator<Set<Set<Literal>>> {
    private final Union unionFunction;

    public ClausalUnionFunction(Union unionFunction){
        this.unionFunction = unionFunction;
    }

    @Override
    public Set<Set<Literal>> apply(Set<Set<Literal>> arg0, Set<Set<Literal>> arg1) {
        return arg0.parallelStream()
            .map(clause -> clauseAndMatrix(arg1, clause))
            .reduce(unionFunction::apply)
            .get();
    }

    private Set<Set<Literal>> clauseAndMatrix(Set<Set<Literal>> matrix, Set<Literal> clause) {
        return matrix.parallelStream()
            .map(clause2 -> unionOf(clause, clause2))
            .collect(Collectors.toSet());
    }

    private Set<Literal> unionOf(Set<Literal> clause1, Set<Literal> clause2) {
        Set<Literal> union = new HashSet<>();
        union.addAll(clause1);
        union.addAll(clause2);
        return union;
    }
    
}
