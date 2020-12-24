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
	private JLayeredPane layeredPane = new JLayeredPane();//层级面板
	private int height=750;
	private int width= height*868/1228;
	private JLabel backgroundLabel;
	private ImageIcon snowIcon;
	private int snowSize=0;		//雪的大小 决定每秒钟产生雪花数与雪花下落速度 
	private final int maxSnowSize=1000;		//最大雪的大小
	private final double snowAmountDivide=0.04;	//每秒钟雪花数比上雪的大小
	private final double snowSpeedDivide=0.2;	//雪花下落速度比上雪的大小
	private int snowStatus=1;	// -1 雪越下越小 0不变 1 雪越下越大
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
			public boolean fall() {		//下落一像素 返回是否删除 删除1
				//System.out.println("下落");
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
				//System.out.println("新建SnowMoveThread"+speed);
				this.speed=speed;
				new Thread(SnowMoveThread.this).start();
			}
			
			public void addNewSnowLabel(SnowLabel snowLabel) {
				//System.out.println("SnowMoveThread:添加新标签");
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
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
		    	}
		    }	
		}
		
		class SnowMoveThreadController{
			private Map<Integer,SnowMoveThread> map = new HashMap<>();
			public void addNewSnowLabel(int startX,int speed) {
				//System.out.println("SnowMoveThreadController添加:"+startX+","+speed);
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
							// TODO 自动生成的 catch 块
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
							// TODO 自动生成的 catch 块
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
						// TODO 自动生成的 catch 块
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
		
		setSize(width,height+37);// 设置横纵坐标和宽高
	    setLocationRelativeTo(null);
		setTitle("ChristmasCard");// 标题
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
            	System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
	}
	
	
}
