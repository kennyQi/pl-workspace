package hsl.h5.base.result.line;

import hg.common.page.Pagination;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.qo.line.HslLineQO;

import java.util.TreeMap;
/**
 * 
 * @类功能说明：线路列表上拉返回
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年2月4日下午5:43:42
 * @版本：V1.0
 *
 */
public class PullUpListResult extends ApiResult{
	/**
	 * 线路pagination
	 */
	private Pagination pagination;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private TreeMap<String,String> cityMap;
	
	private HslLineQO hslLineQO;
	/**
	 * 是否还有数据
	 */
	private Boolean haveMore;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public TreeMap<String, String> getCityMap() {
		return cityMap;
	}

	public void setCityMap(TreeMap<String, String> cityMap) {
		this.cityMap = cityMap;
	}

	public HslLineQO getHslLineQO() {
		return hslLineQO;
	}

	public void setHslLineQO(HslLineQO hslLineQO) {
		this.hslLineQO = hslLineQO;
	}

	public Boolean getHaveMore() {
		return haveMore;
	}

	public void setHaveMore(Boolean haveMore) {
		this.haveMore = haveMore;
	}
}
