// Generated from TBQLParser.g4 by ANTLR 4.7.1
package query.tbql.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TBQLParser}.
 */
public interface TBQLParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TBQLParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(TBQLParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(TBQLParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttributeGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAttributeGlobalConstraint(TBQLParser.AttributeGlobalConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttributeGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAttributeGlobalConstraint(TBQLParser.AttributeGlobalConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TimeWindowGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 */
	void enterTimeWindowGlobalConstraint(TBQLParser.TimeWindowGlobalConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TimeWindowGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 */
	void exitTimeWindowGlobalConstraint(TBQLParser.TimeWindowGlobalConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallGlobalConstraint(TBQLParser.FunctionCallGlobalConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallGlobalConstraint}
	 * labeled alternative in {@link TBQLParser#globalConstraint}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallGlobalConstraint(TBQLParser.FunctionCallGlobalConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAtTimeConstraint(TBQLParser.AtTimeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAtTimeConstraint(TBQLParser.AtTimeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FromToTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void enterFromToTimeConstraint(TBQLParser.FromToTimeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FromToTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void exitFromToTimeConstraint(TBQLParser.FromToTimeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BeforeTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void enterBeforeTimeConstraint(TBQLParser.BeforeTimeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BeforeTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void exitBeforeTimeConstraint(TBQLParser.BeforeTimeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AfterTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAfterTimeConstraint(TBQLParser.AfterTimeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AfterTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAfterTimeConstraint(TBQLParser.AfterTimeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LastTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void enterLastTimeConstraint(TBQLParser.LastTimeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LastTimeConstraint}
	 * labeled alternative in {@link TBQLParser#timeWindowConstraint}.
	 * @param ctx the parse tree
	 */
	void exitLastTimeConstraint(TBQLParser.LastTimeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#multieventQuery}.
	 * @param ctx the parse tree
	 */
	void enterMultieventQuery(TBQLParser.MultieventQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#multieventQuery}.
	 * @param ctx the parse tree
	 */
	void exitMultieventQuery(TBQLParser.MultieventQueryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NormalEventPattern}
	 * labeled alternative in {@link TBQLParser#eventPattern}.
	 * @param ctx the parse tree
	 */
	void enterNormalEventPattern(TBQLParser.NormalEventPatternContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NormalEventPattern}
	 * labeled alternative in {@link TBQLParser#eventPattern}.
	 * @param ctx the parse tree
	 */
	void exitNormalEventPattern(TBQLParser.NormalEventPatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarPathPattern}
	 * labeled alternative in {@link TBQLParser#eventPattern}.
	 * @param ctx the parse tree
	 */
	void enterVarPathPattern(TBQLParser.VarPathPatternContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarPathPattern}
	 * labeled alternative in {@link TBQLParser#eventPattern}.
	 * @param ctx the parse tree
	 */
	void exitVarPathPattern(TBQLParser.VarPathPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#entity}.
	 * @param ctx the parse tree
	 */
	void enterEntity(TBQLParser.EntityContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#entity}.
	 * @param ctx the parse tree
	 */
	void exitEntity(TBQLParser.EntityContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OpOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOpOperation(TBQLParser.OpOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OpOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOpOperation(TBQLParser.OpOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtomOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterAtomOperation(TBQLParser.AtomOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtomOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitAtomOperation(TBQLParser.AtomOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterParenOperation(TBQLParser.ParenOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenOperation}
	 * labeled alternative in {@link TBQLParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitParenOperation(TBQLParser.ParenOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#pathOperation}.
	 * @param ctx the parse tree
	 */
	void enterPathOperation(TBQLParser.PathOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#pathOperation}.
	 * @param ctx the parse tree
	 */
	void exitPathOperation(TBQLParser.PathOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#event}.
	 * @param ctx the parse tree
	 */
	void enterEvent(TBQLParser.EventContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#event}.
	 * @param ctx the parse tree
	 */
	void exitEvent(TBQLParser.EventContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtomAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomAttributeExpr(TBQLParser.AtomAttributeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtomAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomAttributeExpr(TBQLParser.AtomAttributeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OpAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 */
	void enterOpAttributeExpr(TBQLParser.OpAttributeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OpAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 */
	void exitOpAttributeExpr(TBQLParser.OpAttributeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenAttributeExpr(TBQLParser.ParenAttributeExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenAttributeExpr}
	 * labeled alternative in {@link TBQLParser#attributeExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenAttributeExpr(TBQLParser.ParenAttributeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttributeConstraintFull}
	 * labeled alternative in {@link TBQLParser#attributeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAttributeConstraintFull(TBQLParser.AttributeConstraintFullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttributeConstraintFull}
	 * labeled alternative in {@link TBQLParser#attributeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAttributeConstraintFull(TBQLParser.AttributeConstraintFullContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttributeConstraintCollection}
	 * labeled alternative in {@link TBQLParser#attributeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAttributeConstraintCollection(TBQLParser.AttributeConstraintCollectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttributeConstraintCollection}
	 * labeled alternative in {@link TBQLParser#attributeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAttributeConstraintCollection(TBQLParser.AttributeConstraintCollectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#collection}.
	 * @param ctx the parse tree
	 */
	void enterCollection(TBQLParser.CollectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#collection}.
	 * @param ctx the parse tree
	 */
	void exitCollection(TBQLParser.CollectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#collectionElement}.
	 * @param ctx the parse tree
	 */
	void enterCollectionElement(TBQLParser.CollectionElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#collectionElement}.
	 * @param ctx the parse tree
	 */
	void exitCollectionElement(TBQLParser.CollectionElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#timeWindow}.
	 * @param ctx the parse tree
	 */
	void enterTimeWindow(TBQLParser.TimeWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#timeWindow}.
	 * @param ctx the parse tree
	 */
	void exitTimeWindow(TBQLParser.TimeWindowContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#withClause}.
	 * @param ctx the parse tree
	 */
	void enterWithClause(TBQLParser.WithClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#withClause}.
	 * @param ctx the parse tree
	 */
	void exitWithClause(TBQLParser.WithClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttributeRelation}
	 * labeled alternative in {@link TBQLParser#eventRelation}.
	 * @param ctx the parse tree
	 */
	void enterAttributeRelation(TBQLParser.AttributeRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttributeRelation}
	 * labeled alternative in {@link TBQLParser#eventRelation}.
	 * @param ctx the parse tree
	 */
	void exitAttributeRelation(TBQLParser.AttributeRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TemporalRelation}
	 * labeled alternative in {@link TBQLParser#eventRelation}.
	 * @param ctx the parse tree
	 */
	void enterTemporalRelation(TBQLParser.TemporalRelationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TemporalRelation}
	 * labeled alternative in {@link TBQLParser#eventRelation}.
	 * @param ctx the parse tree
	 */
	void exitTemporalRelation(TBQLParser.TemporalRelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(TBQLParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(TBQLParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#result}.
	 * @param ctx the parse tree
	 */
	void enterResult(TBQLParser.ResultContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#result}.
	 * @param ctx the parse tree
	 */
	void exitResult(TBQLParser.ResultContext ctx);
	/**
	 * Enter a parse tree produced by {@link TBQLParser#limit}.
	 * @param ctx the parse tree
	 */
	void enterLimit(TBQLParser.LimitContext ctx);
	/**
	 * Exit a parse tree produced by {@link TBQLParser#limit}.
	 * @param ctx the parse tree
	 */
	void exitLimit(TBQLParser.LimitContext ctx);
}