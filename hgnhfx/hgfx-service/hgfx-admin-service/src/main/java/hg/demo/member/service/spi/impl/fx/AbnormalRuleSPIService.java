package hg.demo.member.service.spi.impl.fx;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.cache.AbnormalRuleCache;
import hg.demo.member.service.dao.hibernate.fx.AbnormalRuleDAO;
import hg.demo.member.service.dao.hibernate.fx.NewMileOrderDao;
import hg.demo.member.service.domain.manager.fx.AbnormalRuleManager;
import hg.demo.member.service.qo.hibernate.fx.AbnormalRuleQO;
import hg.demo.member.service.qo.hibernate.fx.NewMileOrderQO;
import hg.demo.member.service.qo.hibernate.fx.ProductQO;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.abnormalRule.CheckAbnormalRuleCommand;
import hg.fx.command.abnormalRule.ModifyAbnormalRuleCommand;
import hg.fx.domain.AbnormalRule;
import hg.fx.domain.MileOrder;
import hg.fx.spi.AbnormalRuleSPI;
import hg.fx.spi.qo.AbnormalRuleSQO;
import hg.fx.util.CodeUtil;
import hg.fx.util.DateUtil;

@Transactional
@Service("abnormalRuleSPIService")
public class AbnormalRuleSPIService extends BaseService implements
		AbnormalRuleSPI {

	@Autowired
	private AbnormalRuleDAO dao;

	@Autowired
	private AbnormalRuleCache cache;

	@Autowired
	private NewMileOrderDao newMileOrderDao;

	private String joinChar = "__";

	@Override
	public List<AbnormalRule> queryList(AbnormalRuleSQO sqo) {
		return dao.queryList(AbnormalRuleQO.bulid(sqo));
	}

	@Override
	public AbnormalRule queryUnique(AbnormalRuleSQO sqo) {
		return dao.queryUnique(AbnormalRuleQO.bulid(sqo));
	}

	@Override
	public AbnormalRule modifyAbnormalRule(ModifyAbnormalRuleCommand command) {
		AbnormalRule rule = dao.get(command.getId());
		dao.update(new AbnormalRuleManager(rule).modifyAbnormalRuleManager(
				command).get());
		// 更新缓存中的规则值
		cache.modifyAbnormalRule(rule);
		return rule;
	}

	@Override
	public String conformOrderToTheRule(CheckAbnormalRuleCommand command) {

		AbnormalRule rule = null;

		// 缓存获取规则值 判断
		// 缓存不存在时 添加
		if (!cache.isExistRule()) {
			rule = dao.queryFirst(new AbnormalRuleQO());
			cache.setAbnormalRule(rule);
			return this.checkRule(rule, command);
		}

		rule = cache.getAbnormalRuleByKey();
		return this.checkRule(rule, command);
	}

	/**
	 * 比较规则
	 * */
	private String checkRule(AbnormalRule rule, CheckAbnormalRuleCommand command) {

		StringBuffer sb = new StringBuffer();
		if (rule.getOrderUnitMax().longValue() < command.getMileageNum().longValue())
			sb.append("超过单笔订单积分上限;");

		// 当日次数
		String today = DateUtil.formatDate2(new Date());
		checkAndPut("DAY",command, today, rule.getDayMax(), "同账号超过每日订单上限次数;", sb,1);

		// 当月次数
		final String tomonth = today.substring(0, 7);
		checkAndPut("MON",command, tomonth, rule.getMouthMax(), "同账号超过每月订单上限次数;", sb,31);
 
		if (sb.length()>0){
			return sb.toString().substring(0, sb.toString().length()-1);
		}else{
			return null;
		}

	}

	/**
	 * 检查旧的值是否超标，并将新的统计值保存
	 * 
	 * @param command
	 *            订单信息
	 * @param dateKey
	 *            表示日期的key
	 * @param max
	 *            最大值
	 * @param errorTip
	 *            错误提示
	 * @param sb
	 *            将错误提示加入的stirngbuffer
	 * @param expireDays 失效天数           
	 * 
	 */
	private void checkAndPut(String keyType, CheckAbnormalRuleCommand command, String dateKey,
			final Integer max, final String errorTip, StringBuffer sb, int expireDays) {
		String key = "HGFXAR"+joinChar +keyType +joinChar+ command.getCardNo() + joinChar + command.getProdId() + joinChar + dateKey;
		 
		final String jedisString = CodeUtil.getJedisString(key);
		if(jedisString == null){
			CodeUtil.setJedis(key,  "1",expireDays);
		}else{
			
			final int oldVal = Integer.parseInt(jedisString);
			if (max <= oldVal) {
				sb.append(errorTip);
			}
			// put new value
			final int newVal = oldVal + 1 ;
			CodeUtil.setJedis(key,  ""+newVal);
		}
	}

	public static void main(String[] args) {
		String today = DateUtil.formatDate2(new Date());
		final String tomonth = today.substring(0, 7);
//		System.out.println(tomonth);
	}
}
