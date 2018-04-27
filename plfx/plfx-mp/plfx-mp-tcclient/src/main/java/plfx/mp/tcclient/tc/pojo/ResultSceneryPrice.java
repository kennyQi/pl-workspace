package plfx.mp.tcclient.tc.pojo;

import java.util.List;

import plfx.mp.tcclient.tc.domain.PriceQueryScenery;


/**
 * 用于调用同城获取价格搜索接口请求返回结果
 * @author zhangqy
 *
 */
public class ResultSceneryPrice extends Result {
	private List<PriceQueryScenery> scenery;

	public List<PriceQueryScenery> getScenery() {
		return scenery;
	}

	public void setScenery(List<PriceQueryScenery> scenery) {
		this.scenery = scenery;
	}
	
}
