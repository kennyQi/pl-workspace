package hsl.app.service.local.coupon;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.CouponTransferRecordDAO;
import hsl.domain.model.coupon.CouponTransferRecord;
import hsl.domain.model.coupon.UserSnapshot;
import hsl.pojo.qo.coupon.CouponTransferRecordQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhurz
 */
@Service
@Transactional
public class CouponTransferRecordLocalService extends BaseServiceImpl<CouponTransferRecord, CouponTransferRecordQO, CouponTransferRecordDAO> {

	@Autowired
	private CouponTransferRecordDAO dao;

	@Override
	protected CouponTransferRecordDAO getDao() {
		return dao;
	}

	/**
	 * 创建一条转赠记录
	 *
	 * @param couponId
	 * @param fromUser
	 * @param toUser
	 */
	public void createRecord(String couponId, UserSnapshot fromUser, UserSnapshot toUser) {
		CouponTransferRecord record = new CouponTransferRecord();
		record.create(couponId, fromUser, toUser);
		getDao().save(record);
	}
}
