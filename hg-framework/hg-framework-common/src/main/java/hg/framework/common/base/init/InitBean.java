package hg.framework.common.base.init;

/**
 * 初始化BEAN
 *
 * @author zhurz
 */
public interface InitBean {
	
	/**
	 * SPRING环境初始化完毕后调用
	 *
	 * @throws Exception
	 */
	void springContextStartedRun() throws Exception;
	
}
