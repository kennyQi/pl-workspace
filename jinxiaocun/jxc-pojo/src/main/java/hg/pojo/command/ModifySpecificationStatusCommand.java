package hg.pojo.command;


/**
 * 修改规格状态
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifySpecificationStatusCommand extends JxcCommand {

	/**
	 * 规格id
	 */
	private String specificationId;
	
	/**
	 * 是否启用
	 */
	private Boolean using;

	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}
	
	
}
