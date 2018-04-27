package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class JxcBaseQo extends BaseQo {
	private boolean statusRemoved = false;

	public boolean isRemoved() {
		return statusRemoved;
	}

	public boolean getStatusRemoved() {
		return statusRemoved;
	}

	public void setStatusRemoved(boolean statusRemoved) {
		this.statusRemoved = statusRemoved;
	}

}
