package lxs.app.service.user;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;

import lxs.app.dao.user.ContactsDao;
import lxs.domain.model.user.Contacts;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.command.user.contacts.CreateContactsCommand;
import lxs.pojo.command.user.contacts.DeleteContactsCommand;
import lxs.pojo.command.user.contacts.ModifyContactsCommand;
import lxs.pojo.qo.user.contacts.ContactsQO;
import lxs.pojo.qo.user.user.LxsUserQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactsService extends BaseServiceImpl<Contacts, ContactsQO, ContactsDao> {

	@Autowired
	private ContactsDao contactsDao;
	@Autowired
	private LxsUserService lxsUserService; 
	
	public void CreateContacts(CreateContactsCommand command){
		Contacts contacts = new Contacts();
		contacts.setContactsName(command.getContactsDTO().getContactsName());
		contacts.setiDCardNO(command.getContactsDTO().getContactsIdCardNO());
		contacts.setMobile(command.getContactsDTO().getMobile());
		contacts.setType(command.getContactsDTO().getType());
		contacts.setCreateDate(new Date());
		LxsUserQO lxsUserQO = new LxsUserQO();
		lxsUserQO.setId(command.getContactsDTO().getUser());
		LxsUser user = new LxsUser();
		user = lxsUserService.queryUnique(lxsUserQO);
		contacts.setUser(user);
		contacts.setId(UUIDGenerator.getUUID());
		contactsDao.save(contacts);
	}
	
	public List<Contacts> QueryContacts(ContactsQO contactsQO){
		List<Contacts> contacts = null;
		contacts=contactsDao.queryList(contactsQO);
		return contacts;
	}
	
	public void ModifyContacts(ModifyContactsCommand command){
		Contacts contacts = new Contacts();
		contacts=contactsDao.get(command.getContactsID());
		if(contacts!=null){
			if(command.getContactsName()!=null){
				contacts.setContactsName(command.getContactsName());
			}
			if(command.getContactsIDCardNO()!=null){
				contacts.setiDCardNO(command.getContactsIDCardNO());
			}
			if(command.getMobile()!=null){
				contacts.setMobile(command.getMobile());
			}
			if(command.getType()!=null){
				contacts.setType(command.getType());
			}
			getDao().update(contacts);
		}
	}
	
	public void DeleteContacts(DeleteContactsCommand command){
		contactsDao.deleteById(command.getContactsID());
	}
	@Override
	protected ContactsDao getDao() {
		return contactsDao;
	}

}
