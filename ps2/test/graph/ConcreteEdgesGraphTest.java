package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteEdgesGraph.
 *
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 *
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString()
    //   - Test with an empty graph
    //   - Test with vertices and edges
    //   - Check that the output is a human-readable representation

    @Test
    public void testConcreteEdgesGraphToString() {
        Graph<String> graph = emptyInstance();
        assertEquals("Empty graph should have an empty string representation", "", graph.toString());

        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        graph.set("B", "A", 3);

        String expectedOutput = "Graph with vertices: [A, B] and edges: (A -> B, 5), (B -> A, 3)";
        assertEquals("Graph with edges should have the correct string representation", expectedOutput,
                graph.toString());
    }

    // TODO additional tests for ConcreteEdgesGraph.toString()

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    //   - Test creating an edge with valid parameters
    //   - Test operations on Edge (getSource, getTarget, getWeight)
    //   - Test the toString method

    @Test
    public void testEdgeCreationAndOperations() {
        Edge edge = new Edge("A", "B", 5);

        assertEquals("Edge should have the correct source", "A", edge.getSource());
        assertEquals("Edge should have the correct target", "B", edge.getTarget());
        assertEquals("Edge should have the correct weight", 5, edge.getWeight());

        assertEquals("toString should return the correct string representation", "(A -> B, 5)", edge.toString());
    }

    // TODO additional tests for Edge operations
    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("X", "Y", 8);
        assertEquals("toString should return the correct string representation", "(X -> Y, 8)", edge.toString());
    }

    @Test
    public void testEdgeSetWeight() {
        Edge edge = new Edge("P", "Q", 3);
        edge.setWeight(7);
        assertEquals("setWeight should update the weight", 7, edge.getWeight());
    }

}