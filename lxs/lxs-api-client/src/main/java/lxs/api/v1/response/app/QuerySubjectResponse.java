package lxs.api.v1.response.app;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.domain.model.app.Subject;

/**
 * 
 * @类功能说明：查询主题接口返回
 * @类修改者：
 * @修改日期：2015年9月18日上午11:11:35
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月18日上午11:11:35
 */
public class QuerySubjectResponse extends ApiResponse {

	private List<Subject> subjects;

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
