package plfx.jp.command;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：经销商COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午1:43:23
 * @版本：V1.0
 *
 */
public class DealerCommand extends BaseCommand{
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = -1943540135660669378L;
	
	private String id;
	/**
	 * 经销商名称
	 */
	private String name;
	/**
	 * 经销商代码 比如F1001
	 */
	private String code;
	
	private String[] ids;
	/**
	 * 是否启用
	 */
	private String flag;
	
	/**
	 * 密钥
	 */
	private String secretKey;
	
	/**
	 * 通知地址
	 */
	private String notifyUrl;
	
	/**
	 * 通知字段名
	 */
	private String notifyValue;
	
	private String status;
	
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNotifyValue() {
		return notifyValue;
	}

	public void setNotifyValue(String notifyValue) {
		this.notifyValue = notifyValue;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
