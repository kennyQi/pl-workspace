package zzpl.pojo.command.saga;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CheckSMSValidCommand extends BaseCommand {

	private String sagaID;

	private String smsValid;

	public String getSagaID() {
		return sagaID;
	}

	public void setSagaID(String sagaID) {
		this.sagaID = sagaID;
	}

	public String getSmsValid() {
		return smsValid;
	}

	public void setSmsValid(String smsValid) {
		this.smsValid = smsValid;
	}

}
