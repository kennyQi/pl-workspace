package hsl.app.service.spi.ad;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.service.ad.pojo.dto.ad.AdPositionDTO;
import hg.service.ad.pojo.qo.ad.AdPositionQO;
import hg.service.ad.spi.inter.AdPositionService;
import hsl.pojo.dto.ad.HslAdPositionDTO;
import hsl.pojo.qo.ad.HslAdPositionQO;
import hsl.spi.inter.ad.HslAdPositionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 广告位service实现
 */
@Service
public class HslAdPositionServiceImpl implements HslAdPositionService{
	
	@Autowired
	private AdPositionService adPositionService;
	
	@Override
	public HslAdPositionDTO queryUnique(HslAdPositionQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HslAdPositionDTO> queryList(HslAdPositionQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		return null;
	}

	@Override
	public List<HslAdPositionDTO> getAdPositionList(HslAdPositionQO qo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HslAdPositionDTO queryAdPosition(HslAdPositionQO hslAdPositionQO) {
		AdPositionQO adPositionQO = BeanMapperUtils.map(hslAdPositionQO, AdPositionQO.class);
		AdPositionDTO AdPositionDTO = adPositionService.queryUnique(adPositionQO);
		HslAdPositionDTO hslAdPositionDTO = BeanMapperUtils.map(AdPositionDTO, HslAdPositionDTO.class);
		return hslAdPositionDTO;
	}




}
