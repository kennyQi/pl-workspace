package hsl.pojo.dto.ad;

import java.io.Serializable;

/**
 * @类功能说明：广告位的展示信息
 * @类修改者：
 * @修改日期：2014年12月11日下午4:05:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午4:05:32
 * 
 */
public class HslAdPositionShowInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 显示条数
	 */
	private Integer showNo;
	/**
	 * 加载条数
	 */
	private Integer loadNo;
	/**
	 * 是否切换
	 */
	private Integer changeSpeedSecond;

	public Integer getShowNo() {
		return showNo;
	}

	public void setShowNo(Integer showNo) {
		this.showNo = showNo;
	}

	public Integer getLoadNo() {
		return loadNo;
	}

	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	public Integer getChangeSpeedSecond() {
		return changeSpeedSecond;
	}

	public void setChangeSpeedSecond(Integer changeSpeedSecond) {
		this.changeSpeedSecond = changeSpeedSecond;
	}
}