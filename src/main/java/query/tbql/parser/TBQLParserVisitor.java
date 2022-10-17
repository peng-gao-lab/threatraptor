// Generated from TBQLParser.g4 by ANTLR 4.7.1
package query.tbql.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TBQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TBQLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TBQLParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(TBQLParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttributeGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeGlobalConstraint(TBQLParser.AttributeGlobalConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TimeWindowGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeWindowGlobalConstraint(TBQLParser.TimeWindowGlobalConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtTimeConstraint(TBQLParser.AtTimeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FromToTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromToTimeConstraint(TBQLParser.FromToTimeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BeforeTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeforeTimeConstraint(TBQLParser.BeforeTimeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AfterTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAfterTimeConstraint(TBQLParser.AfterTimeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LastTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastTimeConstraint(TBQLParser.LastTimeConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#multieventQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultieventQuery(TBQLParser.MultieventQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NormalEventPattern}
	 * labeled alternative in {@link TBQLParser#eventPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalEventPattern(TBQLParser.NormalEventPatternContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarPathPattern}
	 * labeled alternative in {@link TBQLParser#eventPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarPathPattern(TBQLParser.VarPathPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#entity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntity(TBQLParser.EntityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OpOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpOperation(TBQLParser.OpOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtomOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomOperation(TBQLParser.AtomOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenOperation(TBQLParser.ParenOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#pathOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathOperation(TBQLParser.PathOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#event}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvent(TBQLParser.EventContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtomAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomAttributeExpr(TBQLParser.AtomAttributeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OpAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpAttributeExpr(TBQLParser.OpAttributeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenAttributeExpr(TBQLParser.ParenAttributeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttributeConstraintFull}
	 * labeled alternative in {@link TBQLParser#attributeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeConstraintFull(TBQLParser.AttributeConstraintFullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttributeConstraintCollection}
	 * labeled alternative in {@link TBQLParser#attributeConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeConstraintCollection(TBQLParser.AttributeConstraintCollectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#collection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollection(TBQLParser.CollectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#collectionElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionElement(TBQLParser.CollectionElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#timeWindow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeWindow(TBQLParser.TimeWindowContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#withClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithClause(TBQLParser.WithClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttributeRelation}
	 * labeled alternative in {@link TBQLParser#eventRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeRelation(TBQLParser.AttributeRelationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TemporalRelation}
	 * labeled alternative in {@link TBQLParser#eventRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemporalRelation(TBQLParser.TemporalRelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#returnClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnClause(TBQLParser.ReturnClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResult(TBQLParser.ResultContext ctx);
	/**
	 * Visit a parse tree produced by {@link TBQLParser#limit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimit(TBQLParser.LimitContext ctx);
}