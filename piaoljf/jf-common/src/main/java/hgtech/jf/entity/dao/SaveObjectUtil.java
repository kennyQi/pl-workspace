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

/**
 * @类功能说明：持久化对象的工具
 * @类修改者：
 * @修改日期：2014年10月14日下午4:46:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月14日下午4:46:58
 *
 */
public class SaveObjectUtil {
	public static void save(File file, Serializable o){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(o);
			os.flush();
			os.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
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
	public static Object read(File file){
		try {
			if(!file.exists())
				return null;
			ObjectInputStream os = new ObjectInputStream(new FileInputStream(file));
			Object o = os.readObject();
			os.close();
			return o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
}
