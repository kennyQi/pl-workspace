package hg.pojo.command;


/**
 * 创建仓库类型
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class CreateWarehouseTypeCommand extends JxcCommand {

	/**
	 * 类型名称
	 */
	private String name;
	
	/**
	 * 物流接口推送地址
	 */
	private String portUrl;
	
	/**
	 * 加密串
	 */
	private String key;
	
	/**
	 * 操作员
	 */
	private String operator;
	
	/**
	 * 保价比例
	 */
	private Double insuredRate;
	
	/**
	 * 接口是否开启
	 */
	private Boolean using;
	
	/**
	 * 渠道商id
	 */
	private String channelId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortUrl() {
		return portUrl;
	}

	public void setPortUrl(String portUrl) {
		this.portUrl = portUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Double getInsuredRate() {
		return insuredRate;
	}

	public void setInsuredRate(Double insuredRate) {
		this.insuredRate = insuredRate;
	}

	public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}
