package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.ChannelSQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午10:20:06
 * @版本： V1.0
 */
@QOConfig(daoBeanId = "channelDAO")
public class ChannelQO extends BaseHibernateQO<String> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@QOAttr(name = "id", type = QOAttrType.EQ)
	private String id;
	/**
	 * 渠道名
	 */
	@QOAttr(name = "channelName", type = QOAttrType.LIKE_ANYWHERE)
	private String channelName;
	
	/**
	 * 排序
	 */
	@QOAttr(name = "channelName",type = QOAttrType.ORDER)
	private Integer orderChannelName;

	/**
	 * 是否需要List<Product> products
	 */
	private Boolean needProducts;

	public static ChannelQO build(ChannelSQO sqo) {
		ChannelQO qo = new ChannelQO();
		qo.setId(sqo.getId());
		qo.setChannelName(sqo.getChannelName());
		qo.setLimit(sqo.getLimit());
		if(sqo.getNeedProducts()!=null)
			qo.setNeedProducts(sqo.getNeedProducts());
		//默认升序排序
		qo.setOrderChannelName(1);
		return qo;
	}

	public String getId() {
		return id;
	}

	
	public Integer getOrderChannelName() {
		return orderChannelName;
	}

	public void setOrderChannelName(Integer orderChannelName) {
		this.orderChannelName = orderChannelName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Boolean getNeedProducts() {
		return needProducts;
	}

	public void setNeedProducts(Boolean needProducts) {
		this.needProducts = needProducts;
	}

}
