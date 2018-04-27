package slfx.jp.app.dao;


import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.order.JPOrderBack;
import slfx.jp.qo.admin.PlatformOrderBackQO;

/**
 * 
 * @类功能说明：平台退废订单DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午4:41:04
 * @版本：V1.0
 *
 */
@Repository
public class JPOrderBackDAO extends BaseDao<JPOrderBack, PlatformOrderBackQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, PlatformOrderBackQO qo) {
		return criteria;
	}

	@Override
	protected Class<JPOrderBack> getEntityClass() {
		return JPOrderBack.class;
	}
}
