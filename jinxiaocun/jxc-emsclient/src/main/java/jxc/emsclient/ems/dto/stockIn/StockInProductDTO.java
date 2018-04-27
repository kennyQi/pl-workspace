package jxc.emsclient.ems.dto.stockIn;

import java.io.Serializable;

/**
 * 入库单商品
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class StockInProductDTO  implements Serializable{

	/**
	 * 商品sku
	 */
	private String sku_code;
	
	/**
	 * 商品名称
	 */
	private String sku_name;
	
	/**
	 * 商品单位（个、件等）
	 */
	private String unit;
	
	/**
	 * 入库数量
	 */
	private Long plan_count;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 行号
	 */
	private Long line_seq;
	
	/**
	 * 条码（暂不使用）
	 */
	private String bar_code;
	
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
	
	/**
	 * 颜色（规格属性）（暂不使用）
	 */
	private String color;
	
	/**
	 * 尺码（规格属性）（暂不使用）
	 */
	private String size;
	
	/**
	 * 预留自定义规格属性一（暂不使用）
	 */
	private String spec1;
	
	/**
	 * 预留自定义规格属性二（暂不使用）
	 */
	private String spec2;
	
	/**
	 * 预留自定义规格属性二（暂不使用）
	 */
	private String spec3;

	/**
	 * 箱包装单品入数（暂不使用）
	 */
	private String package_num;
	
	/**
	 * 长，厘米（箱包装尺寸）（暂不使用）
	 */
	private String package_length;
	
	/**
	 * 宽  厘米（箱包装尺寸）（暂不使用）
	 */
	private String package_width;
	
	/**
	 * 高  厘米（箱包装尺寸）（暂不使用）
	 */
	private String package_height;
	
	/**
	 * 重量  克（箱包装重量）（暂不使用）
	 */
	private String package_weight;
	
	/**
	 * 采购价格(元)（暂不使用）
	 */
	private String purchase_price;
	
	/**
	 * 零售价格(元),标准售价（暂不使用）
	 */
	private String price;

	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getPlan_count() {
		return plan_count;
	}

	public void setPlan_count(Long plan_count) {
		this.plan_count = plan_count;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getLine_seq() {
		return line_seq;
	}

	public void setLine_seq(Long line_seq) {
		this.line_seq = line_seq;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSpec1() {
		return spec1;
	}

	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}

	public String getSpec2() {
		return spec2;
	}

	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}

	public String getSpec3() {
		return spec3;
	}

	public void setSpec3(String spec3) {
		this.spec3 = spec3;
	}

	public String getPackage_num() {
		return package_num;
	}

	public void setPackage_num(String package_num) {
		this.package_num = package_num;
	}

	public String getPackage_length() {
		return package_length;
	}

	public void setPackage_length(String package_length) {
		this.package_length = package_length;
	}

	public String getPackage_width() {
		return package_width;
	}

	public void setPackage_width(String package_width) {
		this.package_width = package_width;
	}

	public String getPackage_height() {
		return package_height;
	}

	public void setPackage_height(String package_height) {
		this.package_height = package_height;
	}

	public String getPackage_weight() {
		return package_weight;
	}

	public void setPackage_weight(String package_weight) {
		this.package_weight = package_weight;
	}

	public String getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(String purchase_price) {
		this.purchase_price = purchase_price;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
