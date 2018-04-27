/**
 * @文件名称：UserDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：
 * @作者：xinglj
 * @时间：2014年10月31日上午10:14:08
 */
package hgtech.jfadmin.dao.imp;

import java.util.ArrayList;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hg.hjf.app.dao.operationlog.OperationLogDao;
import hg.hjf.app.service.sys.SysControlService;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountUK;
import hgtech.jfaccount.dao.AccountDao;
import hgtech.jfadmin.dto.*;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：账户dao
 * @类修改者：
 * @修改日期：2014年10月31日上午10:14:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月31日上午10:14:08
 *
 */
@SuppressWarnings("rawtypes")
@Repository("accountDao")
public class AccountDaoImp extends BaseEntityHiberDao<JfAccountUK, JfAccount> implements AccountDao{
	
	@Autowired
	OperationLogDao operationLogDao;

	
	public AccountDaoImp(){
		super(JfAccount .class);	
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.dao.imp.BaseEntityHiberDao#getHiberObject(hgtech.jf.entity.EntityUK)
	 */
	@Override
	protected JfAccount getHiberObject(JfAccountUK uk) {
		JfAccountUK juk=(JfAccountUK) uk;
		JfAccount r = (JfAccount) getSession().createQuery(
				"from "+JfAccount.class.getName()+" o where o.user='"+juk.user.code+"' and o.acct_type='"+juk.type.getCode()+"'")
				.uniqueResult();
		
		return r;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jfadmin.dao.imp.BaseEntityHiberDao#beforeSaveEntity(java.lang.Object)
	 */
	@Override
	protected void beforeSaveEntity(JfAccount pojo) {
		JfAccount acc=(JfAccount) pojo;
		if(acc.id==null)
		acc.id=UUIDGenerator.getUUID();
//		pojo.syncUK();
	}

    /**
     * 积分分页查询实现类
     */
	@Override
	public Pagination findPagination(Pagination pagination) {
		try {
			Criteria criteria=super.getSession().createCriteria(JfAccount.class);
			if(pagination.getCondition() != null && pagination.getCondition() instanceof showDto){
				showDto condition = (showDto) pagination.getCondition();
				if(null != condition.getCode()   && !"".equals(condition.getCode())){
					criteria.add(Restrictions.eq("user", condition.getCode()));
				}
				
				if(null !=condition.getType() && !"".equals(condition.getType())){
					criteria.add(Restrictions.eq("acct_type",condition.getType()));
				}
				
			}	
			return super.findByCriteria(criteria, pagination.getPageNo(), pagination.getPageSize());
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	   /**
	     * 积分 查询实现类
	     */
		@Override
		public List<JfAccount> querybyCode(String userCode) {
			try {
				Criteria criteria=super.getSession().createCriteria(JfAccount.class);
				criteria.add(Restrictions.eq("user", userCode));
					
				return criteria.list();
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				return new ArrayList<>();
		}
		@Override
		public   JfAccount  querybyCode(String userCode,String acctType) {
			try {
				Criteria criteria=super.getSession().createCriteria(JfAccount.class);
				criteria.add(Restrictions.eq("user", userCode));
				criteria.add(Restrictions.eq("acct_type", acctType));
					
				JfAccount uniqueResult = (JfAccount) criteria.uniqueResult();
				if(uniqueResult==null)
					return new JfAccount();
				else
					return uniqueResult;
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			return null;
				 
		}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.dao.AccountDao#hasAny(hgtech.jfaccount.JfAccountType)
	 */
	@Override
	public boolean hasAny(JfAccountType type) {
	    return getSession().createSQLQuery("select 1 from jf_account where EXISTS (select * from jf_account a where a.acct_type='"+ type.getCode()+"')")
	    .list().size()>0;
	}	
	
	@Override
	public void insertEntity(JfAccount en) {
		checkSysStatus();
		super.insertEntity(en);
	}

	/**
	 * xinglj
	 * 检查系统是否允许
	 */
	private void checkSysStatus() {
		/*if(operationLogDao.isDayEnd())
			throw new RuntimeException("正在进行系统日终处理，不能进行账户操作！");*/
	}

	@Override
	public void saveEntity(JfAccount en) {
		checkSysStatus();
		super.saveEntity(en);
	}
}
