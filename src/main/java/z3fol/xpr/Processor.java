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

package z3fol.xpr;

import com.microsoft.z3.BoolExpr;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import z3fol.antlr.XPRLexer;
import z3fol.antlr.XPRParser;
import z3fol.model.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Processor {

    public static List<BoolExpr> process(State state, List<String> xprs) {
        String text = xprs.stream().map(s -> s + ";").collect(Collectors.joining());

      //  List<BoolExpr> l=new ArrayList<>();
      //  l.add(Z3Utils.ctx().mkFalse());
      //  if(text==null)
       //     return l ;

        XPRErrorHandler errorHandler = new XPRErrorHandler();

        // Get our lexer
        XPRLexer lexer = new XPRLexer(new ANTLRInputStream(text));
        lexer.addErrorListener(errorHandler);

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        XPRParser parser = new z3fol.antlr.XPRParser(tokens);
        parser.addErrorListener(errorHandler);

        // Specify our entry point
        XPRParser.DocumentContext documentContext = parser.document();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        DocumentListener listener = new DocumentListener(Z3Utils.ctx(), state);
        walker.walk(listener, documentContext);

        return listener.facts();
    }

    public static List<BoolExpr> process(State state, String... xprs) {
        return process(state, Arrays.asList(xprs));
    }

}
