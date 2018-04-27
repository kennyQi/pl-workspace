package slfx.api.v1.response.xl;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.xl.pojo.dto.line.LineDTO;

/**
 * 
 * @类功能说明：api接口查询线路RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:44:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLQueryLineResponse extends ApiResponse {
	
	/**
	 * 线路list
	 */
	private List<LineDTO> lineList;
	
	/**
	 * 线路总条数
	 */
	private Integer totalCount;

	public List<LineDTO> getLineList() {
		return lineList;
	}

	public void setLineList(List<LineDTO> lineList) {
		this.lineList = lineList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
