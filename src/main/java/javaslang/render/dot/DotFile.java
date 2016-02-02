package javaslang.render.dot;

import javaslang.collection.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates .dot representation of Javaslang Tree.
 * <p>
 * See <a href="http://www.graphviz.org/">Graphviz</a>
 */
public class DotFile {

    public static DotFile create(Tree<?> tree) {
        return new DotFile(tree);
    }

    private final Map<String, String> labels = new HashMap<>();
    private final List<String> edges = new ArrayList<>();

    private String name = "graph";

    private DotFile(Tree<?> tree) {
        processTree(tree, null, 0);
    }

    public DotFile setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ")
                .append(name)
                .append(" {\n");
        for (Map.Entry<String, String> label : labels.entrySet()) {
            sb.append("  ")
                    .append(label.getKey())
                    .append(" [label=\"")
                    .append(label.getValue().replace("\"", "\\\""))
                    .append("\"];\n");
        }
        for (String edge : edges) {
            sb.append("  ").append(edge).append(";\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    private int processTree(Tree<?> tree, String parentLabel, int index) {
        Object value = tree.getValue();
        String label = "lbl" + index;
        labels.put(label, value.toString());
        if(parentLabel != null) {
            edges.add(parentLabel + " -> " + label);
        }
        for (Tree.Node<?> child : tree.getChildren()) {
            index = processTree(child, label, index + 1);
        }
        return index;
    }
}
