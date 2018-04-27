package slfx.mp.tcclient.tc.pojo.base.response;

import java.util.List;

import slfx.mp.tcclient.tc.domain.base.City;
import slfx.mp.tcclient.tc.pojo.Result;
/**
 * 查询城市列表返回响应
 * @author zhangqy
 *
 */
public class ResultCityListByProvinceId extends Result {
	/**
	 * 城市数量
	 */
	private Integer totalCount;
	/**
	 * 城市
	 */
	private List<City> city;
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<City> getCity() {
		return city;
	}
	public void setCity(List<City> city) {
		this.city = city;
	}
	
}
