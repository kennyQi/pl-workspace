package plfx.mp.tcclient.tc.pojo.base.request;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.base.DivisionInfoByNameDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;
import plfx.mp.tcclient.tc.pojo.Param;

/**
 *	获取名称查询区划信息请求
 * @author zhangqy
 */
public class ParamDivisionInfoByName extends Param {
	/**
	 * 区划名称
	 */
	private String divisionName;
	
	public ParamDivisionInfoByName() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof DivisionInfoByNameDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamDivisionInfoByName)){
			throw new DtoErrorException();
		}
		DivisionInfoByNameDto dto=(DivisionInfoByNameDto)dto1; 
		ParamDivisionInfoByName param=(ParamDivisionInfoByName)param1;
		param.setDivisionName(dto.getDivisionName());
	}
	
	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public static void main(String[] args){
		
	}
	
}
