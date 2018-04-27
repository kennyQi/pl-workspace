package hsl.pojo.dto.ad;

import java.io.Serializable;

/**
 * @类功能说明：广告位的基本信息
 * @类修改者：
 * @修改日期：2014年12月11日下午4:05:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月11日下午4:05:05
 * 
 */
public class HslAdPositionBaseInfoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 广告位名称
	 */
	private String name;
	/**
	 * 客户端类型
	 */
	private Integer clientType;
	/**
	 * 描述
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}