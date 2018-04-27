package jxc.emsclient.ems.service;

import jxc.emsclient.ems.command.stockIn.CreateStockInCommand;
import jxc.emsclient.ems.command.stockIn.RemoveStockInCommand;
import jxc.emsclient.ems.dto.response.CommonResponseDTO;

public interface WmsInNoticeService {
	
	/**
	 * 创建入库通知单
	 */
	public CommonResponseDTO createStockInNotice(CreateStockInCommand createStockInCommand);
	
	/**
	 * 取消入库通知单
	 */
	public CommonResponseDTO removeStockInNotice(RemoveStockInCommand removeStockInCommand);

}
