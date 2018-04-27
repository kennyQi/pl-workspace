package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import lxs.app.dao.app.FunctionIntroductionDao;
import lxs.domain.model.app.FunctionIntroduction;
import lxs.pojo.command.app.AddFunctionIntroductionCommand;
import lxs.pojo.qo.app.FunctionIntroductionQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 * @类功能说明：功能介绍
 * @类修改者：
 * @修改日期：2015-10-26 上午11:15:17
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:15:17
 */
@Service
@Transactional
public class FunctionIntroduntionService
		extends
		BaseServiceImpl<FunctionIntroduction, FunctionIntroductionQO, FunctionIntroductionDao> {

	@Autowired
	private FunctionIntroductionDao functionIntroductionDao;

	@Override
	protected FunctionIntroductionDao getDao() {
		// TODO Auto-generated method stub
		return functionIntroductionDao;
	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加功能介绍
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:15:30
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addBean(AddFunctionIntroductionCommand command) {
		HgLogger.getInstance().info(
				"jinyy",
				"【FunctionIntroduntionService】【addBean】【AddFunctionIntroductionCommand】"
						+ JSON.toJSONString(command));
		FunctionIntroduction functionIntroduction = new FunctionIntroduction();
		functionIntroduction.setId(UUIDGenerator.getUUID());
		functionIntroduction.setVersionNumber(command.getVersionNumber());
		functionIntroduction.setVersionContent(command.getVersionContent());
		save(functionIntroduction);

	}

	/**
	 * 
	 * 
	 * @方法功能说明：修改功能介绍
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:15:53
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void updateBean(AddFunctionIntroductionCommand command) {
		HgLogger.getInstance().info(
				"jinyy",
				"【FunctionIntroduntionService】【updateBean】【AddFunctionIntroductionCommand】"
						+ JSON.toJSONString(command));
		FunctionIntroduction introduction = functionIntroductionDao.get(command
				.getId());
		introduction.setVersionNumber(command.getVersionNumber());
		introduction.setVersionContent(command.getVersionContent());
		getDao().update(introduction);

	}

}
