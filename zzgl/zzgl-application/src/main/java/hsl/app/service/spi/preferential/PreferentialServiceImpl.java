package hsl.app.service.spi.preferential;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import hsl.app.service.local.preferential.PreferentialLocalService;
import hsl.pojo.command.CreatePreferentialCommand;
import hsl.pojo.command.UpdatePreferentialCommand;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.preferential.PreferentialDTO;
import hsl.pojo.qo.preferential.HslPreferentialQO;
import hsl.spi.inter.preferential.HslPreferentialService;
@Service
public class PreferentialServiceImpl extends BaseSpiServiceImpl<PreferentialDTO, HslPreferentialQO,PreferentialLocalService>implements HslPreferentialService{
	@Autowired
	private PreferentialLocalService PreferentialLocalService;
	@Autowired
	private AdService adService;
	@Override
	protected PreferentialLocalService getService() {
		return PreferentialLocalService;
	}

	@Override
	protected Class<PreferentialDTO> getDTOClass() {
		return PreferentialDTO.class;
	}
	/**
	 * 创建特惠专区
	 * @throws Exception 
	 */
	public void createPreferential(
			CreatePreferentialCommand command) {
		command.setPositionId(HslAdConstant.THZQ);
		CreateAdCommand createAdCommand = BeanMapperUtils.map(command, CreateAdCommand.class);
		AdDTO adDTO = null;
		try {
			adDTO = adService.createAd(createAdCommand);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","createPreferential-->远程保存特惠专区失败" + HgLogger.getStackTrace(e));
		}
		if(adDTO!=null){//远程保存广告成功  保存本地
			try{
				command.setAdId(adDTO.getId());
				PreferentialLocalService.CreatePreferential(command);
			}catch(Exception e){
				e.printStackTrace();
				HgLogger.getInstance().error("zhaows", "createPreferential-->本地保存特惠专区失败"+HgLogger.getStackTrace(e));
				//本地保存失败删除已经保存的广告
				try{
					DeleteAdCommand adCommand = new DeleteAdCommand();
					adCommand.setAdId(adDTO.getId());
					adService.deletAd(adCommand);
				}catch(Exception ex){
					ex.printStackTrace();
					HgLogger.getInstance().error("zhaows", "createPreferential-->保存特惠专区的时候回滚添加广告操作失败"+HgLogger.getStackTrace(ex));
				}
			}
		}
		
	}

	public void modifyPreferential(
			UpdatePreferentialCommand command) throws Exception {
		UpdateAdCommand adCommand = BeanMapperUtils.map(command, UpdateAdCommand.class);
		if(StringUtils.isBlank(command.getId())){
			throw new Exception("特惠专区主键不存在");
		}
		try {
			if(StringUtils.isNotBlank(command.getAdId())&&StringUtils.isNotBlank(command.getId())){
				//广告id不存在而本地id存在，则查询本地数据库找出广告id
				PreferentialDTO ppDTO = PreferentialLocalService.getPreferential(command.getId());
				//跟新本地信息
				
				if(ppDTO!=null){
					command.setAdId(ppDTO.getAdId());
				}else{
					throw new Exception("特惠专区不存在");
				}
				AdDTO adDTO=null;
				adCommand.setId(command.getAdId());
				adDTO=adService.UpdateAd(adCommand);//更改远程信息
				if(adDTO!=null){
					PreferentialLocalService.updatePreferential(command);	
				}
			}else{
				System.out.println("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void deletePreferential(UpdatePreferentialCommand command) {
		try {
			if(StringUtils.isBlank(command.getId())||StringUtils.isBlank(command.getAdId())){
				throw new Exception("数据不完整");
			}
			DeleteAdCommand adCommand = BeanMapperUtils.map(command, DeleteAdCommand.class);
			adService.deletAd(adCommand);
			PreferentialLocalService.deletePreferential(command);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "deletePreferential-->删除特惠专区操作失败"+HgLogger.getStackTrace(e));
		}
	}

	@Override
	public PreferentialDTO queryUnique(HslPreferentialQO qo) {
		PreferentialDTO dto=PreferentialLocalService.getPreferential(qo.getId());//传入id
		if(dto==null){
			return null;
		}
		AdQO adQO = new AdQO();
		adQO.setId(dto.getAdId());
		AdDTO adDTO = adService.queryUnique(adQO);
		if(adDTO==null){
			return null;
		}
		HslAdDTO dto2 = BeanMapperUtils.map(adDTO, HslAdDTO.class);
		dto.setAdBaseInfo(dto2.getAdBaseInfo());
		dto.setAdStatus(dto2.getAdStatus());
		dto.setPosition(dto2.getPosition());
		return dto;
	}

	@Override
	public List<PreferentialDTO> queryList(HslPreferentialQO qo) {
		List<PreferentialDTO> list=null;
		try {
			list = PreferentialLocalService.queryPreferentialList(qo);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "queryList-->查询特惠专区list失败"+HgLogger.getStackTrace(e));
		}
		return list;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		try {
			HslPreferentialQO qo = (HslPreferentialQO)pagination.getCondition();
			AdQO adQO = BeanMapperUtils.getMapper().map(qo, AdQO.class);
			adQO.setPositionId(HslAdConstant.THZQ);
			pagination.setCondition(adQO);
			pagination = adService.queryPagination(pagination);
			pagination.setCondition(qo);
			pagination = PreferentialLocalService.getPreferentialList(pagination);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "queryPagination-->查询特惠专区分页失败"+HgLogger.getStackTrace(e));
		}
		return pagination;
	}

	/*@Override
	public Pagination getPreferentialList(Pagination pagination) {
		HslPreferentialQO qo = (HslPreferentialQO)pagination.getCondition();
		AdQO adQO = BeanMapperUtils.getMapper().map(qo, AdQO.class);
		pagination.setCondition(adQO);
		pagination = adService.queryPagination(pagination);
		pagination.setCondition(qo);
		pagination = PreferentialLocalService.getPreferentialList(pagination);
		return pagination;
	}*/

}
