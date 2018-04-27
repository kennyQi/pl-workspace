package slfx.mp.tcclient.tc.pojo;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.jd.SceneryDetailDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点详细信息请求
 * @author zhangqy
 */
public class ParamSceneryDetail extends Param {
	/**
	 * 景点Id
	 */
	private String sceneryId;
	/**
	 * 坐标系统
	 */
	private String cs;
	
	public ParamSceneryDetail() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SceneryDetailDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryDetail)){
			throw new DtoErrorException();
		}
		SceneryDetailDto dto=(SceneryDetailDto)dto1; 
		ParamSceneryDetail param=(ParamSceneryDetail)param1; 
		param.setCs(dto.getCs());
		param.setSceneryId(dto.getSceneryId());
	}
	
	public String getSceneryId() {
		return sceneryId;
	}

	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public static void main(String[] args){
		
	}
}
