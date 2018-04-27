package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.line.LxsLineActivityDAO;
import lxs.domain.model.line.LineActivity;
import lxs.pojo.qo.line.LineActivityQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LineActivityService extends BaseServiceImpl<LineActivity, LineActivityQO, LxsLineActivityDAO>{

	@Autowired
	private LxsLineActivityDAO lineActivityDAO;
	@Override
	protected LxsLineActivityDAO getDao() {
		return lineActivityDAO;
	}
	

}
