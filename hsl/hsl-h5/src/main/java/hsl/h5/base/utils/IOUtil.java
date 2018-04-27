package hsl.h5.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * IO处理工具
 * @author 胡永伟
 */
public final class IOUtil {
	
	private IOUtil() {}
	
	/**
	 * 流输出
	 * @param in
	 * @param outs
	 * @throws IOException
	 */
	public static void transfer(InputStream in, 
			OutputStream... outs) throws IOException {
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = in.read(buf)) != -1) {
			for (OutputStream out : outs) {
				out.write(buf, 0, len);
			}
		}
	}
	
	/**
	 * 将数据流转换成字符串
	 * @param in
	 * @return
	 */
	public static String stringify(InputStream in) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(
					new InputStreamReader(in, "utf-8"));
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(reader);
		}
		return sb.toString();
	}
	
	/**
	 * 关闭指定的InputStream
	 * @param ins
	 */
	public static void close(InputStream... ins) {
		if (ins != null && ins.length > 0) {
			for (InputStream in : ins) {
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						;
					}
					in = null;
				}
			}
		}
	}
	
	/**
	 * 关闭指定的OutputStream
	 * @param outs
	 */
	public static void close(OutputStream... outs) {
		if (outs != null && outs.length > 0) {
			for (OutputStream out : outs) {
				if(out != null) {
					try {
						out.close();
					} catch (IOException e) {
						;
					}
					out = null;
				}
			}
		}
	}
	
	/**
	 * 关闭指定的Reader
	 * @param rs
	 */
	public static void close(Reader... rs) {
		if (rs != null && rs.length > 0) {
			for (Reader r : rs) {
				if(r != null) {
					try {
						r.close();
					} catch (IOException e) {
						;
					}
					r = null;
				}
			}
		}
	}
	
	/**
	 * 关闭指定的Writer
	 * @param ws
	 */
	public static void close(Writer... ws) {
		if (ws != null && ws.length > 0) {
			for (Writer w : ws) {
				if(w != null) {
					try {
						w.close();
					} catch (IOException e) {
						;
					}
					w = null;
				}
			}
		}
	}
	
}
