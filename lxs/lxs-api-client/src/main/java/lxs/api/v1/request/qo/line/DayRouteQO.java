package lxs.api.v1.request.qo.line;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class DayRouteQO extends ApiPayload {

	private String lineId;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
