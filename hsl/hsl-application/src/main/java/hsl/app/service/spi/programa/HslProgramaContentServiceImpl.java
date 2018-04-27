package hsl.app.service.spi.programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.dao.ProgramaDao;
import hsl.app.service.local.programa.HslProgramaContentLocalService;
import hsl.domain.model.programa.ProgramaContent;
import hsl.pojo.command.Programa.CreateProgramaContentCommand;
import hsl.pojo.command.Programa.UpdateProgramaContentCommand;
import hsl.pojo.dto.programa.ProgramaContentDTO;
import hsl.pojo.qo.programa.HslProgramaContentQO;
import hsl.spi.inter.programa.HslProgramaContentService;
@Service
public class HslProgramaContentServiceImpl extends BaseSpiServiceImpl<ProgramaContentDTO, HslProgramaContentQO,HslProgramaContentLocalService>implements
		HslProgramaContentService {
	@Autowired
	private HslProgramaContentLocalService hslProgramaContentLocalService;
	@Override
	protected HslProgramaContentLocalService getService() {
		return hslProgramaContentLocalService;
	}

	@Override
	protected Class<ProgramaContentDTO> getDTOClass() {
		return ProgramaContentDTO.class;
	}
	public ProgramaContentDTO queryUnique(HslProgramaContentQO qo) {
		ProgramaContentDTO dto = super.queryUnique(qo);
		return dto;
	}
	@Override
	public boolean updateProgramaContent(UpdateProgramaContentCommand command) {
		try {
			if(command==null||StringUtils.isEmpty(command.getId())){
				return false;
			}
			HgLogger.getInstance().info("zhaows", "updateProgramaContent修改栏目内容："+JSON.toJSONString(command));
			HslProgramaContentQO qo=new HslProgramaContentQO();
			qo.setId(command.getId());
			ProgramaContent programaContent=hslProgramaContentLocalService.queryUnique(qo);
			programaContent.setContent(command.getContent());
			this.hslProgramaContentLocalService.update(programaContent);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "updateProgramaStutas栏目状态更新："+e.getStackTrace());
			return false;
		}
	}

	@Override
	public boolean deleteProgramaContent(String id) {
		try {
			HgLogger.getInstance().info("zhaows", "deletePrograma删除栏目内容id=："+id);
			this.hslProgramaContentLocalService.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "deletePrograma删除栏目："+e.getStackTrace());
			return false;
		}
	}

	@Override
	public ProgramaContentDTO createProgramaContent(CreateProgramaContentCommand command) {
		ProgramaContent programaContent=new ProgramaContent();
		programaContent.createProgramaContent(command);
		hslProgramaContentLocalService.save(programaContent);
		ProgramaContentDTO programaContentDTO=BeanMapperUtils.map(programaContent, ProgramaContentDTO.class);
		return programaContentDTO;
	}
}
