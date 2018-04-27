package hg.pojo.command;

import hg.pojo.dto.product.BrandDTO;

import java.util.List;

@SuppressWarnings("serial")
public class ImportBatchBrandCommand extends JxcCommand {

	/**
	 * 品牌列表
	 */
	private List<BrandDTO> brandList;

	public List<BrandDTO> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<BrandDTO> brandList) {
		this.brandList = brandList;
	}
	
	
}
