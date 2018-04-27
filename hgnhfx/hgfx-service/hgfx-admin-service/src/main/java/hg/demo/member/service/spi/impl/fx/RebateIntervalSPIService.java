package hg.demo.member.service.spi.impl.fx;

import javax.transaction.Transactional;

import hg.demo.member.service.dao.hibernate.fx.RebateIntervalDAO;
import hg.demo.member.service.qo.hibernate.fx.RebateIntervalQO;
import hg.framework.service.component.base.BaseService;
import hg.fx.domain.rebate.RebateInterval;
import hg.fx.spi.RebateIntervalSPI;
import hg.fx.spi.qo.RebateIntervalSQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Transactional
@Service("rebateIntervalSPIService")
public class RebateIntervalSPIService extends BaseService implements RebateIntervalSPI {

	@Autowired
	private RebateIntervalDAO rebateIntervalDao;
	@Override
	public RebateInterval queryUnique(RebateIntervalSQO sqo) {
		// TODO Auto-generated method stub
		return rebateIntervalDao.queryUnique(RebateIntervalQO.build(sqo));
	}

}
