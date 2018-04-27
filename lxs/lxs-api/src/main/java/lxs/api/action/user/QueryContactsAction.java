package lxs.api.action.user;

import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.ContactsDTO;
import lxs.api.v1.request.qo.user.ContactsQO;
import lxs.api.v1.response.user.QueryContactsResponse;
import lxs.app.service.user.ContactsService;
import lxs.domain.model.user.Contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("QueryContactsAction")
public class QueryContactsAction implements LxsAction{

	@Autowired
	private ContactsService contactsService;
	private List<ContactsDTO> contactsDTOList;
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【QueryContactsAction】"+"进入action");
		ContactsQO apicontactsQO = JSON.parseObject(apiRequest.getBody().getPayload(), ContactsQO.class);
		lxs.pojo.qo.user.contacts.ContactsQO  contactsQO =  new lxs.pojo.qo.user.contacts.ContactsQO();
		contactsQO.setUserId(apicontactsQO.getUserId());
		List<Contacts> contactsList = null;
		HgLogger.getInstance().info("lxs_dev", "【QueryContactsAction】"+contactsQO.getUserId()+"开始查询");
		contactsList=contactsService.QueryContacts(contactsQO);
		HgLogger.getInstance().info("lxs_dev", "【QueryContactsAction】"+contactsQO.getUserId()+"查询成功，开始转化");
		contactsDTOList = new ArrayList<ContactsDTO>();
		List<ContactsDTO> ChildrenList = new ArrayList<ContactsDTO>();
		for(Contacts contacts:contactsList){
			int birthDay = Integer.valueOf(contacts.getiDCardNO().substring(6, 14));
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			int now = Integer.valueOf(simpleDateFormat.format(date));
			if(birthDay+110000>=now){
				ContactsDTO contactsDTO =new ContactsDTO();
				contactsDTO.setId(contacts.getId());
				contactsDTO.setUser(contacts.getUser().getId());
				contactsDTO.setContactsName(contacts.getContactsName());
				contactsDTO.setContactsIdCardNO(contacts.getiDCardNO());
				contactsDTO.setMobile(contacts.getMobile());
				contactsDTO.setType(contacts.getType());
				ChildrenList.add(contactsDTO);
			}else{
				ContactsDTO contactsDTO =new ContactsDTO();
				contactsDTO.setId(contacts.getId());
				contactsDTO.setUser(contacts.getUser().getId());
				contactsDTO.setContactsName(contacts.getContactsName());
				contactsDTO.setContactsIdCardNO(contacts.getiDCardNO());
				contactsDTO.setMobile(contacts.getMobile());
				contactsDTO.setType(contacts.getType());
				contactsDTOList.add(contactsDTO);
			}
//			Calendar calendar = Calendar.getInstance();
//			int now = calendar.get(calendar.YEAR);
		}
		contactsDTOList.addAll(ChildrenList);
		QueryContactsResponse queryConstactsResponse = new QueryContactsResponse();
		queryConstactsResponse.setContactsList(contactsDTOList);
		queryConstactsResponse.setMessage("查询成功");
		queryConstactsResponse.setResult(ApiResponse.RESULT_CODE_OK);
		HgLogger.getInstance().info("lxs_dev", "【QueryContactsAction】"+"查询联系人结果"+JSON.toJSONString(queryConstactsResponse));
		return JSON.toJSONString(queryConstactsResponse);
	}

}
