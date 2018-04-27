package lxs.api.v1.response.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.LineInfoDTO;
import lxs.api.v1.dto.line.LineListDTO;

public class QueryLineResponse extends ApiResponse {
	
	private String isLastPage;
	
	private List<LineListDTO> lineList;
	
	private LineInfoDTO line;

	public String getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(String isLastPage) {
		this.isLastPage = isLastPage;
	}

	public List<LineListDTO> getLineList() {
		return lineList;
	}

	public void setLineList(List<LineListDTO> lineList) {
		this.lineList = lineList;
	}

	public LineInfoDTO getLine() {
		return line;
	}

	public void setLine(LineInfoDTO line) {
		this.line = line;
	}

		
}
