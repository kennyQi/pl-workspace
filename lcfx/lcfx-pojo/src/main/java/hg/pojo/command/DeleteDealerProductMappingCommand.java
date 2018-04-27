package hg.pojo.command;

import java.util.List;

/**
 * 删除平台商品对照
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteDealerProductMappingCommand extends JxcCommand {

	/**
	 * 平台商品对照id列表
	 */
	private List<String> dealerProductMappingId;
	
	private Integer sequence;

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<String> getDealerProductMappingId() {
		return dealerProductMappingId;
	}

	public void setDealerProductMappingId(List<String> dealerProductMappingId) {
		this.dealerProductMappingId = dealerProductMappingId;
	}
	
	
}
