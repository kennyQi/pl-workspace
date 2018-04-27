package hsl.api.v1.request.qo.company;

import hsl.api.base.ApiPayload;

public class MemberQO extends ApiPayload{
	
	private String id;
	
	private String departmentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
