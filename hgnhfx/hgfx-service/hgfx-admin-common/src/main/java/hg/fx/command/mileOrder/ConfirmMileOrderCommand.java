package hg.fx.command.mileOrder;

import hg.framework.common.base.BaseSPICommand;

import java.util.List;
/**
 * 确认订单命令
 * @date 2016-6-16上午10:46:02
 * @since
 */
@SuppressWarnings("serial")
public class ConfirmMileOrderCommand extends BaseSPICommand {
	
	private List<String> ids;
	private String id;
	private String refuseReason;
	private String confirmPerson;
	
	
	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(String aduitPerson) {
		this.confirmPerson = aduitPerson;
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
