package hsl.h5.base.result.company;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.commonContact.CommonContactDTO;
public class MemberResult extends ApiResult{
	private CommonContactDTO commonContactDTO;

	/**
	 * 组织名称
	 */
	private String companyName;
	/**
	 * 部门名称
	 */
	private String departName;
	/**
	 * 头像地址
	 */
	private String headImgUrl;
	
	

	public CommonContactDTO getCommonContactDTO() {
		return commonContactDTO;
	}

	public void setCommonContactDTO(CommonContactDTO commonContactDTO) {
		this.commonContactDTO = commonContactDTO;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
}
