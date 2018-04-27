package lxs.api.v1.response.order.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.order.line.LineOrderInfoDTO;
import lxs.api.v1.dto.order.line.LineOrderListDTO;

public class QueryLineOrderResponse extends ApiResponse {
	
	private String isLastPage;

	private List<LineOrderListDTO> lineOrderList;
	
	private LineOrderInfoDTO lineOrderInfo;
	
	public LineOrderInfoDTO getLineOrderInfo() {
		return lineOrderInfo;
	}

	public void setLineOrderInfo(LineOrderInfoDTO lineOrderInfo) {
		this.lineOrderInfo = lineOrderInfo;
	}

	public String getIsLastPage() {
		return isLastPage;
	}
	
	public void setIsLastPage(String isLastPage) {
		this.isLastPage = isLastPage;
	}

	public List<LineOrderListDTO> getLineOrderList() {
		return lineOrderList;
	}

	public void setLineOrderList(List<LineOrderListDTO> lineOrderList) {
		this.lineOrderList = lineOrderList;
	}

}
