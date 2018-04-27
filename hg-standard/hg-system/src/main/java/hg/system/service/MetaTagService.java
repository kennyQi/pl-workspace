package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.seo.CreateMetaTagCommand;
import hg.system.command.seo.DeleteMetaTagCommand;
import hg.system.command.seo.ModifyMetaTagCommand;
import hg.system.model.seo.MetaTag;
import hg.system.qo.MetaTagQo;

/**
 * @类功能说明：META标签服务接口
 * @类修改者：
 * @修改日期：2015-1-23下午4:52:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-23下午4:52:05
 */
public interface MetaTagService extends BaseService<MetaTag, MetaTagQo> {
	
	/**
	 * @方法功能说明：创建META标签
	 * @修改者名字：zhurz
	 * @修改时间：2015-1-23下午4:53:01
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void createMetaTag(CreateMetaTagCommand command);
	
	/**
	 * @方法功能说明：修改META标签
	 * @修改者名字：zhurz
	 * @修改时间：2015-1-23下午4:53:17
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void modifyMetaTag(ModifyMetaTagCommand command);
	
	/**
	 * @方法功能说明：删除META标签
	 * @修改者名字：zhurz
	 * @修改时间：2015-1-23下午4:53:28
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void deleteMetaTag(DeleteMetaTagCommand command);
	
}
