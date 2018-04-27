package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lxs.app.dao.line.LxsDateSalePriceDAO;
import lxs.domain.model.line.DateSalePrice;
import lxs.pojo.qo.line.DateSalePriceQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DateSalePriceService extends
		BaseServiceImpl<DateSalePrice, DateSalePriceQO, LxsDateSalePriceDAO> {

	@Autowired
	private LxsDateSalePriceDAO dateSalePriceDAO;

	@Override
	protected LxsDateSalePriceDAO getDao() {
		return dateSalePriceDAO;
	}

	public double getMax(String propertyName){
		DateSalePriceQO dateSalePriceQO= new DateSalePriceQO();
		return dateSalePriceDAO.maxProperty(propertyName, dateSalePriceQO);
	}
	public double getMin(String propertyName,String lineID){
		DateSalePriceQO dateSalePriceQO= new DateSalePriceQO();
		dateSalePriceQO.setLineID(lineID);
		dateSalePriceQO.setStartDate(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dateSalePriceQO.setEndDate(sdf.parse("2999-12-31 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
			HgLogger.getInstance().info("lxs_dev","查询价格日历最小值报错"+e);
		}
		return dateSalePriceDAO.minProperty(propertyName, dateSalePriceQO);
	}
}
