package pay.record.app.service.base;

import hg.common.component.BaseModel;
import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;

import java.util.List;

import pay.record.app.common.util.EntityConvertUtils;
import pay.record.pojo.dto.BasePayRecordDTO;
import pay.record.spi.inter.BasePayRecordSpiService;

/**
 * 
 * @类功能说明：SPI基础门票查询Service抽象实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月29日下午5:15:50
 * @版本：V1.0
 *
 * @param <DTO>
 * @param <QO>
 * @param <SERVICE>
 */
@SuppressWarnings("rawtypes")
public abstract class BasePayRecordSpiServiceImpl<DTO extends BasePayRecordDTO, QO extends BaseQo, SERVICE extends BaseServiceImpl> implements BasePayRecordSpiService<DTO, QO> {

	abstract protected SERVICE getService();

	abstract protected Class<DTO> getDTOClass();

	@SuppressWarnings("unchecked")
	@Override
//	@Transactional(readOnly = true)
	public DTO queryUnique(QO qo) {
		BaseModel entity = getService().queryUnique(qo);
		return EntityConvertUtils.convertEntityToDto(entity, getDTOClass());
	}

	@SuppressWarnings("unchecked")
	@Override
//	@Transactional(readOnly = true)
	public List<DTO> queryList(QO qo) {
		List<?> list = getService().queryList(qo);
		List<DTO> list2 = EntityConvertUtils.convertEntityToDtoList(list, getDTOClass());
		return list2;
	}

	@Override
//	@Transactional(readOnly = true)
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = getService().queryPagination(pagination);
		List<DTO> list = EntityConvertUtils.convertEntityToDtoList(pagination2.getList(), getDTOClass());
		pagination2.setList(list);
		return pagination2;
	}

}
