package hsl.pojo.command.traveler;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：物理删除游客(下订单时使用的是游客的信息快照不关联游客，故此可直接删除)
 * @类修改者：
 * @修改日期：2015-9-29下午3:07:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-29下午3:07:06
 */
@SuppressWarnings("serial")
public class DeleteTravelerCommand extends BaseCommand {

	/**
	 * 游客ID
	 */
	private String travelerId;

	/**
	 * 来自的用户(用于校验是否属于该用户)
	 */
	private String fromUserId;

	public String getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

}
