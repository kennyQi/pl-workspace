package hsl.spi.inter.clickRecord;

import hsl.pojo.command.clickRecord.PLZXClickRecordCommand;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.qo.log.PLZXClickRecordQo;

import java.util.List;

/**
 * 
 * @类功能说明：票量直销点击记录
 * @类修改者：
 * @修改日期：2015年7月7日上午9:42:21
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月7日上午9:42:21
 */
public interface PLZXClickRecordService {
	/**
	 * 新建用户浏览记录
	 * @param command
	 */
	public void createPLZXClickRecord(PLZXClickRecordCommand command);

	/**
	 * 查询用户浏览记录
	 * @param userClickRecordQO
	 * @return
	 */
	public List<PLZXClickRecord> queryPLZXClickRecord(PLZXClickRecordQo plzxClickRecordQo);
}
