/**
 * @文件名称：SaveObjectUtil.java
 * @类路径：hgtech.jf.entity.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月14日下午4:46:58
 */
package hgtech.jf.entity.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：使用json格式保存 java 对象.在json格式复杂时，容易出错。
 * @类修改者：
 * @修改日期：2014年10月14日下午4:46:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月14日下午4:46:58
 *
 */
public class SaveObjectUtilJsonImp {
	/**
	 * @FieldsMAX:对象最大限制的字节数
	 */
	public static final int MAX = 10*1024*1024;

	public static void save(File file, Serializable o){
		try {
			FileOutputStream os =  (new FileOutputStream(file));
			byte[] bytes = JSONObject.toJSONString(o,SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName, SerializerFeature.WriteDateUseDateFormat).getBytes("utf-8");
			if(bytes.length>MAX)
				throw new RuntimeException("对象最大限制的字节数: "+MAX);
			os.write(bytes);
			os.flush();
			os.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param Clazz 
	 * 
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月16日下午1:37:09
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@return
	 * @return:Object or null 如果文件不存在
	 * @throws
	 */
	public static Object read(File file, Class Clazz){
		try {
			if(!file.exists())
				return null;
			FileInputStream os = (new FileInputStream(file));
			byte[] b=new byte[MAX];//10M
			String s  ;
			int l=os.read(b);
			s=new String(b,0,l,"utf-8");
			Object o=JSONObject.parseObject(s,Clazz);
			os.close();
			return o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		Object o=new Date();
		System.out.println(JSONObject.toJSONString(o, SerializerFeature.WriteClassName));
	}
	
}
