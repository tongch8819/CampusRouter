public class WeightedGraphEdge {
    private int left, right;
    private double weight;

    public WeightedGraphEdge(int left, int right, double weight) {
        this.left = left;
        this.right = right;
        this.weight = weight;
    }

    public int left() { return left; }

    public int right() { return right; }

    public double weight() { return weight; }
}
