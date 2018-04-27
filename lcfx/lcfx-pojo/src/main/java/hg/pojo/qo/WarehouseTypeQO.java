package hg.pojo.qo;


@SuppressWarnings("serial")
public class WarehouseTypeQO extends JxcBaseQo{
	/**
	 * 仓库类型名称
	 */
	private String name;
	
	private Boolean using;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

}
