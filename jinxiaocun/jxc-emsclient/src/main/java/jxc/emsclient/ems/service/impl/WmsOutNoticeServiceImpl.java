package jxc.emsclient.ems.service.impl;

import jxc.emsclient.ems.command.stockOut.CreateOrderCommand;
import jxc.emsclient.ems.command.stockOut.CreateStockOutCommand;
import jxc.emsclient.ems.command.stockOut.RemoveAllStockOutCommand;
import jxc.emsclient.ems.dto.response.CommonResponseDTO;
import jxc.emsclient.ems.dto.stockOut.WmsOrderCancelNoticeDTO;
import jxc.emsclient.ems.dto.stockOut.WmsOrderNoticeDTO;
import jxc.emsclient.ems.dto.stockOut.WmsStockOutNoticeDTO;
import jxc.emsclient.ems.service.WmsOutNoticeService;
import jxc.emsclient.ems.util.EMSConstants;
import jxc.emsclient.ems.util.HttpPost;
import jxc.emsclient.ems.util.XmlUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsOutNoticeService")
@Transactional
public class WmsOutNoticeServiceImpl implements WmsOutNoticeService {


	@Override
	public CommonResponseDTO createOrderNotice(CreateOrderCommand createOrderCommand) {
		WmsOrderNoticeDTO wmsOrderNotice=new WmsOrderNoticeDTO();
		wmsOrderNotice.setWarehouse_code(createOrderCommand.getWarehouse_code());
		wmsOrderNotice.setOwner_code(createOrderCommand.getOwner_code());
		wmsOrderNotice.setOrder_id(createOrderCommand.getOrder_id());
		wmsOrderNotice.setOrder_type_code(createOrderCommand.getOrder_type_code());
		wmsOrderNotice.setOrder_type_name(createOrderCommand.getOrder_type_name());
	
		wmsOrderNotice.setConsignee(createOrderCommand.getOrderReceiveInfo().getConsignee());
		wmsOrderNotice.setAddress(createOrderCommand.getOrderReceiveInfo().getAddress());
		wmsOrderNotice.setPost_Code(createOrderCommand.getOrderReceiveInfo().getPost_Code());
		wmsOrderNotice.setPhone(createOrderCommand.getOrderReceiveInfo().getPhone());
		wmsOrderNotice.setMobile(createOrderCommand.getOrderReceiveInfo().getMobile());
		wmsOrderNotice.setReceiver_code(createOrderCommand.getOrderReceiveInfo().getReceiver_code());
		wmsOrderNotice.setReceiver_name(createOrderCommand.getOrderReceiveInfo().getReceiver_name());
		wmsOrderNotice.setProv(createOrderCommand.getOrderReceiveInfo().getProv());
		wmsOrderNotice.setCity(createOrderCommand.getOrderReceiveInfo().getCity());
		wmsOrderNotice.setDistrict(createOrderCommand.getOrderReceiveInfo().getDistrict());
		
		wmsOrderNotice.setDetails(createOrderCommand.getStockOutProductList());
		wmsOrderNotice.setCount(createOrderCommand.getCount());
		wmsOrderNotice.setSku_count(createOrderCommand.getSku_count());
		wmsOrderNotice.setRemark(createOrderCommand.getRemark());
		
		String content=XmlUtil.toXml(wmsOrderNotice);
		CommonResponseDTO commonResponse= HttpPost.post(content, EMSConstants.OrderNotice);
		
		return commonResponse;
	}

	@Override
	public CommonResponseDTO createStockOutNotice(CreateStockOutCommand createStockOutCommand) {
		WmsStockOutNoticeDTO wmsStockOutNotice=new WmsStockOutNoticeDTO();
		wmsStockOutNotice.setWarehouse_code(createStockOutCommand.getWarehouse_code());
		wmsStockOutNotice.setOwner_code(createStockOutCommand.getOwner_code());
		wmsStockOutNotice.setOrder_id(createStockOutCommand.getOrder_id());
		wmsStockOutNotice.setOrder_type_code(createStockOutCommand.getOrder_type_code());
		wmsStockOutNotice.setOrder_type_name(createStockOutCommand.getOrder_type_name());

		wmsStockOutNotice.setConsignee(createStockOutCommand.getOrderReceiveInfo().getConsignee());
		wmsStockOutNotice.setAddress(createStockOutCommand.getOrderReceiveInfo().getAddress());
		wmsStockOutNotice.setPost_Code(createStockOutCommand.getOrderReceiveInfo().getPost_Code());
		wmsStockOutNotice.setPhone(createStockOutCommand.getOrderReceiveInfo().getPhone());
		wmsStockOutNotice.setMobile(createStockOutCommand.getOrderReceiveInfo().getMobile());
		wmsStockOutNotice.setReceiver_code(createStockOutCommand.getOrderReceiveInfo().getReceiver_code());
		wmsStockOutNotice.setReceiver_name(createStockOutCommand.getOrderReceiveInfo().getReceiver_name());
		wmsStockOutNotice.setProv(createStockOutCommand.getOrderReceiveInfo().getProv());
		wmsStockOutNotice.setCity(createStockOutCommand.getOrderReceiveInfo().getCity());
		wmsStockOutNotice.setDistrict(createStockOutCommand.getOrderReceiveInfo().getDistrict());
		
		wmsStockOutNotice.setDetails(createStockOutCommand.getStockOutProductList());
		wmsStockOutNotice.setCount(createStockOutCommand.getCount());
		wmsStockOutNotice.setSku_count(createStockOutCommand.getSku_count());
		wmsStockOutNotice.setRemark(createStockOutCommand.getRemark());
		
		String content=XmlUtil.toXml(wmsStockOutNotice);
		CommonResponseDTO commonResponse= HttpPost.post(content, EMSConstants.StockOutNotice);
		
		return commonResponse;
	}

	@Override
	public CommonResponseDTO removeAllStockOutNotice(RemoveAllStockOutCommand removeAllStockOutCommand) {
		WmsOrderCancelNoticeDTO wmsOrderCancelNotice=new WmsOrderCancelNoticeDTO();
		wmsOrderCancelNotice.setOwner_code(removeAllStockOutCommand.getOwner_code());
		wmsOrderCancelNotice.setWarehouse_code(removeAllStockOutCommand.getWarehouse_code());
		wmsOrderCancelNotice.setOrder_id(removeAllStockOutCommand.getOrder_id());
		
		String content=XmlUtil.toXml(wmsOrderCancelNotice);
		CommonResponseDTO commonResponse= HttpPost.post(content, EMSConstants.OrderCancelNotice);
		
		return commonResponse;
	}



}
