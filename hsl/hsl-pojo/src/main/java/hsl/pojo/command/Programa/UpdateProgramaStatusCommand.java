package hsl.pojo.command.Programa;

public class UpdateProgramaStatusCommand {
	/**栏目id*/
	private String id;
	/**栏目名称*/
	private String name;
	/**栏目位置*/
	private Integer location;
	/**栏目状态*/
	private Integer status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
