package slfx.jp.spi.inter;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;

import java.util.List;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：SPI JP SERVICE 基础查询接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:09:16
 * @版本：V1.0
 *
 * @param <DTO>
 * @param <QO>
 */
public interface BaseJpSpiService<DTO extends BaseJpDTO, QO extends BaseQo> {

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
