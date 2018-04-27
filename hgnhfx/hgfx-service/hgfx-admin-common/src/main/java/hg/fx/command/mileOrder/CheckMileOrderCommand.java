package hg.fx.command.mileOrder;

import hg.framework.common.base.BaseSPICommand;

import java.util.List;
/**
 * 审核订单命令
 * @date 2016-6-16上午10:46:02
 * @since
 */
@SuppressWarnings("serial")
public class CheckMileOrderCommand extends BaseSPICommand {
	
	private List<String> ids;
	private String id;
	private String checkPersonId;
	private String reason;
	private String aduitPerson;
	
	
	public String getAduitPerson() {
		return aduitPerson;
	}

	public void setAduitPerson(String aduitPerson) {
		this.aduitPerson = aduitPerson;
	}

	public List<String> getIds() {
		return ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getCheckPersonId() {
		return checkPersonId;
	}

	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getIdsString(){
		StringBuffer sb = new StringBuffer();
		if(ids!=null){
			for (int i = 0; i < ids.size(); i++) {
				sb.append(ids.get(i)+",");
			}
			return sb.toString();
		}else{
			return "";
		}
	}

	
}
