package hgtech.jfadmin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 解析jar包文件
 * @author xy
 *
 */
public class JarResolve {

	/**
	 * 解析jar包
	 * 
	 * @param jarPath
	 *            上传后的jar包路径
	 * @param jarName
	 *            jar 包名称
	 */
	public List<String> jarParse(String jarPath, String jarName) {
		File f = new File(jarPath);
		List<String> list=new ArrayList<String>();
		System.out.println(f.getParent());
		if (f.exists()) {
			try {
				JarFile jarfile = new JarFile(f);
				Enumeration entryList = jarfile.entries();
				while (entryList.hasMoreElements()) {
					JarEntry jarentry = (JarEntry) entryList.nextElement();
					if (!jarentry.isDirectory()) {
						InputStream in = jarfile.getInputStream(jarentry);
						try {
							File destFile = new File(f.getParent() + "\\"
									+ jarName + "\\" + jarentry.getName());
							list.add(f.getParent() + "\\"+ jarName + "\\" + jarentry.getName());
							if (!destFile.exists()) {
								destFile.getParentFile().mkdirs();
							}
							OutputStream out = new FileOutputStream(destFile);
							try {
								byte[] buffer = new byte[8192];
								int i;
								while ((i = in.read(buffer)) != -1) {
									out.write(buffer, 0, i);
								}
							} finally {
								out.close();
							}
						} finally {
							in.close();
						}
						createPackageFile(
								f.getParent() + "\\" + jarName + "\\",
								jarentry.getName());
					}
				}
			} catch (Exception ioe) {
				//ioe.printStackTrace();
				throw new RuntimeException("文件错误："+ioe.getMessage(), ioe );
			}
		}
		return list;
	}

	/**
	 * 读取jar包文件
	 * @param jarFilePath jar包解析后的路径
	 * @param filename  jar包entry名称
	 */
	private void createPackageFile(String jarFilePath, String filename) {

		File f = new File(jarFilePath + "//" + filename);
		InputStream in = null;
		if (f.exists()) {
			try {
				int javaIndex = filename.indexOf(".java");
				int classIndex = filename.indexOf(".class");
				int readmeIndex = filename.indexOf("README");
				if (javaIndex > 0 || classIndex > 0 || readmeIndex > 0) {
					in = new FileInputStream(f);
					byte[] b = new byte[8192];
					int len = in.read(b);
					System.out.println(new String(b));
					in.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		JarResolve jartest = new JarResolve();
		/*jartest.jarParse(
				"F:\\tomcatTmp\\tempjar\\jarfile\\jf-PiaolSignLogic.jar",
				"jf-PiaolSignLogic");*/
		jartest.jarParse(
				"F:\\jf-PiaolSignLogic.jar",
				"jf-PiaolSignLogic");
	}

}
