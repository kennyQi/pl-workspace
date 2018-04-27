package lxs.api.action.app;

import hg.log.util.HgLogger;

import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.qo.app.QuerySubjectQO;
import lxs.api.v1.response.app.QuerySubjectResponse;
import lxs.app.service.app.SubjectService;
import lxs.domain.model.app.Subject;
import lxs.pojo.qo.app.SubjectQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QuerySubjectAction")
public class QuerySubjectAction implements LxsAction {

	@Autowired
	private SubjectService subjectService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【QuerySubjectAction】"+"进入action");
		QuerySubjectQO querySubjectQO = JSON.parseObject(apiRequest.getBody().getPayload(), QuerySubjectQO.class);
		HgLogger.getInstance().info("lxs_dev", "【QuerySubjectAction】"+"【querySubjectQO】"+JSON.toJSONString(querySubjectQO));
		QuerySubjectResponse subjectResponse = new QuerySubjectResponse();
		try {
			SubjectQO subjectQO = new SubjectQO();
			if (querySubjectQO.getSubjectType() != null && querySubjectQO.getSubjectType() != 0) {
				subjectQO.setSubjectType(querySubjectQO.getSubjectType());
			}
			subjectQO.setSortProductSum(true);
			List<Subject> subjects = subjectService.queryList(subjectQO);
			HgLogger.getInstance().info("lxs_dev", "【QuerySubjectAction】【subjects长度】"+subjects.size());
			if (subjects !=null && subjects.size()>0) {
				subjectResponse.setSubjects(subjects);
				subjectResponse.setMessage("查询成功");
				subjectResponse.setResult(ApiResponse.RESULT_CODE_OK);
				HgLogger.getInstance().info("lxs_dev", "【QuerySubjectAction】"+"查询主题成功");
			}else {
				subjectResponse.setMessage("查询失败");
				subjectResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("lxs_dev", "【QuerySubjectAction】"+"查询主题失败");
			}
		} catch (Exception e) {
			subjectResponse.setMessage("查询失败");
			subjectResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("lxs_dev", "【QuerySubjectAction】"+"subjectResponse:"+JSON.toJSONString(subjectResponse));
		return JSON.toJSONString(subjectResponse);
	}

}
