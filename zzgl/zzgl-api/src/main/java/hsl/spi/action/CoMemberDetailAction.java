package hsl.spi.action;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.company.MemberQO;
import hsl.api.v1.response.company.MemberListResponse;
import hsl.api.v1.response.company.MemberResponse;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.spi.inter.company.CompanyService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("coMemberDetailAction")
public class CoMemberDetailAction implements HSLAction{
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private CompanyService companyService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		MemberQO memberQO = JSON.parseObject(apiRequest.getBody()
				.getPayload(), MemberQO.class);
		hgLogger.info("yuqz", "查询部门成员请求"+JSON.toJSONString(memberQO));
		return memberDetail(memberQO);
	}

	private String memberDetail(MemberQO memberQO) {
		HslMemberQO hslMemberQO = new HslMemberQO();
		MemberResponse memberResponse = new MemberResponse();
		if(StringUtils.isNotBlank(memberQO.getId())){
			hslMemberQO.setId(memberQO.getId());
		}else{
			memberResponse.setMessage("查询失败");
			memberResponse.setResult(memberResponse.RESULT_CODE_FAIL);
			return JSON.toJSONString(memberResponse);
		}
		
		MemberDTO member = companyService.getMembers(hslMemberQO).get(0);
		
		memberResponse.setMember(member);
		memberResponse.setResult(MemberListResponse.RESULT_CODE_OK);
		memberResponse.setMessage("查询成功");
		return JSON.toJSONString(memberResponse);
	}

}
