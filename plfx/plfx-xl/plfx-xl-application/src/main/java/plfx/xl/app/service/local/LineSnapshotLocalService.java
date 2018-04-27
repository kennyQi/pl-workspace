package plfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.xl.app.dao.LineSnapshotDAO;
import plfx.xl.domain.model.line.Line;
import plfx.xl.domain.model.line.LineSnapshot;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.pojo.qo.LineSnapshotQO;
@Service
@Transactional
public class LineSnapshotLocalService extends BaseServiceImpl<LineSnapshot,LineSnapshotQO,LineSnapshotDAO>{
	@Autowired
	private LineSnapshotDAO lineSnapshotDAO;
	@Autowired
	private LineLocalService lineLocalService;
	
	@Override
	protected LineSnapshotDAO getDao() {
		return lineSnapshotDAO;
	}

	public void save(LineDTO lineDTO) {
		LineQO qo = new LineQO();
		qo.setId(lineDTO.getId());
		Line line = lineLocalService.queryUnique(qo);
		if(line != null){
			LineSnapshot lineSnapshot = new LineSnapshot();
			Hibernate.initialize(line.getDateSalePriceList());
			Hibernate.initialize(line.getLineImageList());
			lineSnapshot.create(line);
			lineSnapshotDAO.save(lineSnapshot);
		}
	}

	
}
