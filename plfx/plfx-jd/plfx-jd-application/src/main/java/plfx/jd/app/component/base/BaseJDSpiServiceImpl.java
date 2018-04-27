package plfx.jd.app.component.base;

import hg.common.component.BaseModel;
import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;

import java.util.List;

import plfx.jd.app.common.util.EntityConvertUtils;
import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;
import plfx.jd.spi.BaseJDSpiService;

/**
 * @类功能说明：SPI基础门票查询Service抽象实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月02日下午4:36:56
 * @版本：V1.0
 * @param <DTO>
 * @param <QO>
 * @param <SERVICE>
 */
@SuppressWarnings("rawtypes")
public abstract class BaseJDSpiServiceImpl<DTO extends BaseJDDTO, QO extends BaseQo, SERVICE extends BaseServiceImpl> implements BaseJDSpiService<DTO, QO> {

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