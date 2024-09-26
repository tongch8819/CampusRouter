import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MapPanelV2 extends JPanel {
    private Map<String, Point> pointsOfInterest;
    private Map<String, Integer> nameToIndexMap; 
    private BufferedImage mapImage;
    private int originalImageWidth;
    private int originalImageHeight;

    private DijkstraFind finder;
    private List<Point> nodes;
    private List<Point> res_nodes;

    private JComboBox<String> sourceComboBox;
    private JComboBox<String> destComboBox;
    private JButton findPathButton;

    public MapPanelV2(String imagePath, String pointsFilePath, String edgesFilePath) {
        pointsOfInterest = new HashMap<>();
        nameToIndexMap = new HashMap<>(); 
        nodes = new ArrayList<Point>();

        try {
            // Load the background image
            mapImage = ImageIO.read(new File(imagePath));
            originalImageWidth = mapImage.getWidth();
            originalImageHeight = mapImage.getHeight();

            // Load points of interest from file
            loadPointsFromFile(pointsFilePath);

            finder = new DijkstraFind(new WeightedGraph(edgesFilePath, pointsFilePath));

            // Initialize UI components
            initializeUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        sourceComboBox = new JComboBox<>(pointsOfInterest.keySet().toArray(new String[0]));
        destComboBox = new JComboBox<>(pointsOfInterest.keySet().toArray(new String[0]));
        findPathButton = new JButton("查找最短路径");

        controlPanel.add(new JLabel("起点:"));
        controlPanel.add(sourceComboBox);
        controlPanel.add(new JLabel("终点:"));
        controlPanel.add(destComboBox);
        controlPanel.add(findPathButton);

        add(controlPanel, BorderLayout.NORTH);

        // 点击按钮要触发的时间
        findPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findShortestPath();
            }
        });
    }

    private void findShortestPath() {
        String sourceName = (String) sourceComboBox.getSelectedItem();
        String destName = (String) destComboBox.getSelectedItem();

        // Use the new HashMap to get indices directly
        int sourceIndex = nameToIndexMap.get(sourceName);
        int destIndex = nameToIndexMap.get(destName);

        ArrayList<Integer> res = finder.shortestPath(sourceIndex, destIndex);
        res_nodes = new ArrayList<Point>();
        for (int i : res) {
            res_nodes.add(nodes.get(i));
        }

        repaint(); // Redraw the panel with the new path
    }

    // Function to load points of interest from the text file
    private void loadPointsFromFile(String pointsFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pointsFilePath), "UTF-8"));

        String line;

        // Read the file line by line
        while ((line = reader.readLine()) != null) {
            // Split the line into components: index, name, x, y
            String[] components = line.split("\\s+");

            if (components.length == 4) {
                int idx = Integer.parseInt(components[0]); // index
                String name = components[1]; // Place name
                int x = Integer.parseInt(components[2]); // X coordinate
                int y = Integer.parseInt(components[3]); // Y coordinate

                // Add the point of interest to the map
                pointsOfInterest.put(name, new Point(x, y));
                nodes.add(new Point(x, y));
                // Add the name and index to the new HashMap
                nameToIndexMap.put(name, idx);
            }
        }

        reader.close();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the actual size of the panel
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();

        // Calculate scaling factors
        double scaleX = (double) panelWidth / originalImageWidth;
        double scaleY = (double) panelHeight / originalImageHeight;

        // Draw the background map image, scaling it to fit the panel
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, panelWidth, panelHeight, this);
        }

        // Set the color for the points of interest
        g.setColor(Color.BLUE);

        // Draw each point of interest after scaling its coordinates
        for (Map.Entry<String, Point> entry : pointsOfInterest.entrySet()) {
            String name = entry.getKey();
            Point originalLocation = entry.getValue();

            // Scale the original point coordinates to the current panel size
            int scaledX = (int) (originalLocation.x * scaleX);
            int scaledY = (int) (originalLocation.y * scaleY);

            // Draw the point
            g.fillOval(scaledX - 5, scaledY - 5, 10, 10);

            // Draw the name of the point next to it
            g.setColor(Color.BLACK);
            g.drawString(name, scaledX + 10, scaledY);
            g.setColor(Color.BLUE); // Reset color for the next point
        }

        if (res_nodes == null)
            return;
        // Set the color for the lines between nodes
        g.setColor(Color.RED);

        // Draw lines between consecutive nodes
        for (int i = 0; i < res_nodes.size() - 1; i++) {
            Point start = res_nodes.get(i);
            Point end = res_nodes.get(i + 1);
            g.drawLine(
                    (int) (start.x * scaleX),
                    (int) (start.y * scaleY),
                    (int) (end.x * scaleX),
                    (int) (end.y * scaleY));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Map with Points of Interest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 680);

        String imagePath = "img/3.png";
        String pointsFilePath = "map/poi.txt";
        String edgesFilePath = "map/edges.txt";

        MapPanelV2 mapPanel = new MapPanelV2(imagePath, pointsFilePath, edgesFilePath);
        frame.add(mapPanel);
        frame.setVisible(true);
    }
}
