/**
 * @文件名称：RuleServiceImp.java
 * @类路径：hgtech.jfadmin.service.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:30:09
 */
package hgtech.jfadmin.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.page.Pagination;
import hgtech.jfadmin.dao.RuleDao;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.service.RuleService;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月22日上午9:30:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月22日上午9:30:09
 *
 */
@Transactional
@Service("ruleService")
public class RuleServiceImp implements RuleService{
	@Resource
	RuleDao ruleDao;

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.RuleService#findPagination(hg.common.page.Pagination)
	 */
	@Override
	public Pagination findPagination(Pagination pagination) {
		return ruleDao.findPagination(pagination);
	}
	
	public void save(RuleHiberEntity  ruleEntity)
	{
		ruleDao.saveRule(ruleEntity);
	}

	@Override
	public void delete(RuleHiberEntity ruleEntity) {
		ruleDao.delete(ruleEntity);
	}

	@Override
	public void update(RuleHiberEntity ruleEntity) {
		ruleDao.update(ruleEntity);
	}

	@Override
	public RuleHiberEntity queryByCode(String code){
		return ruleDao.queryByCode(code);
	}
	
	@Override
	public List<RuleHiberEntity> queryByTemplate(String templateCode)
	{
		return ruleDao.queryByTemplate(templateCode);
	}

	@Override
	public void upgrade(RuleHiberEntity exRule, RuleHiberEntity newRule) {
		 this.update(exRule);
		 this.save(newRule);
	}

	@Override
	public List<RuleHiberEntity> findAll() {
		return ruleDao.findAll();
	}
	
}
