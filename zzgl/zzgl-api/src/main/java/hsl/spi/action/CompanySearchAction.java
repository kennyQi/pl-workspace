package hsl.spi.action;

import java.util.ArrayList;
import java.util.List;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.company.CompanySearchQO;
import hsl.api.v1.request.qo.user.UserQO;
import hsl.api.v1.response.company.CompanySearchResponse;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.CompanySearchDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.spi.inter.company.CompanyService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("companySearchAction")
public class CompanySearchAction implements HSLAction{
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private CompanyService companyService;
	@Override
	public String execute(ApiRequest apiRequest) {
		CompanySearchQO companySearchQO=JSON.parseObject(apiRequest.getBody().getPayload(), CompanySearchQO.class);
		hgLogger.info("yuqz", "部门员工通讯录搜索请求"+JSON.toJSONString(companySearchQO));
		return companySearch(companySearchQO);
	}
	
	private String companySearch(CompanySearchQO companySearchQO) {
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslMemberQO hslMemberQO = new HslMemberQO();
		
		if(StringUtils.isNotBlank(companySearchQO.getUserId())){
			hslCompanyQO.setUserId(companySearchQO.getUserId());
		}
		
		CompanySearchResponse companySearchResponse = new CompanySearchResponse();
		
		List<CompanySearchDTO> companySearchList = new ArrayList<CompanySearchDTO>();
		List<CompanyDTO> companys = companyService.getCompanys(hslCompanyQO);
		if(companys == null || companys.size() <= 0){
			companySearchResponse.setResult(companySearchResponse.COMPANY_NOT_EXIST);
			companySearchResponse.setMessage("没有组织");
			return JSON.toJSONString(companySearchResponse);
		}
		
		if(StringUtils.isNotBlank(companySearchQO.getSearchName())){
			hslMemberQO.setSearchName(companySearchQO.getSearchName());
		}
		
		//先在成员里搜索
		boolean isExist = false;//查询结果是否有成员
		int totalItemSize = 0;//成员查询结果数量
		int i = 0,j = 0, k = 0;//提升性能
		for( i = 0; i < companys.size(); i++){
			CompanyDTO dto = companys.get(i);
//			hslDepartmentQO.setCompanyId(dto.getId());
			List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
			for( j = 0; j < departments.size(); j++){
				hslMemberQO.setDepartmentId(departments.get(j).getId());
				List<MemberDTO> members = companyService.getMembers(hslMemberQO);
				if(members.size() > 0){
					isExist = true;
					totalItemSize = totalItemSize + members.size();
					for( k = 0; k < members.size(); k++){
						CompanySearchDTO companySearchDTO = new CompanySearchDTO();
						companySearchDTO.setMemberName(members.get(k).getName());
						companySearchDTO.setJob(members.get(k).getJob());
						companySearchList.add(companySearchDTO);
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(companySearchQO.getSearchName())){
			hslDepartmentQO.setSearchName(companySearchQO.getSearchName());
		}
		
		i = 0;j = 0; 
		if(!isExist || totalItemSize == 0){
			companySearchList.clear();
//			companySearchList = new ArrayList<CompanySearchDTO>();
			for( i = 0; i < companys.size(); i++){
				CompanyDTO dto = companys.get(i);
//				hslDepartmentQO.setCompanyId(dto.getId());
				List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
				for( j = 0; j < departments.size(); j++){
					hslMemberQO.setDepartmentId(departments.get(j).getId());
					List<MemberDTO> members = companyService.getMembers(hslMemberQO);
					departments.get(j).setTotalCount(members.size());
					CompanySearchDTO companySearchDTO = new CompanySearchDTO();
					companySearchDTO.setCompanyName(dto.getCompanyName());
					companySearchDTO.setDepartName(departments.get(j).getDeptName());
					companySearchDTO.setMemberCount(members.size());
					companySearchList.add(companySearchDTO);
				}
			}
		}
		
		companySearchResponse.setCompanySearchList(companySearchList);
		companySearchResponse.setResult(companySearchResponse.RESULT_CODE_OK);
		companySearchResponse.setMessage("查询成功");
		
//		for(int i = 0; i < companys.size(); i++){
//			CompanyDTO dto = companys.get(i);
//			hslDepartmentQO.setCompanyId(dto.getId());
//			List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
//			for(int j = 0; j < departments.size(); j++){
//				hslMemberQO.setDepartmentId(departments.get(j).getId());
//				List<MemberDTO> members = companyService.getMembers(hslMemberQO);
//				departments.get(j).setTotalCount(members.size());
//			}
//			CompanyListDTO companyListDTO = new CompanyListDTO();
//			companyListDTO.setCompanyId(dto.getId());
//			companyListDTO.setDepartmentList(departments);
//			companyLists.add(companyListDTO);
//		}
//		
//		companyListResponse.setCompanyList(companyLists);
//		companyListResponse.setResult(companyListResponse.RESULT_CODE_OK);
//		companyListResponse.setMessage("查询成功");
		return JSON.toJSONString(companySearchResponse);
	}

}
