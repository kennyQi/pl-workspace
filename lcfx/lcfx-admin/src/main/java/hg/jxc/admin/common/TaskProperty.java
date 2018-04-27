/**
 * @文件名称：JfProperty.java
 * @类路径：hgtech.jfaddmin
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月15日下午3:31:34
 */
package hg.jxc.admin.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @类功能说明：积分系统属性配置
 * @类修改者：
 * @修改日期：2014年10月15日下午3:31:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月15日下午3:31:34
 * 
 */
public class TaskProperty {
	private static TaskProperty property = new TaskProperty();
	private Properties properties;

	void doinit() {
		InputStream inStream = TaskProperty.class.getResourceAsStream("/task.properties");
		properties = new Properties();
		try {
			if (inStream != null)
				properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public synchronized static Properties getProperties() {
		if (property.properties == null || property.properties.size() == 0)
			property.doinit(); 
		return property.properties;
	}
}
