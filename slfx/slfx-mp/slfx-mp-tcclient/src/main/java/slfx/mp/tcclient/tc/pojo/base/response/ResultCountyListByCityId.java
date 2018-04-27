package slfx.mp.tcclient.tc.pojo.base.response;

import java.util.List;

import slfx.mp.tcclient.tc.domain.base.County;
import slfx.mp.tcclient.tc.pojo.Result;

public class ResultCountyListByCityId extends Result {
	/**
	 * 区划数量
	 */
	private Integer totalCount;
	/**
	 * 区划列表
	 */
	private List<County> county;
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<County> getCounty() {
		return county;
	}
	public void setCounty(List<County> county) {
		this.county = county;
	}
	
	
}
