package slfx.xl.spi.inter;


import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.line.LineSnapshotDTO;
import slfx.xl.pojo.qo.LineSnapshotQO;
/**
 * 
 * @类功能说明：线路快照Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月4日下午4:25:29
 * @版本：V1.0
 *
 */
public interface LineSnapshotService extends BaseXlSpiService<LineSnapshotDTO, LineSnapshotQO>{
	/**
	 * 
	 * @方法功能说明：创建线路快照
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月4日下午2:52:25
	 * @修改内容：
	 * @参数：@param line
	 * @return:void
	 * @throws
	 */
	public void createLineSnapshot(LineDTO line);
}
