import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login {
	
	JFrame jf = new JFrame("校园行走最优路径查询系统");
	
	public Login(){
		
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("img/logo.jpg");
		jf.setIconImage(img);
		
		ImageIcon background = new ImageIcon("img/1.png");
		JLabel label = new JLabel(background);
		jf.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		
		JPanel jp = (JPanel)jf.getContentPane();
		jp.setOpaque(false);

		JPanel jpanel = new JPanel();
		jpanel.setOpaque(false);
		jpanel.setLayout(null);
		
		JLabel lb0 = new JLabel("校园行走最优路径查询系统");
		lb0.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		lb0.setForeground(Color.RED);
		lb0.setBounds(300, 35, 1000, 50);
		
		JButton jb1 = new JButton("查询地点");
		JButton jb2 = new JButton("查询路径");
		JButton jb21 = new JButton("查询路径（突发）");
		JButton jb3 = new JButton("显示地图");
		
		jb1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jb2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jb21.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jb3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		jb1.setBounds(490, 120, 120, 30);
		jb2.setBounds(490, 170, 120, 30);
		jb21.setBounds(490, 270, 200, 30);
		jb3.setBounds(490, 220, 120, 30);
		
		jpanel.add(lb0);
		jpanel.add(jb1);
		jpanel.add(jb2);
		jpanel.add(jb3);
		jpanel.add(jb21);
		
		jf.add(jpanel);
		
		jf.setSize(1100, 680);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setVisible(true);
		
		// label.setSize(jf.getSize());
		
		//确保背景图片自适应窗口大小
        jf.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                label.setSize(jf.getWidth(), jf.getHeight()); // 动态调整背景图片大小
                label.setIcon(new ImageIcon(background.getImage().getScaledInstance(jf.getWidth(), jf.getHeight(), Image.SCALE_SMOOTH)));
            }
        });
		
		//查询地点绑定事件监听
		jb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				// jf.setVisible(false);
				System.out.println("Click Search Place");
			}
		});
		
		//查询路径绑定事件监听
		jb2.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent a){
				// jf.setVisible(false);
				// System.out.println("Click Path");
				// new MapPanelV2("img/3.png", "map/poi.txt");
				MapPanelV2 mapPanel = new MapPanelV2("img/3.png", "map/poi.txt", "map/edges.txt");
				jf.getContentPane().removeAll();
				jf.getContentPane().add(mapPanel);
				jf.revalidate();
				jf.repaint();
				// jf.setVisible(true);

				// try {
				// 	// new QueryPath();
				// } catch (IOException e) {
				// 	System.out.println("An I/O error occurred: " + e.getMessage());
				// }
			}
		});
		
		//查询路径绑定事件监听
		jb21.addActionListener(new ActionListener(){
					
			@Override
			public void actionPerformed(ActionEvent a){
				// jf.setVisible(false);
				// System.out.println("Click Path");
				// new MapPanelV2("img/3.png", "map/poi.txt");
				MapPanelV2 mapPanel = new MapPanelV2("img/3.png", "map/poi.txt", "map/edges_emergence.txt");
				jf.getContentPane().removeAll();
				jf.getContentPane().add(mapPanel);
				jf.revalidate();
				jf.repaint();
				// jf.setVisible(true);

				// try {
				// 	// new QueryPath();
				// } catch (IOException e) {
				// 	System.out.println("An I/O error occurred: " + e.getMessage());
				// }
			}
		});

		//显示地图绑定事件监听
		jb3.addActionListener(new ActionListener(){	
			@Override
			public void actionPerformed(ActionEvent a){
				// jf.setVisible(false);
				System.out.println("Click Show Map");
			}
		});
	}
	
	public static void main(String[] args){
		new Login();
	}
}
