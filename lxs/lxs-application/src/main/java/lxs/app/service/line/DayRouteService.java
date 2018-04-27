package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.line.LxsDayRouteDAO;
import lxs.domain.model.line.DayRoute;
import lxs.pojo.qo.line.DayRouteQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DayRouteService extends
		BaseServiceImpl<DayRoute, DayRouteQO, LxsDayRouteDAO> {

	@Autowired
	private LxsDayRouteDAO dayRouteDAO;

	@Override
	protected LxsDayRouteDAO getDao() {
		return dayRouteDAO;
	}
}
