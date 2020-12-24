package pianoPlayer;

import java.io.*;
import javax.sound.sampled.*;

public class Key {
	private MusicPlayer musicPlayer;
	
	//�ڲ��� MusicPlayer
	private class MusicPlayer implements Runnable {
		private File soundFile; // �����ļ�
		private Thread thread;// ���߳�
		private boolean continuePlay=true;
		private float volumePercent;	//0��1��float
		private boolean volumeNeedTemperoryChange=false;
	    
	    //���췽��: ��������·������������
	    public MusicPlayer(String filepath) throws FileNotFoundException {
	        soundFile = new File(filepath);
	        if (!soundFile.exists()) {}
	    }

	    //����
	    public void play(int duation) {
	    	play(duation,1);
	    }
	    
	  //����
	    public void play(int duation,float volumePercent) {
	    	this.volumePercent=volumePercent;
	    	continuePlay=true;
	        thread = new Thread(this);// �����̶߳���
	        thread.start();// �����߳�
	        new StopTimer(duation,MusicPlayer.this);
	    }
	    
	    //ֹͣ����
	    public void stop(){
	    	continuePlay=false;
	    }
	    
	    //��ʱ�ı�����
	    public void temporaryChangeVolumn(float volumePercent) {
	    	this.volumePercent=volumePercent;
	    	volumeNeedTemperoryChange=true;
	    }
	    
	    //��д�߳�ִ�з���
	    public void run() {
	        //byte[] auBuffer = new byte[1024 * 128];// ����128k������
	    	byte[] auBuffer = new byte[256];// ����16k������
	        do {
	            AudioInputStream audioInputStream = null; // ������Ƶ����������
	            SourceDataLine auline = null; // ��Ƶ��Դ������
	            try {
	                // �������ļ��л�ȡ��Ƶ������
	                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	                AudioFormat format = audioInputStream.getFormat(); // ��ȡ��Ƶ��ʽ
	                // ����Դ���������ͺ�ָ����Ƶ��ʽ���������ж���
	                DataLine.Info info = new DataLine.Info(SourceDataLine.class,
	                        format);
	                // ������Ƶϵͳ������ָ�� Line.Info �����е�����ƥ����У���ת��ΪԴ�����ж���
	                auline = (SourceDataLine) AudioSystem.getLine(info);
	                auline.open(format);// ����ָ����ʽ��Դ������
	                
	                FloatControl control = (FloatControl) auline.getControl(FloatControl.Type.MASTER_GAIN);
	                control.setValue(volumePercent*(control.getMaximum()-control.getMinimum())+control.getMinimum());
	                 
	                auline.start();// Դ�����п�����д�
	                int byteCount = 0;// ��¼��Ƶ�������������ֽ���
	                while (byteCount != -1&&continuePlay) {// �����Ƶ�������ж�ȡ���ֽ�����Ϊ-1
	                	if(volumeNeedTemperoryChange) {
	                		control.setValue(volumePercent*(control.getMaximum()-control.getMinimum())+control.getMinimum());
	                		volumeNeedTemperoryChange=false;
	                	}
	                	// ����Ƶ�������ж�������
	                    byteCount = audioInputStream.read(auBuffer, 0,
	                            auBuffer.length);
	                    if (byteCount >= 0) {// ���������Ч����
	                        auline.write(auBuffer, 0, byteCount);// ����Ч����д����������
	                    }
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (UnsupportedAudioFileException e) {
	                e.printStackTrace();
	            } catch (LineUnavailableException e) {
	                e.printStackTrace();
	            } finally {
	                auline.drain();// ���������
	                auline.close();// �ر�������
	            }
	        } while (true);// ����ѭ����־�ж��Ƿ�ѭ������
	    }
	}
	
	//�ڲ��� StopTimer
	private class StopTimer implements Runnable {
		private Thread thread;	// ���߳�
		private int duation;	//��ʱ�����룩
		private MusicPlayer musicPlayer;
		public StopTimer(int duation,MusicPlayer musicPlayer) {
			this.duation=duation;
			this.musicPlayer=musicPlayer;
			thread = new Thread(this);// �����̶߳���
	        thread.start();// �����߳�
		}
		@Override
		public void run() {
			try {
				Thread.sleep(duation);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			musicPlayer.stop();
		}
	
	}
	
	//���췽��
	public Key(int keyNumber) {
		String filePath="pianoKeys/tone ("+keyNumber+").wav";
		try {
			musicPlayer=new MusicPlayer(filePath);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	//���̲���
	public void play(int duation) {
		musicPlayer.play(duation);
    }
	
	public void play(int duation,float volume) {
		musicPlayer.play(duation,volume);
    }
}
