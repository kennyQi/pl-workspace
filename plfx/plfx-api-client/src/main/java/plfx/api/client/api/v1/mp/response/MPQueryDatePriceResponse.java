package plfx.api.client.api.v1.mp.response;

import java.util.List;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;

/**
 * 门票下单返回
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPQueryDatePriceResponse extends ApiResponse {

	private List<DateSalePriceDTO> dateSalePrices;

	private Integer totalCount;


	public List<DateSalePriceDTO> getDateSalePrices() {
		return dateSalePrices;
	}

	public void setDateSalePrices(List<DateSalePriceDTO> dateSalePrices) {
		this.dateSalePrices = dateSalePrices;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
