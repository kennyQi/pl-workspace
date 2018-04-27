package zzpl.api.client.request.workflow;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class WorkflowQO extends ApiPayload {

	/**
	 * 用户角色list
	 */
	private List<String> roleList;

	/**
	 * 公司ID
	 */
	private String companyID;

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public static void main(String[] args) {
		WorkflowQO workflowQO= new WorkflowQO();
		workflowQO.setCompanyID("dasdasdasdasdaasdasas");
		List<String> roleList = new ArrayList<String>();
		roleList.add("dasdasdasdasdasdasd");
		roleList.add("dasdasdasdasdasdasd");
		roleList.add("dasdasdasdasdasdasd");
		workflowQO.setRoleList(roleList);
		System.out.println(JSON.toJSON(workflowQO));
	}
}