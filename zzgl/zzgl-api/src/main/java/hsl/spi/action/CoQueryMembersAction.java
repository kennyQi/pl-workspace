package hsl.spi.action;

import java.util.List;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.company.MemberQO;
import hsl.api.v1.response.company.MemberListResponse;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.spi.inter.company.CompanyService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("coQueryMembersAction")
public class CoQueryMembersAction implements HSLAction {
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private CompanyService companyService;
	@Override
	public String execute(ApiRequest apiRequest) {
		MemberQO memberQO = JSON.parseObject(apiRequest.getBody()
				.getPayload(), MemberQO.class);
		hgLogger.info("yuqz", "查询部门成员请求"+JSON.toJSONString(memberQO));
		return queryMembers(memberQO);
	}
	
	private String queryMembers(MemberQO memberQO) {
		HslMemberQO hslMemberQO = new HslMemberQO();
		MemberListResponse memberListResponse = new MemberListResponse();
		if(StringUtils.isNotBlank(memberQO.getDepartmentId())){
			hslMemberQO.setDepartmentId(memberQO.getDepartmentId());
		}else{
			memberListResponse.setMessage("查询失败");
			memberListResponse.setResult(memberListResponse.RESULT_CODE_FAIL);
			return JSON.toJSONString(memberListResponse);
		}
		
		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
		memberListResponse.setMemberList(memberList);
		memberListResponse.setResult(MemberListResponse.RESULT_CODE_OK);
		memberListResponse.setMessage("查询成功");
		return JSON.toJSONString(memberListResponse);
	}

}
