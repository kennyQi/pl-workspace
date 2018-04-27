package hg.dzpw.pojo.command.platform.scenicspot;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;
import java.util.List;

/**
 * @类功能说明：启用景区
 * @类修改者：
 * @修改日期：2014-11-12上午10:56:19
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12上午10:56:19
 */
public class PlatformActivateScenicSpotCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID
	 */
	private List<String> scenicSpotId;

	public List<String> getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(List<String> scenicSpotId) {
		this.scenicSpotId = (null == scenicSpotId || scenicSpotId.size() < 1)?null:scenicSpotId;
	}
}