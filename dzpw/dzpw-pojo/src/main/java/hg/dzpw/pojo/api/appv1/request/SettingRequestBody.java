package hg.dzpw.pojo.api.appv1.request;

import hg.dzpw.pojo.api.appv1.base.ApiRequestBody;

/**
 * @类功能说明：设置（用于做一些系统设置，目前只有一个设置，是否在实时验票之后，增加一步人工查验证件再确认提交的步骤。）
 * @类修改者：
 * @修改日期：2014-11-18下午3:05:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:05:40
 */
public class SettingRequestBody extends ApiRequestBody {
	private static final long serialVersionUID = 1L;

	/**
	 * 用于做一些系统设置，目前只有一个设置，是否在实时验票之后，增加一步人工查验证件再确认提交的步骤。<br/>
	 * manualCheckCertificate 是否人工查验证件 true 是<br/>
	 * 如果为true，在调用证件验票接口之后，会返回相关票和身份信息，等待景区人员人工核实身份证件后，再调用确认身份信息接口通过验票。<br/>
	 * 如果为false，在调用证件验票接口时，如果发现唯一可用票，会直接核销并返回相关票和身份信息。<br/>
	 */
	private boolean manualCheckCertificate;
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	public boolean isManualCheckCertificate() {
		return manualCheckCertificate;
	}
	public void setManualCheckCertificate(boolean manualCheckCertificate) {
		this.manualCheckCertificate = manualCheckCertificate;
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	
}