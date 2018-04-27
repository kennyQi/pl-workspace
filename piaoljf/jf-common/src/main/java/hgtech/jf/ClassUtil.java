/**
 * @文件名称：ClassUtil.java
 * @类路径：hgtech.jf
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年9月29日下午4:55:04
 */
package hgtech.jf;


/**
 * @类功能说明：类的一些工具：如从文件装载class，
 * @类修改者：
 * @修改日期：2014年9月29日下午4:55:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年9月29日下午4:55:04
 *
 */
public class ClassUtil {
	/**
	 * 
	 * @方法功能说明：动态从一个文件中获取Class
	 * @修改者名字：xinglj
	 * @修改时间：2014年9月30日上午8:59:45
	 * @修改内容：
	 * @参数：@param path
	 * @参数：@param className
	 * @参数：@return
	 * @参数：@throws ClassNotFoundException
	 * @return:Class
	 * @throws
	 */
	public static Class<?> loadFileClass(String path, String className) throws ClassNotFoundException{
		FileClassLoader loader = new FileClassLoader(path);
		Class<?> c=loader.findClass(className);

		return c;
	}
	

}
