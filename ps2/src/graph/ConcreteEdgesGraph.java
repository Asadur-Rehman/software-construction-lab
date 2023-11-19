package graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    // Each vertex in the graph is represented by a unique string label in 'vertices'.
    // Each directed edge is represented by an instance of the Edge class in 'edges',
    // connecting a source vertex to a target vertex with a specified weight.

    // Representation invariant:
    // 1. 'vertices' contains distinct string labels for each vertex.
    // 2. Each edge in 'edges' has valid source and target vertices (present in 'vertices').
    // 3. Weight of each edge is a non-negative integer.

    // Safety from rep exposure:
    // - 'vertices' is a private final set.
    // - 'edges' is a private final list.
    // - Defensive copying is used to return a copy of 'vertices' in the 'vertices()' method.

    // constructor
    public ConcreteEdgesGraph() {
    }

    @Override
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false; // Vertex already exists
        } else {
            vertices.add(vertex);
            return true;
        }
    }

    @Override
    public int set(String source, String target, int weight) {
        // Check if the edge already exists
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int previousWeight = edge.getWeight();
                edge.setWeight(weight);
                return previousWeight;
            }
        }

        // Edge does not exist, create a new one
        Edge newEdge = new Edge(source, target, weight);
        edges.add(newEdge);
        return 0; // No previous weight
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false; // Vertex does not exist
        }

        // Remove the vertex from the set of vertices
        vertices.remove(vertex);

        // Remove any edges connected to the vertex
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));

        return true;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        if (!vertices.contains(target)) {
            throw new IllegalArgumentException("Target vertex does not exist");
        }

        Map<String, Integer> sourcesMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sourcesMap.put(edge.getSource(), edge.getWeight());
            }
        }
        return sourcesMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        if (!vertices.contains(source)) {
            throw new IllegalArgumentException("Source vertex does not exist");
        }

        Map<String, Integer> targetsMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targetsMap.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targetsMap;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Graph with vertices: " + vertices + " and edges: ");
        for (Edge edge : edges) {
            result.append(edge.toString()).append(", ");
        }
        if (!edges.isEmpty()) {
            result.setLength(result.length() - 2); // Remove the trailing comma and space
        } else {
            return "";
        }
        return result.toString();
    }
}

/**
 * Representation of an Edge in the ConcreteEdgesGraph.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>
 * PS2 instructions: the specification and implementation of this class are up to you.
 */
class Edge {

    private final String source;
    private final String target;
    private int weight;

    // Abstraction function:
    // Represents a directed edge from 'source' to 'target' with a weight 'weight'.

    // Representation invariant:
    // - 'source' and 'target' are non-null strings.
    // - 'weight' is a non-negative integer.

    // Safety from rep exposure:
    // - All fields are private and final.
    // - Defensive copying is used in the constructor.

    // constructor
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // checkRep
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight >= 0;
    }

    // methods
    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("(%s -> %s, %d)", source, target, weight);
    }
}
