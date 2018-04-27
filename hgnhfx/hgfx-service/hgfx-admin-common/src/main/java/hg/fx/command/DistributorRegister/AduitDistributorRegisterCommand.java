package hg.fx.command.DistributorRegister;

import java.util.List;

import hg.framework.common.base.BaseSPICommand;

/**
 * 经销商注册申请审核
 * @author zqq
 * @date 2016-07-14
 * */
@SuppressWarnings("serial")
public class AduitDistributorRegisterCommand extends BaseSPICommand {

	private List<String> ids;
	private String id;
	//审核人 这个字段可能会用到
	private String aduitPerson;
	private Boolean isPass;
	
	public Boolean getIsPass() {
		return isPass;
	}
	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAduitPerson() {
		return aduitPerson;
	}
	public void setAduitPerson(String aduitPerson) {
		this.aduitPerson = aduitPerson;
	}
	
}
