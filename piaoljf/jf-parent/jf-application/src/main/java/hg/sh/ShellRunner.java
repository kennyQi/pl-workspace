package hg.sh;
/*
 * 创建日期 2004-11-29
 * $Id: ShellRunner.java,v 1.1 2010/05/28 08:34:46 xushg Exp $
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author linxin 
 * email:<a href="mailto:linxin@jlonline.com">linxin@jlonline.com</a>
 * create date 2004-11-29
 * @version $Revision: 1.1 $
 */
public abstract class ShellRunner
{
    public static   String charSet = "gbk";

	public static interface OnMessageRead
	{
		/** @param message
		 * @return can Teminal */
		boolean  onMessage(String message);
	}
	
	public static final OnMessageRead SYS_OUT = new OnMessageRead(){

		public boolean onMessage(String message)
		{
			System.out.println(message);
			return false ;
			
		}} ; 
	
	public static final OnMessageRead SYS_ERR = new OnMessageRead(){

		public boolean onMessage(String message)
		{
			System.err.println(message);
			return false ;
			
		}} ; 

	private final OnMessageRead  outReader  ;

	private final OnMessageRead  errReader  ;
	
	public Process process ;

	public OutputStream input; 
	
	public ShellRunner(OnMessageRead  outReader, OnMessageRead  errReader)
	{
		this.errReader = errReader ;
		this.outReader = outReader ;
	}
	/**
     * 
     * @return
	 */
	abstract public String getRunCommand() ;
    
    
	private static class ReadThread extends Thread 
	{


		private final OnMessageRead onRead ;
		 
		 private final InputStream inputStream ;
		 
		 private final String cName ;
		
		 private ReadThread (OnMessageRead onRead,InputStream iStream, String cName) 
		 {
			super(cName);
			this.onRead = onRead;
			this.inputStream = iStream ;
			this.cName = cName ;
//			this.setName(cName);
		 }
		 
		 /**
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run()
		{
			try
			{
			    BufferedReader err = new BufferedReader(new InputStreamReader(this.inputStream,charSet));
			    String line = null;		
				while((line = err.readLine()) != null && !isInterrupted()) 
				{
					onRead.onMessage(line); 
				}
			}
			catch (IOException e)
			{
				throw new RuntimeException(cName + "="+e.getMessage() , e) ;
			}
		}
	}
	
	public int RunCommand() 
	{	
		String runCommands = getRunCommand();
		System.out.println("Start Command : " + runCommands);
	    try
        {
            process = Runtime.getRuntime().exec(runCommands);
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
        this.input = process.getOutputStream();
		Thread threadin = new ReadThread(this.outReader,process.getInputStream(),"[INPUT]"); 
		Thread threaderr = new ReadThread(this.errReader,process.getErrorStream(),"[ERROR]"); 
		threadin.start() ;
		threaderr.start() ;
		
		int rcode = 0 ;
		
		try
		{
			rcode = process.waitFor();
		}
		catch (InterruptedException e)
		{
			stopProcess() ;
			rcode = process.exitValue() ;
		}
		
//		new Thread("waitFor:"+runCommands){
//			/* (non-Javadoc)
//			 * @see java.lang.Thread#run()
//			 */
//			@Override
//			public void run() {
//				try
//				{
//					 int ret= process.waitFor();
//					 
//				}
//				catch (InterruptedException e)
//				{
//					e.printStackTrace();
//					stopProcess() ;
//		            
//				}
//			}
//		}.start();
		
		return rcode ;
	}
	// 停止一个进程任务　
	public void stopProcess() 
	{
		if (process!= null )
		{
			process.destroy() ;
		}
	}


    
    
    
	public static void main(String[] args) throws IOException, InterruptedException
    {
		ShellRunner.charSet="gbk";
		int run = ShellRunner. run("cmd.exe /c dir x:\\");
		System.out.println(run);
		System.out.println("--------------");
		
//		 final String cmd = "telnet 10.8.0.21 2300 ";
//		
//		 ShellRunner processid = spawn(cmd);
//		 
//		 //Thread.currentThread().sleep(1000);
//		 
//		 processid.input.write("caogx".getBytes());
    }
	/**
	 * @param cmd
	 * @return 
	 */
	public static ShellRunner spawn(final String cmd) {
		final ShellRunner sh = new ShellRunner(ShellRunner.SYS_OUT, ShellRunner.SYS_ERR) {
			public String getRunCommand() {

				return cmd;
			}
		};

		sh.RunCommand();

		return sh;
	}
	
	public static int run(final String cmd) {
		final ShellRunner sh = new ShellRunner(ShellRunner.SYS_OUT, ShellRunner.SYS_ERR) {
			public String getRunCommand() {

				return cmd;
			}
		};
		return sh.RunCommand();
	}
}
