package hg.dzpw.app.common.adapter;

import java.util.ArrayList;
import java.util.List;

import hg.common.util.BeanMapperUtils;
import hg.dzpw.app.common.util.ModelUtils;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotBaseInfoDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotContactInfoDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotDTO;
import hg.dzpw.dealer.client.dto.scenicspot.ScenicSpotPicDTO;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.scenicspot.ScenicSpotBaseInfo;
import hg.dzpw.domain.model.scenicspot.ScenicSpotPic;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：景区经销商接口适配器
 * @类修改者：
 * @修改日期：2015-4-27下午4:47:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-27下午4:47:20
 */
public class ScenicSpotDealerApiAdapter {

	/**
	 * @方法功能说明：转换景区DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午5:27:30
	 * @修改内容：
	 * @参数：@param scenicSpot
	 * @参数：@return
	 * @return:ScenicSpotDTO
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static ScenicSpotDTO convertDTO(ScenicSpot scenicSpot) {
		
		if (scenicSpot == null)
			return null;
		
		ScenicSpotDTO dto = new ScenicSpotDTO();
		dto.setId(scenicSpot.getId());
		
		dto.setBaseInfo(BeanMapperUtils.map(scenicSpot.getBaseInfo(), ScenicSpotBaseInfoDTO.class));
		
		if (dto.getBaseInfo() == null)
			dto.setBaseInfo(new ScenicSpotBaseInfoDTO());
		
		String theme = "";
		if(scenicSpot.getBaseInfo().getThemeValue()!=null)
		{
		for(String value : scenicSpot.getBaseInfo().getThemeValue())
		{
			if("请选择".equals(value))
				continue;
			theme = theme + ScenicSpotBaseInfo.getThemeType().get(value)+" ";
		}
	    }
		dto.getBaseInfo().setTheme(theme);
		dto.getBaseInfo().setThemeValue(null);
		dto.getBaseInfo().setProvinceId(ModelUtils.getId(scenicSpot.getBaseInfo().getProvince()));
		dto.getBaseInfo().setCityId(ModelUtils.getId(scenicSpot.getBaseInfo().getCity()));
		dto.getBaseInfo().setAreaId(ModelUtils.getId(scenicSpot.getBaseInfo().getArea()));
		dto.setContactInfo(BeanMapperUtils.map(scenicSpot.getContactInfo(), ScenicSpotContactInfoDTO.class));
		
		List<ScenicSpotPicDTO> pics = new ArrayList<ScenicSpotPicDTO>();
		for(ScenicSpotPic pic :scenicSpot.getPics())
		{
			ScenicSpotPicDTO picDto = new ScenicSpotPicDTO();
			picDto.setName(pic.getName());
			picDto.setUrl(pic.getUrl());
			pics.add(picDto);
		}
		dto.setPics(pics);
		
		return dto;
	}

	/**
	 * @方法功能说明：转换景区Qo
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午5:22:18
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:ScenicSpotQo
	 * @throws
	 */
	public ScenicSpotQo convertQo(ScenicSpotQO QO) {
		ScenicSpotQo qo = new ScenicSpotQo();
		qo.setId(QO.getScenicSpotId());
		qo.setName(QO.getScenicSpotName());
		qo.setNameLike(QO.getNameLike());
		qo.setModifyDateBegin(QO.getModifyDateBegin());
		qo.setModifyDateEnd(QO.getModifyDateEnd());
		if (StringUtils.isNotBlank(QO.getProvinceId())) {
			ProvinceQo provinceQo = new ProvinceQo();
			provinceQo.setId(QO.getProvinceId());
			qo.setProvinceQo(provinceQo);
		}
		if (StringUtils.isNotBlank(QO.getCityId())) {
			CityQo cityQo = new CityQo();
			cityQo.setId(QO.getCityId());
			qo.setCityQo(cityQo);
		}
		qo.setRemoved(false);
		qo.setActivated(true);
		
		//图片
		qo.setFetchAllPic(true);
		return qo;
	}

}
