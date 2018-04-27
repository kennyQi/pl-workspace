package hg.pojo.command;


/**
 * 删除项目
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteProjectCommand extends JxcCommand {

	/**
	 * 项目id
	 */
	private String projectId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
