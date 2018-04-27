package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.common.MD5HashUtil;
import hg.demo.member.service.dao.hibernate.fx.DistributorDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorRegisterDAO;
import hg.demo.member.service.dao.hibernate.fx.DistributorUserDAO;
import hg.demo.member.service.dao.mybatis.DistributorMyBatisDao;
import hg.demo.member.service.domain.manager.fx.DistributorManager;
import hg.demo.member.service.domain.manager.fx.DistributorRegisterManager;
import hg.demo.member.service.domain.manager.fx.DistributorUserManager;
import hg.demo.member.service.qo.hibernate.fx.DistributorQO;
import hg.demo.member.service.qo.hibernate.fx.DistributorRegisterQO;
import hg.demo.member.service.qo.hibernate.fx.DistributorUserQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.DistributorRegister.AduitDistributorRegisterCommand;
import hg.fx.command.DistributorRegister.CreateDistributorRegisterCommand;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.reserveInfo.CreateReserveInfoCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorRegister;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.ReserveInfo;
import hg.fx.spi.DistributorRegisterSPI;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.spi.qo.DistributorRegisterSQO;
import hg.fx.util.CharacterUtil;
import hg.fx.util.PinYinUtil;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 商户注册申请记录SPIService
 * @author zqq
 * @date 2016-7-14下午2:27:49
 * @since
 */
@Transactional
@Service("distributorRegisterSPIService")
public class DistributorRegisterSPIService extends BaseService implements DistributorRegisterSPI {

	@Autowired
	private DistributorRegisterDAO distributorRegisterDAO;
	@Autowired
	private DistributorDAO distributorDAO;
	@Autowired
	private DistributorUserDAO distributorUserDAO;
	@Autowired
	private ReserveInfoSPI reserveInfoService;
	@Autowired
	private DistributorMyBatisDao distributorMyBatisDao;
	/**
	 * 查询唯一商户注册申请记录
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public DistributorRegister queryUnique(DistributorRegisterSQO qo) {
		// TODO Auto-generated method stub
		DistributorRegister item = distributorRegisterDAO.queryUnique(DistributorRegisterQO.build(qo));
		return item;
	}

	/**
	 * 条件查询商户注册申请记录
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public List<DistributorRegister> queryList(DistributorRegisterSQO qo) {
		// TODO Auto-generated method stub
		List<DistributorRegister> list = distributorRegisterDAO.queryList(DistributorRegisterQO.build(qo));
		return list;
	}

	/**
	 * 条件分页查询商户注册申请记录
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public Pagination<DistributorRegister> queryPagination(
			DistributorRegisterSQO sqo) {
		// TODO Auto-generated method stub
		Pagination<DistributorRegister> page = distributorRegisterDAO.queryPagination(DistributorRegisterQO.build(sqo));
		return page;
	}

	/**
	 * 创建商户注册申请信息
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public DistributorRegister createDistributorRegister(
			CreateDistributorRegisterCommand command) {
		
		DistributorRegister distributorRegister = new DistributorRegister();
		return distributorRegisterDAO.save(new DistributorRegisterManager(distributorRegister).create(command).get());
	}

	/**
	 * 审核商户注册审核请求
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public List<String> aduitDistributorRegister(
			AduitDistributorRegisterCommand aduitCommand) {
		List<String> res = new ArrayList<String>();
		if(aduitCommand.getIsPass()==null){
			throw new RuntimeException("审核失败，参数有问题");
		}
		if(aduitCommand.getIds()==null||aduitCommand.getIds().size()==0){
			DistributorRegister distributorRegister = distributorRegisterDAO.get(aduitCommand.getId());
				if(distributorRegister.getStatus().intValue()!=DistributorRegister.DISTRIBUTOR_REGISTER_UNCHECK){
					throw new RuntimeException("审核失败，前置状态异常;"+"异常id："+distributorRegister.getId());
					//处理失败
				}else{
				distributorRegisterDAO.update(new DistributorRegisterManager(distributorRegister).aduit(aduitCommand).get());
				//审核通过 创建商户和商户帐号
				if(aduitCommand.getIsPass().booleanValue())//如果审核通过
					create(distributorRegister,0);
				//处理成功
				}
				res.add(distributorRegister.getPhone());
		}else{
			//批量审核
			for(int i=0;i<aduitCommand.getIds().size();i++){
				DistributorRegister distributorRegister = distributorRegisterDAO.get(aduitCommand.getIds().get(i));
					if(distributorRegister.getStatus().intValue()!=DistributorRegister.DISTRIBUTOR_REGISTER_UNCHECK){
						throw new RuntimeException("审核失败，前置状态异常;"+"异常id："+distributorRegister.getId());
						//处理失败
					}else{
						distributorRegisterDAO.update(new DistributorRegisterManager(distributorRegister).aduit(aduitCommand).get());
						//审核通过 创建商户和商户帐号
						if(aduitCommand.getIsPass().booleanValue())
							create(distributorRegister,i);
						//处理成功
						res.add(distributorRegister.getPhone());
					}
			}
		}
		return res;
	}
	
	public DistributorUser create(DistributorRegister distributorRegister,int i) {
		DistributorUser user = new DistributorUser();
		Distributor distributor = null;
		CreateDistributorUserCommand command = new CreateDistributorUserCommand();
		command.setType(1); // 主帐号
		// 后台添加 直接启用，审核通过
		command.setStatus(Distributor.STATUS_OF_IN_USE);
		command.setCheckStatus(Distributor.CHECK_STATUS_PASS);
		//添加基本商户信息
		command.setPassword(MD5HashUtil.toMD5(distributorRegister.getPasswd()));
		command.setAccount(distributorRegister.getLoginName());
		command.setPhone(distributorRegister.getPhone());
		command.setName(distributorRegister.getLinkMan());
		command.setWebSite(distributorRegister.getWebSite());
		command.setCompanyName(distributorRegister.getCompanyName());
		//主账号
		CreateDistributorCommand cmd = new CreateDistributorCommand();
		cmd.setCompanyName(command.getCompanyName());
		cmd.setPhone(command.getPhone());
		cmd.setWebSite(command.getWebSite());
		cmd.setStatus(command.getStatus());
		cmd.setLinkMan(command.getName());
		cmd.setCheckStatus(command.getCheckStatus());
		cmd.setFirstLetter(CharacterUtil.getFirstDistrobutorSpell(command.getCompanyName()));
		distributor =createDistributor(cmd,i);
		return distributorUserDAO.save(new DistributorUserManager(user).createDistributorUser(command,distributor).get());
	
		
	}
	
	private Distributor createDistributor(CreateDistributorCommand command,int i) {
		Distributor distributor = new Distributor();
		command.setCode(getDistributorCode(i)); //设置商户编号
		command.setSignKey(PinYinUtil.getSignkey((command.getCode()+command.getCompanyName())));
		command.setDiscountType(CreateDistributorCommand.DISCOUNT_TYPE_REBATE);
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
	 * 获取经销商编号
	 * @author Caihuan
	 * @date   2016年6月7日
	 */
	private String getDistributorCode(int index)
	{
		StringBuilder sb = new StringBuilder("A");
		String num = String.valueOf(getDistributorNum()+index);
		int length = 8-num.length();
		for(int i=0;i<length;i++)
		{
			sb.append("0");
		}
		sb.append(num);
		return sb.toString();
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
	 * 检查手机号是否存在
	 * (包括正式商户和待审商户记录不包括已拒绝的商户记录)
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public boolean checkExistPhone(String phone) {
		//在申请表中查询是否有重复手机号(排除已通过)
		DistributorRegisterQO distributorRegisterQO = new DistributorRegisterQO();
		distributorRegisterQO.setNotStatus(DistributorRegister.DISTRIBUTOR_REGISTER_CHECK_FAIL);
		distributorRegisterQO.setPhone(phone);
		DistributorRegister distributorRegister = distributorRegisterDAO.queryFirst(distributorRegisterQO);
		if(distributorRegister!=null)
			return true;
		//在商户表中查询是否重复手机号
		DistributorQO distributorQO = new DistributorQO();
		distributorQO.setPhone(phone);
		Distributor distributor = distributorDAO.queryFirst(distributorQO);
		if(distributor!=null)
			return true;
		return false;
	}

	/**
	 * 检查帐号是否存在
	 * @author zqq
	 * @date   2016年7月14日
	 */
	@Override
	public boolean checkExistAccount(String account) {
		//在申请表中查询是否有重复帐号(排除已通过)
		DistributorRegisterQO distributorRegisterQO = new DistributorRegisterQO();
		distributorRegisterQO.setNotStatus(DistributorRegister.DISTRIBUTOR_REGISTER_CHECK_FAIL);
		distributorRegisterQO.setLoginName(account);
		DistributorRegister distributorRegister = distributorRegisterDAO.queryFirst(distributorRegisterQO);
		if(distributorRegister!=null)
			return true;
		//在正式商户帐号重复帐号(排除已通过)
		DistributorUserQO distributorUserQO = new DistributorUserQO();
		distributorUserQO.setEqAccount(account);
		DistributorUser distributorUser = distributorUserDAO.queryFirst(distributorUserQO);
		if(distributorUser!=null)
			return true;
		return false;
	}

}
