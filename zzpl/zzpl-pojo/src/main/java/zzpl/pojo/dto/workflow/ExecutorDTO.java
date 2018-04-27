package zzpl.pojo.dto.workflow;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import zzpl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class ExecutorDTO extends BaseDTO{

	private List<String> userIDs;
	
	private List<String> roleID;
	
	private List<Integer> userFlag;
	
	private String userID;

	private String name;

	private String departmentID;

	private String departmentName;


	public List<String> getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(List<String> userIDs) {
		this.userIDs = userIDs;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<String> getRoleID() {
		return roleID;
	}

	public void setRoleID(List<String> roleID) {
		this.roleID = roleID;
	}
	
	
	public List<Integer> getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(List<Integer> userFlag) {
		this.userFlag = userFlag;
	}

	public static void main(String[] args) {
		ExecutorDTO dto=new ExecutorDTO();
//		List<String> roleID = new ArrayList<String>();
//		roleID.add("fa925022d0c3423c9d70c6b1498b3242");
//		dto.setRoleID(roleID);
		List<Integer> userFlag=new ArrayList<Integer>();
//		userFlag.add(-1);
		userFlag.add(0);
		dto.setUserFlag(userFlag);
		String jsonString=JSON.toJSONString(dto);
		System.out.println(jsonString);
	}
}
