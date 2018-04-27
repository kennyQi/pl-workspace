package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class CheckRecordQo extends BaseQo{
	private String belongToId;

	public String getBelongToId() {
		return belongToId;
	}

	public void setBelongToId(String belongToId) {
		this.belongToId = belongToId;
	}

}
