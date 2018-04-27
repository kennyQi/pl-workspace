package lxs.api.v1.response.line;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.DateSalePriceDTO;

public class QueryDateSalePriceResponse extends ApiResponse {
	List<DateSalePriceDTO> dateSalePriceList;

	public List<DateSalePriceDTO> getDateSalePriceList() {
		return dateSalePriceList;
	}

	public void setDateSalePriceList(List<DateSalePriceDTO> dateSalePriceList) {
		this.dateSalePriceList = dateSalePriceList;
	}

}
