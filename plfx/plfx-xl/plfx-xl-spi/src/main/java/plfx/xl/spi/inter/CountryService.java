package plfx.xl.spi.inter;


import java.util.List;

import plfx.xl.pojo.dto.CountryDTO;
import plfx.xl.pojo.qo.LineCountryQo;


/***
 * 
 * @类功能说明：国家查询service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日上午10:02:14
 * @版本：V1.0
 *
 */
public interface CountryService extends BaseXlSpiService<CountryDTO, LineCountryQo> {

	/***
	 * 
	 * @方法功能说明：查询国际列表
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月17日下午1:28:14
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<CountryDTO>
	 * @throws
	 */
	public List<CountryDTO> queryLineCountryList(LineCountryQo qo);
	
}
