package hg.fx.spi;

import java.util.List;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.domain.Channel;
import hg.fx.spi.qo.ChannelSQO;

/**
 * @类功能说明：渠道SPI
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午9:47:08
 * @版本： V1.0
 */
public interface ChannelSPI extends BaseServiceProviderInterface {
	
	Channel create();

	/**
	 * 
	 * @方法功能说明：查询唯一渠道记录
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月1日 上午10:00:14
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	Channel queryUnique(ChannelSQO sqo);

	/**
	 * 
	 * @方法功能说明：条件查询渠道记录
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月1日 上午10:00:30
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	List<Channel> queryList(ChannelSQO sqo);

	/**
	 * 
	 * @方法功能说明：条件分页查询渠道记录
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月1日 上午10:01:19
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	Pagination<Channel> queryPagination(ChannelSQO sqo);

}
