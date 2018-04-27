package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.command.reserveInfo.CreateReserveInfoCommand;
import hg.fx.command.reserveInfo.ModifyReserveInfoCommand;
import hg.fx.command.reserveRecord.ChargeUpdateReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateChargeReserveRecordCommand;
import hg.fx.domain.MileOrder;
import hg.fx.domain.ReserveInfo;

/**
 * 备付金spi
 * @author xinglj
 *
 */
public interface ReserveInfoSPI extends BaseServiceProviderInterface {
	/**
	 * 冻结备付金
	 * @param distributorId
	 * @param amount
	 * @return 错误信息 or null
	 */
	public String freeze(MileOrder order);
	/**
	 * 冻结完成
	 * @param order
	 */
	public void finishFreeze(MileOrder order);
	/**
	 * 冻结取消
	 * @param order
	 */
	public void cancelFreeze(MileOrder order);
	/**
	 * 商户自助充值。一步到位的充值备付金。更新备付金账户，插入变动明细
	 * @param cr
	 */
	public void onlineCharge(CreateChargeReserveRecordCommand cr);
	
	/**
	 * 审核备付金。更新备付金账户余额，更新变动明细状态为已审核
	 * @author zqq
	 * @param cr
	 */
	public void auditAndUpdateReserve(ChargeUpdateReserveRecordCommand cr);
	
	/**
	 * 创建备付金账户
	 * @param command
	 * @return
	 */
	public ReserveInfo create(CreateReserveInfoCommand command);
	
	/**
	 * 
	 * @方法功能说明：修改里程预警
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月3日 下午6:09:53
	 * @修改内容：
	 * @param id
	 * @param warnValue
	 * @return
	 */
	public ReserveInfo modifyWarnValue(String id, Integer warnValue);
	/**
	 * 更新可欠费里程
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-6-4 下午7:03:20 
	 * @param cmd
	 * @return
	 */
	public ReserveInfo modifyArrearsAmount(ModifyReserveInfoCommand cmd);
	/**更新备付金余额
	 * @param distributorID 商户id
	 * @param count 要增加的数量
	 */
	public abstract void updateAccount(final String distributorID, Long count);
}
