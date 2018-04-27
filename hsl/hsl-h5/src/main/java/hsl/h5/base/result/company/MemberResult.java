package hsl.h5.base.result.company;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.company.MemberDTO;
public class MemberResult extends ApiResult{
	private MemberDTO member;

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
	
	public MemberDTO getMember() {
		return member;
	}

	public void setMember(MemberDTO member) {
		this.member = member;
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
