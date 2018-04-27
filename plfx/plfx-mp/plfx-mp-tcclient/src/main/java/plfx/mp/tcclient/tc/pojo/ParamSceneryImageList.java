package plfx.mp.tcclient.tc.pojo;

import plfx.mp.tcclient.tc.dto.Dto;
import plfx.mp.tcclient.tc.dto.jd.SceneryImageListDto;
import plfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点交通信息请求
 * @author zhangqy
 */
public class ParamSceneryImageList extends Param {
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
	public ParamSceneryImageList() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SceneryImageListDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryImageList)){
			throw new DtoErrorException();
		}
		SceneryImageListDto dto=(SceneryImageListDto)dto1; 
		ParamSceneryImageList param=(ParamSceneryImageList)param1; 
		param.setSceneryId(dto.getSceneryId());
		param.setPage(dto.getPage());
		param.setPageSize(dto.getPageSize());
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
