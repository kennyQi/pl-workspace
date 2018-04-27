/**
 * @文件名称：RuleService.java
 * @类路径：hgtech.jfadmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:27:32
 */
package hgtech.jfadmin.service;

import java.util.List;

import hg.common.page.Pagination;
import hgtech.jfadmin.hibernate.RuleHiberEntity;

/**
 * @类功能说明：规则服务
 * @类修改者：
 * @修改日期：2014年10月22日上午9:27:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月22日上午9:27:32
 *
 */
public interface RuleService {
	/** 获取分页 */
	public abstract Pagination findPagination(Pagination pagination);
	
	public void save(RuleHiberEntity  ruleEntity);
	
	public void delete(RuleHiberEntity ruleEntity) ;
	
	public void update(RuleHiberEntity ruleEntity);
	
	public RuleHiberEntity queryByCode(String code);
	
	public List<RuleHiberEntity> queryByTemplate(String templateCode);
	
	public void upgrade (RuleHiberEntity exRule,RuleHiberEntity newRule);

	
	public List<RuleHiberEntity> findAll();
}
