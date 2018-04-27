package hsl.app.service.local.user;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.TravelerDao;
import hsl.app.dao.UserDao;
import hsl.domain.model.user.User;
import hsl.domain.model.user.traveler.Traveler;
import hsl.pojo.command.traveler.CreateTravelerCommand;
import hsl.pojo.command.traveler.DeleteTravelerCommand;
import hsl.pojo.command.traveler.ModifyTravelerCommand;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.user.TravelerQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：用户游客服务
 * @类修改者：
 * @修改日期：2015-9-29下午3:10:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-29下午3:10:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TravelerLocalService extends BaseServiceImpl<Traveler, TravelerQO, TravelerDao> {

	@Autowired
	private TravelerDao dao;
	@Autowired
	private UserDao userDao;

	@Override
	protected TravelerDao getDao() {
		return dao;
	}

	/**
	 * @方法功能说明：创建游客
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-10下午4:05:09
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return		游客ID
	 * @return:String
	 * @throws
	 */
	public String createTraveler(CreateTravelerCommand command) {
		TravelerQO qo = new TravelerQO();
		qo.setFromUserId(command.getFromUserId());
		qo.setIdType(command.getIdType());
		qo.setIdNo(command.getIdNo());
		if (getDao().queryCount(qo) > 0)
			throw new ShowMessageException("证件重复");
		User fromUser = userDao.get(command.getFromUserId());
		Traveler traveler = new Traveler();
		traveler.manager().createTraveler(command, fromUser);
		getDao().save(traveler);
		return traveler.getId();
	}

	/**
	 * @方法功能说明：修改游客
	 * @修改者名字：zhurz
	 * @修改时间：2015-9-29下午3:10:52
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyTraveler(ModifyTravelerCommand command) {
		if (command.getTravelerId() == null)
			throw new ShowMessageException("游客不存在");
		TravelerQO qo = new TravelerQO();
		qo.setIdNotIn(new String[] { command.getTravelerId() });
		qo.setFromUserId(command.getFromUserId());
		qo.setIdType(command.getIdType());
		qo.setIdNo(command.getIdNo());
		if (getDao().queryCount(qo) > 0)
			throw new ShowMessageException("证件重复");
		Traveler traveler = getDao().get(command.getTravelerId());
		if (traveler == null)
			throw new ShowMessageException("游客不存在");
		traveler.manager().modifyTraveler(command);
		getDao().update(traveler);
	}

	/**
	 * @方法功能说明：物理删除游客
	 * @修改者名字：zhurz
	 * @修改时间：2015-9-29下午3:10:42
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void deleteTraveler(DeleteTravelerCommand command) {
		if (command.getTravelerId() == null)
			throw new ShowMessageException("游客不存在");
		Traveler traveler = getDao().get(command.getTravelerId());
		if (traveler == null)
			throw new ShowMessageException("游客不存在");
		getDao().delete(traveler);
	}
}
