package hsl.app.service.spi.ad;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.DeleteAdCommand;
import hg.service.ad.command.ad.UpdateAdCommand;
import hg.service.ad.command.ad.UpdateAdStatusCommand;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hg.service.ad.pojo.qo.ad.AdPositionQO;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.ad.spi.inter.AdPositionService;
import hg.service.ad.spi.inter.AdService;
import hsl.app.service.local.mp.ScenicSpotLocalService;
import hsl.pojo.command.ad.DeletePCHotCommand;
import hsl.pojo.command.ad.HslCreateAdCommand;
import hsl.pojo.command.ad.HslDeleteAdCommand;
import hsl.pojo.command.ad.HslUpdateAdCommand;
import hsl.pojo.command.ad.HslUpdateAdStatusCommand;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.pojo.qo.ad.HslAdQO;
import hsl.pojo.qo.mp.HslADQO;
import hsl.spi.inter.ad.HslAdService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HslAdServiceImpl  implements HslAdService {
	
	@Autowired
	private AdService adService;
	@Autowired
	private AdPositionService adPositionService;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;

	@Override
	public HslAdDTO queryUnique(HslAdQO qo) {
		return null;
	}

	@Override
	public List<HslAdDTO> queryList(HslAdQO qo) {
		return null;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		HslAdQO qo = (HslAdQO) pagination.getCondition();
		AdQO adQO = BeanMapperUtils.getMapper().map(qo, AdQO.class);
		pagination.setCondition(adQO);
		return adService.queryPagination(pagination);
	}

	@Override
	public List<HslAdDTO> getADList(HslADQO hslADQO) {
		return null;
	}

	@Override
	public List<HslAdPositionDTO> getPositionList(HslAdPositionQO hslADPositionQO) {
		AdPositionQO qo = BeanMapperUtils.map(hslADPositionQO, AdPositionQO.class);
		List<hg.service.ad.pojo.dto.ad.AdPositionDTO> adPositionDTOs = adPositionService.getAdPositionList(qo);
		List<HslAdPositionDTO> dtos = BeanMapperUtils.getMapper().mapAsList(adPositionDTOs, HslAdPositionDTO.class);
		return dtos;
	}

	@Override
	public HslAdDTO createAd(HslCreateAdCommand command) throws Exception {
		CreateAdCommand createAdCommand = BeanMapperUtils.map(command, CreateAdCommand.class);
		AdDTO adDTO = adService.createAd(createAdCommand);
		HslAdDTO hslAdDTO = BeanMapperUtils.map(adDTO, HslAdDTO.class);
		return hslAdDTO;
	}

	@Override
	public void deletAd(HslDeleteAdCommand command) {
		DeleteAdCommand deleteAdCommand = BeanMapperUtils.map(command, DeleteAdCommand.class);
		adService.deletAd(deleteAdCommand);
		if(command.getPositionId()!=null&&command.getPositionId().equals(HslAdConstant.HOT)){
			DeletePCHotCommand deletePCHotCommand = new DeletePCHotCommand();
			deletePCHotCommand.setAdId(command.getAdId());
			scenicSpotLocalService.deleteHotSpot(deletePCHotCommand);
		}
	}

	@Override
	public HslAdDTO modifyAd(HslUpdateAdCommand command) {
		UpdateAdCommand updateAdCommand = BeanMapperUtils.map(command, UpdateAdCommand.class);
		AdDTO adDTO = adService.UpdateAd(updateAdCommand);
		HslAdDTO dto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
		return dto;
	}

	@Override
	public HslAdDTO queryAd(HslAdQO qo) {
		AdQO adQO = BeanMapperUtils.map(qo, AdQO.class);
		AdDTO adDTO = adService.queryAd(adQO);
		HslAdDTO dto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
		return dto;
	}

	@Override
	public HslAdDTO modifyAdStatus(HslUpdateAdStatusCommand command) {
		UpdateAdStatusCommand updateAdStatusCommand = BeanMapperUtils.map(command, UpdateAdStatusCommand.class);
		AdDTO adDTO = adService.UpdateAdStatus(updateAdStatusCommand);
		HslAdDTO dto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
		return dto;
	}



}
