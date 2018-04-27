package hg.system.common.init;

/**
 * @类功能说明：初始化BEAN
 * @类修改者：
 * @修改日期：2015-4-8上午9:53:42
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-8上午9:53:42
 */
public interface InitBean {
	
	/**
	 * @方法功能说明：SPRING环境初始化完毕后调用
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-8上午9:54:26
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	void springContextStartedRun() throws Exception;
	
}
