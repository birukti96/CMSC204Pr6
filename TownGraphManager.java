import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class TownGraphManager implements TownGraphManagerInterface {
    // Instance of the Graph to manage towns and roads
    private Graph map;

    // Constructor to initialize the graph
    public TownGraphManager() {
        map = new Graph();
    }

    // Add a road between two towns with a specified weight and name
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        if (town1 == null || town2 == null || roadName == null || weight < 0) {
            throw new IllegalArgumentException("Invalid input: towns, road name, or weight cannot be null or negative.");
        }

        Town source = new Town(town1);
        Town destination = new Town(town2);

        try {
            return map.addEdge(source, destination, weight, roadName) != null;
        } catch (Exception e) {
            System.err.println("Error adding road: " + e.getMessage());
            return false;
        }
    }

    // Get the name of the road connecting two towns
    @Override
    public String getRoad(String town1, String town2) {
        if (town1 == null || town2 == null) {
            throw new IllegalArgumentException("Towns cannot be null.");
        }

        Town source = new Town(town1);
        Town destination = new Town(town2);

        Road road = map.getEdge(source, destination);
        return (road != null) ? road.getName() : null;
    }

    // Add a town to the graph
    @Override
    public boolean addTown(String v) {
        if (v == null || v.isEmpty()) {
            throw new IllegalArgumentException("Town name cannot be null or empty.");
        }

        Town town = new Town(v);

        try {
            return map.addVertex(town);
        } catch (Exception e) {
            System.err.println("Error adding town: " + e.getMessage());
            return false;
        }
    }

    // Get a Town object by its name if it exists
    @Override
    public Town getTown(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Town name cannot be null or empty.");
        }

        if (containsTown(name)) {
            return new Town(name);
        }
        return null;
    }

    // Check if a town exists in the graph
    @Override
    public boolean containsTown(String v) {
        if (v == null || v.isEmpty()) {
            throw new IllegalArgumentException("Town name cannot be null or empty.");
        }

        Town town = new Town(v);
        return map.containsVertex(town);
    }

    // Check if a road exists between two towns
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        if (town1 == null || town2 == null) {
            throw new IllegalArgumentException("Towns cannot be null.");
        }

        Town source = new Town(town1);
        Town destination = new Town(town2);

        return map.containsEdge(source, destination);
    }

    // Get a sorted list of all road names in the graph
    @Override
    public ArrayList<String> allRoads() {
        Set<Road> roads = map.edgeSet();
        ArrayList<String> roadList = new ArrayList<>();

        for (Road road : roads) {
            roadList.add(road.getName());
        }

        Collections.sort(roadList);
        return roadList;
    }

    // Remove a road connection between two towns by its name
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String roadName) {
        if (town1 == null || town2 == null || roadName == null) {
            throw new IllegalArgumentException("Invalid input: towns or road name cannot be null.");
        }

        Town source = new Town(town1);
        Town destination = new Town(town2);

        Road road = map.getEdge(source, destination);
        if (road != null && road.getName().equals(roadName)) {
            return map.removeEdge(source, destination, road.getWeight(), roadName) != null;
        }
        return false;
    }

    // Remove a town from the graph
    @Override
    public boolean deleteTown(String v) {
        if (v == null || v.isEmpty()) {
            throw new IllegalArgumentException("Town name cannot be null or empty.");
        }

        Town town = new Town(v);
        return map.removeVertex(town);
    }

    // Get a sorted list of all town names in the graph
    @Override
    public ArrayList<String> allTowns() {
        Set<Town> towns = map.vertexSet();
        ArrayList<String> townList = new ArrayList<>();

        for (Town town : towns) {
            townList.add(town.getName());
        }

        Collections.sort(townList);
        return townList;
    }

    // Get the shortest path between two towns as a list of strings
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        if (town1 == null || town2 == null) {
            throw new IllegalArgumentException("Towns cannot be null.");
        }

        Town source = new Town(town1);
        Town destination = new Town(town2);

        ArrayList<String> path = map.shortestPath(source, destination);
        return (path != null) ? path : new ArrayList<>();
    }
}
