package jxc.emsclient.ems.dto.stockOut;

import java.io.Serializable;

/**
 * 出库商品信息
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class StockOutProductDTO implements Serializable{
	

	/**
	 * 商品SKU编码
	 */
	private String sku_code;
	
	/**
	 * 良品出库数量
	 */
	private Long count;
	
	/**
	 * 不良品出库数量
	 */
	private Long junk_count;
	
	/**
	 * 行号(销售单不需要，非销售单需要)
	 */
	private Long line_seq;
	

	/**
	 * 商品销售价格（两位小数）（暂不使用）
	 */
	private Double sale_price;

	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getJunk_count() {
		return junk_count;
	}

	public void setJunk_count(Long junk_count) {
		this.junk_count = junk_count;
	}

	public Long getLine_seq() {
		return line_seq;
	}

	public void setLine_seq(Long line_seq) {
		this.line_seq = line_seq;
	}

	public Double getSale_price() {
		return sale_price;
	}

	public void setSale_price(Double sale_price) {
		this.sale_price = sale_price;
	}

}
