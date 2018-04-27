package plfx.yxgjclient.common.util;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
public class XStreamUtil {
	/**
	 * 使用不同的Converter，解析xml时要使用默认的
	 */
	static XStream toXmlXStream=new XStream(new DomDriver());
	static XStream toObjXStream=new XStream();
	
	static NullConverter nullConverter=new NullConverter();
	static String declearXml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
	static{
		toXmlXStream.autodetectAnnotations(true);
		toXmlXStream.registerConverter(nullConverter);
		toObjXStream.autodetectAnnotations(true);
	}
	/**
	 * xml转为对象
	 * @param T
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToObject(Class<T> T ,String xml){
		toObjXStream.processAnnotations(T);
		return (T)toObjXStream.fromXML(xml);
	}
	/**
	 * 实体对象转为xml
	 * @param obj
	 * @return XML
	 */
	public static String objectToXML(Object obj){
		//添加xml声明头
		String coreXml=toXmlXStream.toXML(obj);
		return declearXml+coreXml;
	}
}
