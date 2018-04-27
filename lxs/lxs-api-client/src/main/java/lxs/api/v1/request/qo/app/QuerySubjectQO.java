package lxs.api.v1.request.qo.app;

import lxs.api.base.ApiPayload;
/**
 * 
 * @类功能说明：查询主题接口请求
 * @类修改者：
 * @修改日期：2015年9月18日上午11:10:51
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:10:51
 */

@SuppressWarnings("serial")
public class QuerySubjectQO extends ApiPayload{

	/**
	 * 主题分类(非必填),1.线路，2.门票
	 */
	private Integer subjectType;

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}
	
}
