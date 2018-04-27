package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.ChannelQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.Channel;
import hg.fx.domain.Product;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午10:21:11
 * @版本： V1.0
 */
@Repository("channelDAO")
public class ChannelDAO extends BaseHibernateDAO<Channel, ChannelQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ChannelQO qo) {
		return criteria;
	}

	@Override
	protected Class<Channel> getEntityClass() {
		return Channel.class;
	}

	@Override
	protected void queryEntityComplete(ChannelQO qo, List<Channel> list) {
		if (qo != null && list != null) {
			if (qo.getNeedProducts() != null && qo.getNeedProducts()) {
				for (Channel c : list) {
					List<Product> products = c.getProducts();
					Hibernate.initialize(products);
				}
			}
		}
	}

}
