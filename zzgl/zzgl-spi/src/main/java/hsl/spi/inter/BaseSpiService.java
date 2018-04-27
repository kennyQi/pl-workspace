package hsl.spi.inter;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hsl.pojo.dto.BaseDTO;

import java.util.List;

/**
 * SPI MP SERVICE 基础查询接口
 * 
 * @author zhurz
 * 
 * @param <DTO>
 * @param <QO>
 */
public interface BaseSpiService<DTO extends BaseDTO, QO extends BaseQo> {

	/**
	 * 查询唯一
	 * 
	 * @param qo
	 * @return
	 */
	public DTO queryUnique(QO qo);

	/**
	 * 查询列表
	 * 
	 * @param qo
	 * @return
	 */
	public List<DTO> queryList(QO qo);

	/**
	 * 分页查询
	 * 
	 * @param pagination
	 * @return
	 */
	public Pagination queryPagination(Pagination pagination);

}
