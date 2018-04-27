package hsl.pojo.command.Programa;

public class UpdateProgramaContentCommand {
	/**
	 * 内容ID
	 */
	private String id;
	/**
	 * 内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
