package plfx.mp.tcclient.tc.pojo;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.jd.NearbySceneryDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点详细信息请求
 * @author zhangqy
 */
public class ParamNearbyScenery extends Param {
	/**
	 * 景点Id
	 */
	private String sceneryId;
	/**
	 * 页码
	 */
	private Integer page;
	/**
	 * 页面大小
	 */
	private Integer pageSize;
	
	public ParamNearbyScenery() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof NearbySceneryDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamNearbyScenery)){
			throw new DtoErrorException();
		}
		NearbySceneryDto dto=(NearbySceneryDto)dto1; 
		ParamNearbyScenery param=(ParamNearbyScenery)param1; 
		param.setPage(dto.getPage());
		param.setPageSize(dto.getPageSize());
		param.setSceneryId(dto.getSceneryId());
	}
	
	public String getSceneryId() {
		return sceneryId;
	}

	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public static void main(String[] args){
		
	}
}
