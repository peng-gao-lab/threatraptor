package query.tbql.executor.constraint;

import query.tbql.executor.TBQLUtils.ValType;

public class AttributeConstraint extends TBQLEventConstraint {
	private String constraintType;
	private String id;
	private String op;
	private String val;
	private boolean negation;
	private ValType valType;
	
	public AttributeConstraint(String id, String op, String val, boolean negation, ValType valType) {
		this.constraintType = "attribute";
		this.id = id;
		this.op = op;
		this.val = val;
		this.negation = negation;
		this.valType = valType;
	}
	
	public String getId() {
		return id;
	}

	public String getOp() {
		return op;
	}

	public String getVal() {
		return val;
	}
	
	public Boolean getNegation() {
		return negation;
	}
	
	public ValType getValType() {
		return valType;
	}
	
	
	@Override
	public String getConstraintType() {
		return constraintType;
	}

	@Override
	public String toString() {
		return "AttributeConstraint [constraintType=" + constraintType + ", id=" + id + ", op=" + op + ", val=" + val
				+ ", negation=" + negation + ", valType=" + valType + "]";
	}
}
