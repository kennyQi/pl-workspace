package hg.pojo.dto.ems;

import java.io.Serializable;

/**
 * 入库单收货确认商品
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class StockInConfirmProductDTO implements Serializable{
	
	/**
	 * SKU编码
	 */
	private String sku_code;
	
	/**
	 * 正品收货数量
	 */
	private String count;
	
	/**
	 * 不良品收货数量
	 */
	private String junk_count;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 行号
	 */
	private String line_seq;
	
	/**
	 * 长，厘米（单品尺寸）（暂不使用）
	 */
	private String length;
	
	/**
	 * 宽  厘米（单品尺寸）（暂不使用）
	 */
	private String width;
	
	/**
	 * 高  厘米（单品尺寸）（暂不使用）
	 */
	private String height;
	
	/**
	 * 重量  克（单品重量）（暂不使用）
	 */
	private String weight;

	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getJunk_count() {
		return junk_count;
	}

	public void setJunk_count(String junk_count) {
		this.junk_count = junk_count;
	}

	public String getLine_seq() {
		return line_seq;
	}

	public void setLine_seq(String line_seq) {
		this.line_seq = line_seq;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}


}
