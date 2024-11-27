import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    private GraphInterface<Town, Road> graph;
    private Town[] town;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
        town = new Town[12];

        // Initialize all towns, including town[0]
        for (int i = 0; i < 12; i++) {
            town[i] = new Town("Town_" + i);
            graph.addVertex(town[i]);
        }

        // Add edges between towns
        graph.addEdge(town[1], town[2], 2, "Road_1");
        graph.addEdge(town[1], town[3], 4, "Road_2");
        graph.addEdge(town[1], town[5], 6, "Road_3");
        graph.addEdge(town[3], town[7], 1, "Road_4");
        graph.addEdge(town[3], town[8], 2, "Road_5");
        graph.addEdge(town[4], town[8], 3, "Road_6");
        graph.addEdge(town[6], town[9], 3, "Road_7");
        graph.addEdge(town[9], town[10], 4, "Road_8");
        graph.addEdge(town[8], town[10], 2, "Road_9");
        graph.addEdge(town[5], town[10], 5, "Road_10");
        graph.addEdge(town[10], town[11], 3, "Road_11");
        graph.addEdge(town[2], town[11], 6, "Road_12");
    }

    @After
    public void tearDown() throws Exception {
        graph = null;
    }

    @Test
    public void testGetEdge() {
        assertEquals(new Road(town[2], town[11], 6, "Road_12"), graph.getEdge(town[2], town[11]));
        assertEquals(new Road(town[3], town[7], 1, "Road_4"), graph.getEdge(town[3], town[7]));
    }

    @Test
    public void testAddEdge() {
        assertFalse(graph.containsEdge(town[3], town[5]));
        graph.addEdge(town[3], town[5], 1, "Road_13");
        assertTrue(graph.containsEdge(town[3], town[5]));
    }

    @Test
    public void testAddVertex() {
        Town newTown = new Town("Town_12");
        assertFalse(graph.containsVertex(newTown));
        graph.addVertex(newTown);
        assertTrue(graph.containsVertex(newTown));
    }

    @Test
    public void testContainsEdge() {
        assertTrue(graph.containsEdge(town[2], town[11]));
        assertFalse(graph.containsEdge(town[3], town[5]));
    }

    @Test
    public void testContainsVertex() {
        assertTrue(graph.containsVertex(new Town("Town_2")));
        assertFalse(graph.containsVertex(new Town("Town_12")));
    }

    @Test
    public void testEdgeSet() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> roadArrayList = new ArrayList<>();
        for (Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Road_1", roadArrayList.get(0));
        assertEquals("Road_10", roadArrayList.get(1));
        assertEquals("Road_11", roadArrayList.get(2));
        assertEquals("Road_12", roadArrayList.get(3));
        assertEquals("Road_2", roadArrayList.get(4));
        assertEquals("Road_8", roadArrayList.get(10));
    }

    @Test
    public void testEdgesOf() {
        Set<Road> roads = graph.edgesOf(town[1]);
        ArrayList<String> roadArrayList = new ArrayList<>();
        for (Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Road_1", roadArrayList.get(0));
        assertEquals("Road_2", roadArrayList.get(1));
        assertEquals("Road_3", roadArrayList.get(2));
    }

    @Test
    public void testRemoveEdge() {
        assertTrue(graph.containsEdge(town[2], town[11]));
        graph.removeEdge(town[2], town[11], 6, "Road_12");
        assertFalse(graph.containsEdge(town[2], town[11]));
    }

    @Test
    public void testRemoveVertex() {
        assertTrue(graph.containsVertex(town[2]));
        graph.removeVertex(town[2]);
        assertFalse(graph.containsVertex(town[2]));
    }

    @Test
    public void testVertexSet() {
        Set<Town> towns = graph.vertexSet();
        assertTrue(towns.contains(town[1]));
        assertTrue(towns.contains(town[10]));
        assertTrue(towns.contains(town[11]));
        assertTrue(towns.contains(town[2]));
        assertTrue(towns.contains(town[3]));
    }

    @Test
    public void testTown_1ToTown_11() {
        ArrayList<String> path = graph.shortestPath(town[1], town[11]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town_1 via Road_1 to Town_2 2 mi", path.get(0).trim());
        assertEquals("Town_2 via Road_12 to Town_11 6 mi", path.get(1).trim());
    }

    @Test
    public void testTown1ToTown_10() {
        ArrayList<String> path = graph.shortestPath(town[1], town[10]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town_1 via Road_2 to Town_3 4 mi", path.get(0).trim());
        assertEquals("Town_3 via Road_5 to Town_8 2 mi", path.get(1).trim());
        assertEquals("Town_8 via Road_9 to Town_10 2 mi", path.get(2).trim());
    }

    @Test
    public void testTown_4ToTown_11() {
        ArrayList<String> path = graph.shortestPath(town[4], town[11]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town_4 via Road_6 to Town_8 3 mi", path.get(0).trim());
        assertEquals("Town_8 via Road_9 to Town_10 2 mi", path.get(1).trim());
        assertEquals("Town_10 via Road_11 to Town_11 3 mi", path.get(2).trim());
    }
}
