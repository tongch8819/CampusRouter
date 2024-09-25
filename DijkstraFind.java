import java.io.IOException;
import java.util.*;

public class DijkstraFind {
    private int size;
    private HashMap<Integer, Double> weight; // store weights for each vertex
    private HashMap<Integer, Integer> previousNode; // store previous vertex
    private PriorityQueue<Integer> pq; // store vertices that need to be visited
    private WeightedGraph graph; // graph object


    public DijkstraFind(WeightedGraph graph) {
        this.graph = graph;
        size = graph.size();
    }

    public ArrayList<Integer> shortestPath(int vertexA, int vertexB) {
        previousNode = new HashMap<Integer, Integer>();
        weight = new HashMap<Integer, Double>();
        pq = new PriorityQueue<Integer>(size, PQComparator);

        /* Set all distances to Infinity */
        for (int vertex : graph.vertices())
            weight.put(vertex, Double.POSITIVE_INFINITY);

        previousNode.put(vertexA, -1); // negative means no previous vertex
        weight.put(vertexA, 0.0); // weight to has to be 0
        pq.add(vertexA); // enqueue first vertex

        while (pq.size() > 0) {
            int currentNode = pq.poll();
            ArrayList<WeightedGraphEdge> neighbors = graph.edgesOf(currentNode);

            if (neighbors == null) continue;

            for (WeightedGraphEdge neighbor : neighbors) {
                int nextVertex = neighbor.right();

                double newDistance = weight.get(currentNode) + neighbor.weight();
                if (weight.get(nextVertex) == Double.POSITIVE_INFINITY) {
                    previousNode.put(nextVertex, currentNode);
                    weight.put(nextVertex, newDistance);
                    pq.add(nextVertex);
                } else {
                    if (weight.get(nextVertex) > newDistance) {
                        previousNode.put(nextVertex, currentNode);
                        weight.put(nextVertex, newDistance);
                    }
                }
            }
        }

        /* Path from A to B will be stored here */
        ArrayList<Integer> nodePath = new ArrayList<Integer>();

        /*
        We are reverse walking points to get to the beginning of the path.
        Using temporary stack to reverse the order of node keys stored in nodePath.
        */
        Stack<Integer> nodePathTemp = new Stack<Integer>();
        nodePathTemp.push(vertexB);

        int v = vertexB;
        while (previousNode.containsKey(v) && previousNode.get(v) >= 0 && v > 0) {
            v = previousNode.get(v);
            nodePathTemp.push(v);
        }

        // Put node in ArrayList in reversed order
        while (nodePathTemp.size() > 0)
            nodePath.add(nodePathTemp.pop());

        return nodePath;
    }

    /**
     * Comparator for priority queue
     */
    public Comparator<Integer> PQComparator = new Comparator<Integer>() {

        public int compare(Integer a, Integer b) {
            if (weight.get(a) > weight.get(b)) {
                return 1;
            } else if (weight.get(a) < weight.get(b)) {
                return -1;
            }
            return 0;
        }
    };

    public static void main(String args[]) {
        WeightedGraph graph;

        try {
            graph = new WeightedGraph("map/edges.txt", "map/poi.txt");
            // Print graph
            System.out.print("Representation of WeighedDigraph\n");
            System.out.print(graph);
            System.out.print("\n");

            DijkstraFind finder = new DijkstraFind(graph);

            // // Print tests
            System.out.print("TESTS\n");
            System.out.print("Test 1 0 -> 1: " + finder.shortestPath(0, 1) + "\n");

        } catch (IOException e) {}
    }
}
