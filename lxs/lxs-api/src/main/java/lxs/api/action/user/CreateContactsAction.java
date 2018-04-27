package lxs.api.action.user;

import hg.log.util.HgLogger;
import hg.system.common.util.EntityConvertUtils;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.request.command.user.CreateContactsCommand;
import lxs.api.v1.response.user.CreateContactsResponse;
import lxs.app.service.user.ContactsService;
import lxs.app.service.user.LxsUserService;
import lxs.pojo.dto.user.contacts.ContactsDTO;
import lxs.pojo.exception.user.ContactsException;
import lxs.pojo.qo.user.contacts.ContactsQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("CreateContactsAction")
public class CreateContactsAction implements LxsAction{

	@Autowired
	private ContactsService contactsService;
	@Autowired
	private LxsUserService lxsUserService;
	@Autowired
	private HgLogger hgLogger; 
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【CreateContactsAction】"+"进入action");
		CreateContactsCommand apicreateContactsCommand = JSON.parseObject(apiRequest.getBody().getPayload(), CreateContactsCommand.class);
		lxs.pojo.command.user.contacts.CreateContactsCommand createContactsCommand = new lxs.pojo.command.user.contacts.CreateContactsCommand();
		CreateContactsResponse createContactsResponse = new CreateContactsResponse();
		try{
			createContactsCommand.setContactsDTO(EntityConvertUtils.convertDtoToEntity(apicreateContactsCommand.getContactsDTO(), ContactsDTO.class));
			if(apicreateContactsCommand.getContactsDTO().getUser()==null||StringUtils.isBlank(apicreateContactsCommand.getContactsDTO().getUser())){
				throw new ContactsException(ContactsException.RESULT_CONTACTS_USER_NOT_FOUND,"用户不存在");
			}
			if(lxsUserService.get(apicreateContactsCommand.getContactsDTO().getUser())==null){
				throw new ContactsException(ContactsException.RESULT_CONTACTS_USER_NOT_FOUND,"用户不存在");
			}
			ContactsQO contactsQO = new ContactsQO();
			contactsQO.setContactsIDCardNO(createContactsCommand.getContactsDTO().getContactsIdCardNO());
			contactsQO.setUserId(createContactsCommand.getContactsDTO().getUser());
			HgLogger.getInstance().info("lxs_dev", "【CreateContactsAction】"+"查询用户是否已添加该联系人"+"，身份证号："+createContactsCommand.getContactsDTO().getContactsIdCardNO()+"，userID："+createContactsCommand.getContactsDTO().getUser());
			if(contactsService.queryCount(contactsQO)==0){
				HgLogger.getInstance().info("lxs_dev", "【CreateContactsAction】"+createContactsCommand.getContactsDTO().getContactsName()+"开始添加");
				contactsService.CreateContacts(createContactsCommand);
				HgLogger.getInstance().info("lxs_dev","【CreateContactsAction】"+ createContactsCommand.getContactsDTO().getContactsName()+"添加成功");
				createContactsResponse.setResult(ApiResponse.RESULT_CODE_OK);
				createContactsResponse.setMessage("添加成功");
			}else{
				throw new ContactsException(ContactsException.RESULT_CONTACTS_REPEAT,"该联系人已存在");
			}
		}catch(ContactsException e){
			HgLogger.getInstance().info("lxs_dev", "【CreateContactsAction】"+createContactsCommand.getContactsDTO().getContactsName() + "添加失败，"+e.getMessage());
			createContactsResponse.setMessage(e.getMessage());
			createContactsResponse.setResult(e.getCode());
		}
		HgLogger.getInstance().info("lxs_dev", "【CreateContactsAction】"+"添加联系人结果"+JSON.toJSONString(createContactsResponse));
		return JSON.toJSONString(createContactsResponse);
	}
}
