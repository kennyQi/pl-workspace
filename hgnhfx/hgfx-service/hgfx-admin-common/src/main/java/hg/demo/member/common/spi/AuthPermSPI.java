/**
 * @SecuritySPI.java Create on 2016-5-23下午2:18:03
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.common.spi;

import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.spi.command.authPerm.CreateOrModifyAuthPermCommand;
import hg.demo.member.common.spi.command.authPerm.DeleteAuthPermCommand;
import hg.demo.member.common.spi.qo.AuthPermSQO;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;

import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午2:18:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午2:18:03
 * @version：
 */
public interface AuthPermSPI extends BaseServiceProviderInterface {
	/**
	 * @方法功能说明：查询
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-5-25下午2:53:41
	 * @version：
	 * @修改内容：
	 * @参数：@param sqo
	 * @参数：@return
	 * @return:AuthPerm
	 * @throws
	 */
	Pagination<AuthPerm> queryAuthPermPagination(AuthPermSQO sqo);
	List<AuthPerm> queryAuthPerms(AuthPermSQO sqo);
	AuthPerm queryAuthPerm(AuthPermSQO sqo);
	/**
	 * @方法功能说明：添加
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-5-25下午2:53:41
	 * @version：
	 * @修改内容：
	 * @参数：@param sqo
	 * @参数：@return
	 * @return:AuthPerm
	 * @throws
	 */
	AuthPerm create(CreateOrModifyAuthPermCommand command);
	/**
	 * @方法功能说明：修改
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-5-25下午2:53:41
	 * @version：
	 * @修改内容：
	 * @参数：@param sqo
	 * @参数：@return
	 * @return:AuthPerm
	 * @throws
	 */
	AuthPerm modify(CreateOrModifyAuthPermCommand command);
	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-5-25下午2:53:41
	 * @version：
	 * @修改内容：
	 * @参数：@param sqo
	 * @参数：@return
	 * @return:AuthPerm
	 * @throws
	 */
	void delete(DeleteAuthPermCommand command);
}
