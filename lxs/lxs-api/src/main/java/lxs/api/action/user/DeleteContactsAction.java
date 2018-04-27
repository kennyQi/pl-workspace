package lxs.api.action.user;

import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.DeleteContactsCommand;
import lxs.api.v1.response.user.DeleteContactsResponse;
import lxs.app.service.user.ContactsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component("DeleteContactsAction")
public class DeleteContactsAction implements LxsAction{

	@Autowired
	private ContactsService contactsService;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【DeleteContactsAction】"+"进入action");
		DeleteContactsCommand apideleteContactsCommand = JSON.parseObject(apiRequest.getBody().getPayload(),DeleteContactsCommand.class);
		lxs.pojo.command.user.contacts.DeleteContactsCommand deleteContactsCommand = new lxs.pojo.command.user.contacts.DeleteContactsCommand();
		deleteContactsCommand.setContactsID(apideleteContactsCommand.getContactsID());
		HgLogger.getInstance().info("lxs_dev", "【DeleteContactsAction】"+deleteContactsCommand.getContactsID()+"开始删除");
		contactsService.DeleteContacts(deleteContactsCommand);
		HgLogger.getInstance().info("lxs_dev", "【DeleteContactsAction】"+deleteContactsCommand.getContactsID()+"删除成功");
		DeleteContactsResponse deleteContactsResponse = new DeleteContactsResponse();
		deleteContactsResponse.setMessage("删除成功");
		deleteContactsResponse.setResult(ApiResponse.RESULT_CODE_OK);
		HgLogger.getInstance().info("lxs_dev", "【DeleteContactsAction】"+"删除联系人结果"+JSON.toJSONString(deleteContactsResponse));
		return JSON.toJSONString(deleteContactsResponse);
	}

}
