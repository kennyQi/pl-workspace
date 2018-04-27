package plfx.mp.tcclient.tc.pojo;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.jd.PriceCalendarDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点详细信息请求
 * @author zhangqy
 */
public class ParamPriceCalendar extends Param {
	/**
	 * 价格id
	 */
	private Integer policyId;
	/**
	 * 价格id
	 */
	private String startDate;
	/**
	 * 查询结束日期
	 */
	private String endDate;
	/**
	 * 是否显示详情(0:不显示,1显示)
	 */
	private Integer showDetail;
	
	public ParamPriceCalendar() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof PriceCalendarDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamPriceCalendar)){
			throw new DtoErrorException();
		}
		PriceCalendarDto dto=(PriceCalendarDto)dto1; 
		ParamPriceCalendar param=(ParamPriceCalendar)param1; 
		param.setEndDate(dto.getEndDate());
		param.setStartDate(dto.getStartDate());
		param.setPolicyId(dto.getPolicyId());
		param.setShowDetail(dto.getShowDetail());
	}
	

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getShowDetail() {
		return showDetail;
	}

	public void setShowDetail(Integer showDetail) {
		this.showDetail = showDetail;
	}

	public static void main(String[] args){
		
	}
}
