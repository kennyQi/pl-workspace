package lxs.api.v1.request.qo.app;

import lxs.api.base.ApiPayload;

/**
 * 
 * @类功能说明：查询轮播图接口请求
 * @类修改者：
 * @修改日期：2015年9月18日上午11:06:40
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:06:40
 */
@SuppressWarnings("serial")
public class QueryIntroductionQO extends ApiPayload{
	private Integer introductionType;
	private String introductionContent;
	private String id;

	public Integer getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(Integer introductionType) {
		this.introductionType = introductionType;
	}

	public String getIntroductionContent() {
		return introductionContent;
	}

	public void setIntroductionContent(String introductionContent) {
		this.introductionContent = introductionContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
