package hsl.app.service.spi.programa;
import java.io.Serializable;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.common.util.EntityConvertUtils;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.programa.HslProgramaLocalService;
import hsl.domain.model.programa.Programa;
import hsl.pojo.command.Programa.CreateProgramaCommand;
import hsl.pojo.command.Programa.UpdateProgramaStatusCommand;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.spi.inter.programa.HslProgramaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
/**
 * @类功能说明：栏目spi服务类
 * @公司名称：浙江票量科技有限公司
 * @作者：zhaows
 * @创建时间：2015年4月21日
 */
@Service
public class HslProgramaServiceImpl extends BaseSpiServiceImpl<ProgramaDTO, HslProgramaQO, HslProgramaLocalService> implements HslProgramaService{
	@Autowired
	private HslProgramaLocalService hslProgramaLocalService;
	@Override
	public ProgramaDTO queryUnique(HslProgramaQO qo) {
		ProgramaDTO dto = super.queryUnique(qo);
		return dto;
	}
	@Override
	protected HslProgramaLocalService getService() {
		return hslProgramaLocalService;
	}

	@Override
	protected Class<ProgramaDTO> getDTOClass() {
		return ProgramaDTO.class;
	}
	/**
	 * 分页查询栏目
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月21日15.17
	 * @param pageSize
	 * @param pageNo
	 * @param command 
	 * @return
	 */
	@Override
	@Transactional
	public Pagination queryPagination(Pagination pagination) {
		try {
			Pagination pagination2 = hslProgramaLocalService.queryPagination(pagination);
			List<ProgramaDTO> list = EntityConvertUtils.convertEntityToDtoList(pagination2.getList(), getDTOClass());
			if(list!=null&&list.size()>0){
				pagination2.setList(list);
			}
			return pagination2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		 
		
	}
	@Override
	public ProgramaDTO createPrograma(CreateProgramaCommand command){
		Programa programa=hslProgramaLocalService.createPrograma(command);
		ProgramaDTO programaDTO=BeanMapperUtils.map(programa, ProgramaDTO.class);
		return programaDTO;
		
		
	}
	@Override
	public boolean updateProgramaStutas(UpdateProgramaStatusCommand command) {
		try {
			if(command==null||StringUtils.isEmpty(command.getId())){
				return false;
			}
			HgLogger.getInstance().info("zhaows", "updateProgramaStutas栏目状态更新："+JSON.toJSONString(command));
			HslProgramaQO qo=new HslProgramaQO();
			qo.setId(command.getId());
			Programa programa=hslProgramaLocalService.queryUnique(qo);
			programa.setStatus(command.getStatus());
			this.hslProgramaLocalService.update(programa);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "updateProgramaStutas栏目状态更新："+e.getStackTrace());
			return false;
		}
	}
	@Override
	public boolean deletePrograma(String ids) {
		try {
			HgLogger.getInstance().info("zhaows", "deletePrograma删除栏目id=："+ids);
			Serializable[] idAry=ids.split(",");
			hslProgramaLocalService.deleteByIds(idAry);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "deletePrograma删除栏目："+e.getStackTrace());
			return false;
		}
	}
	
}
