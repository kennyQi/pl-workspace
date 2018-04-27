package hg.dzpw.app.common;

/**
 * @类功能说明：系统配置
 * @类修改者：chenys
 * @修改日期：2014-12-22下午3:54:43
 * @修改说明：添加项目管理的配置
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24下午3:54:43
 */
public class SystemConfig {
	
	/**
	 * 临时文件路径(后缀必须包含斜杠)
	 */
	public static String tempFilePath;
	
	/**
	 * 图片访问地址
	 */
	public static String imageHost;
	
	/**
	 * 景区公共密钥
	 */
	public static String scenicSpotPublicKey;
	
	/**
	 * 支付宝交易备注前缀
	 */
	public static String alipaySubjectPrefix;
	
	
	public void setTempFilePath(String tempFilePath) {
		SystemConfig.tempFilePath = tempFilePath;
	}
	public void setImageHost(String imageHost) {
		SystemConfig.imageHost = imageHost;
	}
	public static void setScenicSpotPublicKey(String scenicSpotPublicKey) {
		SystemConfig.scenicSpotPublicKey = scenicSpotPublicKey;
	}
	public static void setAlipaySubjectPrefix(String alipaySubjectPrefix) {
		SystemConfig.alipaySubjectPrefix = alipaySubjectPrefix;
	}
}