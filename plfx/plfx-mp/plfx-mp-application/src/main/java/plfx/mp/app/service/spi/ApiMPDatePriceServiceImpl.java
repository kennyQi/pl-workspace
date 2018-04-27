package plfx.mp.app.service.spi;

import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.mp.request.qo.MPDatePriceQO;
import plfx.api.client.api.v1.mp.response.MPQueryDatePriceResponse;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO;
import plfx.mp.spi.inter.DateSalePriceService;
import plfx.mp.spi.inter.api.ApiMPDatePriceService;

@Service
public class ApiMPDatePriceServiceImpl implements ApiMPDatePriceService {
	
	@Autowired
	private DateSalePriceService dateSalePriceService;

	@Override
	public MPQueryDatePriceResponse apiQueryDateSalePrices(MPDatePriceQO qo) {
		HgLogger.getInstance().info("wuyg", "景区政策查询");
		MPQueryDatePriceResponse response = new MPQueryDatePriceResponse();
		try {
			List<DateSalePriceDTO> dateSalePrices = dateSalePriceService
					.getDateSalePrice(qo.getPolicyId(), qo.getFromClientKey());
			response.setDateSalePrices(dateSalePrices);
			response.setTotalCount(dateSalePrices.size());
		} catch (Exception e) {
			HgLogger.getInstance().error("wuyg", "景区政策查询：失败 ,"+e.getMessage());
			e.printStackTrace();
			response.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		return response;
	}

}