package hsl.api.v1.response.company;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.company.MemberDTO;

public class MemberResponse extends ApiResponse{
	private MemberDTO member;

	public MemberDTO getMember() {
		return member;
	}

	public void setMember(MemberDTO member) {
		this.member = member;
	}
	
}
