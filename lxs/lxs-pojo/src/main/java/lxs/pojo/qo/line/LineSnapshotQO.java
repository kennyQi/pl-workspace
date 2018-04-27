package lxs.pojo.qo.line;

import hg.common.component.BaseQo;

/**
 * @类功能说明：线路快照查询qo
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2015年3月2日
 */
@SuppressWarnings("serial")
public class LineSnapshotQO extends BaseQo {

	/**
	 * 所属线路
	 */
	private String lineID;
	
	/**
	 * 是否查询最新快照
	 */
	private Boolean isNew;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	
}