package plfx.jp.app.service.local.policy;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.policy.PolicySnapshotDAO;
import plfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import plfx.jp.qo.admin.policy.PolicySnapshotQO;

/**
 * 
 * @类功能说明：LOCAL订单SERVICE(操作数据库)实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:22:06
 * @版本：V1.0
 * 
 */
@Service
@Transactional
public class PolicySnapshotLocalService extends BaseServiceImpl<JPPlatformPolicySnapshot, PolicySnapshotQO, PolicySnapshotDAO> {

	@Autowired
	PolicySnapshotDAO policySnapshotDAO;

	@Override
	protected PolicySnapshotDAO getDao() {
		return policySnapshotDAO;
	}

	/**
	 * @方法功能说明：获取当前时间适用的平台价格政策快照
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-15下午2:02:25
	 * @修改内容：
	 * @参数：@param dealerId
	 * @参数：@param supplierCode
	 * @参数：@return
	 * @return:JPPlatformPolicySnapshot
	 * @throws
	 */
	public JPPlatformPolicySnapshot getPolicySnapshot(String dealerId, String supplierCode) {
		
		return null;
	}

}
