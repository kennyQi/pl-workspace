package hg.log.base;

import hg.common.page.Pagination;
import hg.log.po.base.BaseLog;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public abstract class BaseLogDao<T extends BaseLog, QO extends BaseLogQo> {

	@Autowired
	private MongoOperations mongoOperation;

	/**
	 * 构建Query对象
	 * 
	 * @param query
	 * @return
	 */
	protected abstract Query buildQuery(Query query, QO qo);

	/**
	 * 文档类型
	 * 
	 * @return
	 */
	protected abstract Class<T> getDocumentClass();
	
	
	protected String escapeLikeParam(String param) {
		if (param == null) return null;
		String _param = param
				.replace("\\", "\\\\")
				.replace("[", "\\[")
				.replace("]", "\\]")
				.replace("{", "\\{")
				.replace("}", "\\}")
				.replace("(", "\\(")
				.replace(")", "\\)")
				.replace(".", "\\.")
				.replace("|", "\\|")
				.replace("+", "\\+")
				.replace("-", "\\-")
				.replace("^", "\\^")
				.replace("$", "\\$")
				;
		return _param;
	}
	
	/**
	 * 根据ID获取
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id) {
		if (id == null)
			return null;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoOperation.findOne(query, getDocumentClass());
	}

	private Query buildQuery(QO qo) {
		Query query = new Query();
		if (qo.getInclude() != null && qo.getInclude().length > 0)
			for (String key : qo.getInclude())
				query.fields().include(key);
		if (qo.getExclude() != null && qo.getExclude().length > 0)
			for (String key : qo.getExclude())
				query.fields().exclude(key);
		if (qo != null) {
			// ID
			if (StringUtils.isNotBlank(qo.getId())) {
				query.addCriteria(Criteria.where("id").is(qo.getId()));
			}
			// 项目id
			if (StringUtils.isNotBlank(qo.getProjectId())) {
				query.addCriteria(Criteria.where("projectId").is(qo.getProjectId()));
			}
			// 环境id
			if (StringUtils.isNotBlank(qo.getEnvId())) {
				query.addCriteria(Criteria.where("envId").is(qo.getEnvId()));
			}
			// 创建时间
			if (qo.getCreateDateDesc() != null) {
				Sort.Order order = new Sort.Order(
						qo.getCreateDateDesc() ? Direction.DESC : Direction.ASC, "createDate");
				query.with(new Sort(order));
			}
			Criteria createDateCriteria = Criteria.where("createDate");
			if (qo.getCreateDateBegin() != null) {
				createDateCriteria.gte(qo.getCreateDateBegin());
			}
			if (qo.getCreateDateEnd() != null) {
				createDateCriteria.lte(qo.getCreateDateEnd());
			}
			if (qo.getCreateDateBegin() != null || qo.getCreateDateEnd() != null) {
				query.addCriteria(createDateCriteria);
			}
			// 搜索关键字
			if (qo.getTags() != null && qo.getTags().length > 0) {
				query.addCriteria(Criteria.where("tags").all((Object[]) qo.getTags()));
			}
		}
		return buildQuery(query, qo);
	}

	/**
	 * 查询唯一
	 * 
	 * @param qo
	 * @return
	 */
	public T queryUnique(QO qo) {
		return mongoOperation.findOne(buildQuery(qo), getDocumentClass());
	}

	/**
	 * 查询列表
	 * 
	 * @param qo
	 * @return
	 */
	public List<T> queryList(QO qo) {
		return mongoOperation.find(buildQuery(qo), getDocumentClass());
	}


	/**
	 * 分页查询
	 * 
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {

		if (pagination.getCondition() == null || !(pagination.getCondition() instanceof BaseLogQo)) {
			pagination.setCondition(new BaseLogQo());
		}

		Query query = buildQuery((QO) pagination.getCondition());

		Long totalCount = 0L;
		if (pagination.isSelectTotalCount())
			totalCount = mongoOperation.count(query, getDocumentClass());
		else
			totalCount = (long) Integer.MAX_VALUE;
		
		pagination.setTotalCount(totalCount.intValue());

		query.skip(pagination.getFirstResult());
		query.limit(pagination.getPageSize());

		List<T> list = mongoOperation.find(query, getDocumentClass());
		pagination.setList(list);
		
		return pagination;
	}

}
