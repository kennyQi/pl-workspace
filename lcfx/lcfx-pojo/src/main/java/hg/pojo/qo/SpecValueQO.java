package hg.pojo.qo;


@SuppressWarnings("serial")
public class SpecValueQO extends JxcBaseQo {
private String name;
	
	/**
	 * 规格id
	 */
	private String specificationId;

	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
