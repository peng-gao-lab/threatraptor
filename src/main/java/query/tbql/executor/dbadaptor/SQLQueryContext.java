package query.tbql.executor.dbadaptor;

public class SQLQueryContext {
	private StringBuffer select;
	private StringBuffer from;
	private StringBuffer where;
	
	public SQLQueryContext() {
		select = new StringBuffer("select ");
		from = new StringBuffer("from ");
		where = new StringBuffer("where ");
	}

	public StringBuffer getSelect() {
		return select;
	}

	public void setSelect(StringBuffer select) {
		this.select = select;
	}

	public StringBuffer getFrom() {
		return from;
	}

	public void setFrom(StringBuffer from) {
		this.from = from;
	}

	public StringBuffer getWhere() {
		return where;
	}

	public void setWhere(StringBuffer where) {
		this.where = where;
	}
	
	public String getSQLQuery() {
		return select.toString() + from + where;
	}
}
