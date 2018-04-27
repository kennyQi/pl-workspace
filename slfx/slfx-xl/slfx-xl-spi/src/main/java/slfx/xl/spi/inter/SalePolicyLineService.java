package slfx.xl.spi.inter;

import slfx.xl.pojo.command.salepolicy.CancelSalePolicyCommand;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyCommand;
import slfx.xl.pojo.command.salepolicy.IssueSalePolicyCommand;
import slfx.xl.pojo.dto.salepolicy.SalePolicyLineDTO;
import slfx.xl.pojo.qo.SalePolicyLineQO;

/**
 * @类功能说明：价格政策外层Service接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月18日上午10:06:44
 * @版本：V1.0
 */
public interface SalePolicyLineService extends BaseXlSpiService<SalePolicyLineDTO, SalePolicyLineQO> {
	/**
	 * @方法功能说明：创建价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 */
	public SalePolicyLineDTO create(CreateSalePolicyCommand command);
	
	/**
	 * @方法功能说明：发布价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 */
	public boolean isssue(IssueSalePolicyCommand command);
	
	/**
	 * @方法功能说明：取消价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 */
	public boolean cancel(CancelSalePolicyCommand command);
	
	/**
	 * @方法功能说明：更新价格政策状态
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param policyQo
	 */
	public boolean updateStatus(SalePolicyLineQO policyQo);
	
	/**
	 * 
	 * @方法功能说明：定时开始价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午1:41:29
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void startSalePolicy();
	
	/**
	 * 
	 * @方法功能说明：定时下架价格政策
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午1:42:02
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void downSalePolicy();
	
	/**
	 * 
	 * @方法功能说明：查询价格政策详情
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月4日下午5:50:31
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public SalePolicyLineDTO querySalePolicyDetail(SalePolicyLineQO salePolicyLineQO);
}