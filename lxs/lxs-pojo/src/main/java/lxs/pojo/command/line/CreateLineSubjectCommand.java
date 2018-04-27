package lxs.pojo.command.line;

public class CreateLineSubjectCommand {

	/**
	 * 主题ID
	 */
	private String[] subjectID;

	/**
	 * 线路ID 当线路删除或者下架时应删除关联信息
	 */
	private String lineID;

	public String[] getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String[] subjectID) {
		this.subjectID = subjectID;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	
	
}
