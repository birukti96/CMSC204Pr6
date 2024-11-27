public class Road implements Comparable<Road> {
    // Attributes for the road: name, source town, destination town, and weight
    private String roadName;
    private Town roadSource, roadDestination;
    private int weight;

    // Constructor to initialize the road with source, destination, weight, and name
    public Road(Town source, Town destination, int weight, String name) {
        this.roadSource = source;
        this.roadDestination = destination;
        this.weight = weight;
        this.roadName = name;
    }

    // Getter for the road name
    public String getName() {
        return roadName;
    }

    // Getter for the source town
    public Town getSource() {
        return roadSource;
    }

    // Getter for the destination town
    public Town getDestination() {
        return roadDestination;
    }

    // Getter for the weight of the road
    public int getWeight() {
        return weight;
    }

    // Override equals to compare roads by source, destination, name, and weight
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Road)) return false; // Check for correct type
        Road other = (Road) obj;

        // Two roads are equal if they connect the same towns (in any direction),
        // have the same name, and have the same weight
        return ((roadSource.equals(other.roadSource) && roadDestination.equals(other.roadDestination)) ||
                (roadSource.equals(other.roadDestination) && roadDestination.equals(other.roadSource))) &&
                roadName.equalsIgnoreCase(other.roadName) &&
                weight == other.weight;
    }

    // Override hashCode for proper hashing based on the road's attributes
    @Override
    public int hashCode() {
        return roadSource.hashCode() + roadDestination.hashCode() + roadName.hashCode() + weight;
    }

    // Compare roads by their names (case-insensitive)
    @Override
    public int compareTo(Road o) {
        return this.roadName.compareToIgnoreCase(o.getName());
    }

    // String representation of the road for debugging or display
    @Override
    public String toString() {
        return roadName + ", " + roadSource + " - " + roadDestination + " (" + weight + " mi)";
    }
}
