package hsl.app.dao;

import hg.log.base.BaseLogDao;
import hg.log.po.normal.HgMail;
import hsl.pojo.qo.mail.MailRecordQo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：邮件记录管理Dao
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 下午5:04:27
 */
@Repository
public class MailRecordDao extends BaseLogDao<HgMail,MailRecordQo> {
	/**
	 * 构建Query对象
	 */
	@Override
	protected Query buildQuery(Query query, MailRecordQo qo) {
		if(null == qo)
			return query;
		
		//添加查询条件
		if(null != qo.isSSL())
			query.addCriteria(Criteria.where("isSSL").is(qo.isSSL()));
		if(!StringUtils.isBlank(qo.getHost()))
			query.addCriteria(Criteria.where("host").is(qo.getHost()));
		if(!StringUtils.isBlank(qo.getProtocol()))
			query.addCriteria(Criteria.where("protocol").is(qo.getProtocol()));
		if(!StringUtils.isBlank(qo.getUserName()))
			query.addCriteria(Criteria.where("userName").regex(qo.getUserName()));//模糊查询
		if(!StringUtils.isBlank(qo.getTitle()))
			query.addCriteria(Criteria.where("title").regex(qo.getTitle()));//模糊查询
		if(!StringUtils.isBlank(qo.getSender()))
			query.addCriteria(Criteria.where("sender").is(qo.getSender()));
		if(qo.getStatus() >= 0)
			query.addCriteria(Criteria.where("status").is(qo.getStatus()));
		if(null != qo.getAccept() && !StringUtils.isBlank(qo.getAccept().get(0)))
			query.addCriteria(Criteria.where("accept").in(qo.getAccept()));
		//返回查询对象
		return query;
	}
	
	/**
	 * 获取实体(文档)类型
	 */
	@Override
	protected Class<HgMail> getDocumentClass() {
		return HgMail.class;
	}
}