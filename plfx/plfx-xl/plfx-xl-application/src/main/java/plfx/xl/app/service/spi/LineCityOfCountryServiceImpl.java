package plfx.xl.app.service.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineCityOfCountryLocalService;
import plfx.xl.pojo.dto.CityOfCountryDTO;
import plfx.xl.pojo.qo.LineCityQo;
import plfx.xl.spi.inter.CityOfCountryService;

/****
 * 
 * @类功能说明：国家service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日下午2:01:47
 * @版本：V1.0
 *
 */
@Service("cityOfCountryService")
public class LineCityOfCountryServiceImpl extends BaseXlSpiServiceImpl<CityOfCountryDTO, LineCityQo, LineCityOfCountryLocalService> implements CityOfCountryService {

	@Autowired
	private LineCityOfCountryLocalService lineCityOfCountryLocalService;
	
	@Override
	public List<CityOfCountryDTO> queryLineCityList(LineCityQo qo) {
	
		return queryList(qo);
	}

	@Override
	protected LineCityOfCountryLocalService getService() {
	
		return lineCityOfCountryLocalService;
	}

	@Override
	protected Class<CityOfCountryDTO> getDTOClass() {

		return CityOfCountryDTO.class;
	}

	


}
