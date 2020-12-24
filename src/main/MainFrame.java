package main;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane = new JLayeredPane();//�㼶���
	private int height=750;
	private int width= height*868/1228;
	private JLabel backgroundLabel;
	private ImageIcon snowIcon;
	private int snowSize=0;		//ѩ�Ĵ�С ����ÿ���Ӳ���ѩ������ѩ�������ٶ� 
	private final int maxSnowSize=1000;		//���ѩ�Ĵ�С
	private final double snowAmountDivide=0.04;	//ÿ����ѩ��������ѩ�Ĵ�С
	private final double snowSpeedDivide=0.2;	//ѩ�������ٶȱ���ѩ�Ĵ�С
	private int snowStatus=1;	// -1 ѩԽ��ԽС 0���� 1 ѩԽ��Խ��
	public MainFrame() {
		setContentPane(layeredPane);
		
		ImageIcon backgroundIcon = new ImageIcon("image/background.jpg");
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
		backgroundLabel=new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0,width,height);
		layeredPane.add(backgroundLabel,new Integer(100));
		
		snowIcon = new ImageIcon("image/snow.png");
		snowIcon.setImage(snowIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
		
		class SnowLabel extends JLabel{
			private static final long serialVersionUID = 1L;
			private int x;
			private int y=-20;
			public SnowLabel(int x) {
				super(snowIcon);
				this.x=x;
				setSize(20,20);
				setLocation(x,y);
				layeredPane.add(SnowLabel.this,new Integer(300));
			}
			public boolean fall() {		//����һ���� �����Ƿ�ɾ�� ɾ��1
				//System.out.println("����");
				y++;
				setLocation(x,y);
				return y>=height;
			}
//			public void remove() {
//				layeredPane.remove(SnowLabel.this);
//			}
		}
		
		class SnowMoveThread implements Runnable{
			private int speed;
			private List<SnowLabel> snowLabelList = new LinkedList<>();
			private LinkQueue linkQueue = new LinkQueue();
			//private boolean isRunning=false;
			public SnowMoveThread(int speed) {
				//System.out.println("�½�SnowMoveThread"+speed);
				this.speed=speed;
				new Thread(SnowMoveThread.this).start();
			}
			
			public void addNewSnowLabel(SnowLabel snowLabel) {
				//System.out.println("SnowMoveThread:����±�ǩ");
				linkQueue.EnQueue(snowLabel);
//				if(!isRunning) {
//					new Thread(SnowMoveThread.this).start();
//				}
			}
			
		    @Override  
		    public void run() {
		    	//isRunning=true;
		    	Iterator <SnowLabel> it;
		    	while(true) {
		    		it = snowLabelList.iterator();
		    		while(it.hasNext()) {
		    			if(((SnowLabel)it.next()).fall()){
		    				it.remove();
		    			}
		    		}
		    		while(!linkQueue.QueueEmpty()) {
		    			snowLabelList.add((SnowLabel)linkQueue.DeQueue());
		    		}
//		    		if(snowLabelList.isEmpty()) {
//		    			isRunning=false;
//		    			break;
//		    		}
		    		try {
						Thread.sleep(1000/speed);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
		    	}
		    }	
		}
		
		class SnowMoveThreadController{
			private Map<Integer,SnowMoveThread> map = new HashMap<>();
			public void addNewSnowLabel(int startX,int speed) {
				//System.out.println("SnowMoveThreadController���:"+startX+","+speed);
				SnowLabel snowLabel = new SnowLabel(startX);
				int _speed=speed/30*30;
				SnowMoveThread snowMoveThread = map.get(_speed);
				if(snowMoveThread==null) {
					snowMoveThread = new SnowMoveThread(_speed);
					map.put(_speed, snowMoveThread);
				}
				snowMoveThread.addNewSnowLabel(snowLabel);
			}
		}
		
		class SnowGenerateThread implements Runnable{
			@Override 
			public void run() {
				int startX;
				int speed;
				SnowMoveThreadController snowMoveThreadController = new SnowMoveThreadController();
				while(true) {
					//System.out.println("snowSize:"+snowSize);
					if(snowSize==0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}
					}
					else {
						startX=(int)(Math.random()*width);
						speed=(int)(snowSize*snowSpeedDivide+Math.random()*50+50);
						snowMoveThreadController.addNewSnowLabel(startX, speed);
						try {
							Thread.sleep((int)(1000/(1+snowSize*snowAmountDivide)));
						} catch (InterruptedException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}
					}
					
				}
			}
		}
		
		class SnowSizeControlThread implements Runnable{
			@Override 
			public void run() {
				while(true) {
					try {
						Thread.sleep(100000/maxSnowSize);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
					
					snowSize+=snowStatus;
					if(snowSize>maxSnowSize) {
						snowSize=maxSnowSize;
						snowStatus=-1;
					}
					else if(snowSize<0) {
						snowSize=0;
						snowStatus=1;
					}
				}
			}
		}
		
		
		new Thread(new SnowSizeControlThread()).start();
		new Thread(new SnowGenerateThread()).start();
		
		setSize(width,height+37);// ���ú�������Ϳ��
	    setLocationRelativeTo(null);
		setTitle("ChristmasCard");// ����
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
            	System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
	}
	
	
}
