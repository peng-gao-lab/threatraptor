package sograph;

import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.ImportException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityOperationGraph {
    private DirectedPseudograph<EntityVertex, OperationEdge> g;

    public SecurityOperationGraph(String filepath) {
        DOTImporter<EntityVertex, OperationEdge> di = new DOTImporter<>(new EntityVertexProvider(), new OperationEdgeProvider());
        g = new DirectedPseudograph<>(OperationEdge.class);
        try {
            FileReader fr = new FileReader(filepath);
            di.importGraph(g, fr);
        } catch (FileNotFoundException | ImportException e) {
            System.out.println("File Not Exists or File Parsing Error.");
            e.printStackTrace();
        }
    }

    public String generateTBQL() {
        return generateTBQL(true);
    }
    
    public String generateTBQL(Boolean use_with) {
        StringBuilder sb = new StringBuilder();
        for (OperationEdge e: g.edgeSet()) {
            if (!e.v0.type.equals("IP")) { // "IP" cannot be subject
                if (!(e.v0.type.equals("AndroidPackage") && e.v1.type.equals("AndroidPackage"))) {
                    // Cannot have two AndroidPackages
                    TBQL_statement.ctx.register(e);
                }
            }
        }

        for (OperationEdge e: g.edgeSet()) {
            if (!e.v0.type.equals("IP")) { // "IP" cannot be subject
                if (!(e.v0.type.equals("AndroidPackage") && e.v1.type.equals("AndroidPackage"))) {
                    sb.append(String.format("%s\n", new TBQL_statement(e).toString()));
                }
            }
        }

        if (use_with) sb.append(TBQL_statement.ctx.generateEvtSeq());
        sb.append(TBQL_statement.ctx.generateReturn());
        TBQL_statement.reset(1);
        return sb.toString();
    }

    public List<String> generateTBQLForEachEvent() {
        // For each event pattern, generate a TBQL
        List<String> tbqlList = new ArrayList<>();

        for (OperationEdge e: g.edgeSet()) {
            if (!e.v0.type.equals("IP")) { // "IP" cannot be subject
                if (!(e.v0.type.equals("AndroidPackage") && e.v1.type.equals("AndroidPackage"))) {
                    TBQL_statement.ctx.register(e);
                }
            }
        }

        for (OperationEdge e: g.edgeSet()) {
            if (!e.v0.type.equals("IP")) { // "IP" cannot be subject
                if (!(e.v0.type.equals("AndroidPackage") && e.v1.type.equals("AndroidPackage"))) {
                    TBQL_statement tbqlStatement = new TBQL_statement(e);
                    String body = tbqlStatement.toString();
                    String[] tmpStrs = body.split(" ");
                    String evtId = tmpStrs[tmpStrs.length - 1];

                    String tbql = body + "\n" + "return " + tbqlStatement.getLhs().type_character
                            + tbqlStatement.getLhs().cnt + ", " + tbqlStatement.getRhs().type_character + tbqlStatement.getRhs().cnt
                            + ", " + evtId + ".id";
                    tbqlList.add(tbql);
                }
            }
        }

        TBQL_statement.reset(1);
        return tbqlList;
    }
}

class TBQL_statement {
    /*
    Some examples:
        proc p1['cmd.exe'] ~>(5)[start] proc p2[!'%osql.exe'] as evt1
        proc p3['%sqlservr.exe'] ~>(2~5)[write] file f1[name = '%backup1.dmp'] as evt2
        proc p4['%ecngs.exe'] ~>(~5)[read || write] file f1 as evt3
        proc p4 ~>[read || write] ip i1[dstip='172.16.157.129/32'] as evt4
     */

    static class TBQL_context {
        public int base, evt;
        public Map<String, Integer> i, f, p;
        public TBQL_context(int base) {
            this.base = base;
            this.evt = base;
            this.p = new HashMap<>();
            this.i = new HashMap<>();
            this.f = new HashMap<>();
        }
        public void register(OperationEdge e) {
            if (!e.v0.type.equals("IP")) getProcess(e.v0.name);
        }
        public int getEvent() {
            return evt++;
        }
        public int getIP(String name) {
            if (i.containsKey(name)) return i.get(name);
            int ret = i.size() + base;
            i.put(name, ret);
            return ret;
        }
        public int getFile(String name) {
            if (f.containsKey(name)) return f.get(name);
            int ret = f.size() + base;
            f.put(name, ret);
            return ret;
        }
        public int getProcess(String name) {
            if (p.containsKey(name)) return p.get(name);
            int ret = p.size() + base;
            p.put(name, ret);
            return ret;
        }

        public String generateReturn() {
            StringBuilder sb = new StringBuilder();
            sb.append("return ");
            sb.append ("distinct ");
            boolean first = true;

            List<Integer> p_value_list = new ArrayList<>();
            List<Integer> i_value_list = new ArrayList<>();
            List<Integer> f_value_list = new ArrayList<>();
            for (Integer cnt: p.values()) {
                p_value_list.add(cnt);
            }
            for (Integer cnt: i.values()) {
                i_value_list.add(cnt);
            }
            for (Integer cnt: f.values()) {
                f_value_list.add(cnt);
            }
            Collections.sort(p_value_list);
            Collections.sort(i_value_list);
            Collections.sort(f_value_list);

            for (Integer cnt: p_value_list) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.format("p%d", cnt.intValue()));
            }
            for (Integer cnt: i_value_list) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.format("i%d", cnt.intValue()));
            }
            for (Integer cnt: f_value_list) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.format("f%d", cnt.intValue()));
            }
            sb.append("\n");
            return sb.toString();
        }
        public String generateEvtSeq() {
            // e.g.: with evt1 before evt2, evt2 before evt3, evt3 before evt4
            StringBuilder sb = new StringBuilder();
            sb.append("with ");
            boolean first = true;
            for (int i=base; i<evt-1; ++i) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.format("evt%d before evt%d", i, i+1));
            }
            sb.append("\n");
            return sb.toString();
        }
    }
    public static TBQL_context ctx = new TBQL_context(1);
    public class TBQL_entity {
        public String type, type_character, description;
        public int cnt;
        public TBQL_entity(EntityVertex v, TBQL_context ctx, boolean isLhs, String op, EntityVertex other_v) {
            if (v.type.equals("IP")) {
                type = "ip";
                type_character = "i";
                description = String.format("\'%s\'", v.name);
                cnt = ctx.getIP(v.name);
            } 
            else {
                // File
                // isLhs || v.name.endsWith(".exe") || v.name.endsWith(".dll") || ctx.p.containsKey(v.name)
                if (isLhs) {
                    type = "proc";
                    type_character = "p";
                    description = String.format("\'%%%s%%\'", v.name);
                    cnt = ctx.getProcess(v.name);
                } else { // right-hand side
                    if ((op.equals("start") || op.equals("launch")) && !v.name.equals(other_v.name)) {
                        type = "proc";
                        type_character = "p";
                        description = String.format("\'%%%s%%\'", v.name);
                        cnt = ctx.getProcess(v.name);
                    }
                    else {
                        type = "file";
                        type_character = "f";
                        description = String.format("\'%%%s%%\'", v.name);
                        cnt = ctx.getFile(v.name);
                    }
                }
            }
        }

        @Override
        public String toString() {
            return String.format("%s %s%d[%s]", type, type_character, cnt, description);
        }
    }
    private TBQL_entity lhs, rhs;
    private String op;
    public TBQL_statement(OperationEdge e) {
        lhs = new TBQL_entity(e.v0, ctx, true, e.op, e.v1);
        rhs = new TBQL_entity(e.v1, ctx, false, e.op, e.v0);
        op = e.op;
    }
    public TBQL_statement(OperationEdge e, Map behaviorMap) {}

    @Override
    public String toString() {
        return String.format("%s %s %s as evt%d", lhs.toString(), get_translated_op(), rhs.toString(), ctx.getEvent());
    }

    static public void reset(int base) {
        ctx = new TBQL_context(base);
    }

    public String get_translated_op() {        
        if ((op.equals("download") || op.equals("open")) && rhs.type.equals("ip")) return "read";
        if (op.equals("download") && rhs.type.equals("file")) return "write";
        if (op.equals("connect") && rhs.type.equals("ip")) return "read || write || connect";
        if ((op.equals("launch") || op.equals("run") || op.equals("start")) && rhs.type.equals("file")) return "execute";
        if (op.equals("open") && rhs.type.equals("file")) return "open";
        if (op.equals("send") && rhs.type.equals("ip")) return "write";
        if (op.equals("use") && rhs.type.equals("file")) return "read";
        if (op.equals("create") && rhs.type.equals("file")) return "create";
        if (op.equals("launch") && rhs.type.equals("proc")) return "start";
        if (op.equals("read") || op.equals("write") || op.equals("start")) return op;

        return "unknown_operation";
    }

    public TBQL_entity getLhs() {
        return lhs;
    }

    public TBQL_entity getRhs() {
        return rhs;
    }
}