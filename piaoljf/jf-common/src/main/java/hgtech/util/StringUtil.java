package hgtech.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class StringUtil {

	/**
	 * 将软回车移除
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static String removeSoftReturn(String s) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(s));
		String line;
		StringWriter out = new StringWriter();
		BufferedWriter wr = new BufferedWriter(out);
		StringBuffer bufLine = new StringBuffer();
		while ((line = br.readLine()) != null) {
			
			if(line.length()>0){
				//判断
				if (line.charAt(line.length() - 1) == '\\'
						&& line.charAt(line.length() - 2) != '\\') {
					// 软行 比如，
					// ag\
					// e
					bufLine.append(line.subSequence(0, line.length()-1));//remove the "\"
				} else {
					//硬行
					bufLine.append(line);
					wr.append(bufLine);
					wr.newLine();
					bufLine = new StringBuffer();
				}
			}else{
				//空行
				wr.newLine();
			}
		}
		wr.flush();
		return out.getBuffer().toString();
	}

	public static void main(String[] args) throws IOException {
		String s="prop:name=邢立军\\\r\n,"+
				"age=40\r\n"+
				"say hello\r\n";
		System.out.println(s);
		System.out.println(StringUtil.removeSoftReturn(s));
		
		String string = IOUtils.toString(new FileInputStream("D:\\gitwork1.5\\hg-hjf\\hjf-admin\\src\\main\\resources\\remote-config.json"), "utf-8");
		System.out.println(string);
		System.out.println(removeSoftReturn(string ));
	}
	
	public static boolean isMobile(String mob){
		return mob.length()==11 && (mob.startsWith("13") || mob.startsWith("15") || mob.startsWith("17") || mob.startsWith("18"));
	}
}
