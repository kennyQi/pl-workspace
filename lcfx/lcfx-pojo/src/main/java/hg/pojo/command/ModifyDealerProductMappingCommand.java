package hg.pojo.command;

import hg.pojo.dto.product.DealerProductMappingDTO;

import java.util.List;

/**
 * 编辑平台商品对照
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyDealerProductMappingCommand extends JxcCommand {


	/**
	 * 平台商品对照列表
	 */
	private List<DealerProductMappingDTO> dealerProductMappingList;

	public List<DealerProductMappingDTO> getDealerProductMappingList() {
		return dealerProductMappingList;
	}
	public void setDealerProductMappingList(
			List<DealerProductMappingDTO> dealerProductMappingList) {
		this.dealerProductMappingList = dealerProductMappingList;
	}
	
}
