package hg.system.command.seo;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：删除META标签
 * @类修改者：
 * @修改日期：2015-1-23下午4:39:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-23下午4:39:52
 */
@SuppressWarnings("serial")
public class DeleteMetaTagCommand extends BaseCommand {
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
