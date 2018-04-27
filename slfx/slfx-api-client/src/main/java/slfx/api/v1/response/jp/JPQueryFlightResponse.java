package slfx.api.v1.response.jp;

import java.util.HashMap;
import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;

/**
 * 
 * @类功能说明：查询航班RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:44:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPQueryFlightResponse extends ApiResponse {

	/**
	 * 航班列表
	 */
	private List<SlfxFlightDTO> flightList;

	/**
	 * 航班总数
	 */
	private Integer totalCount;
	
	/**
	 * 退改签政策map  key为退改签政策KEY
	 */
	private HashMap<String, String> tgNoteMap;

	public List<SlfxFlightDTO> getFlightList() {
		return flightList;
	}

	public void setFlightList(List<SlfxFlightDTO> flightList) {
		this.flightList = flightList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public HashMap<String, String> getTgNoteMap() {
		return tgNoteMap;
	}

	public void setTgNoteMap(HashMap<String, String> tgNoteMap) {
		this.tgNoteMap = tgNoteMap;
	}

}
