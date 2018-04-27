package test;

public class TestClass {

	public final static Integer I = 1;
	
	public final static Object OBJECT = new Object();
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			TestClass testClass = new TestClass();
			testClass.newThread(i);
		}
	}
	public void newThread(int i){
		final int j = i+I;
		new Thread(){
			public void run (){
				System.out.println("the " +j+" thread start");	
				synchronized (OBJECT) {
					System.out.println("the "+j+" execute SYNC");
					try {
						new Thread().sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("the " +j+" thread over");
			}
		}.start();
	}
}
