package com.behappy.algo.graph.test;

import com.behappy.algo.graph.DepthFirstTraversal;
import com.behappy.algo.structure.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepthFirstTraversalTest {

    private static final byte[][] adjacencyMatrix = new byte[4][4];
    private static final List<Graph.Vertex<Integer>> vertices = new ArrayList<Graph.Vertex<Integer>>();
    private static final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
    private static final Graph.Vertex<Integer> v0 = new Graph.Vertex<Integer>(0);
    private static final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
    private static final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
    private static final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
    private static final Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, vertices, edges);

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

        edges.add(new Graph.Edge<Integer>(0, v0, v1));
        edges.add(new Graph.Edge<Integer>(0, v0, v2));
        edges.add(new Graph.Edge<Integer>(0, v1, v2));
        edges.add(new Graph.Edge<Integer>(0, v2, v0));
        edges.add(new Graph.Edge<Integer>(0, v2, v3));
        edges.add(new Graph.Edge<Integer>(0, v3, v3));
    }

    @Test
    public void test0() {
        final int[] result = DepthFirstTraversal.depthFirstTraversal(4, adjacencyMatrix, 2);
        assertEquals(2, result[0]);
        assertEquals(0, result[1]);
        assertEquals(1, result[2]);
        assertEquals(3, result[3]);
    }

    @Test
    public void test1() {
        final int[] result = DepthFirstTraversal.depthFirstTraversal(4, adjacencyMatrix, 0);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(2, result[2]);
        assertEquals(3, result[3]);
    }

    @Test
    public void test2() {
        final Graph.Vertex<Integer>[] result = DepthFirstTraversal.depthFirstTraversal(graph, v2);
        assertEquals(2, (int)result[0].getValue());
        assertEquals(0, (int)result[1].getValue());
        assertEquals(1, (int)result[2].getValue());
        assertEquals(3, (int)result[3].getValue());
    }

    @Test
    public void test3() {
        final Graph.Vertex<Integer>[] result = DepthFirstTraversal.depthFirstTraversal(graph, v0);
        assertEquals(0, (int)result[0].getValue());
        assertEquals(1, (int)result[1].getValue());
        assertEquals(2, (int)result[2].getValue());
        assertEquals(3, (int)result[3].getValue());
    }
}

