package hg.dzpw.pojo.api.appv1.response;

import hg.dzpw.pojo.api.appv1.base.ApiResponse;

/**
 * @类功能说明：登录结果
 * @类修改者：
 * @修改日期：2014-11-18下午3:27:16
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:27:16
 */
public class LoginResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 结果代码：-1：帐号不存在
	 */
	public final static String RESULT_LOGIN_NAME_NOT_EXISTS = "-1";
	
	/**
	 * 结果代码：-2：密码有误
	 */
	public final static String RESULT_LOGIN_PASSWORD_FAIL = "-2";
	/**
	 * 账号或密码错误
	 */
	public final static String RESULT_LOGIN_NAME_OR_PASSWORD_FAIL = "-21";
	
	/**登录失败,设备号与账号不匹配*/
	public final static String RESULT_LOGIN_DEVICE_NO_MACTH = "-3";
	
	/**
	 * 景区简称
	 */
	private String scenicSpotShortName;
	/**
	 * 景区密钥
	 */
	private String secretKey;
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	
	public String getScenicSpotShortName() {
		return scenicSpotShortName;
	}

	public void setScenicSpotShortName(String scenicSpotShortName) {
		this.scenicSpotShortName = scenicSpotShortName;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	
}