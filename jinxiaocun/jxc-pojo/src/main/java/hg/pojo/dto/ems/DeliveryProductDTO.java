package hg.pojo.dto.ems;

import java.io.Serializable;

/**
 * 发货包裹商品
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeliveryProductDTO  implements Serializable{
	
	/**
	 * 商品编号
	 */
	private String sku_code;
	
	/**
	 * 数量
	 */
	private String count;

	/**
	 * 商品单品长(cm)（暂不使用）
	 */
	private String sku_length;
	
	/**
	 * 商品单品宽(cm)（暂不使用）
	 */
	private String sku_width;
	
	/**
	 * 商品单品高(cm)（暂不使用）
	 */
	private String sku_height;
	
	/**
	 * 商品单品重量(千克)（暂不使用）
	 */
	private String sku_weight;

	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSku_length() {
		return sku_length;
	}

	public void setSku_length(String sku_length) {
		this.sku_length = sku_length;
	}

	public String getSku_width() {
		return sku_width;
	}

	public void setSku_width(String sku_width) {
		this.sku_width = sku_width;
	}

	public String getSku_height() {
		return sku_height;
	}

	public void setSku_height(String sku_height) {
		this.sku_height = sku_height;
	}

	public String getSku_weight() {
		return sku_weight;
	}

	public void setSku_weight(String sku_weight) {
		this.sku_weight = sku_weight;
	}

}
