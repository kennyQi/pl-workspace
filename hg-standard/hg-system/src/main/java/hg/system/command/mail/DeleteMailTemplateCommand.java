package hg.system.command.mail;

import hg.common.component.BaseCommand;
import java.io.Serializable;
import java.util.List;

/**
 * @类功能说明：删除邮件模板指令
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-10-28上午9:47:16
 */
public class DeleteMailTemplateCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	
	private String itemId;
	private List<Serializable> itemIds;

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<Serializable> getItemIds() {
		return itemIds;
	}
	public void setItemIds(List<Serializable> itemIds) {
		this.itemIds = itemIds;
	}
}