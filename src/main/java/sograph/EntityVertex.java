package sograph;

import java.util.Arrays;
import java.util.List;

public class EntityVertex {
    String id;
    String name;
    String type;
    List<String> alias;
    
    public EntityVertex(String id, String name, String type, String alias) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.alias = alias == null ? null : Arrays.asList(alias.split(","));
    }
}
