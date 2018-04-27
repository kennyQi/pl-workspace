package hg.hjf.app.dao.grade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.hjf.domain.model.grade.GradeQo;
import hgtech.jf.piaol.PiaolAdjustBean;
import hgtech.jfaccount.AdjustBean;
import hgtech.jfaccount.Grade;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfUser;
import hgtech.jfaccount.service.AccountService;
import hgtech.jfadmin.dao.UserDao;


@Repository
public class GradeDao extends BaseDao<Grade, GradeQo> {

	@Override
	protected Criteria buildCriteria(Criteria cr, GradeQo qo) {
		if (StringUtils.isNotBlank(qo.getCode()))
			cr.add(Restrictions.eq("code", qo.getCode()));
		cr.addOrder(Order.asc("jf"));
		return cr;
	}

	@Override
	protected Class<Grade> getEntityClass() {
		return Grade.class;
	}

	@Autowired
	UserDao userDao;

	@Autowired
	AccountService accountService;
	/**
	 * 处理会员升降机
	 */
	public void handleUserGrade() {
		
		minuJfEndDate();
		
		autoUpDown();
	}
	
	/**
	 * 处理升级和降级
	 */
	@SuppressWarnings("unchecked")
	protected void autoUpDown() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		
		int upperJf = -1;
		int upCnt = 0,downCnt=0;
		//级别倒叙排序
		for (Object g : getSession().createQuery("from " + getEntityClass().getName() + " o order by o.jf desc").list()) {
			Grade gra = (Grade) g;
			// 设置等级上线
			if (upperJf != -1)
				gra.setToJf(upperJf);
		
			// 属于本级别但目前不是本级别的用户
			List<JfUser> list = getSession()
					.createQuery(
					"select u from "
					+ JfAccount.class.getName()
					+ " o,JfUser u  where o.user=u.code and  o.acct_type=? and (u.gradeCode is null or u.gradeCode != ?) and o.jf >="
					+ gra.getJf() + " and o.jf<" + gra.getToJf()).setParameter(0, gra.getAccountType()).setParameter(1, gra.getCode()).list();
			list.addAll(getSession()
				.createQuery("select u from "
						+ JfAccount.class.getName()
						+ " o,JfUser u  where o.user=u.code and  o.acct_type=? and u.gradeInvalidDate =? and u.gradeCode = '"+ gra.getCode() +"' and o.jf >="
					+ gra.getJf() + " and o.jf<" + gra.getToJf())
				.setString(0, gra.getAccountType()).setString(1, today).list());
			for(JfUser u:list) {
		
				Grade oldG = u.getGradeCode() == null ? null : queryUnique(new GradeQo(u.getGradeCode()));
		
				boolean upGrade =false,downGrade =false;
				if(oldG == null )
					upGrade =true;
				else if (oldG.getJf() < gra.getJf())
					upGrade = true;
				else if(oldG.getJf() > gra.getJf()
						//可以当日升级，但级别失效期日才可以降级
						&& u.getGradeInvalidDate().equals(today))
					downGrade = true;
					
				if(upGrade){
					upCnt ++;
					System.out.println("user " + u.getCode() + ( " 升级" ));
				}else if(downGrade){
					downCnt++;
					System.out.println("user " + u.getCode() + ( " 降级" ));
				}
				
				if(upGrade || downGrade){
					u.gradeCode = gra.getCode();
					if(oldG!=null)
						u.oldGradeCode = oldG.getCode();
					
					Calendar validC = Calendar.getInstance();
					validC.add(Calendar.YEAR, gra.getValidYear());
					u.gradeInvalidDate =sdf.format( validC.getTime());
					u.gradeUpdateDate = today;
					userDao.saveEntity(u);
				}else{
					//不升不降，但已经是失效日了，需要设定失效日
					if(today.equals(u.getGradeInvalidDate())){
						Calendar validC = Calendar.getInstance();
						validC.add(Calendar.YEAR, gra.getValidYear());
						u.gradeInvalidDate =sdf.format( validC.getTime());
						u.gradeUpdateDate = today;
						userDao.saveEntity(u);
					}
				}
				
			}
		
			upperJf = gra.getJf();
		}
		System.out.println(String.format("升级人数%d，降级人数%d",upCnt,downCnt));
	}
	
	private void minuJfEndDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		
		int upCnt = 0,downCnt=0;
		 Iterator iterate = getSession()
					.createQuery("from JfUser where gradeInvalidDate=?")
									.setParameter(0, today).iterate();
			while (iterate.hasNext()) {
				JfUser u = (JfUser) iterate.next();
				Grade oldG = u.getGradeCode() == null ? null : queryUnique(new GradeQo(u.getGradeCode()));
		
				//扣减点积分
				{	
					
					PiaolAdjustBean cal = new PiaolAdjustBean();
					cal.bean = new AdjustBean();
					cal.bean.userCode=u.getCode().getS();
					cal.bean.accountTypeId=oldG.getAccountType();
					cal.bean.jf = -oldG.getInvalidJf();
					cal.bean.remark=String.format("等级到期扣减积分。到期等级%s，扣除积分值%s", oldG.getName(),oldG.getInvalidJf()+"");
					accountService.adjust(cal, -1);
				}
			}
		
	}
	 
	/**
	 * 级别到期日检查是否降级
	 */
	 	

}
