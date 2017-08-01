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

// Generated from XPR.g4 by ANTLR 4.5.1
package z3fol.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XPRParser}.
 */
public interface XPRListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XPRParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(XPRParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(XPRParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(XPRParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(XPRParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#typeIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeIdentifier(XPRParser.TypeIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#typeIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeIdentifier(XPRParser.TypeIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#typeTuple}.
	 * @param ctx the parse tree
	 */
	void enterTypeTuple(XPRParser.TypeTupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#typeTuple}.
	 * @param ctx the parse tree
	 */
	void exitTypeTuple(XPRParser.TypeTupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#typeSet}.
	 * @param ctx the parse tree
	 */
	void enterTypeSet(XPRParser.TypeSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#typeSet}.
	 * @param ctx the parse tree
	 */
	void exitTypeSet(XPRParser.TypeSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(XPRParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(XPRParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variableTuple}.
	 * @param ctx the parse tree
	 */
	void enterVariableTuple(XPRParser.VariableTupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variableTuple}.
	 * @param ctx the parse tree
	 */
	void exitVariableTuple(XPRParser.VariableTupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variableIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableIdentifier(XPRParser.VariableIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variableIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableIdentifier(XPRParser.VariableIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variableWithType}.
	 * @param ctx the parse tree
	 */
	void enterVariableWithType(XPRParser.VariableWithTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variableWithType}.
	 * @param ctx the parse tree
	 */
	void exitVariableWithType(XPRParser.VariableWithTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variableWithTypeList}.
	 * @param ctx the parse tree
	 */
	void enterVariableWithTypeList(XPRParser.VariableWithTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variableWithTypeList}.
	 * @param ctx the parse tree
	 */
	void exitVariableWithTypeList(XPRParser.VariableWithTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#anyExpression}.
	 * @param ctx the parse tree
	 */
	void enterAnyExpression(XPRParser.AnyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#anyExpression}.
	 * @param ctx the parse tree
	 */
	void exitAnyExpression(XPRParser.AnyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#anyExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterAnyExpressionList(XPRParser.AnyExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#anyExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitAnyExpressionList(XPRParser.AnyExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#sumop}.
	 * @param ctx the parse tree
	 */
	void enterSumop(XPRParser.SumopContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#sumop}.
	 * @param ctx the parse tree
	 */
	void exitSumop(XPRParser.SumopContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#mulop}.
	 * @param ctx the parse tree
	 */
	void enterMulop(XPRParser.MulopContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#mulop}.
	 * @param ctx the parse tree
	 */
	void exitMulop(XPRParser.MulopContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#arithExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithExpression(XPRParser.ArithExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#arithExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithExpression(XPRParser.ArithExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#arithExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterArithExpressionList(XPRParser.ArithExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#arithExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitArithExpressionList(XPRParser.ArithExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#arithSum}.
	 * @param ctx the parse tree
	 */
	void enterArithSum(XPRParser.ArithSumContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#arithSum}.
	 * @param ctx the parse tree
	 */
	void exitArithSum(XPRParser.ArithSumContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#arithMul}.
	 * @param ctx the parse tree
	 */
	void enterArithMul(XPRParser.ArithMulContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#arithMul}.
	 * @param ctx the parse tree
	 */
	void exitArithMul(XPRParser.ArithMulContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#arithPow}.
	 * @param ctx the parse tree
	 */
	void enterArithPow(XPRParser.ArithPowContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#arithPow}.
	 * @param ctx the parse tree
	 */
	void exitArithPow(XPRParser.ArithPowContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#arithAtom}.
	 * @param ctx the parse tree
	 */
	void enterArithAtom(XPRParser.ArithAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#arithAtom}.
	 * @param ctx the parse tree
	 */
	void exitArithAtom(XPRParser.ArithAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setExpression}.
	 * @param ctx the parse tree
	 */
	void enterSetExpression(XPRParser.SetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setExpression}.
	 * @param ctx the parse tree
	 */
	void exitSetExpression(XPRParser.SetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setSum}.
	 * @param ctx the parse tree
	 */
	void enterSetSum(XPRParser.SetSumContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setSum}.
	 * @param ctx the parse tree
	 */
	void exitSetSum(XPRParser.SetSumContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setMul}.
	 * @param ctx the parse tree
	 */
	void enterSetMul(XPRParser.SetMulContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setMul}.
	 * @param ctx the parse tree
	 */
	void exitSetMul(XPRParser.SetMulContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setSumOp}.
	 * @param ctx the parse tree
	 */
	void enterSetSumOp(XPRParser.SetSumOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setSumOp}.
	 * @param ctx the parse tree
	 */
	void exitSetSumOp(XPRParser.SetSumOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setAtom}.
	 * @param ctx the parse tree
	 */
	void enterSetAtom(XPRParser.SetAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setAtom}.
	 * @param ctx the parse tree
	 */
	void exitSetAtom(XPRParser.SetAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#inlineSet}.
	 * @param ctx the parse tree
	 */
	void enterInlineSet(XPRParser.InlineSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#inlineSet}.
	 * @param ctx the parse tree
	 */
	void exitInlineSet(XPRParser.InlineSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#inlineSetValues}.
	 * @param ctx the parse tree
	 */
	void enterInlineSetValues(XPRParser.InlineSetValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#inlineSetValues}.
	 * @param ctx the parse tree
	 */
	void exitInlineSetValues(XPRParser.InlineSetValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#inlineSetEmpty}.
	 * @param ctx the parse tree
	 */
	void enterInlineSetEmpty(XPRParser.InlineSetEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#inlineSetEmpty}.
	 * @param ctx the parse tree
	 */
	void exitInlineSetEmpty(XPRParser.InlineSetEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#cmpop}.
	 * @param ctx the parse tree
	 */
	void enterCmpop(XPRParser.CmpopContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#cmpop}.
	 * @param ctx the parse tree
	 */
	void exitCmpop(XPRParser.CmpopContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#cmpStatement}.
	 * @param ctx the parse tree
	 */
	void enterCmpStatement(XPRParser.CmpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#cmpStatement}.
	 * @param ctx the parse tree
	 */
	void exitCmpStatement(XPRParser.CmpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#eqop}.
	 * @param ctx the parse tree
	 */
	void enterEqop(XPRParser.EqopContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#eqop}.
	 * @param ctx the parse tree
	 */
	void exitEqop(XPRParser.EqopContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#eqStatement}.
	 * @param ctx the parse tree
	 */
	void enterEqStatement(XPRParser.EqStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#eqStatement}.
	 * @param ctx the parse tree
	 */
	void exitEqStatement(XPRParser.EqStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setop}.
	 * @param ctx the parse tree
	 */
	void enterSetop(XPRParser.SetopContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setop}.
	 * @param ctx the parse tree
	 */
	void exitSetop(XPRParser.SetopContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(XPRParser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(XPRParser.SetStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#subsetExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubsetExpression(XPRParser.SubsetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#subsetExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubsetExpression(XPRParser.SubsetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#boolExpression}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpression(XPRParser.BoolExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#boolExpression}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpression(XPRParser.BoolExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#logop}.
	 * @param ctx the parse tree
	 */
	void enterLogop(XPRParser.LogopContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#logop}.
	 * @param ctx the parse tree
	 */
	void exitLogop(XPRParser.LogopContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#quantifiedStatement}.
	 * @param ctx the parse tree
	 */
	void enterQuantifiedStatement(XPRParser.QuantifiedStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#quantifiedStatement}.
	 * @param ctx the parse tree
	 */
	void exitQuantifiedStatement(XPRParser.QuantifiedStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(XPRParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(XPRParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(XPRParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(XPRParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(XPRParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(XPRParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#statementAtom}.
	 * @param ctx the parse tree
	 */
	void enterStatementAtom(XPRParser.StatementAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#statementAtom}.
	 * @param ctx the parse tree
	 */
	void exitStatementAtom(XPRParser.StatementAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(XPRParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(XPRParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#factDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFactDeclaration(XPRParser.FactDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#factDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFactDeclaration(XPRParser.FactDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(XPRParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(XPRParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignment(XPRParser.VariableAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignment(XPRParser.VariableAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#document}.
	 * @param ctx the parse tree
	 */
	void enterDocument(XPRParser.DocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#document}.
	 * @param ctx the parse tree
	 */
	void exitDocument(XPRParser.DocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPRParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(XPRParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPRParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(XPRParser.LineContext ctx);
}