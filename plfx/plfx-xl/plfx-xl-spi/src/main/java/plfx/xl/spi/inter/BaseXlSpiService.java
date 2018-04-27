package plfx.xl.spi.inter;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;

import java.util.List;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：基础接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月2日下午6:12:35
 * @版本：V1.0
 *
 * @param <DTO>
 * @param <QO>
 */
public interface BaseXlSpiService<DTO extends BaseXlDTO, QO extends BaseQo> {

	/**
	 * 查询唯一
	 * @param qo
	 * @return
	 */
	public DTO queryUnique(QO qo);

	/**
	 * 查询列表
	 * @param qo
	 * @return
	 */
	public List<DTO> queryList(QO qo);

	/**
	 * 分页查询
	 * @param pagination
	 * @return
	 */
	public Pagination queryPagination(Pagination pagination);

}
