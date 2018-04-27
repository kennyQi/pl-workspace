package slfx.mp.tcclient.tc.pojo;

import slfx.mp.tcclient.tc.dto.Dto;
import slfx.mp.tcclient.tc.dto.jd.SceneryPriceDto;
import slfx.mp.tcclient.tc.exception.DtoErrorException;

/**
 *	获取景点详细信息请求
 * @author zhangqy
 */
public class ParamSceneryPrice extends Param {
	/**
	 * 景点Id
	 */
	private String sceneryIds;
	/**
	 * 影响返回内容节点数量1、	简单 2、详细 默认为1
	 */
	private Integer showDetail;
	/**
	 * 是否使用缓存0:不使用    1使用;默认为0
	 */
	private Integer useCache;
	
	public static final Integer SHOW_MESSAGE_UN_DETAIL=1;//简单
	public static final Integer SHOW_MESSAGE_DETAIL=2;//详细
	public static final Integer USE_CACHE_FALSE=0;//不使用
	public static final Integer USE_CACHE_TRUE=1;//使用
	
	
	
	public ParamSceneryPrice() {
		super();
	}
	
	/**
	 * 创建对象
	 * @param dto
	 * @return
	 */
	public  void setParams(Param param1,Dto dto1)  throws Exception{
		if(!(dto1 instanceof SceneryPriceDto)){
			throw new DtoErrorException();
		}
		if(!(param1 instanceof ParamSceneryPrice)){
			throw new DtoErrorException();
		}
		SceneryPriceDto dto=(SceneryPriceDto)dto1; 
		ParamSceneryPrice param=(ParamSceneryPrice)param1; 
		param.setSceneryIds(dto.getSceneryIds());
		param.setShowDetail(dto.getShowDetail());
		param.setUseCache(dto.getUseCache());
	}
	
	public String getSceneryIds() {
		return sceneryIds;
	}

	public void setSceneryIds(String sceneryIds) {
		this.sceneryIds = sceneryIds;
	}

	public Integer getShowDetail() {
		return showDetail;
	}

	public void setShowDetail(Integer showDetail) {
		this.showDetail = showDetail;
	}

	public Integer getUseCache() {
		return useCache;
	}

	public void setUseCache(Integer useCache) {
		this.useCache = useCache;
	}

	public static void main(String[] args){
		
	}
}
