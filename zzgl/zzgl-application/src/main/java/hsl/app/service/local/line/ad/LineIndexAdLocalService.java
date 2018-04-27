package hsl.app.service.local.line.ad;
import java.util.ArrayList;
import java.util.List;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hsl.app.dao.line.ad.LineIndexAdDAO;
import hsl.domain.model.xl.ad.LineIndexAd;
import hsl.pojo.command.line.ad.CreateLineIndexAdCommand;
import hsl.pojo.command.line.ad.ModifyLineIndexAdCommand;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.line.ad.LineIndexAdDTO;
import hsl.pojo.qo.line.ad.LineIndexAdQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class LineIndexAdLocalService extends BaseServiceImpl<LineIndexAd, LineIndexAdQO, LineIndexAdDAO>{
	@Autowired
	private LineIndexAdDAO lineIndexAdDAO;
	@Override
	protected LineIndexAdDAO getDao() {
		return lineIndexAdDAO;
	}
	public void createLineIndexAd(CreateLineIndexAdCommand command) {
		LineIndexAd lineIndexAd=new LineIndexAd();
		lineIndexAd.createIndexAd(command);
		lineIndexAdDAO.save(lineIndexAd);
	}

	public void modifyLineIndexAd(ModifyLineIndexAdCommand command) {
		LineIndexAdQO qo=new LineIndexAdQO();
		qo.setId(command.getId());
		LineIndexAd lineIndexAd=lineIndexAdDAO.queryUnique(qo);
		lineIndexAd.modifyLineIndexAd(command);
		lineIndexAdDAO.update(lineIndexAd);
	}
	public void deleteLineIndexAd(String id) {
		LineIndexAdQO qo=new LineIndexAdQO();
		qo.setId(id);
		LineIndexAd lineIndexAd=lineIndexAdDAO.queryUnique(qo);
		lineIndexAdDAO.delete(lineIndexAd);
	}
	/**
	 * @方法功能说明：根据广告查询线路首页广告
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月23日上午10:13:13
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public  Pagination queryLineIndexAds(Pagination pagination){
		int count=0;
		List<AdDTO> adlist = (List<AdDTO>)pagination.getList();
		List<LineIndexAdDTO> dtos = new ArrayList<LineIndexAdDTO>();
		if(adlist!=null&&adlist.size()>0){
			LineIndexAdQO lineIndexAdQO = (LineIndexAdQO)pagination.getCondition();
			for(AdDTO adDTO : adlist){
				lineIndexAdQO.setAdId(adDTO.getId());
				List<LineIndexAd> list = lineIndexAdDAO.queryList(lineIndexAdQO);
				if(list!=null&&list.size()>0){
					LineIndexAdDTO dto = BeanMapperUtils.map(list.get(0), LineIndexAdDTO.class);
					HslAdDTO addto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
					dto.setAdBaseInfo(addto.getAdBaseInfo());
					dto.setAdStatus(addto.getAdStatus());
					dto.setPosition(addto.getPosition());
					dtos.add(dto);
					count++;
				}
			}
			
		}
		LineIndexAdQO qo = (LineIndexAdQO)pagination.getCondition();
		if(StringUtils.isNotBlank(qo.getAdId())){
			pagination.setTotalCount(count);
		}
		pagination.setList(dtos);
		return pagination;
	}
}
