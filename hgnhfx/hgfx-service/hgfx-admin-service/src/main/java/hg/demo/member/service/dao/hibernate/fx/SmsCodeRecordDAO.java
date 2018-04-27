package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.SmsCodeRecordQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.SmsCodeRecord;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:21:51
 * @版本： V1.0
 */
@Repository("smsCodeRecordDAO")
public class SmsCodeRecordDAO extends BaseHibernateDAO<SmsCodeRecord, SmsCodeRecordQO> {

	@Override
	protected Class<SmsCodeRecord> getEntityClass() {
		return SmsCodeRecord.class;
	}

	@Override
	protected void queryEntityComplete(SmsCodeRecordQO qo,
			List<SmsCodeRecord> list) {
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, SmsCodeRecordQO qo) {
		return criteria;
	}

}
