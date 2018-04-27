package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.rebate.AduitRebateSetCommand;
import hg.fx.command.rebate.CreateRebateSetCommand;
import hg.fx.command.rebate.ModifyRebateSetCommand;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.spi.qo.RebateSetSQO;

import java.util.List;
/**
 * 返利配置spi
 * @author zqq
 * @date 2016-7-25上午9:53:54
 * @since
 */
public interface RebateSetSPI extends BaseServiceProviderInterface{

	/**
	 * 分页查询返利配置审核记录
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 上午9:58:40 
	 * @param sqo
	 * @return
	 */
	public Pagination<RebateSet> queryAduitPagination(RebateSetSQO sqo);
	/**
	 * List查询返利配置审核记录
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 上午9:58:40 
	 * @param sqo
	 * @return
	 */
	public List<RebateSet> queryAduitList(RebateSetSQO sqo);
	/**
	 * 单个查询
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 上午10:05:04 
	 * @param sqo
	 * @return
	 */
	public RebateSet queryUnique(RebateSetSQO sqo);
	
	/**
	 * 根据当前配置id查询上月配置信息
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 上午10:34:57 
	 * @param rebateSetId
	 * @return
	 */
	public RebateSet queryRelativeSetById(String rebateSetId);
	/**
	 * 审核返利配置
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 上午10:41:17 
	 * @param cmd
	 * @param isPass true-通过;false-拒绝
	 */
	public void aduitRebateSet(AduitRebateSetCommand cmd , boolean isPass);
	/**
	 * 查询当前月配置
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午1:50:14 
	 * @return
	 */
	public Pagination<RebateSet> queryCurrMonthSet(RebateSetSQO sqo);
	/**
	 * 查询次月配置
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午1:50:29 
	 * @return
	 */
	public Pagination<RebateSet> queryNextMonthSet(RebateSetSQO sqo);
	/**
	 * 创建返利配置申请
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午2:44:18 
	 * @param cmd
	 */
	public void createApplyRebateSet(CreateRebateSetCommand cmd);
	/**
	 * 创建默认返利配置
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午2:44:18 
	 * @param cmd
	 */
	public void createDefaultRebateSet(CreateRebateSetCommand cmd);
	/**
	 * 修改默认返利配置
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-26 下午7:08:14 
	 * @param cmd
	 */
	public void modifyDefaultRebateSet(ModifyRebateSetCommand cmd,String id);
}
