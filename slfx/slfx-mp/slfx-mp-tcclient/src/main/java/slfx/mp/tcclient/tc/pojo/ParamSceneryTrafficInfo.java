package slfx.mp.tcclient.tc.pojo;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.jd.SceneryTrafficInfoDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点交通信息请求
 * @author zhangqy
 */
public class ParamSceneryTrafficInfo extends Param {
	/**
	 * 景点Id
	 */
	private String sceneryId;
	
	
	public ParamSceneryTrafficInfo() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SceneryTrafficInfoDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryTrafficInfo)){
			throw new DtoErrorException();
		}
		SceneryTrafficInfoDto dto=(SceneryTrafficInfoDto)dto1; 
		ParamSceneryTrafficInfo param=(ParamSceneryTrafficInfo)param1; 
		param.setSceneryId(dto.getSceneryId());
	}
	
	public String getSceneryId() {
		return sceneryId;
	}

	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}

	public static void main(String[] args){
		
	}
}
