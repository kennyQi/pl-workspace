/**
 * @文件名称：MyClassLoader.java
 * @类路径：hgtech.jf
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年9月29日下午5:46:42
 */
package hgtech.jf;


import java.io.File;
import java.io.FileInputStream;

/**从文件中加载一个类
 * @类功能说明：类大小不能超过1M
 * @类修改者：
 * @修改日期：2014年9月29日下午5:46:42
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年9月29日下午5:46:42
 *
 */
public class FileClassLoader extends ClassLoader{
	String path;
	/**
	 * @类名：MyClassLoader.java
	 * @描述：类所在的目录
	 * @@param path
	 */
	public FileClassLoader(String path) {
		super();
		this.path = path;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	@Override
	protected Class<?> findClass(String name)   {
		String file=path+"/"+name.replace('.', '/')+".class";
		if(!new File(file).exists()){
			try {
				return Class.forName(name);
			} catch (ClassNotFoundException e) {
				String x = "class not found under classpath, and class not find for file "+file;
				System.out.println(x);
				throw new RuntimeException(x+"\n"+e.toString(),e.getCause());
			}//system loader
		}
		System.out.println("to load class file from "+file);
		byte[] b;
		int len;
		try {
			FileInputStream fileInputStream =  new FileInputStream(file);
			b = new byte[1024000];//1M
			len = fileInputStream.read(b);
			fileInputStream.close();
		} catch ( Exception e) {
			throw new RuntimeException(e);
		} 
		
		return defineClass(name, b, 0,len);
	}
	
}
