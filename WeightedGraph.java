import java.awt.Point;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class WeightedGraph {
    private HashMap<Integer, ArrayList<WeightedGraphEdge>> adj = new HashMap(); // adjacency-list

    public WeightedGraph() {
    }

    public WeightedGraph(String edge_file, String node_file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(node_file));
        String line;
        ArrayList<Point> pointsArray = new ArrayList<>();

        // Read the file line by line
        while ((line = reader.readLine()) != null) {
            // Split the line into components: index, name, x, y
            String[] components = line.split("\\s+");
            
            if (components.length == 4) {
                String name = components[1];                // Place name
                int x = Integer.parseInt(components[2]);     // X coordinate
                int y = Integer.parseInt(components[3]);     // Y coordinate

                // Add the name and position to their respective arrays
                // pointNames.add(name);
                pointsArray.add(new Point(x, y));
            }
        }

        reader.close();

        reader = new BufferedReader(new FileReader(edge_file));
        line = null;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s");

            if (parts.length == 2) {
                int left = Integer.parseInt(parts[0]);
                int right = Integer.parseInt(parts[1]);
                // double weight = Double.parseDouble(parts[2]);
                double weight = calculateDistanceBetweenPoints(pointsArray.get(left), pointsArray.get(right));

                addEdge(new WeightedGraphEdge(left, right, weight));
                addEdge(new WeightedGraphEdge(right, left, weight));
            }
        }
        reader.close();
    }

    public double calculateDistanceBetweenPoints(Point p1, Point p2) {
        double x1 = p1.x;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public void addEdge(WeightedGraphEdge newEdge) {
        if (!adj.containsKey(newEdge.left()))
            adj.put(newEdge.left(), new ArrayList<WeightedGraphEdge>());

        // add left to right
        ArrayList<WeightedGraphEdge> currentEdges = adj.get(newEdge.left());

        /*
         * Check if edge already exists,
         * if it is, replace it with new one assuming it needs to be updated
         */
        boolean edgeExists = false;
        for (int i = 0; i < currentEdges.size(); i++) {
            if (currentEdges.get(i).right() == newEdge.right()) {
                currentEdges.set(i, newEdge);
                edgeExists = true;
                break;
            }
        }

        if (!edgeExists)
            currentEdges.add(newEdge);

        adj.put(newEdge.left(), currentEdges);

    }

    public ArrayList<WeightedGraphEdge> edgesOf(int vertex) {
        return adj.get(vertex);
    }

    public ArrayList<WeightedGraphEdge> edges() {
        ArrayList list = new ArrayList<WeightedGraphEdge>();

        for (int from : adj.keySet()) {
            ArrayList<WeightedGraphEdge> currentEdges = adj.get(from);
            for (WeightedGraphEdge e : currentEdges) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * @return iterable of all vertices in the graph.
     */
    public Iterable<Integer> vertices() {
        HashSet set = new HashSet();
        for (WeightedGraphEdge edge : edges()) {
            set.add(edge.left());
            set.add(edge.right());
        }

        return set;
    }

    public int size() {
        return adj.size();
    }

    public String toString() {
        String out = "";
        for (int from : adj.keySet()) {
            ArrayList<WeightedGraphEdge> currentEdges = adj.get(from);
            out += from + " -> ";

            if (currentEdges.size() == 0)
                out += "-,";

            for (WeightedGraphEdge edge : currentEdges)
                out += edge.right() + " @ " + String.format( "%.2f", edge.weight() ) + ", ";

            out += "\n";
        }

        return out;
    }
}
