package hg.dzpw.app.pojo.qo;

/**
 * @类功能说明：单票查询
 * @类修改者：
 * @修改日期：2014-11-27下午1:48:19
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-27下午1:48:19
 */
public class SingleTicketQo extends TicketQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属套票
	 */
	private GroupTicketQo groupTicketQo;

	/**
	 * 对应景区
	 */
	private ScenicSpotQo scenicSpotQo;
	
	/**
	 * 单票状态
	 */
	private Integer status;
	

	public GroupTicketQo getGroupTicketQo() {
		return groupTicketQo;
	}

	public void setGroupTicketQo(GroupTicketQo groupTicketQo) {
		this.groupTicketQo = groupTicketQo;
	}

	public ScenicSpotQo getScenicSpotQo() {
		return scenicSpotQo;
	}

	public void setScenicSpotQo(ScenicSpotQo scenicSpotQo) {
		this.scenicSpotQo = scenicSpotQo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
