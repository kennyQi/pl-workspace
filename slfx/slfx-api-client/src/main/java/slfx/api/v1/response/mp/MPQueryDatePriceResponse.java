package slfx.api.v1.response.mp;

import java.util.List;
import slfx.api.base.ApiResponse;
import slfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;

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
