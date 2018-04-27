package hsl.app.service.local.account;

import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;
import hsl.app.dao.account.AccountDao;
import hsl.app.dao.account.HoldUserSnapshotDao;
import hsl.app.dao.account.PayCodeDao;
import hsl.domain.model.user.User;
import hsl.domain.model.user.account.Account;
import hsl.domain.model.user.account.GrantCodeRecord;
import hsl.domain.model.user.account.HoldUserSnapshot;
import hsl.domain.model.user.account.PayCode;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.PayCodeCommand;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.account.HoldUserSnapshotQO;
import hsl.pojo.qo.account.PayCodeQO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class PayCodeLocalService extends BaseServiceImpl<PayCode,PayCodeQO, PayCodeDao>{
	@Autowired
	private PayCodeDao payCodeDao;
	@Autowired
	private HoldUserSnapshotDao holdUserSnapshotDao;
	@Autowired
	private AccountDao accountDao;
	@Override
	protected PayCodeDao getDao() {
		return payCodeDao;
	}
	/**
	 * @throws Exception 
	 * @throws AccountException 
	 * 
	 * @方法功能说明：兑换码充值
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-2上午9:37:16
	 * @参数：@param payCodeCommand
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String recharge(PayCodeCommand payCodeCommand) throws Exception {
		HgLogger.getInstance().info("zhaows","recharge---兑换码充值参数" + JSON.toJSONString(payCodeCommand));
		HoldUserSnapshot snapshot=new HoldUserSnapshot();
		/*************保存卡券持有用户信息*****************/
		String code=payCodeCommand.getCode();//充值码
		PayCodeQO payCodeQO=new PayCodeQO();
		payCodeQO.setCode(code);
		HgLogger.getInstance().info("zhaows","recharge---查询充值码是否可用参数" + JSON.toJSONString(payCodeQO));
		PayCode payCode=payCodeDao.queryUnique(payCodeQO);
		HgLogger.getInstance().info("zhaows","recharge---返回查询充值码是否可用" + JSON.toJSONString(payCode));
		if(payCode!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String price=payCode.getPrice().toString();
			String id=payCode.getId();
			Date date=payCode.getCreateDate();
			String md5=MD5Security.md5(id+price+sdf.format(date));
			if(!(payCode.getGrantCodeRecord().getStatus().equals(GrantCodeRecord.GRANT_CODE_RECORD_CHECKED))||!md5.equalsIgnoreCase(code)){
				throw new AccountException(AccountException.NOT_AVAILABLE,"充值码不可用");
			}else if(payCode.getHoldUserSnapshot()!=null){
				throw new AccountException(AccountException.ALREADY_PAYCODE,"充值码已充值");
			}else{
				HoldUserSnapshotQO holdUserSnapshotQO=new HoldUserSnapshotQO();
				holdUserSnapshotQO.setUserId(payCodeCommand.getHoldUserSnapshotCommand().getUserId());
				HgLogger.getInstance().info("zhaows","recharge---查询卡券持有用户快照参数" + JSON.toJSONString(holdUserSnapshotQO));
				HoldUserSnapshot holdUserSnapshot=holdUserSnapshotDao.queryUnique(holdUserSnapshotQO);
				HgLogger.getInstance().info("zhaows","recharge---返回卡券持有用户快照" + JSON.toJSONString(holdUserSnapshot));
				/**********判断用户是否存在*********/
				if(holdUserSnapshot==null){
					snapshot.create(payCodeCommand.getHoldUserSnapshotCommand());
					payCode.setHoldUserSnapshot(snapshot);
					holdUserSnapshotDao.save(snapshot);
				}else{
					payCode.setHoldUserSnapshot(holdUserSnapshot);
				}
				AccountQO accountQO=new AccountQO();
				accountQO.setUserID(payCodeCommand.getHoldUserSnapshotCommand().getUserId());
				HgLogger.getInstance().info("zhaows","recharge---查询账户参数" + JSON.toJSONString(accountQO));
				Account account=accountDao.queryUnique(accountQO);//查询出帐号信息
				HgLogger.getInstance().info("zhaows","recharge---查询账户返回数据" + JSON.toJSONString(account));
				if(account!=null){//帐户存在修改余额
					account.setBalance(account.getBalance()+payCode.getPrice());//当前帐号余额加本次充值金额
					accountDao.update(account);
				}else{//账户不存在创建账户
					Account accounts=new Account(); 
					AccountCommand accountCommand=new AccountCommand();
					accountCommand.setBalance(payCode.getPrice());
					accountCommand.setMoneyOrigin(payCode.getGrantCodeRecord().getBusinessPartners().getCompanyName());//金额来源为合作公司名字
					accounts.create(accountCommand);
					User  user=new User();
					user.setId(payCodeCommand.getHoldUserSnapshotCommand().getUserId());
					accounts.setUser(user);
					accountDao.save(accounts);
				}
				/**********关联用户账户余额*********/
				payCode.setRechargeDate(new Date());
				HgLogger.getInstance().info("zhaows","recharge---关联充值码和用户参数" + JSON.toJSONString(payCode));
				payCodeDao.update(payCode);//关联充值码和用户
			}
			
			
			
		}else{
			throw new AccountException(AccountException.NOT_PAYCODE,"充值码不存在");
		}
		return "success";
	}
	/**
	 * 
	 * @方法功能说明：查询合作伙伴下兑换码列表
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-1下午1:33:25
	 * @参数：@param Pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryPayCodePagination(Pagination pagination){
		HgLogger.getInstance().info("zhaows","queryPayCodePagination---兑换码列表查询参数" + JSON.toJSONString(pagination));
		pagination=payCodeDao.queryPagination(pagination);
		HgLogger.getInstance().info("zhaows","queryPayCodePagination---返回兑换码列表查询列表" + JSON.toJSONString(pagination));
		List<PayCode> listPayCode=(List<PayCode>) pagination.getList();
		List<PayCodeDTO> listPayCodeDTO=new ArrayList<PayCodeDTO>();
		for(PayCode payCode:listPayCode){
			Hibernate.isInitialized(payCode.getHoldUserSnapshot());
			Hibernate.isInitialized(payCode.getGrantCodeRecord());
			PayCodeDTO dto=EntityConvertUtils.convertDtoToEntity(payCode, PayCodeDTO.class);
			listPayCodeDTO.add(dto);
		}
		pagination.setList(listPayCodeDTO);
		return pagination;
	}
}
