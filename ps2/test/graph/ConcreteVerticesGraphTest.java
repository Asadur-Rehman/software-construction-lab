/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   Empty Graph:

    // Test that the method produces an empty string when the graph is empty.
    // Domain: No vertices in the graph.

    // Graph with a Single Vertex:
    // Test that the method produces the correct string representation when there is only one vertex in the graph.
    // Domain: One vertex in the graph.

    // Graph with Multiple Vertices and Edges:
    // Test that the method correctly represents a graph with multiple vertices and edges.
    // Include cases where vertices have outgoing edges with different weights.
    // Domain: Multiple vertices with various edges and weights.
    
    // Graph with Disconnected Components:
    // Test that the method handles graphs with disconnected components correctly.
    // Include cases where there are vertices without outgoing or incoming edges.
    // Domain: Disconnected graph components.
   
    // Graph with Cycles:
    // Test that the method handles graphs with cycles (e.g., A -> B -> C -> A).
    // Ensure that cycles are represented correctly in the string.
    // Domain: Graph with cycles.
    
    // Graph with Self-Loops:
    // Test that the method handles vertices with self-loops correctly.
    // Ensure that self-loops are represented correctly in the string.
    // Domain: Vertices with self-loops.
    
    // tests for ConcreteVerticesGraph.toString()
    @Test
    void testToStringWithEmptyGraph() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertEquals("", graph.toString());
    }

    @Test
    void testToStringWithOneVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        assertEquals("A -> []\n", graph.toString());
    }

    @Test
    void testToStringWithMultipleVertices() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 3);
        graph.set("B", "C", 2);

        String expected = "A -> [B (3)]\n" +
                          "B -> [C (2)]\n" +
                          "C -> []\n";

        assertEquals(expected, graph.toString());
    }

    @Test
    void testToStringWithDisconnectedComponents() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        String expected = "A -> []\n" +
                          "B -> []\n" +
                          "C -> []\n";

        assertEquals(expected, graph.toString());
    }

    @Test
    void testToStringWithCycles() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 1);
        graph.set("B", "C", 2);
        graph.set("C", "A", 3);

        String expected = "A -> [B (1)]\n" +
                          "B -> [C (2)]\n" +
                          "C -> [A (3)]\n";

        assertEquals(expected, graph.toString());
    }

    @Test
    void testToStringWithSelfLoops() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "A", 1);
        graph.set("B", "B", 2);
        graph.set("C", "C", 3);

        String expected = "A -> [A (1)]\n" +
                          "B -> [B (2)]\n" +
                          "C -> [C (3)]\n";

        assertEquals(expected, graph.toString());
    }
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //Constructor:

    // Test that a Vertex is constructed with the correct label.
    // Domain: Provide valid labels, and verify that the label is set appropriately.
    // Add Source:

    // Test adding incoming edges (sources) to a Vertex.
    // Domain: Add sources with different weights and verify that they are correctly added.
    // Add Target:

    // Test adding outgoing edges (targets) to a Vertex.
    // Domain: Add targets with different weights and verify that they are correctly added.
    // Remove Source:

    // Test removing incoming edges (sources) from a Vertex.
    // Domain: Add sources, remove them, and verify that the sources map is updated correctly.
    // Remove Target:

    // Test removing outgoing edges (targets) from a Vertex.
    // Domain: Add targets, remove them, and verify that the targets map is updated correctly.
    // Get Sources:

    // Test getting the incoming edges (sources) of a Vertex.
    // Domain: Verify that the returned map is correct for various cases, including when there are no sources.
    // Get Targets:

    // Test getting the outgoing edges (targets) of a Vertex.
    // Domain: Verify that the returned map is correct for various cases, including when there are no targets.
    // CheckRep:

    // Test the representation invariant check.
    // Domain: Intentionally violate the representation invariant and ensure that an assertion error is thrown.
    // ToString:

    // Test the string representation of a Vertex.
    // Domain: Create vertices with different combinations of sources and targets, and verify that the string representation is as expected.
    // Equals and HashCode:

    // If equals and hashCode methods are implemented, test them for correctness.
    // Domain: Create multiple Vertex instances with similar or different configurations, and verify that equality is determined correctly.
    // Self-Loops:

    // Test adding and removing self-loops (edges from a vertex to itself).
    // Domain: Add and remove self-loops, and verify that the state is consistent.



    @Test
    void testAddSource() {
        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");

        vertexA.addSource(vertexB, 3);

        assertTrue(vertexA.getSources().isEmpty()); // Adding an incoming edge to A from B shouldn't affect A's sources
        assertTrue(vertexB.getTargets().isEmpty()); // B's targets should still be empty

        vertexB.addTarget(vertexA, 3);

        assertEquals(1, vertexA.getSources().size());
        assertTrue(vertexA.getSources().containsKey(vertexB));


        assertEquals(1, vertexB.getTargets().size());
        assertTrue(vertexB.getTargets().containsKey(vertexA));


        vertexA.checkRep();
    }

    @Test
    void testAddTarget() {
        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");

        vertexA.addTarget(vertexB, 3);

        assertTrue(vertexA.getTargets().isEmpty()); // Adding an outgoing edge from A to B shouldn't affect A's targets
        assertTrue(vertexB.getSources().isEmpty()); // B's sources should still be empty

        vertexB.addSource(vertexA, 3);

        assertEquals(1, vertexA.getTargets().size());
        assertTrue(vertexA.getTargets().containsKey(vertexB));

        assertEquals(1, vertexB.getSources().size());
        assertTrue(vertexB.getSources().containsKey(vertexA));


        vertexA.checkRep();
    }

    @Test
    void testRemoveSource() {
        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");

        vertexA.addSource(vertexB, 3);
        vertexB.addTarget(vertexA, 3);

        vertexA.removeTarget(vertexB);

        assertTrue(vertexA.getSources().isEmpty());
        assertTrue(vertexB.getTargets().isEmpty());

        vertexA.checkRep();
    }

    @Test
    void testRemoveTarget() {
        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");

        vertexA.addTarget(vertexB, 3);
        vertexB.addSource(vertexA, 3);

        vertexA.removeTarget(vertexB);

        assertTrue(vertexA.getTargets().isEmpty());
        assertTrue(vertexB.getSources().isEmpty());

        vertexA.checkRep();
    }
    
}
