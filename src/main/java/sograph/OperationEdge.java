package sograph;

public class OperationEdge {
    public EntityVertex v0, v1;
    public String op, op_orig, evidence;
    
    public OperationEdge(EntityVertex v0, EntityVertex v1, String op, String op_orig, String evidence) {
        this.v0 = v0;
        this.v1 = v1;
        this.op = op;
        this.op_orig = op_orig;
        this.evidence = evidence;
    }
}
