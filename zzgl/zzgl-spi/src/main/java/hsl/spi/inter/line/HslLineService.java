package hsl.spi.inter.line;

import java.util.List;

import hsl.pojo.command.clickRecord.PLZXClickRecordCommand;
import hsl.pojo.command.line.InitLineCommand;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.qo.log.PLZXClickRecordQo;
import hsl.spi.inter.BaseSpiService;
import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;

public interface HslLineService extends BaseSpiService<LineDTO, HslLineQO>{
	
	/**
	 * 更新线路
	 * @throws Exception
	 */
	public void updateLineData(XLUpdateLineMessageApiCommand command) throws Exception;
	
	/**
	 * 同步线路数据
	 * @param command
	 * @throws Exception
	 */
	public void initLineData(InitLineCommand command) throws Exception;
	
	/**
	 * 获取同步线路进度
	 * @return
	 */
	public double getProgress();
	
	/**
	 * 停止同步
	 */
	public void stopInit();
	
	/**
	 * @方法功能说明：从分销查询最新线路信息
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月3日上午10:58:07
	 * @修改内容：
	 * @参数：@param lineQO
	 * @参数：@return
	 * @return:LineDTO
	 * @throws
	 */
	public LineDTO queryLine(HslLineQO lineQO);
	
	
	public List<LineDTO> queryUserClickRecord(PLZXClickRecordQo plzxClickRecordQo);
	
}
