package hg.pojo.dto.ems;

import java.io.Serializable;
import java.util.List;

/**
 * 发货包裹信息
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeliveryDTO implements Serializable{

	/**
	 * 包裹运单号
	 */
	private String mailno;
	
	/**
	 * 长(cm)（暂不使用）
	 */
	private String length;
	
	/**
	 * 宽(cm)（暂不使用）
	 */
	private String width;
	
	/**
	 * 高(cm)（暂不使用）
	 */
	private String height;
	
	/**
	 * 包裹重量（千克）（暂不使用）
	 */
	private String weight;
	
	private List<DeliveryProductDTO> sku_details;

	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
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

	public List<DeliveryProductDTO> getSku_details() {
		return sku_details;
	}

	public void setSku_details(List<DeliveryProductDTO> sku_details) {
		this.sku_details = sku_details;
	}
	
	
}
