package hg.pojo.qo;


@SuppressWarnings("serial")
public class UnitQO extends JxcBaseQo {

	/**
	 * 单位名
	 */
	private String name;
	
	private Boolean nameLike;

	public UnitQO() {
		nameLike = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

}
