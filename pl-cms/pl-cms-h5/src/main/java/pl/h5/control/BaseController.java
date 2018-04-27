package pl.h5.control;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
/**
 * @类功能说明：基础Controller
 * @类修改者：
 * @修改日期：2015年3月11日下午4:07:04
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月11日下午4:07:04
 */
public class BaseController {
	/**
	 * 自动将yyyy-MM-dd格式的参数转换成Date型参数
	 * @param binder
	 */
	@InitBinder 
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false)); 
	}
	/**
	 * 验证是否是微信用户
	 * @param request
	 * @return
	 */
	protected boolean isWCBrowser(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent").toLowerCase();
		if (agent.indexOf("micromessenger") > -1) {
			return true;
		}
		return false;
	}
	/**
	 * 修饰日志信息标题
	 * @param title
	 * @return
	 */
	protected String newHeader(String title){
		return "<<---------------移动端--"+title+"--------------->>\r\n";
	}
	protected String newLog(String title, String head, String msg){
		StringBuilder log = new StringBuilder();
		log.append("<<---------------移动端--"+title+"--------------\r\n\n\n");
		log.append("\t\t\t\t\t\t------" + head + "------\r\n\n");
		log.append(msg+"\r\n\n");
		log.append("\n\n\t\t\t\t\t\t---------------移动端--"+title+"-------------->>\r\n");
		return log.toString();
	}
	
	protected String printMsg(String head,String msg){
		StringBuilder log = new StringBuilder();
		log.append("\t\t\t\t\t\t------" + head + "------\r\n\n");
		log.append(msg+"\r\n\n");
		return log.toString();
	}
}
