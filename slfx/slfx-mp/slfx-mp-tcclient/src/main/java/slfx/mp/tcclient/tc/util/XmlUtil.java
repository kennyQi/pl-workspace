package slfx.mp.tcclient.tc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import slfx.mp.tcclient.tc.pojo.Param;
import slfx.mp.tcclient.tc.pojo.Request;
import slfx.mp.tcclient.tc.pojo.ResponseHead;
import slfx.mp.tcclient.tc.pojo.Result;



/**
 * 
 * @Description: Xml生成解析工具
 * @author zhangqy
 */
@SuppressWarnings({"unchecked", "rawtypes","deprecation"})
public class XmlUtil {

	/**
	 * 生成xml文档//
	 * 
	 * @param request
	 * @return
	 */
	private static Document generateDocument(Request request) {

		Document document = DocumentHelper.createDocument();
		// 添加元素 request
		Element requestElement = document.addElement("request");

		// 添加元素 head
		Element headElement = requestElement.addElement("header");


		// 添加元素 param
		Element bodyElement = requestElement.addElement("body");
		// 反射处理head
		
		Field[] heads = request.getHeader().getClass().getDeclaredFields();
		//生成头部信息
		callHead(headElement,request,heads);
		// 反射处理param
		callField(bodyElement,request.getParam(),true);
		
		return document;
	}
	/**
	 * 生成对应的头部信息
	 */
	public static void callHead(Element headElement,Request request,Field[] heads){
		for (Field field : heads) {
			Element fieldElement = headElement.addElement(field.getName());
			try {
				//判断返回类型如果是list则生成多层
				Method m1 = request.getHeader().getClass().getMethod("get" +field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
				Type type=m1.getGenericReturnType();
				if(type==String.class){
					Method m = request.getHeader().getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
					Object obj = m.invoke(request.getHeader());
					fieldElement.setText(obj == null ? "" : (String) obj);
				}else{
					String classStr=type.toString().replaceAll("class ", "");
					if(List.class.isAssignableFrom(Class.forName(classStr.replaceAll("<[\\w\\.]{1,}>", "")))){
						String subClassStr=classStr.replaceAll("[\\w\\.]{1,}<", "").replaceAll(">[\\w\\.]*", "");
						Element subElement=fieldElement.addElement(subClassStr.substring(subClassStr.lastIndexOf(".")+1, subClassStr.length()));
						Field[] fs=Class.forName(subClassStr).getClass().getDeclaredFields();
						callHead(subElement,request,fs);
					}
				}
				
				
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public static void callField(Element bodyElement,Object object,boolean flagg){
		//回调结束
		if((!(object instanceof Param))&&flagg){
			return;
		}
		Field[] params =object.getClass().getDeclaredFields();	
		for (Field field : params) {
			boolean flag=Modifier.isStatic(field.getModifiers());
			//如果是静态继续下一个属性
			if(flag){
				continue;
			}
			
			try {
				Method m =object.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
				Object obj = m.invoke(object);
				System.out.println(obj);
				if(obj != null){
					Element fieldElement = bodyElement.addElement(field.getName());
					if(obj instanceof String){
						fieldElement.setText(obj == null ? "" : (String) obj);	
					}else{
						Type type=m.getGenericReturnType();
						String classStr=type.toString().replaceAll("class ", "");
						if(List.class.isAssignableFrom(Class.forName(classStr.replaceAll("<[\\w\\.]{1,}>", "")))){
							String subClassStr=classStr.replaceAll("[\\w\\.]{1,}<", "").replaceAll(">[\\w\\.]*", "");
							List ojblist=(List) obj;
							for(int i=0;i<ojblist.size();i++){
								Element subElement=fieldElement.addElement(subClassStr.substring(subClassStr.lastIndexOf(".")+1, subClassStr.length()));
								callField(subElement,ojblist.get(i),false);
							}
						}else{
							fieldElement.setText(obj == null ? "" :  obj.toString());
						}
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		Object obj=null;
		try {
			obj=object.getClass().getSuperclass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		callField(bodyElement,obj,true);
	}
	/**
	 * 生成xml字符串
	 */
	public static String generateXmlString(Request request) {
		Document document = generateDocument(request);
//		return document.asXML();
		return formatXML(document, "utf-8");
	}

	/**
	 * 格式化XML文档
	 * 
	 * @param document
	 *            xml文档
	 * @param charset
	 *            字符串的编码
	 * @return 格式化后XML字符串
	 */
	public static String formatXML(Document document, String charset) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		format.setIndent(false);
		format.setNewlines(false);
		format.setNewLineAfterDeclaration(false);
		StringWriter sw = new StringWriter();
		XMLWriter xmlWriter = new StandaloneWriter(sw, format);
		try {
			xmlWriter.write(document);
			xmlWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (xmlWriter != null) {
					xmlWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sw.toString();
	}

	/**
	 * 解析xml字符串
	 * 
	 * @param xmlString
	 * @return
	 * @throws DocumentException
	 */
	public static void analyzeXmlString(Result result,ResponseHead head, String xmlString,boolean unlist) {
		System.out.println(xmlString);
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
			document = saxReader.read(byteArrayInputStream);

			// 获取根元素response
			Element responseElement = document.getRootElement();

			// 获取response元素下的子元素
			Iterator<Element> oneLevelElementItor = responseElement.elementIterator();
			while (oneLevelElementItor.hasNext()) {
				Element e1 = oneLevelElementItor.next();
				System.out.println("response子元素：" + e1.getName());
				// 获取head元素下的子元素
				Iterator<Element> headElementItor = e1.elementIterator();
				if ("header".equals(e1.getName())) {
					while (headElementItor.hasNext()) {
						Element e2 = headElementItor.next();
						System.out.println("head子元素：" + e2.getName() + ":" + e2.getText());
						try {
							Method m= head.getClass().getMethod("set" + e2.getName().substring(0, 1).toUpperCase() + e2.getName().substring(1), String.class);
							m.invoke(head, new String(e2.getText().getBytes()));
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					
					}
				}
				// 反射处理result
				// 获取result下的子元素
				if ("body".equals(e1.getName())) {
					List<Element> elist=e1.elements();
					
					for(int i=0;i<elist.size();i++){
						Element e=elist.get(i);
						callResult(result,e,0,false,unlist);
					}
					
				}

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
	}
	
	public static <T> T callResult(T result,Element e1,Integer count,boolean isObj,boolean unlist){
		Iterator<Attribute> resultAttributeItor=e1.attributeIterator();
		//遍历属性，属性肯定是字段
		while (resultAttributeItor.hasNext()) {
			Attribute e3 = resultAttributeItor.next();
			System.out.println("result属性：" + e3.getName() + ":" + e3.getText());
			try {
				Method m1 = result.getClass().getMethod("get" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1));
				Type type=m1.getGenericReturnType();
				Method m =null;
				if(type==Integer.class){
					m= result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Integer.class);
				}else if(type==Double.class){
					m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Double.class);
				}else if(type==Boolean.class){
					m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Boolean.class);
				}else{
					 m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), String.class);
				}
				//Method m = result.getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), String.class);
				if(type==Integer.class){
					m.invoke(result,new Integer(new String(e3.getText().getBytes())) );	
				}else if(type==Double.class){
					m.invoke(result,new Double(new String(e3.getText().getBytes())));
				}else if(type==Boolean.class){
					m.invoke(result,new Boolean(new String(e3.getText().getBytes())));
				}else if(type==Date.class){
					m.invoke(result,new Date(new String(e3.getText().getBytes())));
				}else{
					m.invoke(result, new String(e3.getText().getBytes()));	
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}
		Iterator<Element> resultElementItor =e1.elementIterator();
		// 反射处理result
		//遍历元素
		//标识是否需要新增一个list
		boolean flag=true;
		String flagStr="";
		boolean hasNext=false;
		while (resultElementItor.hasNext()) {
			hasNext=true;
			Element e3 = resultElementItor.next();
			if(flagStr.equals(e3.getName())){
				flag=false;
				flagStr=e3.getName();
			}else{
				flag=true;
				flagStr=e3.getName();
			}
			System.out.println("result子元素：" + e3.getName() + ":" + e3.getText());
			//判断是否是list
			try {
				Method m1 = result.getClass().getMethod("get" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1));
				Type type=m1.getGenericReturnType();
				Method m =null;
				String classStr=type.toString().replaceAll("class ", "");
				if(List.class.isAssignableFrom(Class.forName(classStr.replaceAll("<[\\w\\.]{1,}>", "")))){//表示是list，递归调用
					String subClassStr=classStr.replaceAll("[\\w\\.]{1,}<", "").replaceAll(">[\\w\\.]*", "");
					if(flag){//list第一次处理
						List list=new ArrayList();	
						m= result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), List.class);
						m.invoke(result, list);
					}
					m= result.getClass().getMethod("get" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1));
					List list=(List) m.invoke(result);
					Object obj=Class.forName(subClassStr).newInstance();
					//List<Element> elist=e3.elements();
					//遍历回调子元素
					//for(int i=0;i<elist.size();i++){
					if(isObj){
						//判断对应的类名有没有对应的方法名
						Method[] methods=Class.forName(subClassStr).getDeclaredMethods();
						for(Method md:methods){
							if(md.getName().equals("get"+e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1))){
								m =Class.forName(subClassStr).getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), String.class);
								m.invoke(obj, new String(e3.getText().getBytes()));	
							}
							list.add(callResult(obj,e3,1,false,true));
						}
						
						//count=1;
					}else{
						Method m11 = result.getClass().getMethod("get" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1));
						Type type11=m11.getGenericReturnType();
						System.out.println(type.toString().replaceAll("class ", ""));
						System.out.println(unlist);
						if(unlist&&List.class.isAssignableFrom(Class.forName(type11.toString().replaceAll("class ", "").replaceAll("<[\\w\\.]{1,}>", "")))){
							List<Element> elist=e3.elements();
							//遍历回调子元素
							for(int i=0;i<elist.size();i++){
								if(i!=0){
									obj=Class.forName(subClassStr).newInstance();
								}
								list.add(callResult(obj,elist.get(i),count,false,true));
							}
						}else{
							list.add(callResult(obj,e3,1,false,true));
						}
					}
					//}
					
				}else{
					//不是list对象直接赋值
					setValue(type,result,e3,m,classStr,flag);
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} 

		}
		// 单个属性
		if (!hasNext) {
			char[] cs = e1.getName().toCharArray();
			cs[0] = Character.toUpperCase(cs[0]);
			String getName = "get" + String.valueOf(cs);
			String setName = "set" + String.valueOf(cs);
			try {
				Method m1 = result.getClass().getMethod(getName);
				Type type = m1.getGenericReturnType();
				Method m = null;
				if (type == Integer.class) {
					m = result.getClass().getMethod(setName, Integer.class);
					m.invoke(result, new Integer(new String(e1.getText().getBytes())));
				} else if (type == Double.class) {
					m = result.getClass().getMethod(setName, Double.class);
					m.invoke(result, new Double(new String(e1.getText().getBytes())));
				} else if (type == Boolean.class) {
					m = result.getClass().getMethod(setName, Boolean.class);
					m.invoke(result, new Boolean(new String(e1.getText().getBytes())));
				} else {
					m = result.getClass().getMethod(setName, String.class);
					m.invoke(result, new String(e1.getText().getBytes()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;

	}
	
	public static  <T> T setValue(Type type,T result,Element e3,Method m,String classStr,boolean flag){
		try {
			if(type==Integer.class){
				m= result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Integer.class);
				String intstr=new String(e3.getText().getBytes());
				m.invoke(result,new Integer("".equals(intstr)?"0":intstr) );	
			}else if(type==Double.class){
				m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Double.class);
				String doublestr=new String(e3.getText().getBytes());
				m.invoke(result,new Double("".equals(doublestr)?"0":doublestr));
			}else if(type==Boolean.class){
				m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Double.class);
				String doublestr=new String(e3.getText().getBytes());
				m.invoke(result,new Boolean("".equals(doublestr)?"false":doublestr));
			}else if(type==Date.class){
				m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Date.class);
				m.invoke(result, new Date(new String(e3.getText().getBytes())));
			}else if(type==String.class){
				m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), String.class);
				m.invoke(result, new String(e3.getText().getBytes()));	
			}else{
				m = result.getClass().getMethod("set" + e3.getName().substring(0, 1).toUpperCase() + e3.getName().substring(1), Class.forName(classStr));
				Object obj1=Class.forName(classStr).newInstance();
				m.invoke(result,callResult(obj1,e3,1,true,true));
				//调用这个对象的所有属性的set方法
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	class A{
		private String name;
		private List values;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<String> getValues() {
			return values;
		}
		public void setValues(List<String> values) {
			this.values = values;
		}
		
	}
	class B<T> extends A{
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String s="";
		try {
			System.out.println(List.class.isAssignableFrom(Class.forName("java.lang.String")));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		XmlUtil xu=new XmlUtil();
//		A a=xu.new A();
//		B b=xu.new B();
//		System.out.println(a.getClass().isAssignableFrom(b.getClass()));
//		try {
//			Method m1=a.getClass().getMethod("getName");
//			Method m2=a.getClass().getMethod("getValues");
//			Integer aas=1;
//			String s="java.util.List<java.lang.String>";
//			System.out.println(m1.getGenericReturnType().toString().equals("java.util.List<java.lang.String>"));
//			System.out.println(s.replaceAll("<[\\w\\.]{1,}>", ""));
//			System.out.println((s.replaceAll("[\\w\\.]{1,}<", "")).replaceAll(">[\\w\\.]*", ""));
//			System.out.println(List.class.isAssignableFrom(Class.forName(m2.getGenericReturnType().toString().replaceAll("<[\\w\\.]{1,}>", ""))));
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// String xmaaal = "<Response><Head><ServerDateTime>2013-12-23 18:01:37</ServerDateTime></Head><Result><ReturnCode>xx</ReturnCode><ReturnMsg>卧槽</ReturnMsg></Result></Response>";
		// analyzeXmlString(xmaaal);
	}
}
