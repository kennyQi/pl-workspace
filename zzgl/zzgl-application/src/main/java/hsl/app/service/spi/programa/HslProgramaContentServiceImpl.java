package hsl.app.service.spi.programa;
import com.alibaba.fastjson.JSON;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hg.service.ad.command.ad.DeleteAdCommand;
import hg.service.ad.spi.inter.AdService;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.line.ad.LineIndexAdLocalService;
import hsl.app.service.local.programa.HslProgramaContentLocalService;
import hsl.domain.model.programa.ProgramaContent;
import hsl.domain.model.xl.ad.LineIndexAd;
import hsl.pojo.command.Programa.CreateProgramaContentCommand;
import hsl.pojo.command.Programa.UpdateProgramaContentCommand;
import hsl.pojo.dto.programa.ProgramaContentDTO;
import hsl.pojo.qo.line.ad.LineIndexAdQO;
import hsl.pojo.qo.programa.HslProgramaContentQO;
import hsl.spi.inter.programa.HslProgramaContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class HslProgramaContentServiceImpl extends BaseSpiServiceImpl<ProgramaContentDTO, HslProgramaContentQO,HslProgramaContentLocalService>implements
		HslProgramaContentService {
	@Autowired
	private HslProgramaContentLocalService hslProgramaContentLocalService;
	@Autowired
	private LineIndexAdLocalService lineIndexAdLocalService;
	@Autowired
	private AdService adService;
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
			HgLogger.getInstance().error("zhaows", "updateProgramaStutas栏目状态更新："+HgLogger.getStackTrace(e));
			return false;
		}
	}
	@Override
	public boolean deleteProgramaContent(String id) {
		try {
			HgLogger.getInstance().info("chenxy", "deleteProgramaContent删除栏目内容id=："+id);
			//删除对应的广告
			LineIndexAdQO qo=new LineIndexAdQO();
			qo.setContentId(id);
			List<LineIndexAd> listLineIndexAd=lineIndexAdLocalService.queryList(qo);
			for(LineIndexAd LineIndexAd:listLineIndexAd){
				lineIndexAdLocalService.deleteById(LineIndexAd.getId());
				DeleteAdCommand daCommand=new DeleteAdCommand();
				daCommand.setAdId(LineIndexAd.getAdId());
				adService.deletAd(daCommand);
			}
			this.hslProgramaContentLocalService.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().info("chenxy", "deleteProgramaContent删除栏目："+HgLogger.getStackTrace(e));
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
