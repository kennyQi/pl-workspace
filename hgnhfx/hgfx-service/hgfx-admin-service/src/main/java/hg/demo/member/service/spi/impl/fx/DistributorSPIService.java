package hg.demo.member.service.spi.impl.fx;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.mybatis.DistributorMyBatisDao;
import hg.demo.member.service.domain.manager.fx.DistributorManager;
import hg.demo.member.service.qo.hibernate.fx.DistributorQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.command.reserveInfo.CreateReserveInfoCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.ReserveInfo;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.util.PinYinUtil;

/**
 * 商户service实现
 * @author Caihuan
 * @date   2016年6月1日
 */
@Transactional
@Service("distributorSPIService")
public class DistributorSPIService extends BaseService implements DistributorSPI {

	
	@Autowired
	private DistributorMyBatisDao distributorMyBatisDao;
	@Autowired
	private DistributorDAO distributorDAO;
	@Autowired
	private ReserveInfoSPI reserveInfoService;
	
	@Override
	public Distributor queryUnique(DistributorSQO qo) {
		// TODO Auto-generated method stub
		return distributorDAO.queryUnique(DistributorQO.build(qo));
	}

	@Override
	public List<Distributor> queryList(DistributorSQO qo) {
		// TODO Auto-generated method stub
		return distributorDAO.queryList(DistributorQO.build(qo));
	}

	@Override
	public Pagination<Distributor> queryPagination(DistributorSQO sqo) {
		return distributorDAO.queryPagination(DistributorQO.build(sqo));
	}

	@Override
	public List<Distributor> batchEnableDisable(String[] ids, Boolean flag) throws Exception {
		
		int length = ids.length;
		List<Distributor> list = null;
		if(length>0)
		{
			Integer status = flag?Distributor.CHECK_STATUS_PASS:Distributor.CHECK_STATUS_REFUSE;
			list = new ArrayList<Distributor>();
			ModifyDistributorCommand command = new ModifyDistributorCommand();
			//只审批待审核的状态的商户
			for(int i = 0;i<length;i++)
			{
				Distributor distributor = distributorDAO.get(ids[i]);
				if(distributor.getCheckStatus()==Distributor.CHECK_STATUS_WATTING)
				{
					command.setCheckStatus(status);//修改属性
					list.add(new DistributorManager(distributor).modify(command).get());
				}
			}
		}
		
		return  (List<Distributor>) distributorDAO.update(list);
	}

	@Override
	public Distributor modifyDistributor(ModifyDistributorCommand command) {
		Distributor distributor = distributorDAO.get(command.getId());
		return distributorDAO.update(new DistributorManager(distributor).modify(command).get());
	}

	@Override
	public Distributor createDistributor(CreateDistributorCommand command) {
		Distributor distributor = new Distributor();
		command.setCode(getDistributorCode()); //设置商户编号
		command.setSignKey(PinYinUtil.getSignkey((command.getCode()+command.getCompanyName())));
		CreateReserveInfoCommand cmd = new CreateReserveInfoCommand();
		cmd.setAmount(0L);
		cmd.setArrearsAmount(0);
		cmd.setFreezeBalance(0L);
		cmd.setUsableBalance(0L);
		cmd.setWarnValue(10000);
		ReserveInfo reserveInfo = reserveInfoService.create(cmd);
		return distributorDAO.save(new DistributorManager(distributor).create(command,reserveInfo).get());
	}
	
	/**
	 * 获取商户数量
	 * @author Caihuan
	 * @date   2016年6月7日
	 */
	private Integer getDistributorNum()
	{
		Integer num = distributorMyBatisDao.getDistributorNum();
		if(num == null)
			num = 0;
		num++;
		return num;
	}
	
	/**
	 * 获取经销商编号
	 * @author Caihuan
	 * @date   2016年6月7日
	 */
	private String getDistributorCode()
	{
		StringBuilder sb = new StringBuilder("A");
		String num = String.valueOf(getDistributorNum());
		int length = 8-num.length();
		for(int i=0;i<length;i++)
		{
			sb.append("0");
		}
		sb.append(num);
		return sb.toString();
	}

	@Override
	public boolean checkExistPhone(String phone) {
		Integer num = distributorMyBatisDao.getPhoneCount(phone);
		if(num==null||num==0)
			return false;
		return true;
	}

}
