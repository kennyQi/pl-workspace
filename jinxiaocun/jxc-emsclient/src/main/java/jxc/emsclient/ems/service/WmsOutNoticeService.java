package jxc.emsclient.ems.service;


import jxc.emsclient.ems.command.stockOut.CreateOrderCommand;
import jxc.emsclient.ems.command.stockOut.CreateStockOutCommand;
import jxc.emsclient.ems.command.stockOut.RemoveAllStockOutCommand;
import jxc.emsclient.ems.dto.response.CommonResponseDTO;

public interface WmsOutNoticeService {
	
	/**
	 * 创建销售出库通知单
	 */
	public CommonResponseDTO createOrderNotice(CreateOrderCommand createOrderCommand);
	
	/**
	 * 创建非销售出库通知单
	 */
	public CommonResponseDTO createStockOutNotice(CreateStockOutCommand createStockOutCommand);
	
	/**
	 * 创建取消出库(包括销售出库和非销售出库)通知单
	 */
	public CommonResponseDTO removeAllStockOutNotice(RemoveAllStockOutCommand removeAllStockOutCommand);
}
