package test;

import java.util.Properties;


public class Test {

	public static void main(String[] args) {
		
		MyThread thread1 = new MyThread("thread1");
		MyThread thread2 = new MyThread("thread2");
		MyThread thread3 = new MyThread("thread3");
		MyThread thread4 = new MyThread("thread4");
		
//		thread1.start();
//		thread2.start();
//		thread3.start();
//		thread4.start();
		
		thread1.run();
		thread2.run();
		thread3.run();
		thread4.run();
		
	}
	
	static class MyThread extends Thread {
		public MyThread(String name) {
			super(name);
		}
		public void run() {
			for(int i=0; i<20; i++) {
				System.out.println(this.getName());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
