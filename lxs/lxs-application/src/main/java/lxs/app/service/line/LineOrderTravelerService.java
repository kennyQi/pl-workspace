package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.line.LineOrderTravelerDAO;
import lxs.domain.model.line.LineOrderTraveler;
import lxs.pojo.qo.line.LineOrderTravelerQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LineOrderTravelerService extends BaseServiceImpl<LineOrderTraveler, LineOrderTravelerQO, LineOrderTravelerDAO>{

	@Autowired
	private LineOrderTravelerDAO lineOrderTravelerDAO;
	@Override
	protected LineOrderTravelerDAO getDao() {
		return lineOrderTravelerDAO;
	}

}
