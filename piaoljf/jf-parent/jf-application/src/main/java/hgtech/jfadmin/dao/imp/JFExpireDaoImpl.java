/**
 * 
 */
package hgtech.jfadmin.dao.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.hibernate.HibernateSimpleDao;
import hgtech.jf.JfProperty;
import hgtech.jf.piaol.PiaolAdjustBean;
import hgtech.jfaccount.AdjustBean;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.service.AccountService;
import hgtech.jfadmin.dao.JFExpireDao;

/**
 * @author xinglj
 * 
 */
@Repository("jfExpireDao")
public class JFExpireDaoImpl extends HibernateSimpleDao implements JFExpireDao {

		
	private static final int ONE_DAY_TIME = 24*60*60*1000;
	@Autowired
	AccountService accountService;
	
	/**
	 * 
	 * @方法功能说明：查询当日的积分流水
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public List<JfFlow> queryByNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(new Date());
		String hql = "from JfFlow where status='NOR' and invalidDate like '" + d + "%'";
		return super.find(hql);
	}

	/**
	  查询几日内到期流水
	 */
	@Override
	public List<JfFlow> queryExpireFlow(String usercode, int days) {
		 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd1 = sdf.format(System.currentTimeMillis() ) + " 00:00:00";
		String sd2 = sdf.format(System.currentTimeMillis() + days* ONE_DAY_TIME) + " 23:59:59";
			
		String hql =String.format( "from JfFlow where  user='%s' and invalidDate between '%s' and '%s' and status='NOR' order by invalidDate ",
									usercode,sd1,sd2);
		List<JfFlow> find = super.find(hql);
		for(JfFlow f:find){
			f.account.syncUK();
		}
		return find;
	}

	/**
	 * 1:有要过期的，0：没有
	 */
	@Override
	public int queryExpireFlowSize(String userCode, int expireWithinDays) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd1 = sdf.format(System.currentTimeMillis() ) + " 00:00:00";
		String sd2 = sdf.format(System.currentTimeMillis() + expireWithinDays* ONE_DAY_TIME) + " 23:59:59";

		/*try {
			if(sdf.parse(sdf.format(sd1)).getTime()==AccountService.DT_YEAR3000.getTime())
				throw new RuntimeException("请修改3000年是永久有效的bug！");
		} catch (ParseException e) {
			e.printStackTrace();
		}
*/		
		 String sql =String.format( "select 1 from dual where exists ("
				 +"select *"
				+"from JF_FLOW jfflow0_ where jfflow0_.user='%s' and (jfflow0_.invalid_date between '%s' and '%s') and jfflow0_.status='NOR' )",
				userCode,sd1,sd2);
		 return super.getSession().createSQLQuery(sql).list().size();
	}
	
	/**
	 * 
	 * @方法功能说明：更新积分流水为过期
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public void updateFlowExpire(JfFlow jfFlow) {
		super.update(jfFlow);
	}

	/**
	 * 
	 * @方法功能说明：更新用户
	 * @修改者名字：xy
	 * @修改时间：2014年12月04 
	 */
	@Override
	public void updateJFExpire(JfAccount jfAcccount) {
		super.update(jfAcccount);
	}

	/**
	 * 
	 * @方法功能说明： 处理过期积分 
	 * @修改者名字：xy,xinglj
	 * @修改时间：2014年12月04,2015.7.29 
	 */
	@Override
	public void updateJf() {
		 // 查询过期的积分
		List<JfFlow> flowlist = null;
	    flowlist = this.queryByNowDate();
		for (JfFlow f : flowlist) {
			//1. 将过期的积分流水更新为过期
			f.setStatus(JfFlow.EXP); // 过期
			f.setUpdateTime(new Date());		
			f.setCannotConsume();
			this.update(f); // 流水更新为过期
			//2.  插入扣减流水，更新积分...
			int remainAmount = 0;// 剩余积分
			JfAccount account = f.getAccount();
			remainAmount = f.getJf() - f.getUsejf();  
			//如果积分很少了，只能让账户失效为0。否则减去失效分数后账户为负值了
			if(account.getJf()<remainAmount)
				remainAmount = (int) account.getJf();
			
			PiaolAdjustBean jfchange= new PiaolAdjustBean();
			jfchange.bean = new AdjustBean();
			jfchange.bean.jf = -remainAmount;
			jfchange.bean.accountTypeId=account.getAcct_type();
			jfchange.bean.batchNo=f.batchNo;
			jfchange.bean.remark="积分过期";
			jfchange.bean.tradeFlowId=  f.getFlowId().getS();
			jfchange.bean.userCode=account.user;
			// 将过期的积分减去
			if(remainAmount>0){
				accountService.costJf(jfchange, account, JfTradeType.expire,JfProperty.getJfValidYear() /*AccountService.INT_NOFLOWVALID*/);
			}
		}
		System.out.println("处理失效流水条数："+flowlist.size());
	}
}
