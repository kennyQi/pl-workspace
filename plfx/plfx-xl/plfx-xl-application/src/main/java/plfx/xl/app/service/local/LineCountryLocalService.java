package plfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.xl.app.dao.CountryDAO;
import plfx.xl.domain.model.line.Countrys;
import plfx.xl.pojo.qo.LineCountryQo;

/****
 * 
 * @类功能说明：国家service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日下午2:38:35
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class LineCountryLocalService extends BaseServiceImpl<Countrys, LineCountryQo, CountryDAO> {

	@Autowired
	private  CountryDAO countryDAO;
	
	@Override
	protected CountryDAO getDao() {
		
		return countryDAO;
	}


}
