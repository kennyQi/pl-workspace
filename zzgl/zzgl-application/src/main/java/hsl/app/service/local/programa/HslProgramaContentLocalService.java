package hsl.app.service.local.programa;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.ProgramaContentDao;
import hsl.domain.model.programa.ProgramaContent;
import hsl.pojo.qo.programa.HslProgramaContentQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HslProgramaContentLocalService extends BaseServiceImpl<ProgramaContent, HslProgramaContentQO, ProgramaContentDao>{
	@Autowired
	private ProgramaContentDao programaContentDao;


	@Override
	protected ProgramaContentDao getDao() {
		return programaContentDao;
	}
	
}
