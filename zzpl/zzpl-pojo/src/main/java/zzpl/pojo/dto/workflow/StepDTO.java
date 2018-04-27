package zzpl.pojo.dto.workflow;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class StepDTO extends BaseDTO {
	/**
	 * 环节编号
	 */
	private String stepNO;
	/**
	 * 环节名称
	 */
	private String stepName;
	
	public String getStepNO() {
		return stepNO;
	}
	public void setStepNO(String stepNO) {
		this.stepNO = stepNO;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	
}
