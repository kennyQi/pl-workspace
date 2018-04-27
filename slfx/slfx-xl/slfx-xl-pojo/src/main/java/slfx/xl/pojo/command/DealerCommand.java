package slfx.xl.pojo.command;

import hg.common.component.BaseCommand;

public class DealerCommand extends BaseCommand{
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = -1943540135660669378L;
	
	private String id;
	
	private String name;
	
	private String code;
	
	/** 经销商商城访问地址：http://www.aa.com */
	private String dealerUrl;
	
	private String[] ids;
	
	private String flag;
	
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

	public String getDealerUrl() {
		return dealerUrl;
	}

	public void setDealerUrl(String dealerUrl) {
		this.dealerUrl = dealerUrl;
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

	private String status;

}
