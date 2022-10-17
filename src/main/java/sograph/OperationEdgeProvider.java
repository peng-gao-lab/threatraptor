package sograph;

import org.jgrapht.io.Attribute;
import org.jgrapht.io.EdgeProvider;
import java.util.Map;

public class OperationEdgeProvider implements EdgeProvider<EntityVertex, OperationEdge> {
    @Override
    public OperationEdge buildEdge(EntityVertex v0, EntityVertex v1, String s, Map<String, Attribute> map) {
        //return new OperationEdge(v0, v1, map.get("label").getValue(), map.get("comment").getValue(), map.get("xlabel").getValue());
        return new OperationEdge(v0, v1, map.get("label").getValue(), map.get("label").getValue(), map.get("xlabel").getValue());
    }
}
