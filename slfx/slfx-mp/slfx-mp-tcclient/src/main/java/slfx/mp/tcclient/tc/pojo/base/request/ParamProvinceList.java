package slfx.mp.tcclient.tc.pojo.base.request;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.base.ProvinceListDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;
import slfx.mp.tcclient.tc.pojo.Param;

/**
 *	获取省列表信息请求
 * @author zhangqy
 */
public class ParamProvinceList extends Param {

	
	public ParamProvinceList() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof ProvinceListDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamProvinceList)){
			throw new DtoErrorException();
		}
//		ProvinceListDto dto=(ProvinceListDto)dto1; 
//		ParamProvinceList param=(ParamProvinceList)param1; 
	}
	
	

	public static void main(String[] args){
		
	}

	
}
