package hsl.pojo.dto.ad;

import java.io.Serializable;

/**
 * @类功能说明：广告状态
 * @类修改者：
 * @修改日期：2014年12月11日下午3:57:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午3:57:39
 * 
 */
public class HslAdStatusDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 按广告优先级和广告位的加载条数，自动维护是否显示状态
	 */
	private Boolean isShow;
	/**
	 * 点击数
	 */
	private Integer clickNo;
	
	public Integer getClickNo() {
		return clickNo;
	}
	public void setClickNo(Integer clickNo) {
		this.clickNo = clickNo;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
}