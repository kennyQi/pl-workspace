package slfx.jp.pojo.dto.flight;

import java.util.*;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：经停查询结果DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:54:46
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class QueryStopResultDTO extends BaseJpDTO{
	
	/**
	 *航班起飞信息
	 */
	public FlightStartInfoDTO startInfo;
	
	/**
	 * 航班到达信息
	 */
	public FlightEndInfoDTO endInfo;
	
	/**
	 * 航班经停信息
	 */
	public List<FlightStopInfoDTO> stopInfoList;

	public FlightStartInfoDTO getStartInfo() {
		return startInfo;
	}

	public void setStartInfo(FlightStartInfoDTO startInfo) {
		this.startInfo = startInfo;
	}

	public FlightEndInfoDTO getEndInfo() {
		return endInfo;
	}

	public void setEndInfo(FlightEndInfoDTO endInfo) {
		this.endInfo = endInfo;
	}

	public List<FlightStopInfoDTO> getStopInfoList() {
		return stopInfoList;
	}

	public void setStopInfoList(List<FlightStopInfoDTO> stopInfoList) {
		this.stopInfoList = stopInfoList;
	}
	

}