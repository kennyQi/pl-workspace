package lxs.pojo.qo.line;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class LineImageQO extends BaseQo{
	private String lineID;
	
	private String lineDayRouteId;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getLineDayRouteId() {
		return lineDayRouteId;
	}

	public void setLineDayRouteId(String lineDayRouteId) {
		this.lineDayRouteId = lineDayRouteId;
	}
	
	
}
