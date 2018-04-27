package hsl.pojo.dto.company;

import hsl.pojo.dto.BaseDTO;

/**
 * 公司DTO
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class CompanyDTO extends BaseDTO{
	/**
	 * 公司名字
	 */
	private String companyName;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
