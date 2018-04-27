package hsl.h5.base.result.company;

import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.company.CompanySearchDTO;

/**
 * 
 * @类功能说明：部门员工通讯录搜索结果
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2014年10月21日下午1:51:02
 * @版本：V1.0
 *
 */
public class CompanySearchResult extends ApiResult{
	
	private List<CompanySearchDTO> companySearchList;
	
	public final static String COMPANY_NOT_EXIST = "-1";//没有组织
	
	public final static String COMPANY_MEMBER = "1";//搜索有成员
	
	public final static String COMPANY_NOMEMBER = "2";//搜索结果无成员，有部门
	
	public final static String COMPANY_NORESULT = "3";//搜索成员部门都无结果时

	public List<CompanySearchDTO> getCompanySearchList() {
		return companySearchList;
	}

	public void setCompanySearchList(List<CompanySearchDTO> companySearchList) {
		this.companySearchList = companySearchList;
	}

}
