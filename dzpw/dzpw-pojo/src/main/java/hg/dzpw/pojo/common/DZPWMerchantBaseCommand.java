package hg.dzpw.pojo.common;


/**
 * @类功能说明：商户端操作的COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-2-3下午3:51:32
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class DZPWMerchantBaseCommand extends BaseCommand {

	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	
	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

}
