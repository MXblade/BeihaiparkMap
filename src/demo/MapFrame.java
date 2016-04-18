package demo;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapFrame extends JFrame{
	//获取屏幕的宽度和高度
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	int Px,Py;//用来存储当前选中点的坐标
	
	Element element = null;
	// 可以使用相对路径
	File mapxml = new File("xml/BHpark.xml");

	// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
	DocumentBuilder db = null;
	DocumentBuilderFactory dbf = null;
	NodeList childNodes = null;

	
	public MapFrame() {
		
		//设定界面的宽度和高度
		int Framewidth = 770;
		int Frameheight = 800;
		this.setTitle("北海公园景点查询");
		this.setSize(Framewidth, Frameheight);
		//设定界面的位置
		this.setLocation((width-Framewidth)/2, (height-Frameheight)/2);
		//设置窗体的关闭方式为关闭程序自动结束
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		//定义地图Panel，将地图显示出来
		JPanel mainPanel = new JPanel(){
			@Override  
	        protected void paintComponent(Graphics g) {  
	            ImageIcon icon = new ImageIcon("./jpg/BeihaiParkmap.jpg");  
	            Image img = icon.getImage();  
	            g.drawImage(img, 10, 10, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
	        } 
		};
		
		//定义右侧Panel和文本显示框
		JPanel rightPanel = new JPanel();
		JTextArea text = new JTextArea("包含的景点：" +"\n"+ "北海公园",39, 20);
		text.setEditable(false);

		//定义底端Panel和选择信息
		JPanel belowPanel = new JPanel();
		JButton buttonD = new JButton("确定");
		JLabel labelX = new JLabel("请选择地图中的一点      X:  ",JLabel.RIGHT);
		JLabel labelY = new JLabel("Y:  ",JLabel.RIGHT);
		JLabel labelDt = new JLabel("请选择方向：",JLabel.RIGHT);
		JTextField textlineX = new JTextField("X坐标",4);
		JTextField textlineY = new JTextField("Y坐标",4);
		textlineX.setEditable(false);
		textlineY.setEditable(false);
		buttonD.setEnabled(false);
		//设置组合框的属性
		JComboBox<String> direction = new JComboBox<>();
		direction.addItem("北面(上)");
		direction.addItem("南面(下)");
		direction.addItem("西面(左)");
		direction.addItem("东面(右)");
		
				
 
		//添加3个Panel和里面的控件
		getContentPane().add(mainPanel,BorderLayout.CENTER);
		getContentPane().add(belowPanel,BorderLayout.SOUTH);
		getContentPane().add(rightPanel,BorderLayout.EAST);
		rightPanel.add(text);
		belowPanel.add(labelX);
		belowPanel.add(textlineX);
		belowPanel.add(labelY);
		belowPanel.add(textlineY);
		belowPanel.add(labelDt);
		belowPanel.add(direction);
		belowPanel.add(buttonD);
		

		//设定界面可显示
		this.setVisible(true);

		//对地图panel添加mouseLinsener，获取点击点的坐标，将坐标显示在textlineX和textlineY中
		mainPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				if((e.getX()>=10) && (e.getX()<=510) && (e.getY()>=10) && (e.getY()<=710))
				{
					//在mainpaenl中图片的起始位置为（10，10），对图中的坐标点来说，需要对点击所得的点进行修正
					Px = e.getX() - 10;
					Py = e.getY() - 10;
					textlineX.setText(String.valueOf(Px));
					textlineY.setText(String.valueOf(Py));
					buttonD.setEnabled(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		//添加button“确定”的点击事件
		buttonD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				//获取direction的选择值,0为上，1为下，2为左，3为右
				int drn = direction.getSelectedIndex();
				//将符合条件的景点的内容添加到temp中，最后统一显示在text中
				String temp = "包含的景点：\n";
				//用于存储每一个景点的位置坐标
				int x = 0;
				int y = 0;
				//用于存储每一个景点名字
				String name = null;
				
				
				//读取xml对应节点
				try {
					// 返回documentBuilderFactory对象
					dbf = DocumentBuilderFactory.newInstance();
					// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
					db = dbf.newDocumentBuilder();
					
					// 得到一个DOM并返回给document对象
					Document dt = db.parse(mapxml);
					// 得到一个elment根元素
					element = dt.getDocumentElement();
					// 获得根元素下的子节点
					childNodes = element.getChildNodes();

				}
				catch (Exception f) {
					f.printStackTrace();
				}
				 
				 
				// 遍历这些子节点
				for (int i = 0; i < childNodes.getLength(); i++) {
					// 获得每个对应位置i的结点
					Node node1 = childNodes.item(i);
					if ("view_spot".equals(node1.getNodeName())) {
						// 获得<view_spot>下的节点
						NodeList nodeDetail = node1.getChildNodes();
						// 遍历<view_spot>下的节点
						for (int j = 0; j < nodeDetail.getLength(); j++) {
							// 获得<view_spot>元素每一个节点
							Node detail = nodeDetail.item(j);
							if ("name".equals(detail.getNodeName())) // 输出景点名称
								name = detail.getTextContent();
							else if ("location".equals(detail.getNodeName())) // 输出pass
							{
								//获得<location>下的节点
								NodeList nodeDetailXY = detail.getChildNodes();
								
								//遍历<location>下的节点
								for(int k = 0; k < nodeDetailXY.getLength(); k++)
								{
									
									Node detalxy = nodeDetailXY.item(k);
									if("x".equals(detalxy.getNodeName()))
										x = Integer.parseInt(detalxy.getTextContent());
									else if("y".equals(detalxy.getNodeName()))
										y = Integer.parseInt(detalxy.getTextContent());
								}
								
								//判断景点是否在视野内，通过角度和距离
								if(isView(x, y, drn) && (distance(x,y) <= 550))
								{
									//将符合条件的节点添加的temp中
									temp = temp + name +": x:" + x + "   y:" + y +"\n";
								}
								
							}
							
						}
					}

				}
				//在text中显示出遍历出符合条件的内容
				text.setText(temp);
			}
		});
		
	}
	
	//判断景点是否在视野角度范围内
	public boolean isView(int x, int y, int direction)
	{
		boolean isView = false;
		if(direction == 0)
		{
			if(y > Py)
			{
				isView = false;
			}
			else if((x == Px) || (xielv(x,y) >= 1) || (xielv(x,y) <= -1))
			{
				isView = true;
			}
		}
		else if(direction == 1)
		{
			if(y < Py)
			{
				isView = false;
			}
			else if((x == Px) || (xielv(x,y) >= 1) || (xielv(x,y) <= -1))
			{
				isView = true;
			}
			
		}
		else if(direction == 2)
		{
			if(x > Px)
			{
				isView = false;
			}
			else if((y == Py) || (xielv(x,y) <= 1) || (xielv(x,y) >= -1))
			{
				isView = true;
			}
		}
		else if(direction == 3)
		{
			if(x < Px)
			{
				isView = false;
			}
			else if((y == Py) || (xielv(x,y) <= 1) || (xielv(x,y) >= -1))
			{
				isView = true;
			}
		}
		return isView;
	}
	
	//与当前选中点的距离
	public int distance(int x, int y)
	{
		int distance = 0;
		float xdistance = (x-Px)*(x-Px);
		float ydistance = (y-Py)*(y-Py);
		distance = (int)Math.sqrt(xdistance+ydistance);
		return distance;
	}
	
	//与当前选中点的斜率
	public double xielv(int x, int y)
	{
		double k = 0;
		k = (double)(y-Py)/(double)(x-Px);
		return k;
	}
}


