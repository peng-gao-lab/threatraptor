package query.tbql.executor.constraint;

import query.tbql.executor.datetime.TBQLDateTime;

public class TimeWindowConstraint extends TBQLEventConstraint {
	private String constraintType;
	private TBQLDateTime tbqlDateTime; // naturally specifies a time window constraint
	
	public TimeWindowConstraint(TBQLDateTime tbqlDateTime) {
		this.constraintType = "timeWindow";
		this.tbqlDateTime = tbqlDateTime;
	}
	
	public TBQLDateTime getTbqlDateTime() {
		return tbqlDateTime;
	}

	@Override
	public String getConstraintType() {
		return constraintType;
	}

	@Override
	public String toString() {
		return "TimeWindowConstraint [constraintType=" + constraintType + ", tbqlDateTime=" + tbqlDateTime + "]";
	}
}
