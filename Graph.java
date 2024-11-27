import java.util.*;

public class Graph implements GraphInterface<Town, Road> {
    // Adjacency list to store graph structure
    private Map<Town, List<Road>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    // Get the road (edge) between two towns if it exists
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        if (sourceVertex == null || destinationVertex == null) return null;
        for (Road road : adjList.getOrDefault(sourceVertex, new ArrayList<>())) {
            if (road.getDestination().equals(destinationVertex)) return road;
        }
        return null;
    }

    // Add a new road (edge) between two towns with specified weight and description
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        if (sourceVertex == null || destinationVertex == null) throw new NullPointerException();
        if (!adjList.containsKey(sourceVertex) || !adjList.containsKey(destinationVertex))
            throw new IllegalArgumentException();

        // Create and add the road to both towns
        Road road = new Road(sourceVertex, destinationVertex, weight, description);
        adjList.get(sourceVertex).add(road);
        adjList.get(destinationVertex).add(new Road(destinationVertex, sourceVertex, weight, description));
        return road;
    }

    // Add a new town (vertex) to the graph
    @Override
    public boolean addVertex(Town v) {
        if (v == null) throw new NullPointerException();
        if (adjList.containsKey(v)) return false; // Vertex already exists
        adjList.put(v, new ArrayList<>()); // Add vertex with empty list of roads
        return true;
    }

    // Check if a road (edge) exists between two towns
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        return getEdge(sourceVertex, destinationVertex) != null;
    }

    // Check if a town (vertex) exists in the graph
    @Override
    public boolean containsVertex(Town v) {
        return adjList.containsKey(v);
    }

    // Return a set of all roads (edges) in the graph
    @Override
    public Set<Road> edgeSet() {
        Set<Road> edges = new HashSet<>();
        for (List<Road> roads : adjList.values()) {
            edges.addAll(roads);
        }
        return edges;
    }

    // Get all roads (edges) connected to a town
    @Override
    public Set<Road> edgesOf(Town vertex) {
        if (!adjList.containsKey(vertex)) throw new IllegalArgumentException();
        return new HashSet<>(adjList.get(vertex));
    }

    // Remove a road (edge) between two towns
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road toRemove = getEdge(sourceVertex, destinationVertex);
        if (toRemove != null) {
            adjList.get(sourceVertex).remove(toRemove);
            adjList.get(destinationVertex).removeIf(road -> road.getDestination().equals(sourceVertex));
        }
        return toRemove;
    }

    // Remove a town (vertex) from the graph
    @Override
    public boolean removeVertex(Town v) {
        if (!adjList.containsKey(v)) return false;
        adjList.remove(v); // Remove the town
        for (List<Road> roads : adjList.values()) {
            roads.removeIf(road -> road.getDestination().equals(v)); // Remove all connected roads
        }
        return true;
    }

    // Return a set of all towns (vertices) in the graph
    @Override
    public Set<Town> vertexSet() {
        return adjList.keySet();
    }

    // Maps to store distances and previous vertices for shortest path calculation
    private Map<Town, Integer> distanceMap;
    private Map<Town, Town> previousVertexMap;

    // Find the shortest path from source to destination using Dijkstra's algorithm
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        dijkstraShortestPath(sourceVertex);
        ArrayList<String> path = new ArrayList<>();
        Town current = destinationVertex;

        // Backtrack from destination to source using the previousVertexMap
        while (current != null && !current.equals(sourceVertex)) {
            Town prev = previousVertexMap.get(current);
            if (prev == null) break; // No path exists
            Road road = getEdge(prev, current);
            path.add(prev.getName() + " via " + road.getName() + " to " + current.getName() + " " + road.getWeight() + " mi");
            current = prev;
        }

        Collections.reverse(path); // Reverse the path to get the correct order
        return path.isEmpty() ? null : path;
    }

    // Compute shortest paths from the source vertex to all other vertices
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        if (!adjList.containsKey(sourceVertex)) throw new IllegalArgumentException("Source vertex not found");

        // Initialize distances and previous vertex maps
        distanceMap = new HashMap<>();
        previousVertexMap = new HashMap<>();

        for (Town town : adjList.keySet()) {
            distanceMap.put(town, Integer.MAX_VALUE); // Initially set to "infinity"
            previousVertexMap.put(town, null); // No previous vertex yet
        }

        distanceMap.put(sourceVertex, 0); // Distance to source is 0
        PriorityQueue<Town> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));
        priorityQueue.add(sourceVertex);

        // Process vertices in the priority queue
        while (!priorityQueue.isEmpty()) {
            Town current = priorityQueue.poll();

            // Iterate over neighbors of the current town
            for (Road road : adjList.get(current)) {
                Town neighbor = road.getDestination();
                int newDist = distanceMap.get(current) + road.getWeight();

                // Update distance and previous vertex if a shorter path is found
                if (newDist < distanceMap.get(neighbor)) {
                    distanceMap.put(neighbor, newDist);
                    previousVertexMap.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }
    }
}
