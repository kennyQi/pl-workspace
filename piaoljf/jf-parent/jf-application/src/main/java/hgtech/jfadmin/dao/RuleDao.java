/**
 * @文件名称：RuleDao.java
 * @类路径：hgtech.jfadmin.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:38:47
 */
package hgtech.jfadmin.dao;

import java.util.List;

import hg.common.page.Pagination;
import hgtech.jfadmin.hibernate.RuleHiberEntity;

/**
 * @类功能说明：规则dao
 * @类修改者：
 * @修改日期：2014年10月22日上午9:38:47
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月22日上午9:38:47
 *
 */
public interface RuleDao {

	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月22日上午9:42:54
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	Pagination findPagination(Pagination pagination);
	
	public void saveRule(RuleHiberEntity ruleEntity);
	
	public void delete(RuleHiberEntity ruleEntity) ;
	
	public void update(RuleHiberEntity ruleEntity);
	
	public RuleHiberEntity queryByCode(String code);
	
	public List<RuleHiberEntity> queryByTemplate(String templateCode);
	
	public void upgrade (RuleHiberEntity exRule,RuleHiberEntity newRule);

	public List<RuleHiberEntity> findAll();



}
