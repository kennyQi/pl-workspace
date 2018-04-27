package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;
import zzpl.api.client.dto.user.FrequentFlyerDTO;

@SuppressWarnings("serial")
public class AddFrequentFlyerCommand extends ApiPayload {

	private FrequentFlyerDTO frequentFlyer;

	public FrequentFlyerDTO getFrequentFlyer() {
		return frequentFlyer;
	}

	public void setFrequentFlyer(FrequentFlyerDTO frequentFlyer) {
		this.frequentFlyer = frequentFlyer;
	}

}
