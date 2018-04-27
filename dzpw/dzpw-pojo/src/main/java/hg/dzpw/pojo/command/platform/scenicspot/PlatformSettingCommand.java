package hg.dzpw.pojo.command.platform.scenicspot;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明：设置
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014年11月24日上午10:19:01
 */
@SuppressWarnings("serial")
public class PlatformSettingCommand extends DZPWPlatformBaseCommand {
	/**
	 * 是否人工查验证件
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
