package lxs.api.action.user;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.ModifyContactsCommand;
import lxs.api.v1.response.user.ModifyContactsResponse;
import lxs.app.service.user.ContactsService;
import lxs.pojo.exception.user.ContactsException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component("ModifyContactsAction")
public class ModifyContactsAction implements LxsAction {

	@Autowired
	private ContactsService contactsService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【ModifyContactsAction】"+"进入action");
		ModifyContactsCommand apimodifyContactsCommand = JSON.parseObject(apiRequest.getBody().getPayload(), ModifyContactsCommand.class);
		lxs.pojo.command.user.contacts.ModifyContactsCommand modifyContactsCommand= new lxs.pojo.command.user.contacts.ModifyContactsCommand();
		modifyContactsCommand.setContactsID(apimodifyContactsCommand.getContactsDTO().getId());
		ModifyContactsResponse modifyContactsResponse = new ModifyContactsResponse();
		try{
			if(apimodifyContactsCommand.getContactsDTO().getId()==null||StringUtils.isBlank(apimodifyContactsCommand.getContactsDTO().getId())){
				throw new ContactsException(ContactsException.RESULT_CONTACTS_USER_NOT_FOUND,"联系人不存在");
			}
			if(apimodifyContactsCommand.getContactsDTO().getContactsName()!=null){
				modifyContactsCommand.setContactsName(apimodifyContactsCommand.getContactsDTO().getContactsName());
			}
			if(apimodifyContactsCommand.getContactsDTO().getContactsIdCardNO()!=null){
				modifyContactsCommand.setContactsIDCardNO(apimodifyContactsCommand.getContactsDTO().getContactsIdCardNO());
			}
			if(apimodifyContactsCommand.getContactsDTO().getMobile()!=null){
				modifyContactsCommand.setMobile(apimodifyContactsCommand.getContactsDTO().getMobile());
			}
			if(apimodifyContactsCommand.getContactsDTO().getType()!=null){
				modifyContactsCommand.setType(apimodifyContactsCommand.getContactsDTO().getType());
			}
			if(contactsService.get(apimodifyContactsCommand.getContactsDTO().getId())==null){
				throw new ContactsException(ContactsException.RESULT_CONTACTS_USER_NOT_FOUND,"联系人不存在");
			}
			HgLogger.getInstance().info("lxs_dev", "【ModifyContactsAction】"+modifyContactsCommand.getContactsID()+"开始修改");
			contactsService.ModifyContacts(modifyContactsCommand);
			HgLogger.getInstance().info("lxs_dev", "【ModifyContactsAction】"+modifyContactsCommand.getContactsID()+"修改成功");
			modifyContactsResponse.setMessage("修改成功");
			modifyContactsResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(ContactsException e){
			HgLogger.getInstance().info("lxs_dev", "【ModifyContactsAction】"+"联系人修改失败"+e.getMessage());
			modifyContactsResponse.setMessage(e.getMessage());
			modifyContactsResponse.setResult(e.getCode());
		}
		HgLogger.getInstance().info("lxs_dev", "【ModifyContactsAction】"+"修改联系人结果"+JSON.toJSONString(modifyContactsResponse));
		return JSON.toJSONString(modifyContactsResponse);
	}

}
