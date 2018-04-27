package hg.dzpw.pojo.common;

/**
 * @类功能说明：汇金宝支付服务配置
 * @类修改者：
 * @修改日期：2015-5-6下午6:05:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-6下午6:05:43
 */
public class HjbPayServiceConfig {

	private String cerPath;
	private String signPath;
	private String signPassword;
	private String HJBServerUrl;

	/**
	 * 接口版本号
	 */
	private String version;
	/**
	 * 票务平台唯一标识
	 */
	private String merchantId;
	/**
	 * 商户在汇金宝平台唯一标识
	 */
	private String dzpwCstNo;
	/**
	 * 操作员编号
	 */
	private String dzpwUserId;

	public String getCerPath() {
		return cerPath;
	}

	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}

	public String getSignPath() {
		return signPath;
	}

	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}

	public String getSignPassword() {
		return signPassword;
	}

	public void setSignPassword(String signPassword) {
		this.signPassword = signPassword;
	}

	public String getHJBServerUrl() {
		return HJBServerUrl;
	}

	public void setHJBServerUrl(String hJBServerUrl) {
		HJBServerUrl = hJBServerUrl;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getDzpwCstNo() {
		return dzpwCstNo;
	}

	public void setDzpwCstNo(String dzpwCstNo) {
		this.dzpwCstNo = dzpwCstNo;
	}

	public String getDzpwUserId() {
		return dzpwUserId;
	}

	public void setDzpwUserId(String dzpwUserId) {
		this.dzpwUserId = dzpwUserId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
