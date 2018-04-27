package jxc.app.service.system;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateAttentionCommand;
import hg.pojo.command.ModifyAttentionCommand;
import hg.pojo.command.RemoveAttentionCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.AttentionQo;
import jxc.app.dao.system.AttentionDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.system.Attention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttentionService extends BaseServiceImpl<Attention, AttentionQo, AttentionDao> {
	@Autowired
	private AttentionDao attentionDao;

	@Override
	protected AttentionDao getDao() {
		return attentionDao;
	}

	@Autowired
	private JxcLogger logger;

	public void createAttention(CreateAttentionCommand command) throws SettingException {
		if (attentionNameIsExisted(command.getAttentionName(), null)) {
			throw new SettingException(SettingException.ATTENTION_NAME_EXIST, "注意事项已存在");
		}

		Attention attention = new Attention();
		attention.createAttention(command);
		save(attention);

		logger.debug(this.getClass(), "wkq", "添加注意事项:" + command.getAttentionName(), command.getOperatorName(), command.getOperatorType(),
				command.getOperatorAccount(), "");

	}

	public void modifyAttention(ModifyAttentionCommand command) throws SettingException {
		if (attentionNameIsExisted(command.getAttentionName(), command.getId())) {
			throw new SettingException(SettingException.ATTENTION_NAME_EXIST, "注意事项已存在");
		}

		Attention attention = get(command.getId());
		attention.modifyAttention(command);
		update(attention);

		logger.debug(this.getClass(), "wkq", "修改注意事项:" + command.getAttentionName(), command.getOperatorName(), command.getOperatorType(),
				command.getOperatorAccount(), "");

	}

	public void removeAttention(RemoveAttentionCommand command) throws SettingException {
		if (attentionIsUsing(command.getId())) {
			throw new SettingException(SettingException.ATTENTION_NAME_EXIST, "注意事项已存在");
		}

		Attention attention = get(command.getId());
		attention.removeAttention(command);
		update(attention);

		logger.debug(this.getClass(), "wkq", "删除注意事项:" + command.getId(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(),
				"");

	}

	private boolean attentionIsUsing(String id) {
		return false;
	}

	private boolean attentionNameIsExisted(String attentionName, String id) {
		AttentionQo qo = new AttentionQo();
		qo.setAttentionName(attentionName);
		Attention attention = queryUnique(qo);
		if (id == null) {
			if (attention != null) {
				return true;
			}
		} else {
			if (attention != null && !id.equals(attention.getId())) {
				return true;
			}
		}
		return false;
	}
}
