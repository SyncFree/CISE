/*******************************************************************************
 * ===========================================================
 * This file is part of the CISE tool software.
 *
 * The CISE tool software contains proprietary and confidential information of Inria.
 * All rights reserved. Reproduction, adaptation or distribution, in whole or in part, is
 * forbidden except by express written permission of Inria.
 * Version V1.5.1., July 2017
 * Authors: Mahsa Najafzadeh, Michał Jabczyński, Marc Shapiro
 * Copyright (C) 2017, Inria
 * ===========================================================
 ******************************************************************************/

package z3fol.model;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Sort;
import z3fol.xpr.Z3Utils;

import java.util.*;

public final class State {

    private final Map<String, Sort> sorts = new HashMap<>();
    private final Map<String, Expr> exprs = new HashMap<>();

    private final Map<String, Expr> tokens = new HashMap<>();

    private final List<Sort> sortss = new ArrayList<>();
    private final List<Expr> exprss = new ArrayList<>();

    private final List<Expr> tokenss = new ArrayList<>();

    public Sort getSort(String name) {
        return sorts.get(name);
    }

    public Expr getExpr(String name) {
        return exprs.get(name);
    }

    public Expr getToken(String name) {
        return tokens.get(name);
    }

    public void putSort(String name, Sort value) {
        sorts.put(name, value);
    }

    public void putExpr(String name, Expr value) {
        exprs.put(name, value);
    }

    public Collection<Sort> getSorts() {
        return sorts.values();
    }

    public Collection<Expr> getExprs() {
        return exprs.values();
    }

    public void addExpr(Expr e) { exprss.add(e);}

    public void addToken(Expr e) { tokenss.add(e);}

    public void addSort(Sort s) { sortss.add(s);}

    public List<Expr> getExprss() { return exprss;}

    public List<Sort> getSortss() { return sortss;}

    public State copy() {
        State s = new State();
        for(Map.Entry e : sorts.entrySet())
            s.sorts.put((String)e.getKey(),(Sort) e.getValue());
        //s.sorts.putAll(sorts);
        for(Map.Entry e : exprs.entrySet())
            s.exprs.put((String) e.getKey(), (Expr) e.getValue());

      //  s.exprs.putAll(exprs);

        return s;
    }

    public BoolExpr compareTo(State s) {

        if (s.exprs.values().size()!= this.exprs.size())
            return Z3Utils.ctx().mkFalse();
        Iterator itr1 = s.exprs.values().iterator();
        Iterator itr2 = this.exprs.values().iterator();
        BoolExpr result= Z3Utils.ctx().mkTrue();


        while(itr1.hasNext()) {
            Expr element1 = (Expr) itr1.next();
            Expr element2 = (Expr) itr2.next();
            result = Z3Utils.ctx().mkAnd(Z3Utils.ctx().mkEq(element1, element2), result);

        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        sorts.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> s.append("type ").append(e.getKey()).append(" ").append(e.getValue()).append('\n'));

        exprs.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> s.append(e.getValue().getSort()).append(" ").append(e.getKey()).append(" := ").append(e.getValue()).append('\n'));

        return s.toString();
    }

}
