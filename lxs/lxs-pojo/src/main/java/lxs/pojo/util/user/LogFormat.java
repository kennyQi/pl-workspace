package lxs.pojo.util.user;

/**
 * 格式化日志工具
 * @author Administrator
 *
 */
public class LogFormat {
	public static String log(String title,String msg){
		StringBuilder log = new StringBuilder();
		log.append("\r\n\n\n");
		log.append("\t\t\t\t\t\t------" + title + "------\r\n\n");
		log.append(msg+"\r\n\n");
		log.append("\n\n\t\t\t\t\t\t\r\n");
		return log.toString();
	}
}
