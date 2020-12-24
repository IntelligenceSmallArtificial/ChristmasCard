package pianoPlayer;

public class Piano {
	private Key keys[];
	
	public Piano() {
		keys = new Key[88]; 
		for(int i=0;i<88;i++) {
			keys[i] = new Key(i);
		}
	}
	
	
	
	//1		40
	//#1	41
	//2		42
	//#2	43
	//3		44
	//4		45
	//#4	46
	//5		47
	//#5	48
	//6		49
	//#6	50
	//7		51
	//1		52
	//+#1	53
	//+2	54
	//+#2	55
	//+3	56
	//+4	57
	//+#4	58
	//+5	59
	//+#5	60
	//+6	61
	//+#6	62
	//+7	63
	
	public void playStar() {
		class playStarThread implements Runnable{   //连接房间的线程
	    	private int majorMove=5;
	    	private int speed=120;
	    	private int beat=60000/speed;
	    	private int playBeat=50000/speed;
	    	private int counter=0;
			public void play(int key,int beatUp,int beatDown) {
				keys[key+majorMove].play(playBeat*beatUp/beatDown);
	    		System.out.println(++counter);
	    		try {Thread.sleep(beat*beatUp/beatDown);} catch (InterruptedException e) {}
			}
			public void play(int key,int beatDown) {
				play(key,1,beatDown);
			}
		    @Override  
		    public void run() {
		    	while(true) {
		    		//1		40
		    		//#1	41
		    		//2		42
		    		//#2	43
		    		//3		44
		    		//4		45
		    		//#4	46
		    		//5		47
		    		//#5	48
		    		//6		49
		    		//#6	50
		    		//7		51
		    		//+1	52
		    		//+#1	53
		    		//+2	54
		    		//+#2	55
		    		//+3	56
		    		//+4	57
		    		//+#4	58
		    		//+5	59
		    		//+#5	60
		    		//+6	61
		    		//+#6	62
		    		//+7	63
		    		counter=0;
//		    		try {Thread.sleep(500);} catch (InterruptedException e) {}
		    		play(47,2);//5
		    		play(47,2);//5
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(52,2);//1
		    		play(47,3,2);//5
		    		
		    		play(47,2);//5
		    		play(47,2);//5
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(52,2);//1
		    		play(49,3,2);//6
		    		
		    		play(49,2);//6
		    		play(49,2);//6
		    		play(57,2);//4
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(51,3,2);//7
		    		
		    		play(59,2);//5
		    		play(61,2);//6
		    		play(59,2);//5
		    		play(57,2);//4
		    		play(54,2);//2
		    		play(56,3,2);//3
		    		
		    		play(47,2);//5
		    		play(47,2);//5
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(52,2);//1
		    		play(47,3,2);//5
		    		
		    		play(47,2);//5
		    		play(47,2);//5
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(52,2);//1
		    		play(49,3,2);//6
		    		
		    		play(49,2);//6
		    		play(49,2);//6
		    		play(57,2);//4
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(59,2);//5
		    		play(59,2);//5
		    		play(59,2);//5
		    		play(59,2);//5
		    		play(61,2);//6
		    		play(59,2);//5
		    		play(57,2);//4
		    		play(54,2);//2
		    		play(52,2,1);//1
		    		
		    		play(56,2);//3
		    		play(56,2);//3
		    		play(56,1);//3
		    		
		    		play(56,2);//3
		    		play(56,2);//3
		    		play(56,1);//3
		    		
		    		play(56,2);//3
		    		play(59,2);//5
		    		play(52,3,4);//1
		    		play(54,4);//2
		    		play(56,2,1);//3
		    		
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(56,2);//3
		    		play(56,2);//3
		    		play(56,2);//3
		    		
		    		play(56,2);//3
		    		play(54,2);//2
		    		play(54,2);//2
		    		play(52,2);//1
		    		play(54,1);//2
		    		play(59,1);//5
		    		
		    		play(56,2);//3
		    		play(56,2);//3
		    		play(56,1);//3
		    		
		    		play(56,2);//3
		    		play(56,2);//3
		    		play(56,1);//3
		    		
		    		play(56,2);//3
		    		play(59,2);//5
		    		play(52,3,4);//1
		    		play(54,4);//2
		    		play(56,2,1);//3
		    		
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(57,2);//4
		    		play(56,2);//3
		    		play(56,2);//3
		    		play(56,2);//3
		    		
		    		play(59,2);//5
		    		play(59,2);//5
		    		play(57,2);//4
		    		play(54,2);//2
		    		play(52,2,1);//1
		    	}
		    }
		}
		new Thread(new playStarThread()).start();
	}
	
	
	
}
