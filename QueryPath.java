import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

class QueryPath extends JPanel {
	JFrame jf = new JFrame("校园行走最优路径查询系统");

	private Map<String, Point> pointsOfInterest;
    private BufferedImage mapImage; // null
    private int originalImageWidth;
    private int originalImageHeight;

    private DijkstraFind finder;
    private List<Point> nodes;
    private List<Point> res_nodes;

    private JComboBox<String> sourceComboBox;
    private JComboBox<String> destComboBox;
    // private JButton findPathButton;
	
	public QueryPath() throws IOException{
		// data structure initialization
		pointsOfInterest = new HashMap<>();
        nodes = new ArrayList<Point>();

		loadPointsFromFile("map/poi.txt");
		mapImage = ImageIO.read(new File("img/3.png"));
		originalImageWidth = mapImage.getWidth();
		originalImageHeight = mapImage.getHeight();

		finder = new DijkstraFind(new WeightedGraph("map/edges.txt", "map/poi.txt"));


		
		// Toolkit t = Toolkit.getDefaultToolkit();
		// Image img = t.getImage("img/logo.jpg");
		// jf.setIconImage(img);
		
		// ImageIcon background = new ImageIcon("img/3.png");
		// JLabel label = new JLabel(background);
		// label.setBounds(0, 0, 850, 850);
		// jf.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		
		JPanel jp = (JPanel)jf.getContentPane();
	
		jp.setOpaque(false);
		
		ImageIcon bg = new ImageIcon("img/4.jpg");
	    JLabel label2 = new JLabel(bg);
	    label2.setBounds(850, -56, 300, 850);
	    jf.getLayeredPane().add(label2, new Integer(Integer.MIN_VALUE));
		
	    JPanel jp2 = (JPanel) jf.getContentPane();
	    jp2.setOpaque(false);
	    
	    
		initializeUI();
		
	}
	
	// public void newPathAction() {
	// 	final JTextField c = new JTextField(20); // 用户输入起点名称
	// 	final JTextField d = new JTextField(20); // 用户输入终点名称
    //     int num1 = solve.checkname(c.getText());
    //     int num2 = solve.checkname(d.getText());
    //     if (num1 == num2 && num1 != 0) {
    //         JOptionPane.showMessageDialog(null, "已经在目的地!", "提示", JOptionPane.INFORMATION_MESSAGE);
    //     } else {
    //         if (!solve.vis[num1] || !solve.vis[num2] || num1 == 0 || num2 == 0 || num1 > solve.cnt || num2 > solve.cnt) {
    //             show_error();
    //         } else {
    //             solve.dijkstra(num1, num2);
    //             ArrayList<Integer> path = null; // Assuming this method exists
    //             JTextArea text = null;
	// 			updatePathInfo(text, path, distance); // Update path info on UI
    //         }
    //     }
    // }

    // private void updatePathInfo(JTextArea text, ArrayList<Integer> path, Double distance) {
    //     StringBuilder sb = new StringBuilder();
    //     for (int i = path.size() - 1; i >= 0; i--) {
    //         sb.append(solve.name[path.get(i)]).append(i != 0 ? " ---> " : "");
    //     }
    //     int num2 = 0;
	// 	sb.append("\n预计时间:").append(String.format("%.2f", distance * 6 / 10000)).append("h");
    //     text.setText(sb.toString());
    // }

	private void initializeUI() {
        // setLayout(new BorderLayout());

		JPanel jpanel = new JPanel();
	    jpanel.setOpaque(false);
	    jpanel.setLayout(null);




	    
	    JLabel start_label = new JLabel("起点:", JLabel.CENTER);
		start_label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		// start_label.setBounds(850, 20, 100, 20);

		JLabel end_label = new JLabel("终点:", JLabel.CENTER);
		end_label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		// end_label.setBounds(850, 50, 100, 20);
		
		// final JTextField start_textfield = new JTextField();
		// final JTextField end_textfield = new JTextField();
		// start_textfield.setBounds(930, 23, 170, 20);
		// end_textfield.setBounds(930, 53, 170, 20);

		JComboBox<String> sourceComboBox = new JComboBox<>(pointsOfInterest.keySet().toArray(new String[0]));
		// sourceComboBox.setBounds(930, 23, 170, 20);
    	JComboBox<String> destComboBox = new JComboBox<>(pointsOfInterest.keySet().toArray(new String[0]));
		// destComboBox.setBounds(930, 53, 170, 20);

		jpanel.add(start_label);
		jpanel.add(end_label);
		jpanel.add(sourceComboBox);
		jpanel.add(destComboBox);


		// JButton min_time_button = new JButton("最短时间");
		// min_time_button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		// min_time_button.setForeground(Color.BLACK);
		// min_time_button.setBackground(Color.YELLOW);
		// min_time_button.setBorderPainted(false);
		// final JTextArea text = new JTextArea();
		// text.setBounds(850, 195, 300, 300);
		// text.setOpaque(false);
		// jpanel.add(text);
		// min_time_button.setBounds(1030, 99, 100, 30);
		
		// min_time_button.addActionListener(new ActionListener(){
		// 	@Override
		// 	public void actionPerformed(ActionEvent e){
		// 		int num1 = solve.checkname(c.getText());
		// 		int num2 = solve.checkname(d.getText());
		// 		if (num1 == num2 && num1 != 0){
		// 			JOptionPane.showMessageDialog(null, "已经在目的地!", "提示", JOptionPane.INFORMATION_MESSAGE);
		// 		}
		// 		else{
		// 			if (!solve.vis[num1] || !solve.vis[num2] || num1 == 0 || num2 == 0 || num1 > solve.cnt || num2 > solve.cnt)
		// 				show_error();
		// 			else{
		// 				// solve.dijkstra(num1, num2);
		// 				// Graphics graphics = jf.getGraphics();
		// 				// newpaint(graphics);
		// 				String ans = "";
		// 				for (int i = solve.plan_cnt; i >= 0; i--){
		// 					//最后一个点不加箭头
		// 					if (i != 0){
		// 						ans += solve.name[solve.ans[i]] + "--->";
		// 					}
		// 					else
		// 						ans += solve.name[solve.ans[i]];
		// 					//最后一个点直接跳出，不打印步行二字
		// 					if (i == 0)
		// 						continue;

		// 					ans += "步行\n";
		// 				}
		// 				ans += "\n预计时间:" + String.format("%.2f", solve.dis[num2] * 6 / 10000) + "h";
		// 				text.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		// 				text.setText(ans);
		// 				text.setForeground(Color.BLACK);
		// 			}
		// 		}
		// 	}
		// });
		











		// JLabel recommendation, yuji; 	//标签
		// recommendation = new JLabel("推荐路线", JLabel.CENTER);
		// recommendation.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		// recommendation.setBounds(950, 140, 100, 50);
		// jpanel.add(recommendation);






		JButton shortestpath_button = new JButton("最短路径");
		shortestpath_button.setBorderPainted(false);
		shortestpath_button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		shortestpath_button.setForeground(Color.WHITE);
		shortestpath_button.setBackground(Color.GREEN);
		// shortestpath_button.setBounds(880, 99, 100, 30);
		shortestpath_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findShortestPath();
			}
		});
		jpanel.add(shortestpath_button);


		
		JButton exit = new JButton("退出");
		exit.setBorderPainted(false);
		exit.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		exit.setForeground(Color.WHITE);
		exit.setBackground(Color.RED);
		// exit.setBounds(950, 600, 100, 40);
		
		exit.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent a) {
				int id = 0;
				if (a.getButton() == a.BUTTON1){	
					// jf.setVisible(false);
					new Login();
				}
			}
			public void mousePressed(MouseEvent a) {}
			public void mouseReleased(MouseEvent a) {}
			public void mouseEntered(MouseEvent a) {}
			public void mouseExited(MouseEvent a) {}
		});

		jpanel.add(exit);
	












		
		// jpanel.add(min_time_button);
		
		jf.add(jpanel, BorderLayout.CENTER);
		jpanel.setLayout(null);
		jf.setSize(1150, 800);
		
		jf.setLocationRelativeTo(null); //在屏幕中间显示(居中显示)
		jf.setResizable(false);
		jf.setVisible(true); //显示窗体
		 //监听事件：监听窗口关闭程序
        // 适配器模式：
		jf.addWindowListener(new WindowAdapter() {
      		//窗体点击关闭时，要做的事
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				new Login();
			}
		});
	}

	// @Override
    // protected void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     // Get the actual size of the panel
    //     int panelWidth = this.getWidth();
    //     int panelHeight = this.getHeight();

    //     // Calculate scaling factors
    //     double scaleX = (double) panelWidth / originalImageWidth;
    //     double scaleY = (double) panelHeight / originalImageHeight;

    //     // Draw the background map image, scaling it to fit the panel
    //     if (mapImage != null) {
    //         g.drawImage(mapImage, 0, 0, panelWidth, panelHeight, this);
    //     }

    //     // Set the color for the points of interest
    //     g.setColor(Color.BLUE);

    //     // Draw each point of interest after scaling its coordinates
    //     for (Map.Entry<String, Point> entry : pointsOfInterest.entrySet()) {
    //         String name = entry.getKey();
    //         Point originalLocation = entry.getValue();

    //         // Scale the original point coordinates to the current panel size
    //         int scaledX = (int) (originalLocation.x * scaleX);
    //         int scaledY = (int) (originalLocation.y * scaleY);

    //         // Draw the point
    //         g.fillOval(scaledX - 5, scaledY - 5, 10, 10);

    //         // Draw the name of the point next to it
    //         g.setColor(Color.BLACK);
    //         g.drawString(name, scaledX + 10, scaledY);
    //         g.setColor(Color.BLUE); // Reset color for the next point
    //     }

    //     if (res_nodes == null)
    //         return;
    //     // Set the color for the lines between nodes
    //     g.setColor(Color.RED);

    //     // Draw lines between consecutive nodes
    //     for (int i = 0; i < res_nodes.size() - 1; i++) {
    //         Point start = res_nodes.get(i);
    //         Point end = res_nodes.get(i + 1);
    //         g.drawLine(
    //                 (int) (start.x * scaleX),
    //                 (int) (start.y * scaleY),
    //                 (int) (end.x * scaleX),
    //                 (int) (end.y * scaleY));
    //     }
    // }

    
	public void show_error(){
		JOptionPane.showMessageDialog(null, "输入值非法!", "错误!", JOptionPane.ERROR_MESSAGE);
	}
	
	private void loadPointsFromFile(String pointsFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pointsFilePath), "UTF-8"));

        String line;

        // Read the file line by line
        while ((line = reader.readLine()) != null) {
            // Split the line into components: index, name, x, y
            String[] components = line.split("\\s+");

            if (components.length == 4) {
                // int idx = Integer.parseInt(components[0]); // index
                String name = components[1]; // Place name
                int x = Integer.parseInt(components[2]); // X coordinate
                int y = Integer.parseInt(components[3]); // Y coordinate

                // Add the point of interest to the map
                pointsOfInterest.put(name, new Point(x, y));
                nodes.add(new Point(x, y));
            }
        }

        reader.close();
    }

	private void findShortestPath() {
        String sourceName = (String) sourceComboBox.getSelectedItem();
        String destName = (String) destComboBox.getSelectedItem();

        int sourceIndex = new ArrayList<>(pointsOfInterest.keySet()).indexOf(sourceName);
        int destIndex = new ArrayList<>(pointsOfInterest.keySet()).indexOf(destName);

        ArrayList<Integer> res = finder.shortestPath(sourceIndex, destIndex);
        res_nodes = new ArrayList<Point>();
        for (int i : res) {
            res_nodes.add(nodes.get(i));
        }

        repaint(); // Redraw the panel with the new path
    }

}
