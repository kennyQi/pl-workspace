package hg.pojo.dto.ems;

import java.io.Serializable;

/**
 * 出库单出库确认商品
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class StockOutConfirmProductDTO implements Serializable{
	
	/**
	 * sku编码
	 */
	private String sku_code;
	
	/**
	 * 正品数量
	 */
	private String count;
	
	/**
	 * 不良品数量
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

	public String getJunk_count() {
		return junk_count;
	}

	public void setJunk_count(String junk_count) {
		this.junk_count = junk_count;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLine_seq() {
		return line_seq;
	}

	public void setLine_seq(String line_seq) {
		this.line_seq = line_seq;
	}


}
