package slfx.mp.tcclient.tc.pojo.base.request;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.base.CityListByProvinceIdDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;
import slfx.mp.tcclient.tc.pojo.Param;

/**
 *	获取省列表信息请求
 * @author zhangqy
 */
public class ParamCityListByProvinceId extends Param {
	/**
	 * 省ID
	 */
	private Integer provinceId;
	
	public ParamCityListByProvinceId() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof CityListByProvinceIdDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamCityListByProvinceId)){
			throw new DtoErrorException();
		}
		CityListByProvinceIdDto dto=(CityListByProvinceIdDto)dto1; 
		ParamCityListByProvinceId param=(ParamCityListByProvinceId)param1; 
		param.setProvinceId(dto.getProvinceId());
	}
	
	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public static void main(String[] args){
		
	}
}
