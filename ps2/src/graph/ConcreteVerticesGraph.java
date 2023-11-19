/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();


    // Abstraction function:
    //   Represents a graph with vertices and weighted edges. Each vertex is stored
    //   in the 'vertices' list, and edges are represented by the 'sources' and 'targets'
    //   maps in each vertex. The 'sources' map contains incoming edges, and the 'targets'
    //   map contains outgoing edges. The graph is directed, and weights are associated
    //   with each edge.
    //
    // Representation invariant:
    //   - 'vertices' is not null.
    //   - No two vertices in 'vertices' have the same label.
    //   - All vertices in 'vertices' adhere to their own representation invariant.
    //
    // Safety from rep exposure:
    //   - 'vertices' is a private, final field.
    //   - 'vertices' is never exposed directly; it is accessed through methods that
    //     ensure safety, such as add, remove, and other accessor methods.

    public ConcreteVerticesGraph() {
        // No explicit initialization is needed for the constructor.
        // The 'vertices' list is already initialized as an empty ArrayList.
        checkRep();
    }

    private void checkRep() {
        assert vertices != null : "Vertices list cannot be null";
        for (Vertex v : vertices) {
            assert v != null : "Vertex in the vertices list cannot be null";
            v.checkRep();
        }
    }

    @Override
    public boolean add(String vertex) {
        checkRep();
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                return false; // Vertex with the given label already exists
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        checkRep();
        Vertex sourceVertex = findVertex(source);
        Vertex targetVertex = findVertex(target);

        if (sourceVertex == null || targetVertex == null) {
            throw new IllegalArgumentException("Source or target vertex not found");
        }

        int previousWeight = sourceVertex.addTarget(targetVertex, weight);
        targetVertex.addSource(sourceVertex, weight);

        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        checkRep();
        Vertex targetVertex = findVertex(vertex);

        if (targetVertex == null) {
            return false; // Vertex not found
        }

        // Remove the vertex from sources of other vertices
        for (Vertex v : vertices) {
            v.removeTarget(targetVertex);
        }

        // Remove the vertex from targets of other vertices
        targetVertex.removeAllSources();

        // Remove the vertex from the vertices list
        vertices.remove(targetVertex);

        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        checkRep();
        Set<String> vertexSet = new HashSet<>();
        for (Vertex v : vertices) {
            vertexSet.add(v.getLabel());
        }
        checkRep();
        return vertexSet;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        checkRep();
        Vertex targetVertex = findVertex(target);

        if (targetVertex == null) {
            throw new IllegalArgumentException("Target vertex not found");
        }

        Map<String, Integer> sourceMap = new HashMap<>();
        for (Map.Entry<Vertex, Integer> entry : targetVertex.getSources().entrySet()) {
            sourceMap.put(entry.getKey().getLabel(), entry.getValue());
        }

        checkRep();
        return sourceMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        checkRep();
        Vertex sourceVertex = findVertex(source);

        if (sourceVertex == null) {
            throw new IllegalArgumentException("Source vertex not found");
        }

        Map<String, Integer> targetMap = new HashMap<>();
        for (Map.Entry<Vertex, Integer> entry : sourceVertex.getTargets().entrySet()) {
            targetMap.put(entry.getKey().getLabel(), entry.getValue());
        }

        checkRep();
        return targetMap;
    }

    @Override
    public String toString() {
        checkRep();
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v.toString()).append("\n");
        }
        checkRep();
        return sb.toString();
    }

    // Helper method to find a vertex by its label
    private Vertex findVertex(String label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * Represents a vertex in a graph with a label and incoming/outgoing edges.
 *
 * <p>
 * 
 */
class Vertex {

//   Abstraction Function:</b> Each instance represents a unique vertex with a
//   Incoming edges are stored in the 'sources' map, where the key is the source
//   vertex, and the value
//   label.
//   is the weight of the edge. Outgoing edges are stored in the 'targets' map,
//   where the key is the
//   target vertex, and the value is the weight of the edge.
//  
//   <p>
//   <b>Representation Invariant:</b>
//   - 'label' is not null.
//   - 'sources' and 'targets' maps are not null.
//   - All keys in 'sources' and 'targets' maps are not null.
//  
//   <p>
//   <b>Safety from Rep Exposure:</b>
//   - All fields are private and final.
//   - 'label' is never exposed directly; it is accessed through the getLabel()
//   method.
//   - 'sources' and 'targets' maps are never exposed directly; they are accessed
//   through the
//   getSources() and getTargets() methods, which return unmodifiable views.

    private final String label;
    private final Map<Vertex, Integer> sources;
    private final Map<Vertex, Integer> targets;

    /**
     * Constructs a new vertex with the given label.
     *
     * @param label The label of the new vertex.
     * @throws IllegalArgumentException if the label is null.
     */
    public Vertex(String label) {
        this.label = label;
        this.sources = new HashMap<>();
        this.targets = new HashMap<>();
        checkRep();
    }

      /**
     * Checks the representation invariant of the vertex.
     *
     * <p><b>Representation Invariant:</b>
     * - 'label' is not null.
     * - 'sources' and 'targets' maps are not null.
     * - All keys in 'sources' and 'targets' maps are not null.
     */
    public void checkRep() {
        assert label != null : "Vertex label cannot be null";
        assert sources != null : "Sources map cannot be null";
        assert targets != null : "Targets map cannot be null";
        for (Map.Entry<Vertex, Integer> entry : targets.entrySet()) {
            assert entry.getKey() != null : "Target vertex in targets map cannot be null";
        }
        for (Map.Entry<Vertex, Integer> entry : sources.entrySet()) {
            assert entry.getKey() != null : "Source vertex in sources map cannot be null";
        }
    }
    

    /**
     * Adds an outgoing edge to the specified target vertex with the given weight.
     *
     * @param target The target vertex.
     * @param weight The weight of the outgoing edge.
     * @throws IllegalArgumentException if the target vertex is null.
     */
    public int addTarget(Vertex target, int weight) {
        checkRep();
        Integer previousWeight = targets.put(target, weight);
        checkRep();
        return previousWeight != null ? previousWeight : 0;
    }

    /**
     * Removes the outgoing edge to the specified target vertex.
     *
     * @param target The target vertex.
     * @throws IllegalArgumentException if the target vertex is null.
     */
    public void removeTarget(Vertex target) {
        checkRep();
        targets.remove(target);
        checkRep();
    }

    /**
     * Adds an incoming edge from the specified source vertex with the given weight.
     *
     * @param source The source vertex.
     * @param weight The weight of the incoming edge.
     * @throws IllegalArgumentException if the source vertex is null.
     */
    public void addSource(Vertex source, int weight) {
        checkRep();
        sources.put(source, weight);
        checkRep();
    }

    public void removeAllSources() {
        checkRep();
        sources.clear();
        checkRep();
    }

    /**
     * Returns the label of this vertex.
     *
     * @return The label of this vertex.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Adds an outgoing edge to the specified target vertex with the given weight.
     *
     * @param target The target vertex.
     * @param weight The weight of the outgoing edge.
     * @throws IllegalArgumentException if the target vertex is null.
     */
    public Map<Vertex, Integer> getSources() {
        return Collections.unmodifiableMap(sources);
    }

     /**
     * Returns an unmodifiable view of the targets map (outgoing edges).
     *
     * @return An unmodifiable view of the targets map.
     */
    public Map<Vertex, Integer> getTargets() {
        return Collections.unmodifiableMap(targets);
    }

    @Override
    public String toString() {
        checkRep();
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(" -> [");
        for (Map.Entry<Vertex, Integer> entry : targets.entrySet()) {
            sb.append(entry.getKey().getLabel()).append(" (").append(entry.getValue()).append("), ");
        }
        if (!targets.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the trailing comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}
