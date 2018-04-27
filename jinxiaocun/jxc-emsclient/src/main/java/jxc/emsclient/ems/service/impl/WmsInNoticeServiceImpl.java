package jxc.emsclient.ems.service.impl;

import jxc.emsclient.ems.command.stockIn.CreateStockInCommand;
import jxc.emsclient.ems.command.stockIn.RemoveStockInCommand;
import jxc.emsclient.ems.dto.response.CommonResponseDTO;
import jxc.emsclient.ems.dto.stockIn.WmsInstoreCancelNoticeDTO;
import jxc.emsclient.ems.dto.stockIn.WmsStockInNoticeDTO;
import jxc.emsclient.ems.service.WmsInNoticeService;
import jxc.emsclient.ems.util.EMSConstants;
import jxc.emsclient.ems.util.HttpPost;
import jxc.emsclient.ems.util.XmlUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsInNoticeService")
@Transactional
public class WmsInNoticeServiceImpl  implements WmsInNoticeService {
	

	@Override
	public CommonResponseDTO createStockInNotice(CreateStockInCommand createStockInCommand) {
		WmsStockInNoticeDTO wmsStockInNotice=new WmsStockInNoticeDTO();
		wmsStockInNotice.setWarehouse_code(createStockInCommand.getWarehouse_code());
		wmsStockInNotice.setOwner_code(createStockInCommand.getOwner_code());
		wmsStockInNotice.setAsn_id(createStockInCommand.getAsn_id());
		wmsStockInNotice.setOrder_type_code(createStockInCommand.getOrder_type_code());
		wmsStockInNotice.setOrder_type_name(createStockInCommand.getOrder_type_name());
		wmsStockInNotice.setCount(createStockInCommand.getCount());
		wmsStockInNotice.setSku_count(createStockInCommand.getSku_count());
		wmsStockInNotice.setRemark(createStockInCommand.getRemark());
		wmsStockInNotice.setLogisticProviderId(createStockInCommand.getLogisticProviderId());
		wmsStockInNotice.setLogisticProviderName(createStockInCommand.getLogisticProviderName());
		wmsStockInNotice.setPre_arrive_time(createStockInCommand.getPre_arrive_time());
		wmsStockInNotice.setSale_order_id(createStockInCommand.getSale_order_id());
		wmsStockInNotice.setSend_Time(createStockInCommand.getSend_Time());
		wmsStockInNotice.setSender_code(createStockInCommand.getSender_code());
		wmsStockInNotice.setSender_contact(createStockInCommand.getSender_contact());
		wmsStockInNotice.setSender_name(createStockInCommand.getSender_name());
		wmsStockInNotice.setSender_phone(createStockInCommand.getSender_phone());
		wmsStockInNotice.setTms_order_code(createStockInCommand.getTms_order_code());
		wmsStockInNotice.setDetails(createStockInCommand.getStockInProductList());
		String content=XmlUtil.toXml(wmsStockInNotice);
		CommonResponseDTO commonResponse= HttpPost.post(content, EMSConstants.StockInNotice);
		return commonResponse;
	}

	@Override
	public CommonResponseDTO removeStockInNotice(RemoveStockInCommand removeStockInCommand) {
		WmsInstoreCancelNoticeDTO wmsInstoreCancelNotice=new WmsInstoreCancelNoticeDTO();
		wmsInstoreCancelNotice.setWarehouse_code(removeStockInCommand.getWarehouse_code());
		wmsInstoreCancelNotice.setOwner_code(removeStockInCommand.getOwner_code());
		wmsInstoreCancelNotice.setAsn_id(removeStockInCommand.getAsn_id());
		String content=XmlUtil.toXml(wmsInstoreCancelNotice);
		System.out.println(XmlUtil.toXml(wmsInstoreCancelNotice));

		CommonResponseDTO commonResponse=HttpPost.post(content, EMSConstants.InstoreCancelNotice);
		return commonResponse;
	}

}
