package sograph;

import org.jgrapht.io.Attribute;
import org.jgrapht.io.VertexProvider;
import java.util.Map;

public class EntityVertexProvider implements VertexProvider<EntityVertex> {
    @Override
    public EntityVertex buildVertex(String s, Map<String, Attribute> map) {
        String alias = map.containsKey("comment") ? map.get("comment").getValue() : null; // map.getOrDefault() do not have ".getValue"
        return new EntityVertex(s, map.get("label").getValue(), map.get("xlabel").getValue(), alias);
    }
}
