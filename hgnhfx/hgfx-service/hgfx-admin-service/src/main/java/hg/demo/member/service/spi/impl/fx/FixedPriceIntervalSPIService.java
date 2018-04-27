package hg.demo.member.service.spi.impl.fx;


import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.dao.hibernate.fx.FixedPriceIntervalDAO;
import hg.demo.member.service.qo.hibernate.fx.FixedPriceIntervalQO;
import hg.demo.member.service.qo.hibernate.fx.ProductQO;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.domain.fixedprice.FixedPriceInterval;
import hg.fx.spi.FixedPriceIntervalSPI;
import hg.fx.spi.qo.FixedPriceIntervalSQO;
import hg.fx.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service("fixedPriceIntervalSPIService")
public class FixedPriceIntervalSPIService implements FixedPriceIntervalSPI{

	@Autowired
	private FixedPriceIntervalDAO fixedPriceIntervalDAO;
	@Autowired
	private AuthUserDAO authUserDAO;
	
	@Override
	public FixedPriceInterval queryFixedPriceInterval(FixedPriceIntervalSQO sqo) {
		FixedPriceIntervalQO fixedPriceIntervalQO =  new FixedPriceIntervalQO();
		ProductQO productQO= new ProductQO();
		productQO.setId(sqo.getProductID());
		fixedPriceIntervalQO.setProductQO(productQO);
		fixedPriceIntervalQO.setCreateDateYYYYMMType(1);
		fixedPriceIntervalQO.setCreateDateYYYYMM(sqo.getCreateDate());
		fixedPriceIntervalQO.setIsImplement(true);
		FixedPriceInterval fixedPriceInterval = fixedPriceIntervalDAO.queryUnique(fixedPriceIntervalQO);
		if(fixedPriceInterval==null){
			fixedPriceIntervalQO =  new FixedPriceIntervalQO();
			productQO= new ProductQO();
			productQO.setId(sqo.getProductID());
			fixedPriceIntervalQO.setProductQO(productQO);
			fixedPriceIntervalQO.setCreateDateYYYYMMType(2);
			fixedPriceIntervalQO.setCreateDateYYYYMM(sqo.getCreateDate());
			fixedPriceIntervalQO.setIsImplement(true);
			//fixedPriceIntervalQO.setInvalidDate(null);
			fixedPriceInterval = fixedPriceIntervalDAO.queryUnique(fixedPriceIntervalQO);
			if(fixedPriceInterval==null){
				fixedPriceInterval=null;	
			}
		}
		return fixedPriceInterval;
		
	}
	@Override
	public void createFixedPriceInterval(FixedPriceInterval fixedPriceInterval,String authUserID) {
		//查询当月有生效
		FixedPriceIntervalQO fixedPriceIntervalQO =  new FixedPriceIntervalQO();
		ProductQO productQO= new ProductQO();
		productQO.setId(fixedPriceInterval.getProduct().getId());
		fixedPriceIntervalQO.setProductQO(productQO);
		fixedPriceIntervalQO.setCreateDateYYYYMMType(1);
		fixedPriceIntervalQO.setCreateDateYYYYMM(fixedPriceInterval.getCreateDateYYYYMM());
		fixedPriceIntervalQO.setIsImplement(true);
		FixedPriceInterval oldfixedPriceInterval = fixedPriceIntervalDAO.queryUnique(fixedPriceIntervalQO);
		if(oldfixedPriceInterval==null){
			fixedPriceIntervalQO =  new FixedPriceIntervalQO();
			productQO= new ProductQO();
			productQO.setId(fixedPriceInterval.getProduct().getId());
			fixedPriceIntervalQO.setProductQO(productQO);
			fixedPriceIntervalQO.setCreateDateYYYYMMType(2);
			fixedPriceIntervalQO.setCreateDateYYYYMM(fixedPriceInterval.getCreateDateYYYYMM());
			fixedPriceIntervalQO.setIsImplement(true);
			fixedPriceIntervalQO.setInvalidDate(null);
			oldfixedPriceInterval = fixedPriceIntervalDAO.queryUnique(fixedPriceIntervalQO);
			//将以前生效的区间的失效时间更新进去
			String nextMonth = String.valueOf(fixedPriceInterval.getCreateDateYYYYMM())+"01 235959"; 
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
			Calendar calendar = Calendar.getInstance();
			try {
				calendar.setTime(simpleDateFormat.parse(nextMonth));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			calendar.add(Calendar.DATE, -1);
			oldfixedPriceInterval.setInvalidDate(calendar.getTime());
		}else{
			oldfixedPriceInterval.setInvalidDate(new Date());
			oldfixedPriceInterval.setIsImplement(false);
		}
		fixedPriceIntervalDAO.update(oldfixedPriceInterval);
		
		//保存新的
		fixedPriceInterval.setId(UUIDGenerator.getUUID());
		fixedPriceInterval.setCreateDate(new Date());
		fixedPriceInterval.setIsImplement(true);
		fixedPriceInterval.setOperator(authUserDAO.get(authUserID));
		fixedPriceIntervalDAO.save(fixedPriceInterval);
		
	}
	@Override
	public FixedPriceInterval queryLastFixedPriceInterval(
			FixedPriceIntervalSQO sqo) {
		FixedPriceIntervalQO fixedPriceIntervalQO =  new FixedPriceIntervalQO();
		ProductQO productQO= new ProductQO();
		productQO.setId(sqo.getProductID());
		fixedPriceIntervalQO.setProductQO(productQO);
		fixedPriceIntervalQO.setCreateDateYYYYMMType(3);
		fixedPriceIntervalQO.setCreateDateYYYYMM(sqo.getCreateDate());
		fixedPriceIntervalQO.setIsImplement(true);
		List<FixedPriceInterval> fixedPriceIntervalList = fixedPriceIntervalDAO.queryList(fixedPriceIntervalQO);
		FixedPriceInterval fixedPriceInterval = fixedPriceIntervalList.get(0);
		return fixedPriceInterval;
	}

}
