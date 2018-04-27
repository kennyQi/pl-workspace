package slfx.mp.tcclient.tc.pojo.order.request;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.order.SceneryOrderDetailDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;
import slfx.mp.tcclient.tc.pojo.Param;
/**
 * 提交订单接口请求
 * @author zhangqy
 *
 */
public class ParamSceneryOrderDetail extends Param {
	/**
	 * 数据源库 0读库 1写库 默认为0
	 */
	private Integer writeDB;
	/**
	 * 订单流水号 逗号分隔，最多20个
	 */
	private String serialIds;
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SceneryOrderDetailDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryOrderDetail)){
			throw new DtoErrorException();
		}
		SceneryOrderDetailDto dto=(SceneryOrderDetailDto)dto1; 
		ParamSceneryOrderDetail param=(ParamSceneryOrderDetail)param1; 
		param.setSerialIds(dto.getSerialIds());
		param.setWriteDB(dto.getWriteDB());
	}

	public Integer getWriteDB() {
		return writeDB;
	}

	public void setWriteDB(Integer writeDB) {
		this.writeDB = writeDB;
	}

	public String getSerialIds() {
		return serialIds;
	}

	public void setSerialIds(String serialIds) {
		this.serialIds = serialIds;
	}

}
