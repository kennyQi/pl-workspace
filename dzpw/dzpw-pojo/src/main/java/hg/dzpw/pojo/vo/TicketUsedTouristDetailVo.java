package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明：使用过套票（联票）的用户明细视图对象
 * @类修改者：
 * @修改日期：2014-11-20下午3:32:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午3:32:00
 */
public class TicketUsedTouristDetailVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 游客ID
	 */
	private String touristId;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	/**
	 * 证件类型
	 */
	private String cerType;

	/**
	 * 证件号码
	 */
	private String cerNo;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 联票（政策）名称
	 */
	private String ticketPolicyName;

	/**
	 * 入园景区名称
	 */
	private String scenicSpotName;

	/**
	 * 入园时间
	 */
	private List<Date> useDates;

	public String getTouristId() {
		return touristId;
	}

	public void setTouristId(String touristId) {
		this.touristId = touristId;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getCerType() {
		return cerType;
	}

	public void setCerType(Integer cerType) {
		if (cerType != null)
			this.cerType = String.valueOf(cerType);
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public List<Date> getUseDates() {
		if (useDates == null)
			useDates = new ArrayList<Date>();
		return useDates;
	}

	public void setUseDates(List<Date> useDates) {
		this.useDates = useDates;
	}

}
