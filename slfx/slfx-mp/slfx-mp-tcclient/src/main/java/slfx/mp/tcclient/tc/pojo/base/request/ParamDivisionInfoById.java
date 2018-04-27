package slfx.mp.tcclient.tc.pojo.base.request;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.base.DivisionInfoByIdDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;
import slfx.mp.tcclient.tc.pojo.Param;

/**
 *	根据ID查询区划信息请求
 * @author zhangqy
 */
public class ParamDivisionInfoById extends Param {
	/**
	 * 区划Id
	 */
	private Integer divisionId;
	
	public ParamDivisionInfoById() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof DivisionInfoByIdDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamDivisionInfoById)){
			throw new DtoErrorException();
		}
		DivisionInfoByIdDto dto=(DivisionInfoByIdDto)dto1; 
		ParamDivisionInfoById param=(ParamDivisionInfoById)param1; 
		param.setDivisionId(dto.getDivisionId());
	}
	
	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}
	

	public static void main(String[] args){
		
	}

	

	
}
