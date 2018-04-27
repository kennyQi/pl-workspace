package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.SmsCodeRecordQO;
import hg.demo.member.service.qo.hibernate.fx.WarnSmsRecordQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.SmsCodeRecord;
import hg.fx.domain.WarnSmsRecord;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： xinglj
 * @创建时间： 2016年6月1日 下午7:21:51
 * @版本： V1.0
 */
@Repository("warnSmsRecordDAO")
public class WarnSmsRecordDAO extends BaseHibernateDAO<WarnSmsRecord, WarnSmsRecordQO> {

	@Override
	protected Class<WarnSmsRecord> getEntityClass() {
		return WarnSmsRecord.class;
	}

	@Override
	protected void queryEntityComplete(WarnSmsRecordQO qo,
			List<WarnSmsRecord> list) {
				
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, WarnSmsRecordQO qo) {
		return criteria;
	}

	 

}
