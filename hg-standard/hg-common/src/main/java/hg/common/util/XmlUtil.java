package hg.common.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;

import org.apache.poi.ss.formula.functions.T;

/**
 *  xml报文处理工具类
 * @author xinglj
 *
 */
public class XmlUtil {
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
				return str.substring(starTagNumTmp + starTagTmp.getBytes().length, endTagNumTmp);
			}
		}
		return str.substring(starTagNum + statTag.getBytes().length, endTagNum);
	}
	
	/**
	 * 替换指定标签中的值
	 * @param str 报文内容
	 * @param tag 标签名称
	 * @param newValue 替换内容
	 * @return
	 */
	public static String replaceNodeValue(String str, String tag, String newValue) {
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
				return str.substring(0, starTagNumTmp + starTagTmp.getBytes().length) + newValue + str.substring(endTagNumTmp);
			}
		}
		return str.substring(0, starTagNum + statTag.getBytes().length) + newValue + str.substring(endTagNum);
	}
	
	/**
	 * 从xml报文中去掉指定的标签
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
				return str.substring(0, starTagNumTmp) + str.substring(endTagNumTmp + endTagTmp.getBytes().length);
			}
		}
		return str.substring(0, starTagNum) + str.substring(endTagNum + endTag.getBytes().length);
	}
	
	/**
	 * 
	 * @方法功能说明：beanToXML 
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2016年4月25日下午5:31:02
	 * @version：
	 * @修改内容：
	 * @参数：@param bean
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	 public static String beanToXML(Object bean) {  
	  
	        try {  
	            JAXBContext context = JAXBContext.newInstance(bean.getClass());  
	            Marshaller marshaller = context.createMarshaller();  
	            Writer out=new StringWriter();
		    marshaller.marshal(bean, out);
		    return out.toString();
	        } catch (JAXBException e) {
	            throw new RuntimeException(e);
	        }  
	}  
	    /**
	     *   
	     * @方法功能说明：
	     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	     * @修改时间：2016年4月25日下午5:31:09
	     * @version：
	     * @修改内容：
	     * @参数：@param clz
	     * @参数：@param is
	     * @参数：@return
	     * @return:Object
	     * @throws
	     */
	    public static Object XMLStringToBean(Class  clz,String is){  
	        try {  
	            JAXBContext context = JAXBContext.newInstance(clz);  
	            Unmarshaller unmarshaller = context.createUnmarshaller();  
	            return unmarshaller.unmarshal(new StringReader(is));  
	        } catch (JAXBException e) {  
	            throw new RuntimeException(e);	
	        }
	          
	    }
	    
	    
}
