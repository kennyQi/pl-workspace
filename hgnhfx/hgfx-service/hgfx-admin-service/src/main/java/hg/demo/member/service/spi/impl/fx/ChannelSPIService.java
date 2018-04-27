package hg.demo.member.service.spi.impl.fx;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hg.demo.member.service.dao.hibernate.fx.ChannelDAO;
import hg.demo.member.service.qo.hibernate.fx.ChannelQO;
import hg.framework.common.model.Pagination;
import hg.framework.service.component.base.BaseService;
import hg.fx.domain.Channel;
import hg.fx.spi.ChannelSPI;
import hg.fx.spi.qo.ChannelSQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午10:28:03
 * @版本： V1.0
 */
@Transactional
@Service("channelSPIService")
public class ChannelSPIService extends BaseService implements ChannelSPI {

	@Autowired
	private ChannelDAO channelDAO;

	@Override
	public Channel create() {
		return null;
	}

	@Override
	public Channel queryUnique(ChannelSQO sqo) {
		return channelDAO.queryUnique(ChannelQO.build(sqo));
	}

	@Override
	public List<Channel> queryList(ChannelSQO sqo) {
		return channelDAO.queryList(ChannelQO.build(sqo));
	}

	@Override
	public Pagination<Channel> queryPagination(ChannelSQO sqo) {
		return channelDAO.queryPagination(ChannelQO.build(sqo));
	}

}
