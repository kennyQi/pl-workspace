package plfx.yxgjclient.common.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class NullConverter implements Converter {

//    @SuppressWarnings("rawtypes")  
//    private Class currentType;  
  	private final String packName="plfx.yxgjclient.pojo";
  	
  	@SuppressWarnings("rawtypes")
	public static final ThreadLocal<Class> threadLocal = new ThreadLocal<Class>();
  	
    @SuppressWarnings("rawtypes") 
    @Override
    public boolean canConvert(Class type) {  
    	//只有指定包里的实体才能被转换
//    	threadLocal.set(type);
//        currentType = type;  
        if(type.getPackage().getName()!=null&&type.getPackage().getName().startsWith(packName)){
            return true;  
        } else {  
            return false;  
        }  
    }  
  @Override
    public void marshal(Object source, HierarchicalStreamWriter writer,  
            MarshallingContext context) {  
        try {  
            marshalSuper(source, writer, context, threadLocal.get());  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  
  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    private Object getObj(Class clazz, String nodeName, Object source)  
            throws Exception {  
    	if (source==null) {
			return null;
		}
        Method method = clazz.getMethod("get"  
                + Character  
                        .toUpperCase(nodeName.substring(0, 1).toCharArray()[0])  
                + nodeName.substring(1));  
        Object obj = null;  
        obj = method.invoke(clazz.cast(source), new Object[0]);  
        return obj;  
    }  
  
    @SuppressWarnings({ "rawtypes" })  
    private void objConverter(Object source, HierarchicalStreamWriter writer,  
            MarshallingContext context, Class clazz, String nodeName,  
            Class fieldClazz) throws Exception {  
        Object obj = getObj(clazz, nodeName, source);  
        if (obj==null) {
        	return;
//        	writer.startNode(nodeName); 
//			writer.setValue("");
		}else{
	        writer.startNode(nodeName);  
	        marshalSuper(obj, writer, context, fieldClazz); 
		}
        writer.endNode();  
		
    }  
  
    @SuppressWarnings({ "rawtypes" })  
    private void collectionConverter(Object source,  
            HierarchicalStreamWriter writer, MarshallingContext context,  
            Class clazz, String nodeName, Field field) throws Exception {  
        java.lang.reflect.Type[] types = ((ParameterizedType) field.getGenericType())  
                .getActualTypeArguments();  
        Object obj = getObj(clazz, nodeName, source);  
        if (obj==null) {
        	return;
//        	writer.startNode(nodeName); 
//			writer.setValue("");
		}else{
	        Collection collection = null;  
	        if (field.getType().equals(List.class)) {  
	            collection = (List) obj;  
	        } else if (field.getType().equals(Set.class)) {  
	            collection = (Set) obj;  
	        }  
	        writer.startNode(nodeName);  
	        for (Object object : collection) {  
	            String clazzName = ((Class) types[0]).getSimpleName();  
	            writer.startNode(Character.toLowerCase(clazzName.substring(0, 1)  
	                    .toCharArray()[0]) + clazzName.substring(1));  
	            marshalSuper(object, writer, context, (Class) types[0]);  
	            writer.endNode();  
	        } 
        }
        writer.endNode();  
    }  
  
    @SuppressWarnings({ "rawtypes" })  
    private void basicTypeConverter(Object source,  
            HierarchicalStreamWriter writer, MarshallingContext context,  
            Class clazz, String nodeName) throws Exception {  
        Object obj = getObj(clazz, nodeName, source); 
        if (obj==null) {
//        	return ;
        	writer.startNode(nodeName);  
            writer.setValue("");  
		}else{
	        writer.startNode(nodeName);  
	        writer.setValue(obj.toString());  
		}
        writer.endNode();  
    }  
  
    @SuppressWarnings({ "rawtypes" })  
    private void marshalSuper(Object source, HierarchicalStreamWriter writer,  
            MarshallingContext context, Class clazz) throws Exception {  
        Field fields[] = clazz.getDeclaredFields(); 
        Class cl=clazz.getSuperclass();
        if (YIGJConstant.classList.contains(cl)) {
        	Field superFields[]=clazz.getSuperclass().getDeclaredFields();
        	int fieldSize=fields.length+superFields.length;
        	Field newFields[]=new Field[fieldSize];
        	//合并子类和父类字段
        	for (int i = 0; i < fields.length; i++) {
				newFields[i]=fields[i];
			}
        	for (int i = 0; i < superFields.length; i++) {
				newFields[i+fields.length]=superFields[i];
			}
        	fields=newFields;
		}
        
        
        for (Field field : fields) {  
            String nodeName = field.getName();  
            Class fieldClazz = field.getType();  
            String packName=null;
            //if (clazzNamesList.contains(fieldClazz.getSimpleName())) {  
            if (fieldClazz.getPackage()!=null) {
				packName=fieldClazz.getPackage().getName();
			}
            if(packName!=null&&packName.startsWith(this.packName)){
                objConverter(source, writer, context, clazz, nodeName,  
                        fieldClazz);  
            } else if (Arrays.asList(fieldClazz.getInterfaces()).contains(  
                    Collection.class)) {  
                collectionConverter(source, writer, context, clazz, nodeName,  
                        field);  
            } else {  
                basicTypeConverter(source, writer, context, clazz, nodeName);  
            }  
        }  
    }  
  @Override
    public Object unmarshal(HierarchicalStreamReader reader,  
            UnmarshallingContext context) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  

}  