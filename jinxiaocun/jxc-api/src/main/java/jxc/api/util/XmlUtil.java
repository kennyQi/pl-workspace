package jxc.api.util;


import hg.pojo.dto.ems.CommonResponseDTO;
import hg.pojo.dto.ems.DeliveryDTO;
import hg.pojo.dto.ems.DeliveryProductDTO;
import hg.pojo.dto.ems.EmsTraceDTO;
import hg.pojo.dto.ems.StockInConfirmProductDTO;
import hg.pojo.dto.ems.StockOutConfirmProductDTO;
import hg.pojo.dto.ems.TraceDTO;
import hg.pojo.dto.ems.WmsOrderStatusUpdateDTO;
import hg.pojo.dto.ems.WmsStockInConfirmDTO;
import hg.pojo.dto.ems.WmsStockOutConfirmDTO;

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
		xstream.alias("detail", StockOutConfirmProductDTO.class);
		xstream.alias("RequestReceiveInfo", WmsStockInConfirmDTO.class);
		xstream.alias("RequestConsignInfo", WmsStockOutConfirmDTO.class);
		xstream.alias("RequestOrderStatusInfo", WmsOrderStatusUpdateDTO.class);
		xstream.alias("RequestEmsInfo", EmsTraceDTO.class);
		xstream.alias("ship_detail", DeliveryDTO.class);
		xstream.alias("sku_detail", DeliveryProductDTO.class);
		xstream.alias("row", TraceDTO.class);
		xstream.alias("RequestEmsInfo", EmsTraceDTO.class);
		xstream.alias("detail", StockInConfirmProductDTO.class);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

}
