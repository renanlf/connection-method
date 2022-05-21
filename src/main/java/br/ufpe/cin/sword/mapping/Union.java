package br.ufpe.cin.sword.mapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

public class Union implements BinaryOperator<Set<Set<Literal>>> {

    @Override
    public Set<Set<Literal>> apply(Set<Set<Literal>> arg0, Set<Set<Literal>> arg1) {
        final Set<Set<Literal>> set = new HashSet<>();
        set.addAll(arg0);
        set.addAll(arg1);
        return Collections.unmodifiableSet(set);
    }
    
}
