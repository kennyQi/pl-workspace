package hg.demo.member.service.spi.impl.fx;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.DistributorUserDAO;
import hg.demo.member.service.dao.mybatis.ProductMyBatisDao;
import hg.demo.member.service.domain.manager.fx.DistributorUserManager;
import hg.demo.member.service.qo.hibernate.fx.DistributorUserQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.command.distributoruser.RemoveDistributorUserCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.util.CharacterUtil;

@Transactional
@Service("distributorUserSPIService")
public class DistributorUserSPIService extends BaseService implements
		DistributorUserSPI {

	@Autowired
	private DistributorUserDAO distributorUserDAO;
	
	@Autowired
	private DistributorSPI distributorService;
	
	@Autowired
	private ProductMyBatisDao productMyBatisDao;
	
	
	@Override
	public Pagination<DistributorUser> queryPagination(DistributorUserSQO sqo) {
		return distributorUserDAO.queryPagination(DistributorUserQO.build(sqo));
	}

	@Override
	public DistributorUser create(CreateDistributorUserCommand command) {
		DistributorUser user = new DistributorUser();
		Distributor distributor = null;
		//主账号
		if(command.getType()==1)
		{
		CreateDistributorCommand cmd = new CreateDistributorCommand();
		cmd.setCompanyName(command.getCompanyName());
		cmd.setPhone(command.getPhone());
		cmd.setWebSite(command.getWebSite());
		cmd.setStatus(command.getStatus());
		cmd.setLinkMan(command.getName());
		cmd.setFirstLetter(CharacterUtil.getFirstDistrobutorSpell(command.getCompanyName()));
		cmd.setCheckStatus(command.getCheckStatus());
		cmd.setDiscountType(command.getDiscountType());
		distributor = distributorService.createDistributor(cmd);
		}
		//子帐号
		else if(command.getType()==2)
		{
			DistributorSQO qo = new DistributorSQO();
			qo.setId(command.getDistributorId());
		    distributor = distributorService.queryUnique(qo);
		}
		else
		{
			return null;
		}
		return distributorUserDAO.save(new DistributorUserManager(user).createDistributorUser(command,distributor).get());
	
		
	}

	@Override
	public DistributorUser modify(ModifyDistributorUserCommand command) {
		
		DistributorUser user = distributorUserDAO.get(command.getId());
		return distributorUserDAO.update(new DistributorUserManager(user).modifyDistributorUser(command).get());
	}

	@Override
	public DistributorUser queryUnique(DistributorUserSQO sqo) {
		return distributorUserDAO.queryUnique(DistributorUserQO.build(sqo));
	}

	@Override
	public void delete(RemoveDistributorUserCommand command) {
		
		DistributorUser user = distributorUserDAO.get(command.getId());
		if(user!=null)
		{
			distributorUserDAO.update(new DistributorUserManager(user).delete().get());
		}
	}

	@Override
	public Long getMonthReserveInfo(String distributorId) {
	
		 Calendar c = Calendar.getInstance();    
	     c.add(Calendar.MONTH, 0);
	     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
	     c.set(Calendar.HOUR_OF_DAY, 0);
	     c.set(Calendar.MINUTE, 0);
	     c.set(Calendar.SECOND, 0);
	     Date beginDate = c.getTime();
	     
	     c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	     c.set(Calendar.HOUR_OF_DAY,  c.getActualMaximum(Calendar.HOUR_OF_DAY));
	     c.set(Calendar.MINUTE,  59);
	     c.set(Calendar.SECOND, 59);
	     Date endDate = c.getTime();
	     
	     Long sum = productMyBatisDao.sumReserveInfo(distributorId, beginDate, endDate);
		return sum==null?0:sum;
	}
	
}
