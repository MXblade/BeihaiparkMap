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
	//��ȡ��Ļ�Ŀ�Ⱥ͸߶�
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	int Px,Py;//�����洢��ǰѡ�е������
	
	Element element = null;
	// ����ʹ�����·��
	File mapxml = new File("xml/BHpark.xml");

	// documentBuilderΪ������ֱ��ʵ����(��XML�ļ�ת��ΪDOM�ļ�)
	DocumentBuilder db = null;
	DocumentBuilderFactory dbf = null;
	NodeList childNodes = null;

	
	public MapFrame() {
		
		//�趨����Ŀ�Ⱥ͸߶�
		int Framewidth = 770;
		int Frameheight = 800;
		this.setTitle("������԰�����ѯ");
		this.setSize(Framewidth, Frameheight);
		//�趨�����λ��
		this.setLocation((width-Framewidth)/2, (height-Frameheight)/2);
		//���ô���Ĺرշ�ʽΪ�رճ����Զ�����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		//�����ͼPanel������ͼ��ʾ����
		JPanel mainPanel = new JPanel(){
			@Override  
	        protected void paintComponent(Graphics g) {  
	            ImageIcon icon = new ImageIcon("./jpg/BeihaiParkmap.jpg");  
	            Image img = icon.getImage();  
	            g.drawImage(img, 10, 10, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
	        } 
		};
		
		//�����Ҳ�Panel���ı���ʾ��
		JPanel rightPanel = new JPanel();
		JTextArea text = new JTextArea("�����ľ��㣺" +"\n"+ "������԰",39, 20);
		text.setEditable(false);

		//����׶�Panel��ѡ����Ϣ
		JPanel belowPanel = new JPanel();
		JButton buttonD = new JButton("ȷ��");
		JLabel labelX = new JLabel("��ѡ���ͼ�е�һ��      X:  ",JLabel.RIGHT);
		JLabel labelY = new JLabel("Y:  ",JLabel.RIGHT);
		JLabel labelDt = new JLabel("��ѡ����",JLabel.RIGHT);
		JTextField textlineX = new JTextField("X����",4);
		JTextField textlineY = new JTextField("Y����",4);
		textlineX.setEditable(false);
		textlineY.setEditable(false);
		buttonD.setEnabled(false);
		//������Ͽ������
		JComboBox<String> direction = new JComboBox<>();
		direction.addItem("����(��)");
		direction.addItem("����(��)");
		direction.addItem("����(��)");
		direction.addItem("����(��)");
		
				
 
		//���3��Panel������Ŀؼ�
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
		

		//�趨�������ʾ
		this.setVisible(true);

		//�Ե�ͼpanel���mouseLinsener����ȡ���������꣬��������ʾ��textlineX��textlineY��
		mainPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO �Զ����ɵķ������
				if((e.getX()>=10) && (e.getX()<=510) && (e.getY()>=10) && (e.getY()<=710))
				{
					//��mainpaenl��ͼƬ����ʼλ��Ϊ��10��10������ͼ�е��������˵����Ҫ�Ե�����õĵ��������
					Px = e.getX() - 10;
					Py = e.getY() - 10;
					textlineX.setText(String.valueOf(Px));
					textlineY.setText(String.valueOf(Py));
					buttonD.setEnabled(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		
		//���button��ȷ�����ĵ���¼�
		buttonD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				//��ȡdirection��ѡ��ֵ,0Ϊ�ϣ�1Ϊ�£�2Ϊ��3Ϊ��
				int drn = direction.getSelectedIndex();
				//�����������ľ����������ӵ�temp�У����ͳһ��ʾ��text��
				String temp = "�����ľ��㣺\n";
				//���ڴ洢ÿһ�������λ������
				int x = 0;
				int y = 0;
				//���ڴ洢ÿһ����������
				String name = null;
				
				
				//��ȡxml��Ӧ�ڵ�
				try {
					// ����documentBuilderFactory����
					dbf = DocumentBuilderFactory.newInstance();
					// ����db������documentBuilderFatory�����÷���documentBuildr����
					db = dbf.newDocumentBuilder();
					
					// �õ�һ��DOM�����ظ�document����
					Document dt = db.parse(mapxml);
					// �õ�һ��elment��Ԫ��
					element = dt.getDocumentElement();
					// ��ø�Ԫ���µ��ӽڵ�
					childNodes = element.getChildNodes();

				}
				catch (Exception f) {
					f.printStackTrace();
				}
				 
				 
				// ������Щ�ӽڵ�
				for (int i = 0; i < childNodes.getLength(); i++) {
					// ���ÿ����Ӧλ��i�Ľ��
					Node node1 = childNodes.item(i);
					if ("view_spot".equals(node1.getNodeName())) {
						// ���<view_spot>�µĽڵ�
						NodeList nodeDetail = node1.getChildNodes();
						// ����<view_spot>�µĽڵ�
						for (int j = 0; j < nodeDetail.getLength(); j++) {
							// ���<view_spot>Ԫ��ÿһ���ڵ�
							Node detail = nodeDetail.item(j);
							if ("name".equals(detail.getNodeName())) // �����������
								name = detail.getTextContent();
							else if ("location".equals(detail.getNodeName())) // ���pass
							{
								//���<location>�µĽڵ�
								NodeList nodeDetailXY = detail.getChildNodes();
								
								//����<location>�µĽڵ�
								for(int k = 0; k < nodeDetailXY.getLength(); k++)
								{
									
									Node detalxy = nodeDetailXY.item(k);
									if("x".equals(detalxy.getNodeName()))
										x = Integer.parseInt(detalxy.getTextContent());
									else if("y".equals(detalxy.getNodeName()))
										y = Integer.parseInt(detalxy.getTextContent());
								}
								
								//�жϾ����Ƿ�����Ұ�ڣ�ͨ���ǶȺ;���
								if(isView(x, y, drn) && (distance(x,y) <= 550))
								{
									//�����������Ľڵ���ӵ�temp��
									temp = temp + name +": x:" + x + "   y:" + y +"\n";
								}
								
							}
							
						}
					}

				}
				//��text����ʾ����������������������
				text.setText(temp);
			}
		});
		
	}
	
	//�жϾ����Ƿ�����Ұ�Ƕȷ�Χ��
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
	
	//�뵱ǰѡ�е�ľ���
	public int distance(int x, int y)
	{
		int distance = 0;
		float xdistance = (x-Px)*(x-Px);
		float ydistance = (y-Py)*(y-Py);
		distance = (int)Math.sqrt(xdistance+ydistance);
		return distance;
	}
	
	//�뵱ǰѡ�е��б��
	public double xielv(int x, int y)
	{
		double k = 0;
		k = (double)(y-Py)/(double)(x-Px);
		return k;
	}
}


