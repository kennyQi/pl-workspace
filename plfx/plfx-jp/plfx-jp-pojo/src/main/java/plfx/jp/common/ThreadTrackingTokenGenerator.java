package plfx.jp.common;

import hg.common.util.UUIDGenerator;

/**
 * @类功能说明：跟踪线程Token生成器
 * @类修改者：
 * @修改日期：2015-7-21上午10:54:31
 * @修改说明：记录日志时附带跟踪Token，方便查询一个线程内的完整日志
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-21上午10:54:31
 */
public class ThreadTrackingTokenGenerator {

	private final static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	/**
	 * @方法功能说明：创建新的线程跟踪token
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-21上午11:07:29
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String newget() {
		String token = UUIDGenerator.getUUID();
		threadLocal.set(token);
		return token;
	}
	
	/**
	 * @方法功能说明：获取线程跟踪token
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-21上午11:07:29
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String get() {
		String token = threadLocal.get();
		if (token == null)
			threadLocal.set(token = UUIDGenerator.getUUID());
		return token;
	}

	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					//System.out.println("当前token:" + get());
					Thread.sleep(1000);
					//System.out.println("当前token:" + get());
					Thread.sleep(1000);
					//System.out.println("当前token:" + get());
					Thread.sleep(1000);
					//System.out.println("当前token:" + get());
					Thread.sleep(1000);
					//System.out.println("结束:" + get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
	}
}
