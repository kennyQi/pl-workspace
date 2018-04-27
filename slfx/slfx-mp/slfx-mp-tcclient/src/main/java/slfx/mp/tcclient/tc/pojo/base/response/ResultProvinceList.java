package slfx.mp.tcclient.tc.pojo.base.response;

import java.util.List;

import slfx.mp.tcclient.tc.domain.base.Province;
import slfx.mp.tcclient.tc.pojo.Result;
/**
 * 行政区列表返回信息
 * @author zhangqy
 *
 */
public class ResultProvinceList extends Result {
	/**
	 * 省数量
	 */
	private Integer totalCount;
	/**
	 * 省
	 */
	private List<Province> province;
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<Province> getProvince() {
		return province;
	}
	public void setProvince(List<Province> province) {
		this.province = province;
	}
	
}
