package pay.record.admin.common;

/**
 * @类功能说明：系统配置
 * @类修改者：chenys
 * @修改日期：2014-12-22下午3:54:43
 * @修改说明：添加项目管理的配置
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24下午3:54:43
 */
public class AdminConfig {
	/**
	 * 图片访问地址
	 */
	public static String imageHost;
	
	/**
	 * 项目管理员id
	 */
	public static String authId;
	
	/**
	 * 项目管理员名称
	 */
	public static String authName;
	
	public void setImageHost(String imageHost) {
		AdminConfig.imageHost = imageHost;
	}
	public void setAuthId(String authId) {
		AdminConfig.authId = authId;
	}
	public void setAuthName(String authName) {
		AdminConfig.authName = authName;
	}
}