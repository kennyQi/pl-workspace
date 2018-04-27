package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import lxs.app.dao.app.IntroductionDAO;
import lxs.domain.model.app.Introduction;
import lxs.pojo.command.app.AddIntroductionCommand;
import lxs.pojo.qo.app.IntroductionQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 * @类功能说明：介绍相关模块service
 * @类修改者：
 * @修改日期：2015-10-26 上午11:16:35
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:16:35
 */
@Service
@Transactional
public class IntroductionService extends
		BaseServiceImpl<Introduction, IntroductionQO, IntroductionDAO> {

	@Autowired
	private IntroductionDAO IntroductionDao;

	@Override
	protected IntroductionDAO getDao() {
		// TODO Auto-generated method stub
		return IntroductionDao;
	}

	/**
	 * 
	 * 
	 * @方法功能说明： 1旅游简介添加
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:19:26
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addLyjj(AddIntroductionCommand addintroductioncommand) {
		HgLogger.getInstance().info(
				"jinyy",
				"【IntroductionService】【addLyjj】【AddIntroductionCommand】"
						+ JSON.toJSONString(addintroductioncommand));

		Introduction introduction = new Introduction();
		introduction.setId(UUIDGenerator.getUUID());
		introduction.setIntroductionType(Introduction.JQJJ);
		introduction.setIntroductionContent(addintroductioncommand
				.getIntroductionContent());
		save(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：1旅游简介修改
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:19:56
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updateLyjj(AddIntroductionCommand command) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【updateLyjj】【AddIntroductionCommand】"+JSON.toJSONString(command));
		Introduction introduction = IntroductionDao.get(command.getId());
		introduction.setIntroductionContent(command.getIntroductionContent());
		getDao().update(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：2旅游协议添加
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:20:18
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addLyxy(AddIntroductionCommand addintroductioncommand) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【addLyxy】【AddIntroductionCommand】"+JSON.toJSONString(addintroductioncommand));

		Introduction introduction = new Introduction();
		introduction.setId(UUIDGenerator.getUUID());
		introduction.setIntroductionType(Introduction.LYXY);
		introduction.setIntroductionContent(addintroductioncommand
				.getIntroductionContent());
		save(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改2旅游协议
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:23:43
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updateLyxy(AddIntroductionCommand command) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【updateLyxy】【AddIntroductionCommand】"+JSON.toJSONString(command));

		Introduction introduction = IntroductionDao.get(command.getId());
		introduction.setIntroductionContent(command.getIntroductionContent());
		getDao().update(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加经营许可
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:24:16
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addJyxk(AddIntroductionCommand addintroductioncommand) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【addJyxk】【AddIntroductionCommand】"+JSON.toJSONString(addintroductioncommand));
		Introduction introduction = new Introduction();
		introduction.setId(UUIDGenerator.getUUID());
		introduction.setIntroductionType(Introduction.JYXK);
		introduction.setIntroductionContent(addintroductioncommand
				.getIntroductionContent());
		save(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明： 修改经营许可
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:24:28
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updateJyxk(AddIntroductionCommand command) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【updateJyxk】【AddIntroductionCommand】"+JSON.toJSONString(command));

		Introduction introduction = IntroductionDao.get(command.getId());
		introduction.setIntroductionContent(command.getIntroductionContent());
		getDao().update(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改介绍123
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:24:58
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updateIntroduction(AddIntroductionCommand command) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【updateIntroduction】【AddIntroductionCommand】"+JSON.toJSONString(command));

		Introduction introduction = IntroductionDao.get(command.getId());
		introduction.setIntroductionContent(command.getIntroductionContent());
		getDao().update(introduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加介绍 123
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:25:18
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addIntroduction(AddIntroductionCommand command) {
		HgLogger.getInstance().info("jinyy", "【IntroductionService】【addIntroduction】【AddIntroductionCommand】"+JSON.toJSONString(command));

		Introduction introduction = new Introduction();
		introduction.setId(UUIDGenerator.getUUID());

		if (command.getIntroductionType() == 1) {
			introduction.setIntroductionType(Introduction.JQJJ);
		} else if (command.getIntroductionType() == 2) {
			introduction.setIntroductionType(Introduction.LYXY);
		} else if (command.getIntroductionType() == 3) {
			introduction.setIntroductionType(Introduction.JYXK);
		}

		introduction.setIntroductionContent(command.getIntroductionContent());
		save(introduction);

	}

}
