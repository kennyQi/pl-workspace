package lxs.api.action.line;

import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.line.DateSalePriceDTO;
import lxs.api.v1.request.qo.line.DateSalePriceQO;
import lxs.api.v1.response.line.QueryDateSalePriceResponse;
import lxs.app.service.line.DateSalePriceService;
import lxs.app.service.line.LineService;
import lxs.domain.model.line.DateSalePrice;
import lxs.domain.model.line.Line;
import lxs.pojo.exception.line.LineException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryDateSalePriceAction")
public class QueryDateSalePriceAction implements LxsAction {

	@Autowired
	private DateSalePriceService dateSalePriceService;

	@Autowired
	private LineService lineService;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【QueryDateSalePriceAction】"+"进入action");
		DateSalePriceQO apidateSalePriceQO = JSON.parseObject(apiRequest.getBody().getPayload(),DateSalePriceQO.class);
		QueryDateSalePriceResponse queryDateSalePriceResponse = new QueryDateSalePriceResponse();
		lxs.pojo.qo.line.DateSalePriceQO dateSalePriceQO = new lxs.pojo.qo.line.DateSalePriceQO();
		try{
			if(apidateSalePriceQO.getLineID()==null){
				throw new LineException(LineException.RESULT_NO_LINE, "线路为空");
			}
			HgLogger.getInstance().info("lxs_dev", "【QueryDateSalePriceAction】"+"线路ID："+apidateSalePriceQO.getLineID());
			dateSalePriceQO.setLineID(apidateSalePriceQO.getLineID());
			Line line = lineService.get(apidateSalePriceQO.getLineID());
			Integer lastPayTotalDaysBeforeStart = line.getPayInfo().getLastPayTotalDaysBeforeStart();
			Date d=new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String now = simpleDateFormat.format(d);
			try {
				d=simpleDateFormat.parse(now);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*			Date now = null;
			now=simpleDateFormat.parse(d);*/
			dateSalePriceQO.setStartDate(new Date(d.getTime() + lastPayTotalDaysBeforeStart * 24 * 60 * 60 * 1000));
			dateSalePriceQO.setEndDate(apidateSalePriceQO.getEndDate());
			List<DateSalePrice> dateSalePriceList = dateSalePriceService.queryList(dateSalePriceQO);
			HgLogger.getInstance().info("lxs_dev", "【QueryDateSalePriceAction】"+"查询成功开始转化");
			List<DateSalePriceDTO> dateSalePriceDTOs = new ArrayList<DateSalePriceDTO>();
			for(DateSalePrice dateSalePrice:dateSalePriceList){
				DateSalePriceDTO dateSalePriceDTO = new DateSalePriceDTO();
				dateSalePriceDTO.setSaleDate(dateSalePrice.getSaleDate());
				dateSalePriceDTO.setAdultPrice(dateSalePrice.getAdultPrice());
				dateSalePriceDTO.setChildPrice(dateSalePrice.getChildPrice());
				dateSalePriceDTO.setNumber(dateSalePrice.getNumber());
				dateSalePriceDTOs.add(dateSalePriceDTO);
			}
			queryDateSalePriceResponse.setDateSalePriceList(dateSalePriceDTOs);
			queryDateSalePriceResponse.setResult(ApiResponse.RESULT_CODE_OK);
			queryDateSalePriceResponse.setMessage("查询成功");
		}catch (LineException e){
			HgLogger.getInstance().info("lxs_dev", "【QueryDateSalePriceAction】"+"异常，"+HgLogger.getStackTrace(e));
			queryDateSalePriceResponse.setResult(e.getCode());
			queryDateSalePriceResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev",  "【QueryDateSalePriceAction】"+"查询价格日历结果"+JSON.toJSONString(queryDateSalePriceResponse));
		return JSON.toJSONString(queryDateSalePriceResponse);
	}

}
