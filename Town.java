public class Town implements Comparable<Town> {
    // Attribute for the town's name
    private String townName;

    // Constructor to initialize the town with a name
    public Town(String name) {
        this.townName = name;
    }

    // Getter for the town's name
    public String getName() {
        return townName;
    }

    // Compare towns by their names (case-insensitive)
    @Override
    public int compareTo(Town o) {
        return this.townName.compareToIgnoreCase(o.getName());
    }

    // Override equals to compare towns by their names (case-insensitive)
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Town)) return false; // Check if the object is a Town
        Town other = (Town) obj;
        return this.townName.equalsIgnoreCase(other.getName());
    }

    // Override hashCode to generate hash based on the town's name (case-insensitive)
    @Override
    public int hashCode() {
        return townName.toLowerCase().hashCode();
    }

    // String representation of the town for debugging or display
    @Override
    public String toString() {
        return townName;
    }
}
