package query.tbql.executor.constraint;

import java.util.ArrayList;

public class TBQLEventRelationConstraint {
	private String constraintType;
	private String eventIDLeft;
	private String entityIDLeft;
	private String entityAttributeLeft;
	private String eventIDRight;
	private String entityIDRight;
	private String entityAttributeRight;
	private String relation;

	public TBQLEventRelationConstraint(String constraintType, String eventID1, String entityID1,
			String entityAttribute1, String eventID2, String entityID2, String entityAttribute2, String relation) {
		this.constraintType = constraintType;
		this.eventIDLeft = eventID1;
		this.entityIDLeft = entityID1;
		this.entityAttributeLeft = entityAttribute1;
		this.eventIDRight = eventID2;
		this.entityIDRight = entityID2;
		this.entityAttributeRight = entityAttribute2;
		this.relation = relation;
	}

	public String getEventIDLeft() {
		return eventIDLeft;
	}

	public void setEventIDLeft(String eventIDLeft) {
		this.eventIDLeft = eventIDLeft;
	}

	public String getEntityIDLeft() {
		return entityIDLeft;
	}

	public String getEntityAttributeLeft() {
		return entityAttributeLeft;
	}

	public String getEventIDRight() {
		return eventIDRight;
	}

	public void setEventIDRight(String eventIDRight) {
		this.eventIDRight = eventIDRight;
	}

	public String getEntityIDRight() {
		return entityIDRight;
	}

	public String getEntityAttributeRight() {
		return entityAttributeRight;
	}

	public String getRelation() {
		return relation;
	}

	public String getConstraintType() {
		return constraintType;
	}

	@Override
	public String toString() {
		return "BQLEventRelationConstraint [constraintType=" + constraintType + ", eventIDLeft=" + eventIDLeft
				+ ", entityIDLeft=" + entityIDLeft + ", entityAttributeLeft=" + entityAttributeLeft + ", eventIDRight="
				+ eventIDRight + ", entityIDRight=" + entityIDRight + ", entityAttributeRight=" + entityAttributeRight
				+ ", relation=" + relation + "]";
	}
	
	public ArrayList<String> toList() {
		ArrayList<String> res = new ArrayList<>();
		res.add(eventIDLeft);
		res.add(entityIDLeft);
		res.add(entityAttributeLeft);
		res.add(eventIDRight);
		res.add(entityIDRight);
		res.add(entityAttributeRight);
		res.add(relation);
		
		return res;
	}
	
	public void swapLeftAndRight() {
		String tmp;
		
		tmp = eventIDLeft;
		eventIDLeft = eventIDRight;
		eventIDRight = tmp;
		
		tmp = entityIDLeft;
		entityIDLeft = entityIDRight;
		entityIDRight = tmp;
		
		tmp = entityAttributeLeft;
		entityAttributeLeft = entityAttributeRight;
		entityAttributeRight = tmp;
	}
}
