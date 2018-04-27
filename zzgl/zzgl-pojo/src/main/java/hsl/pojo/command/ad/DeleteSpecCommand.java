package hsl.pojo.command.ad;

@SuppressWarnings("serial")
public class DeleteSpecCommand extends HslDeleteAdCommand{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
