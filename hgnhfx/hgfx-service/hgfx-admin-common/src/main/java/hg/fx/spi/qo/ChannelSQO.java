package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午10:16:47
 * @版本： V1.0
 */
public class ChannelSQO extends BaseSPIQO {

	private static final long serialVersionUID = 1L;

	/** 
	 * id 
	 */
	private String id;

	/** 
	 * 渠道名
	 */
	private String channelName;

	/**
	 * 是否需要List<Product> products
	 */
	private Boolean needProducts;

	public String getId() {
		return id;
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
