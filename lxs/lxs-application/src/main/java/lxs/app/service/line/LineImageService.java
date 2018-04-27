package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.line.LxsLineImageDao;
import lxs.domain.model.line.LineImage;
import lxs.pojo.qo.line.LineImageQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LineImageService extends BaseServiceImpl<LineImage, LineImageQO, LxsLineImageDao>{

	@Autowired
	private LxsLineImageDao lxsLineImageDao;
	@Override
	protected LxsLineImageDao getDao() {
		return lxsLineImageDao;
	}

}
