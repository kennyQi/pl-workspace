package slfx.jp.qo.admin.policy;

import hg.common.component.BaseQo;
import slfx.jp.command.admin.jp.JPOrderCreateSpiCommand;

public class PolicySnapshotQO extends BaseQo {

	public PolicySnapshotQO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PolicySnapshotQO(JPOrderCreateSpiCommand command) {
	   this.setId(command.getPolicyId());
	}

	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = -4570024265699210776L;
	
	
}
