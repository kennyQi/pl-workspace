/**
 * @文件名称：RuleSessionDao.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月24日下午12:37:39
 */
package hgtech.jfadmin.dao.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hg.common.component.hibernate.HibernateSimpleDao;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.hibernate.SessionHiberEntity;
import hgtech.jfadmin.util.RuleUtil;
import hgtech.jfcal.dao.RuledSession;
import hgtech.jfcal.model.DataRow;

/**
 * @类功能说明：规则session的hibernate实现
 * @类修改者：
 * @修改日期：2014年10月24日下午12:37:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月24日下午12:37:39
 *
 */
public class RuleSessionHiberImp extends HibernateSimpleDao implements RuledSession{

	String rule;
	/**
	 * @类名：RuleSessionDao.java
	 * @描述：hibernate实现的session dao
	 * @@param rule
	 */
	public RuleSessionHiberImp(String rule2) {
		super();
		this.rule = rule2;
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.RuledSession#getRuleCode()
	 */
	@Override
	public String getRuleCode() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.RuledSession#clear()
	 */
	@Override
	public void clear() {
		
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.RuledSession#get(java.lang.String)
	 */
	@Override
	public DataRow get(String user) {
		Criteria criteria=super.getSession().createCriteria(SessionHiberEntity.class);
		criteria.add(Restrictions.eq("ruleCode", rule));
		criteria.add(Restrictions.eq("userCode",user));
		String props;
		SessionHiberEntity se=(SessionHiberEntity) criteria.uniqueResult();
		if(se==null)
			return null;
		props=se.getProps();
		Map p=(Map) JSONObject.parseObject(props, Map.class);
		DataRow dara = new DataRow();
		dara.row.putAll(p);
		getSession().evict(se);
		return dara;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.RuledSession#put(java.lang.String, hgtech.jfcal.model.DataRow)
	 */
	@Override
	public void put(String user, DataRow row) {
		String p=JSONObject.toJSONString(row.row,SerializerFeature.WriteClassName);
		SessionHiberEntity en= new SessionHiberEntity();
		en.setProps(p);
		en.setRuleCode(rule);
		en.setUserCode(user);
		super.getSession().saveOrUpdate(en);
		getSession().flush();
		getSession().evict(en);
	}

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("age", 40);
		map.put("birth", new Date());
		String s=JSONObject.toJSONString(map, SerializerFeature.WriteClassName);
		System.out.println(s);
		
		 Map map2=JSONObject.parseObject(s, Map.class);
		 System.out.println(map2);
	}
}
