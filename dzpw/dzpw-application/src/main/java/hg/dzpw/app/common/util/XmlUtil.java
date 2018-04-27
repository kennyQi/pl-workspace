package hg.dzpw.app.common.util;

import hg.dzpw.pojo.api.hjb.HJBRegisterRequestDto;

import java.util.Map;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;

/**
 * 报文处理工具类
 * @Time 2015-04-28
 * @author guotx
 * 
 */
public class XmlUtil {
	private static XStream xStream=new XStream();
	private static Logger logger=Logger.getLogger(XmlUtil.class.getName());
	// 判断字符串是否为空
	public static boolean isNull(String str) {
		if (null == str || str.matches("[ ]*") || "null".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取某个标签下的内容(不区分大小写)
	 * 
	 * @param tag
	 * @return
	 */
	public static String getNodeVal(String str, String tag) {

		if (isNull(tag)) {
			return "";
		}

		String statTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";

		int starTagNum = str.indexOf(statTag);
		int endTagNum = str.indexOf(endTag);

		if (starTagNum == -1 || endTagNum == -1) {
			// 考虑可能出现大小写而不匹配的情况
			String strTmp = str.toUpperCase();
			String starTagTmp = statTag.toUpperCase();
			String endTagTmp = endTag.toUpperCase();

			int starTagNumTmp = strTmp.indexOf(starTagTmp);
			int endTagNumTmp = strTmp.lastIndexOf(endTagTmp);

			if (starTagNumTmp == -1 || endTagNumTmp == -1) {
				return "";
			} else {
				return str.substring(starTagNumTmp
						+ starTagTmp.getBytes().length, endTagNumTmp);
			}
		}
		return str.substring(starTagNum + statTag.getBytes().length, endTagNum);
	}

	/**
	 * 替换指定标签中的值
	 * 
	 * @param str
	 *            报文内容
	 * @param tag
	 *            标签名称
	 * @param newValue
	 *            替换内容
	 * @return
	 */
	public static String replaceNodeValue(String str, String tag,
			String newValue) {
		if (isNull(tag)) {
			return str;
		}

		String statTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";

		int starTagNum = str.indexOf(statTag);
		int endTagNum = str.indexOf(endTag);

		if (starTagNum == -1 || endTagNum == -1) {
			// 考虑可能出现大小写而不匹配的情况
			String strTmp = str.toUpperCase();
			String starTagTmp = statTag.toUpperCase();
			String endTagTmp = endTag.toUpperCase();

			int starTagNumTmp = strTmp.indexOf(starTagTmp);
			int endTagNumTmp = strTmp.lastIndexOf(endTagTmp);

			if (starTagNumTmp == -1 || endTagNumTmp == -1) {
				return str;
			} else {
				return str.substring(0, starTagNumTmp
						+ starTagTmp.getBytes().length)
						+ newValue + str.substring(endTagNumTmp);
			}
		}
		return str.substring(0, starTagNum + statTag.getBytes().length)
				+ newValue + str.substring(endTagNum);
	}

	/**
	 * 从xml报文中去掉指定的标签
	 * 
	 * @param str
	 * @param tag
	 * @return
	 */
	public static String removeNode(String str, String tag) {
		if (isNull(tag)) {
			return str;
		}

		String statTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";

		int starTagNum = str.indexOf(statTag);
		int endTagNum = str.indexOf(endTag);

		if (starTagNum == -1 || endTagNum == -1) {
			// 考虑可能出现大小写而不匹配的情况
			String strTmp = str.toUpperCase();
			String starTagTmp = statTag.toUpperCase();
			String endTagTmp = endTag.toUpperCase();

			int starTagNumTmp = strTmp.indexOf(starTagTmp);
			int endTagNumTmp = strTmp.lastIndexOf(endTagTmp);

			if (starTagNumTmp == -1 || endTagNumTmp == -1) {
				return str;
			} else {
				return str.substring(0, starTagNumTmp)
						+ str.substring(endTagNumTmp
								+ endTagTmp.getBytes().length);
			}
		}
		return str.substring(0, starTagNum)
				+ str.substring(endTagNum + endTag.getBytes().length);
	}

	/**
	 * 将Map数据转换为xml格式
	 * 
	 * @param srcMap
	 * @return
	 */
	public static String map2Xml(Map<String, String> srcMap) {
		StringBuffer sb = new StringBuffer();
		for (String key : srcMap.keySet()) {
			sb.append("<" + key + ">" + srcMap.get(key) + "</" + key + ">");
		}
		return sb.toString();
	}

	/**
	 * 将实体解析成xml，并去除根节点
	 * @param object 要转换的实体对象
	 * @return 转换后的xml，去除了根节点对象包路径
	 */
	public static String object2Xml(Object object){
		String xml=xStream.toXML(object);
		int start=xml.indexOf(">")+1;
		int end=xml.lastIndexOf("<");
		
		return xml.substring(start, end).trim();
	}
	
	/**
	 * 将xml转换为实体对象
	 * @param T 要转换的实体对象类型
	 * @param xml 要解析的xml报文
	 * @return 指定类型的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T>T xml2Object(Class<T> T,String xml){
		String className=T.getName();
		xml=removeHead(xml);
		xml="<"+className+">"+xml+"</"+className+">";
		T bean=null;
		try {
			bean = (T) xStream.fromXML(xml);
		} catch (CannotResolveClassException e) {
			logger.info("XML解析成实体出错，xml格式错误：\n"+xml);
			return null;
		}
		return bean;
	}
	public static String removeHead(String xml) {
		if (xml.startsWith("<?xml")) {
			int start=xml.indexOf(">");
			return xml.substring(start+1);
		}
		return xml;
	}

	public static void main(String[] args) {
		HJBRegisterRequestDto dto=new HJBRegisterRequestDto();
		dto.setAddress("北京");
		dto.setCallerIp("192.168.1.1");
		dto.setLoginName("huipu");
		String xmlString=object2Xml(dto);
		//System.out.println(xmlString);
		dto=xml2Object(HJBRegisterRequestDto.class, xmlString);
		HJBRegisterRequestDto.class.getName();
	}
}
