package pay.record.spi.inter;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;

import java.util.List;

import pay.record.pojo.dto.BasePayRecordDTO;

/**
 * 
 * @类功能说明：基础接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月20日下午4:24:44
 * @版本：V1.0
 *
 * @param <DTO>
 * @param <QO>
 */
public interface BasePayRecordSpiService<DTO extends BasePayRecordDTO, QO extends BaseQo> {

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
