package com.syd.algo.graph.test;

import com.syd.algo.graph.BreadthFirstTraversal;
import com.syd.algo.structure.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BreadthFirstTraversalTest {

    private static final byte[][] adjacencyMatrix = new byte[4][4];
    private static final List<Graph.Vertex<Integer>> vertices = new ArrayList<Graph.Vertex<Integer>>();
    private static final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
    private static final Graph.Vertex<Integer> v0 = new Graph.Vertex<>(0);
    private static final Graph.Vertex<Integer> v1 = new Graph.Vertex<>(1);
    private static final Graph.Vertex<Integer> v2 = new Graph.Vertex<>(2);
    private static final Graph.Vertex<Integer> v3 = new Graph.Vertex<>(3);
    private static final Graph<Integer> graph = new Graph<>(Graph.TYPE.DIRECTED, vertices, edges);

    static {
        // v0
        adjacencyMatrix[0][1] = 1;
        adjacencyMatrix[0][2] = 1;
        // v1
        adjacencyMatrix[1][2] = 1;
        // v2
        adjacencyMatrix[2][0] = 1;
        adjacencyMatrix[2][3] = 1;
        // v3
        adjacencyMatrix[3][3] = 1;
    }

    static {
        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        edges.add(new Graph.Edge<>(0, v0, v1));
        edges.add(new Graph.Edge<>(0, v0, v2));
        edges.add(new Graph.Edge<>(0, v1, v2));
        edges.add(new Graph.Edge<>(0, v2, v0));
        edges.add(new Graph.Edge<>(0, v2, v3));
        edges.add(new Graph.Edge<>(0, v3, v3));
    }

    @Test
    public void test0() {
        final int[] result = BreadthFirstTraversal.breadthFirstTraversal(4, adjacencyMatrix, 2);
        assertEquals(2, result[0]);
        assertEquals(0, result[1]);
        assertEquals(3, result[2]);
        assertEquals(1, result[3]);
    }

    @Test
    public void test1() {
        final int[] result = BreadthFirstTraversal.breadthFirstTraversal(4, adjacencyMatrix, 0);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(2, result[2]);
        assertEquals(3, result[3]);
    }

    @Test
    public void test2() {
        final Graph.Vertex<Integer>[] result = BreadthFirstTraversal.breadthFirstTraversal(graph, v2);
        assertEquals(2, (int)result[0].getValue());
        assertEquals(0, (int)result[1].getValue());
        assertEquals(3, (int)result[2].getValue());
        assertEquals(1, (int)result[3].getValue());
    }

    @Test
    public void test3() {
        final Graph.Vertex<Integer>[] result = BreadthFirstTraversal.breadthFirstTraversal(graph, v0);
        assertEquals(0, (int)result[0].getValue());
        assertEquals(1, (int)result[1].getValue());
        assertEquals(2, (int)result[2].getValue());
        assertEquals(3, (int)result[3].getValue());
    }
}

