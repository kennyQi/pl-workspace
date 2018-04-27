package hsl.h5.base.result.mp;

import java.util.List;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.mp.DateSalePriceDTO;

/**
 * 门票价格日历查询返回
 * 
 * @author yuxx
 * 
 */
public class MPQueryDatePriceResult extends ApiResult{

	private List<DateSalePriceDTO> dateSalePrices;

	private Integer totalCount;

	/**
	 * 价格日历查询无结果
	 */
	public final static String RESULT_DATEPRICE_NOTFOUND = "-1";
	
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
