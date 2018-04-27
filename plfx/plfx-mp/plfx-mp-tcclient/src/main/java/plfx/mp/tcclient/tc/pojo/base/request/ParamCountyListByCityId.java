package plfx.mp.tcclient.tc.pojo.base.request;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.base.CountyListByCityIdDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;
import plfx.mp.tcclient.tc.pojo.Param;

/**
 *	获取省列表信息请求
 * @author zhangqy
 */
public class ParamCountyListByCityId extends Param {
	/**
	 * 城市ID
	 */
	private Integer cityId;
	public ParamCountyListByCityId() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof CountyListByCityIdDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamCountyListByCityId)){
			throw new DtoErrorException();
		}
		CountyListByCityIdDto dto=(CountyListByCityIdDto)dto1; 
		ParamCountyListByCityId param=(ParamCountyListByCityId)param1;
		param.setCityId(dto.getCityId());
	}
	

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	public static void main(String[] args){
		
	}

	
}
