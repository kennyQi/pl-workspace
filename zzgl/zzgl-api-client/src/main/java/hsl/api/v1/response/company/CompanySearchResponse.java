package hsl.api.v1.response.company;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.CompanySearchDTO;

/**
 * 
 * @类功能说明：组织架构搜索结果
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
public class CompanySearchResponse extends ApiResponse{
	
	private List<CompanySearchDTO> companySearchList;
	
	public final static String COMPANY_NOT_EXIST = "-1";//没有组织

	public List<CompanySearchDTO> getCompanySearchList() {
		return companySearchList;
	}

	public void setCompanySearchList(List<CompanySearchDTO> companySearchList) {
		this.companySearchList = companySearchList;
	}

}
