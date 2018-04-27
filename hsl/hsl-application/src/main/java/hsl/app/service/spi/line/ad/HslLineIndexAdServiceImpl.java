package hsl.app.service.spi.line.ad;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.DeleteAdCommand;
import hg.service.ad.command.ad.UpdateAdCommand;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.ad.spi.inter.AdService;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.line.ad.LineIndexAdLocalService;
import hsl.domain.model.xl.ad.LineIndexAd;
import hsl.pojo.command.line.ad.CreateLineIndexAdCommand;
import hsl.pojo.command.line.ad.ModifyLineIndexAdCommand;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.line.ad.LineIndexAdDTO;
import hsl.pojo.exception.LineIndexAdException;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.line.ad.LineIndexAdQO;
import hsl.pojo.qo.mp.HslPCHotScenicSpotQO;
import hsl.spi.inter.line.ad.HslLineIndexAdService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @类功能说明：线路首页广告业务实现类
 * @类修改者：
 * @修改日期：2015年4月22日下午5:11:06
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月22日下午5:11:06
 */
@Service
public class HslLineIndexAdServiceImpl extends BaseSpiServiceImpl<LineIndexAdDTO, LineIndexAdQO, LineIndexAdLocalService> implements HslLineIndexAdService{
	@Autowired
	private LineIndexAdLocalService lineIndexAdLocalService;
	@Autowired
	private AdService adService;
	@Override
	public void createLineIndexAd(CreateLineIndexAdCommand command) throws LineIndexAdException {
		CreateAdCommand createAdCommand = BeanMapperUtils.map(command, CreateAdCommand.class);
		//先判断是否存在
		AdQO adQO = new AdQO();
		adQO.setTitle(command.getTitle());
		List<AdDTO> ads = adService.queryList(adQO);
		if(ads!=null&&ads.size()>0){
			throw new LineIndexAdException(LineIndexAdException.RESULT_LINEINDEXAD_EXIST,"线路广告已经存在");
		}
		AdDTO adDTO = null;
		try {
			adDTO = adService.createAd(createAdCommand);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "添加线路首页广告保存广告失败"+HgLogger.getStackTrace(e));
		}
		if(adDTO!=null){
			try {
				//远程保存广告成功
				command.setAdId(adDTO.getId());
				lineIndexAdLocalService.createLineIndexAd(command);
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("chenxy", "添加线路首页广告保存明细失败"+HgLogger.getStackTrace(e));
				//本地保存热门景点的明细失败，广告回滚
				try {
					DeleteAdCommand adCommand = new DeleteAdCommand();
					adCommand.setAdId(adDTO.getId());
					adService.deletAd(adCommand);
				} catch (Exception e2) {
					e2.printStackTrace();
					HgLogger.getInstance().error("zhuxy", "添加线路首页广告回滚广告失败"+HgLogger.getStackTrace(e2));
				}
				
			}
		}
	}

	@Override
	public void modifyLineIndexAd(ModifyLineIndexAdCommand command) throws LineIndexAdException{
		UpdateAdCommand adCommand = BeanMapperUtils.map(command, UpdateAdCommand.class);
		if(StringUtils.isBlank(command.getId())){
			throw new LineIndexAdException(LineIndexAdException.RESULT_LINEINDEXAD_NOEXIST,"特价门票主键不存在");
		}
		if(StringUtils.isBlank(command.getAdId())&&StringUtils.isNotBlank(command.getId())){
			//广告id不存在而本地id存在，则查询本地数据库找出广告id
			LineIndexAdQO qo=new LineIndexAdQO();
			qo.setId(command.getId());
			LineIndexAd lineIndexAd = lineIndexAdLocalService.queryUnique(qo);
			if(lineIndexAd!=null){
				command.setAdId(lineIndexAd.getAdId());
			}else{
				throw new LineIndexAdException(LineIndexAdException.RESULT_LINEINDEXAD_NOEXIST,"特价门票不存在");
			}
		}
		adCommand.setId(command.getAdId());
		AdDTO adDTO = null;
		try {
			adDTO = adService.UpdateAd(adCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(adDTO!=null){
			//远程保存广告成功
			lineIndexAdLocalService.modifyLineIndexAd(command);
		}
	}

	@Override
	public void deleteLineIndexAd(String id) throws LineIndexAdException{
		LineIndexAdQO qo=new LineIndexAdQO();
		qo.setId(id);
		LineIndexAd lineIndexAd=lineIndexAdLocalService.queryUnique(qo);
		try {
			DeleteAdCommand adCommand = new DeleteAdCommand();
			adCommand.setAdId(lineIndexAd.getAdId());
			adService.deletAd(adCommand);
		} catch (Exception e2) {
			e2.printStackTrace();
			HgLogger.getInstance().error("zhuxy", "添加线路首页广告回滚广告失败"+HgLogger.getStackTrace(e2));
		}
		lineIndexAdLocalService.deleteLineIndexAd(id);
	}
	@Override
	public Pagination queryLineIndexAds(Pagination pagination) {
		LineIndexAdQO qo = (LineIndexAdQO)pagination.getCondition();
		AdQO adQO = BeanMapperUtils.getMapper().map(qo, AdQO.class);
		pagination.setCondition(adQO);
		pagination = adService.queryPagination(pagination);
		pagination.setCondition(qo);
		pagination = lineIndexAdLocalService.queryLineIndexAds(pagination);
		return pagination;
	}
	@Override
	protected LineIndexAdLocalService getService() {
		return lineIndexAdLocalService;
	}

	@Override
	protected Class<LineIndexAdDTO> getDTOClass() {
		return LineIndexAdDTO.class;
	}
	
}
