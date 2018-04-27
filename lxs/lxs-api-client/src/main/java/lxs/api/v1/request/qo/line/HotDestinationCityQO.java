package lxs.api.v1.request.qo.line;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class HotDestinationCityQO extends ApiPayload{

	public final static Integer TYPE_WITH_GROUP = 1; // 跟团游
	public final static Integer TYPE_SELF_HELP = 2; // 自助游

	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
