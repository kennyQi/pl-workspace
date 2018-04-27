package plfx.yxgjclient.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	public static boolean stringToFile(File file, String content) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedOutputStream bufferedOutputStream = null;
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			bufferedOutputStream.write(content.getBytes("UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static boolean stringToFile(String fileName, String content) {
		return stringToFile(new File(fileName), content);
	}

	/**
	 * 读取文件获取string
	 */
	public static String stringFromFile(String fileName) {
		File sourcefFile = new File(fileName);
		if (!sourcefFile.exists()) {
			System.err.println("文件" + sourcefFile.getAbsolutePath() + "不存在");
			return null;
		}
		BufferedReader bufferedReader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			FileReader reader = new FileReader(sourcefFile);
			String temp = null;
			bufferedReader = new BufferedReader(reader);
			while ((temp = bufferedReader.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					System.err.println("关闭reader失败");
				}
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		String fileName = "c:res.xml";
		String content = "dfsaghiofsadutg 价格ioraitjugrate人哇突然啊他果然";
		FileUtil.stringToFile(fileName, content);
	}
}