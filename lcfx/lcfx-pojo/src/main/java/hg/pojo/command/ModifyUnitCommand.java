package hg.pojo.command;

@SuppressWarnings("serial")
public class ModifyUnitCommand extends JxcCommand{
	private String unitName;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
