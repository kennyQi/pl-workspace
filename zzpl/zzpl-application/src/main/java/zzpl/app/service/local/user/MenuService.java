package zzpl.app.service.local.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.MenuDAO;
import zzpl.domain.model.user.Menu;
import zzpl.pojo.qo.user.MenuQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class MenuService extends BaseServiceImpl<Menu, MenuQO, MenuDAO> {

	@Autowired
	private MenuDAO menuDAO;

	@Override
	protected MenuDAO getDao() {
		return menuDAO;
	}

}
