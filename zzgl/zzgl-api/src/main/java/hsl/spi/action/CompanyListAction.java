package hsl.spi.action;

import java.util.ArrayList;
import java.util.List;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.company.CompanyQO;
import hsl.api.v1.response.company.CompanyListResponse;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.company.CompanyListDTO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.spi.inter.company.CompanyService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("companyListAction")
public class CompanyListAction implements HSLAction{
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private CompanyService companyService;
	@Override
	public String execute(ApiRequest apiRequest) {
		CompanyQO companyQo=JSON.parseObject(apiRequest.getBody().getPayload(), CompanyQO.class);
		hgLogger.info("yuqz", "查询部门员工通讯录请求"+JSON.toJSONString(companyQo));
		return queryOrganizationList(companyQo);
	}
	private String queryOrganizationList(CompanyQO companyQo) {
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslMemberQO hslMemberQO = new HslMemberQO();
		
		if(StringUtils.isNotBlank(companyQo.getUserId())){
			hslCompanyQO.setUserId(companyQo.getUserId());
		}
		
		CompanyListResponse companyListResponse = new CompanyListResponse();
		List<CompanyListDTO> companyLists = new ArrayList<CompanyListDTO>();
		List<CompanyDTO> companys = companyService.getCompanys(hslCompanyQO);
		if(companys == null || companys.size() <= 0){
			companyListResponse.setResult(companyListResponse.COMPANY_NOT_EXIST);
			companyListResponse.setMessage("没有组织");
			return JSON.toJSONString(companyListResponse);
		}
		for(int i = 0; i < companys.size(); i++){
			CompanyDTO dto = companys.get(i);
//			hslDepartmentQO.setCompanyId(dto.getId());
			List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
			for(int j = 0; j < departments.size(); j++){
				hslMemberQO.setDepartmentId(departments.get(j).getId());
				List<MemberDTO> members = companyService.getMembers(hslMemberQO);
				departments.get(j).setTotalCount(members.size());
			}
			CompanyListDTO companyListDTO = new CompanyListDTO();
			companyListDTO.setCompanyId(dto.getId());
			companyListDTO.setCompanyName(dto.getCompanyName());
			companyListDTO.setDepartmentList(departments);
			companyLists.add(companyListDTO);
		}
		
		companyListResponse.setCompanyList(companyLists);
		companyListResponse.setResult(companyListResponse.RESULT_CODE_OK);
		companyListResponse.setMessage("查询成功");
		
		return JSON.toJSONString(companyListResponse);
	}
	
}
