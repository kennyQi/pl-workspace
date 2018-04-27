package hsl.api.v1.response.company;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.company.MemberDTO;

public class MemberListResponse extends ApiResponse{

	private List<MemberDTO> memberList;
	
	public List<MemberDTO> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<MemberDTO> memberList) {
		this.memberList = memberList;
	}
	
}
