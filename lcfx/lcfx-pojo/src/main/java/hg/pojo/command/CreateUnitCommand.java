package hg.pojo.command;

@SuppressWarnings("serial")
public class CreateUnitCommand extends JxcCommand{
	private String unitName;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
