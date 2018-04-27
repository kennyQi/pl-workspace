package hg.pojo.command;

import hg.pojo.dto.product.SpecificationDTO;

import java.util.List;

@SuppressWarnings("serial")
public class ImportBatchSpecificationCommand extends JxcCommand {

	/**
	 * 商品规格列表
	 */
	private List<SpecificationDTO> specificationList;

	public List<SpecificationDTO> getSpecificationList() {
		return specificationList;
	}

	public void setSpecificationList(List<SpecificationDTO> specificationList) {
		this.specificationList = specificationList;
	}

}
