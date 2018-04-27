package hg.demo.member.service.qo.hibernate.fx;

import org.apache.commons.lang.StringUtils;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.ProductSQO;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "productDAO")
public class ProductQO extends BaseHibernateQO<String> {

	/**
	 * 渠道
	 */
	@QOAttr(name = "channel", type = QOAttrType.LEFT_JOIN)
	private ChannelQO channelQO;

	/**
	 * 商品编码
	 */
	@QOAttr(name = "prodCode", type = QOAttrType.EQ)
	private String prodCode;

	/**
	 * 商品名称
	 */
	@QOAttr(name = "prodName", type = QOAttrType.LIKE_ANYWHERE)
	private String prodName;

	public static ProductQO build(ProductSQO sqo) {
		ProductQO productQO = new ProductQO();
		if (StringUtils.isNotBlank(sqo.getChannelID())) {
			ChannelQO channelQO = new ChannelQO();
			channelQO.setId(sqo.getChannelID());
			productQO.setChannelQO(channelQO);
		}
		productQO.setId(sqo.getProductID());
		productQO.setProdCode(sqo.getProdCode());
		productQO.setProdName(sqo.getProdName());
		productQO.setLimit(sqo.getLimit());
		return productQO;
	}

	public ChannelQO getChannelQO() {
		return channelQO;
	}

	public void setChannelQO(ChannelQO channelQO) {
		this.channelQO = channelQO;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

}
