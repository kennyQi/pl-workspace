/**
 * @TimeTest.java Create on 2015-6-11上午10:27:24
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.web.task;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015-6-11上午10:27:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2015-6-11上午10:27:24
 * @version：
 */
import hg.captcha.common.HGCaptchaConstants;
import hg.captcha.common.command.SmsCodeCommand;
import hg.captcha.common.dto.HGCaptchaDTO;
import hg.captcha.sdk.HGCaptchaService;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.smsCodeRecord.CreateSmsCodeRecordCommand;
import hg.fx.command.smsCodeRecord.ModifySmsCodeRecordCommand;
import hg.fx.command.warnSmsCodeRecord.CreateWarnSmsRecord;
import hg.fx.domain.Distributor;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.SmsCodeRecordSPI;
import hg.fx.spi.WarnSmsRecordSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.util.SmsUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("warnTask")
public class WarnTask extends EveryDayTask {
	@Autowired
	DistributorSPI distributorSPI;
	@Autowired
	HGCaptchaService captchaService;
	@Autowired
	SmsUtil smsUtil;
	@Autowired
	SmsCodeRecordSPI smsCodeRecordSPI;
	@Autowired
	WarnSmsRecordSPI warnSmsRecordSPI;
	
	private static final String pattern = "yyyy年M月d日H时m分";
	public WarnTask() {
		String getFileTime = (String) TaskProperty.getProperties().get("smsWarnTime");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
//				System.out.println("备付金余额短信预警任务 执行。。。");
				doWarn();
			}
			@Override
			public String toString() {
				return "备付金余额短信预警任务";
			}
		};

		startEveryDayTask(getFileTime, task);
	}
	
	public void doWarn() {
		DistributorSQO qo=new DistributorSQO();
		qo.setStatus(Distributor.STATUS_OF_IN_USE);
		for(Distributor d:distributorSPI.queryList(qo)){
			
			final boolean b = d.getReserveInfo().getUsableBalance().longValue() < d.getReserveInfo().getWarnValue().longValue();
//			System.out.println("warn "+d.getName()+"?"+b);
			if(b){
				final String text = String.format(SmsUtil.SMS_PRE汇购+ "尊敬的汇购积分用户，您好！截止到%s，您的可用积分余额为%s，为了保障您的正常服务，请及时续航积分。",
						new SimpleDateFormat(pattern).format(new Date()),d.getReserveInfo().getUsableBalance()+"");
				try {
					SmsCodeCommand cdm=new SmsCodeCommand();
					cdm.setMobile(d.getPhone());
					cdm.setMsg(text);
					cdm.setSmsUser(smsUtil.getSmsUser());
					cdm.setSmsPassword(smsUtil.getSmsPassword());
					final HGCaptchaDTO captcha = captchaService.getCaptcha(HGCaptchaConstants.HG_CAPTCHA_TYPE_SMS_CODE, cdm);
					//smsUtil.get.sendSms(d.getPhone(), text);
					
					//log 
//					System.out.println("balance warn sms send to :"+d.getPhone()+" "+captcha.getText());
					CreateSmsCodeRecordCommand command = new CreateSmsCodeRecordCommand();
					command.setType(SmsCodeRecord.TYPE_LC_WARN);
					command.setMobile(d.getPhone());
					command.setCreateDate(new Date());
					command.setFromPlatform("系统");
					final SmsCodeRecord create = smsCodeRecordSPI.create(command);
					ModifySmsCodeRecordCommand modi=new ModifySmsCodeRecordCommand();
					modi.setId(create.getId());
					smsCodeRecordSPI.modifyUsedStatus(modi);
					
					//log warn 
					CreateWarnSmsRecord warn = new CreateWarnSmsRecord();
					warn.setContent(cdm.getMsg());
					warn.setDistributor(d);
					warn.setId(UUIDGenerator.getUUID());
					warn.setMobile(cdm.getMobile());
					warn.setSendTime(new Date());
					warnSmsRecordSPI.create(warn);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
//		System.out.println(new SimpleDateFormat(pattern).format(new Date()));
	}
}