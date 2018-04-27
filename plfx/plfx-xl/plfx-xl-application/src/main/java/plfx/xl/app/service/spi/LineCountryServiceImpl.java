package plfx.xl.app.service.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineCountryLocalService;
import plfx.xl.pojo.dto.CountryDTO;
import plfx.xl.pojo.qo.LineCountryQo;
import plfx.xl.spi.inter.CountryService;

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
@Service("countryService")
public class LineCountryServiceImpl extends BaseXlSpiServiceImpl<CountryDTO, LineCountryQo, LineCountryLocalService> implements CountryService {

	@Autowired
	private LineCountryLocalService lineCountryLocalService;
	
	@Override
	public List<CountryDTO> queryLineCountryList(LineCountryQo qo) {
		
		return queryList(qo);
	}

	@Override
	protected LineCountryLocalService getService() {
		
		return lineCountryLocalService;
	}

	@Override
	protected Class<CountryDTO> getDTOClass() {
		// TODO Auto-generated method stub
		return CountryDTO.class;
	}


}
