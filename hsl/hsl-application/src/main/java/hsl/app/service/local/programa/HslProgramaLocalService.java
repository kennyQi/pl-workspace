package hsl.app.service.local.programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.ProgramaDao;
import hsl.domain.model.programa.Programa;
import hsl.pojo.command.Programa.CreateProgramaCommand;
import hsl.pojo.qo.programa.HslProgramaQO;
@Service
@Transactional
public class HslProgramaLocalService extends BaseServiceImpl<Programa, HslProgramaQO, ProgramaDao>{
	@Autowired
	private ProgramaDao programaDao;

	@Override
	protected ProgramaDao getDao() {
		return programaDao;
	}
	
	/**
	 * @方法功能说明：创建栏目
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月21日12.37
	 * @参数：@param command
	 * @return:Programa
	 */
	public Programa createPrograma(CreateProgramaCommand command){
		Programa programa=new Programa();
		programa.createPrograma(command);
		programaDao.save(programa);
		return programa;
	}


}
