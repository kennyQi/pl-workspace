package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：线路快照QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月2日下午5:55:34
 * @版本：V1.0
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