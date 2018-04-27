package hsl.h5.base.result.company;

import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.company.MemberDTO;

public class MemberListResult extends ApiResult{

	private List<MemberDTO> memberList;
	/**
	 * 组织名称
	 */
	private String companyName;
	
	/**
	 * 部门名称
	 */
	private String departName;
	
	/*
	 * 部门id
	 */
	private String departId;
	
	/*
	 * 成员数量
	 */
	private Integer memberCount;
	
	public List<MemberDTO> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<MemberDTO> memberList) {
		this.memberList = memberList;
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

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	
}
