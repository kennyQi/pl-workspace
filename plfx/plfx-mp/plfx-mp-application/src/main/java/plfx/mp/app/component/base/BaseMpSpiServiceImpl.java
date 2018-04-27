package plfx.mp.app.component.base;

import hg.common.component.BaseModel;
import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.common.util.EntityConvertUtils;
import plfx.mp.pojo.dto.BaseMpDTO;
import plfx.mp.spi.inter.BaseMpSpiService;

/**
 * SPI基础门票查询Service抽象实现
 * 
 * @author zhurz
 * 
 * @param <DTO>
 * @param <QO>
 * @param <SERVICE>
 */
@SuppressWarnings("rawtypes")
@Transactional
public abstract class BaseMpSpiServiceImpl<DTO extends BaseMpDTO, QO extends BaseQo, SERVICE extends BaseServiceImpl> implements BaseMpSpiService<DTO, QO> {

	abstract protected SERVICE getService();

	abstract protected Class<DTO> getDTOClass();

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public DTO queryUnique(QO qo) {
		BaseModel entity = getService().queryUnique(qo);
		return EntityConvertUtils.convertEntityToDto(entity, getDTOClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<DTO> queryList(QO qo) {
		List<?> list = getService().queryList(qo);
		List<DTO> list2 = EntityConvertUtils.convertEntityToDtoList(list, getDTOClass());
		return list2;
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = getService().queryPagination(pagination);
		List<DTO> list = EntityConvertUtils.convertEntityToDtoList(pagination2.getList(), getDTOClass());
		pagination2.setList(list);
		return pagination2;
	}

}
