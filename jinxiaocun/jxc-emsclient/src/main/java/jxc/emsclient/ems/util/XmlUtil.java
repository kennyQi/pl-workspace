package jxc.emsclient.ems.util;


import jxc.emsclient.ems.dto.response.CommonResponseDTO;
import jxc.emsclient.ems.dto.stockIn.StockInProductDTO;
import jxc.emsclient.ems.dto.stockIn.WmsInstoreCancelNoticeDTO;
import jxc.emsclient.ems.dto.stockIn.WmsStockInNoticeDTO;
import jxc.emsclient.ems.dto.stockOut.StockOutProductDTO;
import jxc.emsclient.ems.dto.stockOut.WmsOrderCancelNoticeDTO;
import jxc.emsclient.ems.dto.stockOut.WmsOrderNoticeDTO;
import jxc.emsclient.ems.dto.stockOut.WmsStockOutNoticeDTO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XmlUtil {
	
	
	/**
	 * java转xml
	 * @param obj
	 * @return
	 */
	public static String toXml(Object obj){
		
		//解决单下划线变为双下划线问题
		XStream xstream=new XStream(new XppDriver(new NoNameCoder()));
		
		xstream.alias("RequestASN", WmsStockInNoticeDTO.class);
		xstream.alias("RequestCancelOrder", WmsInstoreCancelNoticeDTO.class);
		xstream.alias("detail", StockInProductDTO.class);
		xstream.alias("RequestOrder", WmsOrderNoticeDTO.class);
		xstream.alias("detail", StockOutProductDTO.class);
		xstream.alias("RequestOrder", WmsStockOutNoticeDTO.class);
		xstream.alias("RequestCancelOrder", WmsOrderCancelNoticeDTO.class);
		xstream.alias("Response", CommonResponseDTO.class);
		//xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
		return xstream.toXML(obj);
	}
	
	/**
	 * xml转java对象
	 * @param xmlStr
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xmlStr, Class<T> cls) {

		XStream xstream = new XStream(new DomDriver());
		xstream.alias("Response", CommonResponseDTO.class);
//		xstream.alias("detail", StockOutConfirmProductDTO.class);
//		xstream.alias("RequestReceiveInfo", WmsStockInConfirmDTO.class);
//		xstream.alias("RequestConsignInfo", WmsStockOutConfirmDTO.class);
//		xstream.alias("RequestOrderStatusInfo", WmsOrderStatusUpdateDTO.class);
//		xstream.alias("RequestEmsInfo", EmsTraceDTO.class);
//		xstream.alias("ship_detail", DeliveryDTO.class);
//		xstream.alias("sku_detail", DeliveryProductDTO.class);
//		xstream.alias("row", TraceDTO.class);
//		xstream.alias("RequestEmsInfo", EmsTraceDTO.class);
//		xstream.alias("detail", StockInConfirmProductDTO.class);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

}
