package hg.pojo.dto.product;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SpecValueDTO implements Serializable{
	private String specificationId;
	private String name;
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
