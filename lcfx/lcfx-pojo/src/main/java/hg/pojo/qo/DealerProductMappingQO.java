package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class DealerProductMappingQO extends BaseQo{
	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * skuid
	 */
	private String skuProductId;
	/**
	 * 序号
	 */
	private Integer sequence;
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getSkuProductId() {
		return skuProductId;
	}

	public void setSkuProductId(String skuProductId) {
		this.skuProductId = skuProductId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
