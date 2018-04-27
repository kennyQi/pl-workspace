package plfx.jp.app.service.spi.pay.balances;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.util.EntityConvertUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.service.local.pay.balances.PayBalancesLocalService;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.command.pay.balances.CreatePayBalancesCommand;
import plfx.jp.command.pay.balances.DeletePayBalancesCommand;
import plfx.jp.command.pay.balances.UpdatePayBalancesCommand;
import plfx.jp.domain.model.pay.balances.PayBalances;
import plfx.jp.pojo.dto.pay.balances.PayBalancesDTO;
import plfx.jp.qo.pay.balances.PayBalancesQO;
import plfx.jp.spi.inter.pay.balances.PayBalancesService;
import plfx.jp.spi.inter.sms.SmsService;


@Service("payBalancesService")
public class PayBalancesServiceImpl extends BaseJpSpiServiceImpl<PayBalancesDTO, PayBalancesQO, PayBalancesLocalService>  implements PayBalancesService{

	@Autowired
	private PayBalancesLocalService payBalancesLocalService;
	
	@Autowired
	private SmsService smsService;
	
	@Override
	protected PayBalancesLocalService getService() {
		return payBalancesLocalService;
	}

	@Override
	protected Class<PayBalancesDTO> getDTOClass() {
		return PayBalancesDTO.class;
	}

	@Override
	public boolean updatePayBalances(PayBalancesDTO payBalancesDTO) {
		boolean bool = false;
		try{
//			//如果余额小于预警余额，发送短信
//			if(payBalancesDTO.getBalances() <= payBalancesDTO.getWarnBalances()){
//				String mobile = SysProperties.getInstance().get("smsMobile");//18626890576
//				HgLogger.getInstance().info("yuqz", "国内机票发送预警短信号码=" + mobile);
//				String msg = "【汇购科技】国内机票支付宝余额不足，余额为" + payBalancesDTO.getBalances();
//				String result = smsService.sendSms(mobile, msg);
//				HgLogger.getInstance().info("yuqz", "国内机票发送预警短信返回状态" + result);
//			}
			PayBalances p = EntityConvertUtils.convertDtoToEntity(payBalancesDTO, PayBalances.class);
			payBalancesLocalService.update(p);
			HgLogger.getInstance().info("yuqz", "国内机票生成订单更新余额成功");
			bool = true;
		}catch(Exception e){
			bool = false;
		}
		return bool;
	}

	@Override
	public boolean warnPayBalances(PayBalancesDTO payBalancesDTO) {
		boolean bool = false;
		try{
			//如果余额小于预警余额，发送短信
			if(payBalancesDTO.getBalances() <= payBalancesDTO.getWarnBalances()){
				String mobile = SysProperties.getInstance().get("smsMobile");//18626890576
				String msg = "【汇购科技】国内机票支付宝余额不足，余额为" + payBalancesDTO.getBalances();
				String result = smsService.sendSms(mobile, msg);
				HgLogger.getInstance().info("yuqz", "国内机票发送预警短信返回状态" + result);
			}
			PayBalances p = EntityConvertUtils.convertDtoToEntity(payBalancesDTO, PayBalances.class);
			payBalancesLocalService.update(p);
			HgLogger.getInstance().info("yuqz", "国内机票生成订单更新余额成功");
			bool = true;
		}catch(Exception e){
			HgLogger.getInstance().info("yuqz", "国内机票生成订单更新余额异常:" + HgLogger.getStackTrace(e));
			bool = false;
		}
		return bool;
	}

	@Override
	public void createPayBalances(CreatePayBalancesCommand command) {
		try{
			PayBalances payBalances = new PayBalances(command);
			payBalancesLocalService.save(payBalances);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean adminUpdatePayBalances(UpdatePayBalancesCommand command) {
		boolean bool = false;
		try{
			PayBalancesQO payBalancesQO = new PayBalancesQO();
			payBalancesQO.setId(command.getId());
			PayBalances payBalances = payBalancesLocalService.queryUnique(payBalancesQO);
			payBalances.setType(command.getType());
			payBalances.setBalances(command.getBalances());
			payBalances.setWarnBalances(command.getWarnBalances());
			payBalancesLocalService.update(payBalances);
			bool = true;
		}catch(Exception e){
			HgLogger.getInstance().info("yuqz", "更新余额异常:" + HgLogger.getStackTrace(e));
			bool = false;
		}
		return bool;
	}

	@Override
	public boolean deletePayBalances(DeletePayBalancesCommand command) {
		boolean bool = false;
		try{
			payBalancesLocalService.deleteById(command.getId());
			bool = true;
		}catch(Exception e){
			HgLogger.getInstance().info("yuqz", "删除余额异常:" + HgLogger.getStackTrace(e));
			bool = false;
		}
		return bool;
	}

}
