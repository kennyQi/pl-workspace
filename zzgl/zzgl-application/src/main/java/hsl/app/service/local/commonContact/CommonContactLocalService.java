package hsl.app.service.local.commonContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hsl.app.dao.UserDao;
import hsl.app.dao.commonContact.CommonContactDao;
import hsl.domain.model.commonContact.CommonContact;
import hsl.domain.model.user.User;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.exception.CommonContactException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.pojo.qo.user.TravelerQO;

@Service
@Transactional
public class CommonContactLocalService extends BaseServiceImpl<CommonContact, CommonContactQO,CommonContactDao>{
	
	@Autowired
	private CommonContactDao commonContactDao;
	@Autowired
	private UserDao userDao;
	

	public CommonContactDTO addCommonContact(CreateCommonContactCommand command) throws CommonContactException{
		HslUserQO qo=new HslUserQO();
		qo.setId(command.getUserId());
		User user=userDao.queryUnique(qo);
		if(user==null){
			throw new CommonContactException (CommonContactException.USER_NO_EXIST,"用户不存在");
		}
		CommonContactQO contactQo = new CommonContactQO();
		contactQo.setUserId(command.getUserId());
		contactQo.setCardNo(command.getCardNo());
		if (getDao().queryCount(contactQo) > 0)
			throw new CommonContactException (CommonContactException.CARDNO_REPEAT,"证件重复");
			//throw new ShowMessageException("证件重复");
		
		CommonContact commonContact=new CommonContact();
		commonContact.createCommonContact(command, user);
		commonContactDao.save(commonContact);
		CommonContactDTO contactDTO=BeanMapperUtils.map(commonContact, CommonContactDTO.class);
		return contactDTO;
	}

	public CommonContact updateCommonContact(
			UpdateCommonContactCommand command) throws CommonContactException{
		
		CommonContactQO contactQo = new CommonContactQO();
		contactQo.setIdNotIn(new String[] { command.getId() });
		contactQo.setUserId(command.getUserId());
		contactQo.setCardNo(command.getCardNo());
		if (getDao().queryCount(contactQo) > 0)
			throw new ShowMessageException("证件重复");
		
		CommonContactQO qo=new CommonContactQO();
		qo.setId(command.getId());
		CommonContact commonContact=commonContactDao.queryUnique(qo);
		commonContact.updateCommonContact(command);
		commonContactDao.update(commonContact);
		//CommonContactDTO contactDTO=BeanMapperUtils.map(commonContact, CommonContactDTO.class);
		return commonContact;
	}

	public boolean delCommonContact(String id) {
		try{
			commonContactDao.deleteById(id);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	

	@Override
	protected CommonContactDao getDao() {
		return commonContactDao;
	}

}
