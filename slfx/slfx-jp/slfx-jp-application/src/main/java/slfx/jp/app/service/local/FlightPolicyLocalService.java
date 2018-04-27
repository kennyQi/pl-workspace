package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.policy.PolicySnapshotDAO;
import slfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import slfx.jp.qo.admin.policy.PolicySnapshotQO;

/**
 * 
 * @类功能说明：平台航班政策SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:45:40
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class FlightPolicyLocalService extends BaseServiceImpl<JPPlatformPolicySnapshot, PolicySnapshotQO, PolicySnapshotDAO>{

	@Autowired
	PolicySnapshotDAO dao;
	
	@Override
	protected PolicySnapshotDAO getDao() {
		return dao;
	}
	
}
