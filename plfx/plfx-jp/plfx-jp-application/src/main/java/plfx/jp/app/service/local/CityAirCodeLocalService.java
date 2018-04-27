package plfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.jp.app.dao.CityAirCodeDAO;
import plfx.jp.domain.model.CityAirCode;
import plfx.jp.pojo.dto.flight.CityAirCodeDTO;
import plfx.jp.qo.api.CityAirCodeQO;

/**
 * 
 * @类功能说明：LOCAL城市机场三字码SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月4日下午2:23:00
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class CityAirCodeLocalService  extends BaseServiceImpl<CityAirCode, CityAirCodeQO, CityAirCodeDAO>{

	@Autowired
	private CityAirCodeDAO cityAirCodeDAO;
	
	@Override
	protected CityAirCodeDAO getDao() {
		return cityAirCodeDAO;
	}

	public List<CityAirCodeDTO> queryCityAirCodeList(){
		
		List<CityAirCodeDTO> cityAirCodeDTOList = null;
		
		List<CityAirCode> cityAirCodeList = cityAirCodeDAO.queryList(new CityAirCodeQO());
		
		if(cityAirCodeList != null && cityAirCodeList.size() > 0){
			cityAirCodeDTOList = new ArrayList<CityAirCodeDTO>();
			CityAirCodeDTO dto = null;
			
			for (CityAirCode cityAirCode : cityAirCodeList) {
				dto = new CityAirCodeDTO();
				dto.setId(cityAirCode.getId() == null ? "":cityAirCode.getId());
				dto.setCityName(cityAirCode.getName() == null ? "" : cityAirCode.getName());
				dto.setCityAirCode(cityAirCode.getCityAirCode() == null ? "" : cityAirCode.getCityAirCode());
				dto.setCityJianPin(cityAirCode.getCityJianPin() == null ? "" : cityAirCode.getCityJianPin());
				dto.setSort(cityAirCode.getSort() == null ? 0 : cityAirCode.getSort());
				dto.setCityQuanPin(cityAirCode.getCityQuanPin() == null ? "" : cityAirCode.getCityQuanPin());
			
				cityAirCodeDTOList.add(dto);
			}
		}
		
		return cityAirCodeDTOList;
	}
	
}
