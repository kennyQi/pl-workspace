package slfx.jp.spi.inter;

import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;

/**
 * 
 * @类功能说明：退废票接口SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:10:30
 * @版本：V1.0
 *
 */
public interface JPPlatformApplyRefundService {

	public YGApplyRefundDTO applyRefund(YGApplyRefundCommand command);
	
	public ABEDeletePnrDTO deletePnr(ABEDeletePnrCommand deleteCommand);
}