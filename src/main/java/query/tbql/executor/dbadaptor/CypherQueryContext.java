package query.tbql.executor.dbadaptor;

public class CypherQueryContext {
	private StringBuffer match;
	private StringBuffer where;
	private StringBuffer returnStr;
	
	public CypherQueryContext() {
		match = new StringBuffer("match ");
		where = new StringBuffer("where ");
		returnStr = new StringBuffer("return ");
	}

	public StringBuffer getMatch() {
		return match;
	}

	public void setMatch(StringBuffer match) {
		this.match = match;
	}

	public StringBuffer getWhere() {
		return where;
	}

	public void setWhere(StringBuffer where) {
		this.where = where;
	}

	public StringBuffer getReturnStr() {
		return returnStr;
	}

	public void setReturnStr(StringBuffer returnStr) {
		this.returnStr = returnStr;
	}

	public String getCypherQuery() {
		return match.toString() + where + returnStr;
	}
}
